package io.github.kag0.petstore.services

import io.github.kag0.petstore.errors._
import io.github.kag0.petstore.models.User
import io.github.kag0.petstore.payloads.TokenResponse

import scala.concurrent.Future

class UserService {
  def createUser(user: User): Future[User]                          = ???
  def createUsersWithListInput(users: Seq[User]): Future[Seq[User]] = ???

  def loginUser(
      username: String,
      password: Array[Char]
  ): Future[Either[InvalidUsernameOrPassword.type, TokenResponse]] = ???

  def logoutUser(tokenId: Long) = ???

  def getUserByName(username: String): Future[Either[UserNotFound.type, User]] =
    ???

  def updateUser(user: User): Future[Unit] = ???
  def deleteUser(id: Long): Future[Unit]   = ???
}
