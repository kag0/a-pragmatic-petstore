package io.github.kag0.petstore.models

case class User(
    username: String,
    firstName: String,
    lastName: String,
    email: String,
    phone: String,
    userStatus: Int = 1
)
