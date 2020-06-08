package edu.holycross.shot.nexus


object JSMain {
  def main(args: Array[String]): Unit = {
    val nexus = Nexus("Built in ScalaJS")
    println("Built in JS " + nexus)
  }
}
