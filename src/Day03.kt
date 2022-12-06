fun main() {
  var lowercase = ('a'..'z')
  var uppercase = ('A'..'Z')

  class Rucksack(val contents: String ) {
    val midpoint: Int
      get() = (contents.length / 2)
    val firstCompartment: String
      get() = contents.substring(0..midpoint)
    val secondCompartment: String
     get() = contents.substring(midpoint..contents.lastIndex)

    fun commonItem(): Char {
      return firstCompartment.find { secondCompartment.contains(it) }!!.toChar()
    }

    fun toSet(): Set<Char> {
      return contents.toSet()
    }
  }

  class Item(val item: Char) {
    fun priority(): Int {
      val p = if (lowercase.contains(item)) {
        lowercase.indexOf(item) + 1
      } else {
        uppercase.indexOf(item) + 27
      }
      return p.toInt()!!
    }
  }

  class Group(val rucksacks: List<Rucksack>) {
    fun badge(): Item {
      val (firstElf, secondElf, thirdElf) = rucksacks.map { it.toSet() }
      return Item(firstElf.intersect(secondElf).intersect(thirdElf).single())
    }
  }

  fun inputToRucksacks(input: List<String>): List<Rucksack> {
    return input.map { Rucksack(it) }
  }
// Find the item type that appears in both compartments of each rucksack.
// What is the sum of the priorities of those item types?
  fun part1(input: List<String>): Int {
    return inputToRucksacks(input).fold(0) { sum, rucksack ->
      sum + Item(rucksack.commonItem()).priority()
    }
  }
// Find the item type that corresponds to the badges of each three-Elf group.
// What is the sum of the priorities of those item types?
  fun part2(input: List<String>): Int {
    val groups = inputToRucksacks(input).chunked(3).map { Group(it) }

    return groups.fold(0) { sum, group ->
      sum + group.badge().priority()
    }
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day03_sample")
  println(part1(testInput))
  check(part1(testInput) == 157)
  check(part2(testInput) == 70)

  val input = readInput("Day03")
  println(part1(input))
  println(part2(input))
}