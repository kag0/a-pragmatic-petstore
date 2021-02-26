package io.github.kag0.petstore.models

import io.github.kag0.petstore.models.Order.Status.Status
import io.github.kag0.petstore.models.Order.Status.placed

import java.time.OffsetDateTime

case class Order(
    id: Long = 0,
    petId: Long,
    quantity: Int = 1,
    shipDate: Option[OffsetDateTime],
    status: Status = placed,
    complete: Boolean = false
)

object Order {
  object Status extends Enumeration {
    type Status = Value
    val placed, approved, delivered = Value
  }
}
