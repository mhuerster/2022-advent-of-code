fun main() {
  val part1PacketSize = 4
  val part2PacketSize = 14

  fun getMarkerStartPosition(input: String, packetSize: Int): Int {
    val windows = input.windowed(size = packetSize, step = 1)
    return windows.indexOfFirst( { it.toCharArray().distinct().count() == packetSize } ) + packetSize
  }

  // test if implementation meets criteria from the description, like:
  // val testInput = readInput("Day00_sample")
  check(getMarkerStartPosition("mjqjpqmgbljsphdztnvjfqwrcgsmlb", part1PacketSize) == 7)
  check(getMarkerStartPosition("bvwbjplbgvbhsrlpgdmjqwftvncz", part1PacketSize) == 5)
  check(getMarkerStartPosition("nppdvjthqldpwncqszvftbrmjlhg", part1PacketSize) == 6)
  check(getMarkerStartPosition("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg", part1PacketSize) == 10)
  check(getMarkerStartPosition("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw", part1PacketSize) == 11)
  // println(part2(testInput))
  // check(part2(testInput) == 1)
  check(getMarkerStartPosition("mjqjpqmgbljsphdztnvjfqwrcgsmlb", part2PacketSize) == 19)
  check(getMarkerStartPosition("bvwbjplbgvbhsrlpgdmjqwftvncz", part2PacketSize) == 23)
  check(getMarkerStartPosition("nppdvjthqldpwncqszvftbrmjlhg", part2PacketSize) == 23)
  check(getMarkerStartPosition("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg", part2PacketSize) == 29)
  check(getMarkerStartPosition("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw", part2PacketSize) == 26)

  val input = readInputText("Day06")
  println(getMarkerStartPosition(input, part1PacketSize))
  println(getMarkerStartPosition(input, part2PacketSize))
}