fun main() {
  var lowercase = ('a'..'z')
  var uppercase = ('A'..'Z')

  fun priority(item: Char): Int {
    val p = if (lowercase.contains(item)) {
      lowercase.indexOf(item) + 1
    } else {
      uppercase.indexOf(item) + 27
    }
    return p.toInt()!!
  }

  fun firstCompartment(rucksack: String): String {
    val midpoint = (rucksack.length / 2) - 1
    return rucksack.substring(0..midpoint)
  }

  fun secondCompartment(rucksack: String): String {
    val midpoint = (rucksack.length / 2)
    return rucksack.substring(midpoint..rucksack.lastIndex)
  }

  fun commonItem(rucksack: String): Char {
    return firstCompartment(rucksack).find {
      secondCompartment(rucksack).contains(it)
    }!!.toChar()
  }

  fun badge(rucksacks: List<String>): Char {
    val (firstElf, secondElf, thirdElf) = rucksacks.map { it.toSet() }
    return(firstElf.intersect(secondElf).intersect(thirdElf)).single()
  }

  fun part1(rucksacks: List<String>): Int {
    return rucksacks.fold(0) { sum, rucksack ->
      sum + priority((commonItem(rucksack)))
    }
  }

  fun part2(rucksacks: List<String>): Int {
    val groups = rucksacks.chunked(3)

    return groups.fold(0) { sum, group ->
      sum + priority(badge(group))
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