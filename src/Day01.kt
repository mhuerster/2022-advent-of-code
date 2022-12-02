fun main() {
    fun part1(input: List<String>): Int {
        val groupSeparator = ",\\s,\\s".toRegex()
        val itemSeparator = ",\\s+".toRegex()

        val groups = input.joinToString()
        val elves = groups.split(groupSeparator).map { it.split(itemSeparator).map { it.toInt() } }//
        val calories = elves.map { it.sum() }
        val result = calories.max()

        println(result)
        return(result)
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_sample")
    val input = readInput("Day01")
    check(part1(testInput) == 24000)
    part1(input)

    //println(part1(input))
    //println(part2(input))
}
