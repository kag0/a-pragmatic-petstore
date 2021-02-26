package io.github.kag0.petstore

package object errors {
  sealed trait PetstoreError

  case object InvalidInput              extends PetstoreError
  case object InvalidIdSupplied         extends PetstoreError
  case object PetNotFound               extends PetstoreError
  case object OrderNotFound             extends PetstoreError
  case object InvalidUsernameOrPassword extends PetstoreError
  case object UserNotFound              extends PetstoreError
  case object OperationNotPermitted     extends PetstoreError
  case object InvalidAuthentication     extends PetstoreError
}
