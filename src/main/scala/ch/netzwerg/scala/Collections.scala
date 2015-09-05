package ch.netzwerg.scala

/**
 * Different collection types with specific characteristics.
 */
object Collections extends App {

  // a classical set
  val orangeFruitSet = Set("orange", "apricot")

  // a linked-list (i.e. a recursive data structure) with fast append-first (::)
  val yellowFruitList = List("banana", "lemon")

  // a classical array
  val redFruitArray = Array("watermelon", "cherry")

  // THE general-purpose data structure with random access
  val blueFruitVector = Vector("blueberry", "grape")

  // indexed access is consistent
  val cherry = redFruitArray(1)
  val grape = blueFruitVector(1)

  // safe indexed access (prevent index-out-of-bounds)
  val safeAccess: Option[String] = redFruitArray.lift(99)

  // concatenation
  val fruit = orangeFruitSet ++ yellowFruitList ++ redFruitArray ++ blueFruitVector

  // Seq, List, Vector, Array all implement Traversable
  val orange = fruit.head
  val otherFruit = fruit.tail

  // syntactic sugar for iteration (e.g. with condition)
  for (f <- fruit) {
    println(f)
  }
  // for / yield sequence comprehensions
  val upperCaseFruit = for (f <- fruit) yield f.toLowerCase
  // for / if filtering
  val bFruits = for (f <- fruit if f.startsWith("b")) yield f

  // immutability enforced by compiler > modifying returns a new copy
  val modified = redFruitArray.updated(0, "apple")

  // type-safe conversion (no casting!)
  val redFruitList: List[String] = redFruitArray.toList
  val yellowFruitSet: Set[String] = yellowFruitList.toSet

  // functional combinators
  val upperCasedYellowFruits = yellowFruitList.map(_.toUpperCase)

  // pattern matching
  redFruitList match {
    case List(_, secondRedFruit) => println(secondRedFruit) // "cherry"
  }

  // utility methods
  val squares = List.tabulate(10)(n => n * n) // List(0, 1, 4, 9, 16, 25, 36, 49, 64, 81)

}