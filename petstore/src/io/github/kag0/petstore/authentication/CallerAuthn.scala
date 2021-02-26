package io.github.kag0.petstore.authentication

sealed trait CallerAuthn

case class UserAuthn(userId: Long, scopes: Set[String]) extends CallerAuthn
case object ApiKeyAuthn                                 extends CallerAuthn
case object PublicAuthn                                 extends CallerAuthn
