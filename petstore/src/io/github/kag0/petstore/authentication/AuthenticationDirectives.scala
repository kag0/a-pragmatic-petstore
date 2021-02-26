package io.github.kag0.petstore.authentication

import akka.http.scaladsl.model.headers.{
  Authorization,
  ModeledCustomHeader,
  ModeledCustomHeaderCompanion,
  OAuth2BearerToken
}
import akka.http.scaladsl.server.Directive1
import akka.http.scaladsl.server.Directives._
import io.github.kag0.petstore.errors.InvalidAuthentication
import io.github.kag0.petstore.routing.errorToResponse

class AuthenticationDirectives(authenticationService: AuthenticationService) {
  def requireUserAuthn: Directive1[UserAuthn] = extractAuthn.flatMap {
    case user: UserAuthn => provide(user)
    case _               => complete(InvalidAuthentication)
  }

  def requireApiAuthn: Directive1[ApiKeyAuthn.type] = extractAuthn.flatMap {
    case ApiKeyAuthn => provide(ApiKeyAuthn)
    case _           => complete(InvalidAuthentication)
  }

  def requireUserOrApiAuthn: Directive1[Either[UserAuthn, ApiKeyAuthn.type]] =
    extractAuthn.flatMap {
      case user: UserAuthn => provide(Left(user))
      case ApiKeyAuthn     => provide(Right(ApiKeyAuthn))
      case _               => complete(InvalidAuthentication)
    }

  def extractAuthn: Directive1[CallerAuthn] = {
    val extractUserAuthn = optionalHeaderValuePF {
      case Authorization(OAuth2BearerToken(token)) => token
    }.flatMap(
      _.flatMap(token =>
        authenticationService.validateToken(token).toOption
      ) match {
        case Some(user) => provide(user)
        case None       => reject
      }
    )

    val extractApiAuthn = optionalHeaderValueByName("api_key").flatMap(
      _.flatMap(apiKey =>
        authenticationService.validateApiKey(apiKey).toOption
      ) match {
        case Some(api) => provide(api)
        case None      => reject
      }
    )

    val extract: Directive1[CallerAuthn] =
      extractUserAuthn.or(extractApiAuthn).or(provide(PublicAuthn))

    extract.flatMap(authn =>
      mapRequest(_.addHeader(AuthnSyntheticHeader(authn))).tflatMap(_ =>
        provide(authn)
      )
    )
  }
}

// this header is used to store the caller authn in the request,
// but is never parsed or rendered (aka is a "synthetic" header)
case class AuthnSyntheticHeader(authn: CallerAuthn)
    extends ModeledCustomHeader[AuthnSyntheticHeader] {
  def companion         = AuthnSyntheticHeader
  def value             = ""
  val renderInRequests  = false
  val renderInResponses = false
}

object AuthnSyntheticHeader
    extends ModeledCustomHeaderCompanion[AuthnSyntheticHeader] {
  val name                 = "__syntheticauthn"
  def parse(value: String) = ???
}
