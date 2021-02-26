package io.github.kag0.petstore.services

import io.github.kag0.petstore.models.Order
import io.github.kag0.petstore.errors._

import scala.concurrent.Future

class StoreService(petService: PetClient) {
  def getInventory: Future[Map[String, Int]]                             = ???
  def placeOrder(order: Order): Future[Either[InvalidInput.type, Order]] = ???
  def getOrderById(id: Long): Future[Either[OrderNotFound.type, Order]]  = ???
  def deleteOrder(id: Long): Future[Either[OrderNotFound.type, Order]]   = ???
}
