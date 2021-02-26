package io.github.kag0.petstore.routing

import akka.http.scaladsl.server.Directives._
import io.github.kag0.petstore.authorization.PetAuthorization

class PetRoutes(authz: PetAuthorization) {

  val pets = "pets"

  // format: off
  val routes = concat(
    path(pets)(
      get (findPets) ~
      post(addPet)
    ),
    path(pets / LongNumber)( petId =>
      get   (getPetById(petId)) ~
      put   (updatePet(petId)) ~
      patch (updatePetWithForm(petId)) ~
      delete(deletePet(petId))
    ),
    path(pets / LongNumber / "uploadImage")( petId =>
      post(uploadFile(petId))
    )
  )
  // format: on

  // finer details of each endpoint defined separately
  // to keep the method/path routing clean
  private lazy val findPets               = ???
  private lazy val addPet                 = ???
  private def getPetById(id: Long)        = ???
  private def updatePet(id: Long)         = ???
  private def updatePetWithForm(id: Long) = ???
  private def deletePet(id: Long)         = ???
  private def uploadFile(id: Long)        = ???
}
