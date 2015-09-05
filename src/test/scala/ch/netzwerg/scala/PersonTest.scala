package ch.netzwerg.scala

import org.scalatest._

class PersonTest extends FlatSpec with Matchers {

  val Name = "Ada"

  "A Person" should "be constructable" in {
    val person = Person(Name)
    person.name should equal(Name)
    person should equal(Person(Name))
  }

}
