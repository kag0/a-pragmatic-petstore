package io.github.kag0.petstore.authorization

import akka.stream.scaladsl.Source
import akka.util.ByteString
import cats.data.EitherT
import io.github.kag0.petstore.authentication.{ApiKeyAuthn, UserAuthn}
import io.github.kag0.petstore.errors._
import io.github.kag0.petstore.models.Pet
import io.github.kag0.petstore.models.Pet.Status.Status
import io.github.kag0.petstore.services.PetClient

import scala.concurrent.{ExecutionContext, Future}

class PetAuthorization(petService: PetClient)(implicit ec: ExecutionContext) {
  val read  = "read:pets"
  val write = "write:pets"

  private def testScope(requiredScope: String*)(implicit auth: UserAuthn) =
    EitherT.fromEither[Future](
      if (requiredScope.toSet.subsetOf(auth.scopes)) Right(())
      else Left(OperationNotPermitted)
    )

  def findPets(status: Option[Status], tag: Option[String])(implicit
      auth: UserAuthn
  ) = testScope(read, write).semiflatMap(_ => petService.findPets(status, tag))

  def addPet(pet: Pet)(implicit auth: UserAuthn) =
    testScope(read, write).semiflatMap(_ => petService.addPet(pet))

  def getPetById(id: Long)(implicit
      auth: Either[UserAuthn, ApiKeyAuthn.type]
  ) = EitherT
    .fromEither[Future](auth)
    .leftFlatMap(testScope(read, write)(_))
    .semiflatMap(_ => petService.getPetById(id))

  def updatePet(pet: Pet)(implicit
      auth: UserAuthn
  ) = testScope(read, write).semiflatMap(_ => petService.updatePet(pet))

  def updatePetWithForm(
      name: Option[String],
      status: Option[Status]
  )(implicit auth: UserAuthn) = testScope(read, write).semiflatMap(_ =>
    petService.updatePetWithForm(name, status)
  )

  def deletePet(id: Long)(implicit
      auth: UserAuthn
  ) = testScope(read, write).semiflatMap(_ => petService.deletePet(id))

  def uploadFile(
      petId: Long,
      image: Source[ByteString, Any],
      metadata: Option[String]
  )(implicit auth: UserAuthn) =
    testScope(read, write).semiflatMap(_ =>
      petService.uploadFile(petId, image, metadata)
    )
}
