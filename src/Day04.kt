fun main() {
  // input: string of format 2-4,6-8
  fun parseSectionStrings(pair: String): List<String> {
    val (section1, section2) = pair.split(",")
    return listOf(section1, section2)
  }

  // input: string of format 2-4
  fun getSectionRange(section: String): Set<Int> {
    val (start, end) = section.split("-").map{ it.toInt()!! }
    return start.rangeTo(end)!!.toSet()
  }

  fun part1(pairs: List<String>): Int {
    return pairs.map {
      parseSectionStrings(it)
        .map { getSectionRange((it))} }
      .count {
        val (a, b) = it
        (a.containsAll(b)).or(b.containsAll(a))
      }
  }

  fun part2(pairs: List<String>): Int {
    return pairs.map {
      parseSectionStrings(it)
        .map { getSectionRange((it))} }
      .count {
        val (a, b) = it
        !a.intersect(b).isEmpty()
      }
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day04_sample")
  println(part1(testInput))
  println(part2(testInput))
  check(part1(testInput) == 2)
  check(part2(testInput) == 4)

  val input = readInput("Day04")
  println(part1(input))
  println(part2(input))
}