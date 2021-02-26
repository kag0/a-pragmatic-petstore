package io.github.kag0.petstore.authentication

import black.door.jose.json.ninny.JsonSupport._
import black.door.jose.jwk.Jwk
import black.door.jose.jwt.{Claims, Jwt}
import io.github.kag0.ninny.{FromJson, ToAndFromJson, ToJson}
import io.github.kag0.petstore.errors.InvalidAuthentication

import java.time.Instant
import java.time.temporal.ChronoUnit.HOURS

case class UserClaims(sub: Long, scp: Set[String])
object UserClaims {
  implicit val toJson   = ToJson.auto[UserClaims]
  implicit val fromJson = FromJson.auto[UserClaims]
}

class AuthenticationService(tokenKey: Jwk, apiKeys: Set[String]) {
  def validateApiKey(key: String) =
    if (apiKeys.contains(key)) Right(())
    else Left(InvalidAuthentication)

  def validateToken(token: String) =
    Jwt
      .validate(token)[UserClaims]
      .using(tokenKey)
      .now
      .map { jwt =>
        val UserClaims(sub, scp) = jwt.claims.unregistered
        UserAuthn(sub, scp)
      }
      .left
      .map(_ => InvalidAuthentication)

  def issueToken(userId: Long, scopes: Set[String]) = {
    val claims = Claims(
      iat = Some(Instant.now()),
      exp = Some(Instant.now().plus(12, HOURS)),
      unregistered = UserClaims(userId, scopes)
    )
    Jwt.sign(claims, tokenKey) -> claims
  }
}
