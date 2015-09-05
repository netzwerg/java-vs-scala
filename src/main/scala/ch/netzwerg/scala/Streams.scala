package ch.netzwerg.scala

object Streams extends App {

  val infiniteStream = Stream.from(1)

  // Scala streams can be re-used

  println(infiniteStream.take(3).toList) // List(1, 2, 3)
  println(infiniteStream.take(3).toList) // List(1, 2, 3)

}
