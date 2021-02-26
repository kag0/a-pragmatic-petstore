import mill._, scalalib._

object petstore extends ScalaModule {
  def scalaVersion = "2.13.5"

  val akkaV = "2.6.8"
  val joseV = "0.4.2"
  def ivyDeps = Agg(
    ivy"com.typesafe.akka::akka-stream:$akkaV",
    ivy"com.typesafe.akka::akka-http:10.2.3",
    ivy"org.typelevel::cats-core:2.4.2",
    ivy"black.door::jose:$joseV",
    ivy"black.door::jose-json-ninny:$joseV",
    ivy"io.github.kag0::ninny:0.2.10"
  )
}
