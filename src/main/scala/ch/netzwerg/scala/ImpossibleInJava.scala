package ch.netzwerg.scala

import java.awt.Color

// Executable program with minimal boiler-plate
object ImpossibleInJava extends App {

  // Type inference, no semicolons
  val foo = "bar"

  // Truly immutable collections (i.e. enforced by compiler)
  val immutable = Vector("apple", "banana")

  // DTOs as one-liners (immutable, with equals/hashCode/toString)
  case class Person(name: String, age: Int)

  // 'new' keyword is optional for case classes (basis for nice, declarative DSLs)
  val person = Person("Homer", 42)

  // Simple string formatting
  println(s"Hi ${person.name}!")

  // Syntactic sugar for ranges
  val numbers = 1 to 5
  val evenNumbers = 2 to 10 by 2

  // Pairs (aka Tuple2; actually up to Tuple22)
  val point: (Int, Int) = (3, 2)

  // Zipping
  val names = Vector("Ada", "Hilary", "Barack")
  val ages = Vector(100, 67, 54)
  val nameAndAge: Vector[(String, Int)] = names.zip(ages)
  val persons: Vector[Person] = nameAndAge.map { p => Person(p._1, p._2) }

  // Fibonacci stream as a one-liner :-)
  val fibs: Stream[Int] = 0 #:: 1 #:: fibs.zip(fibs.tail).map { n => n._1 + n._2 }

  // Functions are first class citizens
  val plusOne = (i: Int) => i + 1
  // Function application is straight forward
  assert(plusOne(10) == 11)

  // Function currying
  val sum = (a: Int, b: Int) => a + b
  val plusTen = sum.curried(10)
  assert(plusTen(5) == 15)

  // Partial function application
  val product = (a: Int) => (b: Int) => a * b
  val timesTwo = product(2)
  assert(timesTwo(4) == 8)

  // Mix-Ins

  trait Coloring {
    protected def color: Color
  }

  trait PinkColoring extends Coloring {
    def color = Color.PINK
  }

  trait YellowColoring extends Coloring {
    def color = Color.YELLOW
  }

  class Thing {
    this:Coloring =>
  }

  val pinkThing = new Thing with PinkColoring
  val yellowThing = new Thing with YellowColoring

  // Implicits

  implicit class FancyStrings(s: String) {
    def toStars = s.map(c => '*')
  }

  assert("hello".toStars == "*****")

  // Streams can be consumed multiple times
  val infiniteStream = Stream.from(1)
  infiniteStream.take(3).toList
  infiniteStream.take(3).toList

  // Dealing with Options
  val options: Stream[Option[String]] = Stream(Some("foo"), None, Some("bar"))
  val values: Stream[String] = options.flatten
  val valuesViaFlatMap: Stream[String] = options.flatMap(o => o.map(identity))

  // this.type

  abstract class Carnivore {

    def eat: this.type = {
      println("eating meat, yummy!")
      this
    }

  }

  class Dog extends Carnivore {

    def bark: Dog = {
      println("wuff!")
      this
    }

  }

  // Without eat returning 'this.type', functions would not compose properly
  new Dog().eat.bark

  //-----------------------------------------------------------------------------------------------
  // Real World Problem 1: Given a type hierarchy, apply different logic depending on runtime type
  //-----------------------------------------------------------------------------------------------

  trait Attribute {
    val name: String
  }

  // sealing to prevent future extensions
  sealed case class SimpleAttribute(name: String) extends Attribute
  sealed case class NumericAttribute(name: String, min: Int, max: Int) extends Attribute
  sealed case class CategoricalAttribute(name: String, categories: Set[String]) extends Attribute

  val attribute: Attribute = randomAttribute

  // Handle runtime types via pattern matching (no instanceof checks, no casts)
  attribute match {
    case attribute @ SimpleAttribute(_) => println(s"Simple is beautiful: $attribute")
    case NumericAttribute(_, min, max) => println(s"Numbers it is! $min, $max")
    case CategoricalAttribute(_, categories) => println(s"Yay, categories! $categories")
  }

  def randomAttribute: Attribute = {
    if (Math.random() > 0.5) NumericAttribute("first", 1, 42) else CategoricalAttribute("second", Set("foo", "bar"))
  }

  //-----------------------------------------------------------------------------------------------
  // Real World Problem 2: Initialize Color instance from string-configuration-based definition
  //-----------------------------------------------------------------------------------------------

  // from hex
  assert(Color.PINK == resolveColor("#FFAFAF"))

  // from r,g,b
  assert(Color.BLUE == resolveColor("0,0,255"))

  // from r,g,b,a
  assert(60 == resolveColor("200,200,200,60").getAlpha)

  def resolveColor(color: String): Color = {

    val OpaqueColor = """(\d+),(\d+),(\d+)""".r
    val TransparentColor = """(\d+),(\d+),(\d+),(\d+)""".r

    color match {
      case hex if hex.startsWith("#") => Color.decode(hex)
      case OpaqueColor(r, g, b) => new Color(r.toInt, g.toInt, b.toInt);
      case TransparentColor(r, g, b, a) => new Color(r.toInt, g.toInt, b.toInt, a.toInt);
    }

  }

}
