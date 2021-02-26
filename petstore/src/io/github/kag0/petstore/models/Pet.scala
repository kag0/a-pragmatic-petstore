package io.github.kag0.petstore.models

import io.github.kag0.petstore.models.Pet.Status.Status

case class Pet(
    id: Long = 0,
    name: String,
    category: Option[String],
    photoUrls: Seq[String],
    tags: Set[String],
    status: Option[Status]
)

object Pet {
  object Status extends Enumeration {
    type Status = Value
    val available, pending, sold = Value
  }
}
