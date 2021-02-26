package io.github.kag0.petstore

import akka.http.scaladsl.marshalling.PredefinedToResponseMarshallers
import akka.http.scaladsl.model.StatusCodes._
import io.github.kag0.petstore.errors._

package object routing {
  implicit val errorToResponse =
    PredefinedToResponseMarshallers.fromStatusCode.compose[PetstoreError] {
      case InvalidInput | InvalidIdSupplied | InvalidUsernameOrPassword =>
        BadRequest
      case PetNotFound | OrderNotFound | UserNotFound => NotFound
      case OperationNotPermitted                      => Forbidden
      case InvalidAuthentication                      => Unauthorized
    }
}
