import mill._, scalalib._

object petstore extends ScalaModule {
  def scalaVersion = "2.13.4"

  val akkaV = "2.6.8"
  def ivyDeps = Agg(
    ivy"com.typesafe.akka::akka-stream:$akkaV",
    ivy"com.typesafe.akka::akka-http:10.2.3"
  )
}
