package io.github.kag0.petstore.services

import akka.stream.scaladsl.Source
import akka.util.ByteString
import io.github.kag0.petstore.models.Pet
import io.github.kag0.petstore.models.Pet.Status.Status
import io.github.kag0.petstore.errors._

import scala.concurrent.Future

trait PetClient {
  def findPets(status: Option[Status], tag: Option[String]): Future[Seq[Pet]]
  def addPet(pet: Pet): Future[Pet]
  def getPetById(id: Long): Future[Either[PetNotFound.type, Pet]]
  def updatePet(pet: Pet): Future[Either[PetstoreError, Pet]]

  def updatePetWithForm(
      name: Option[String],
      status: Option[Status]
  ): Future[Either[PetstoreError, Unit]]

  def deletePet(id: Long): Future[Either[PetNotFound.type, Unit]]

  def uploadFile(
      petId: Long,
      image: Source[ByteString, Any],
      metadata: Option[String]
  ): Future[Unit]
}
