package edu.holycross.shot.nexus

object JVMMain {
  def main(args: Array[String]): Unit = {
    val nexus = Nexus("Built in JVM")
    println("Created nexus object: " + nexus)
  }
}
