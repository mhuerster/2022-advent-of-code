fun main() {
    fun getElvesFromInput(input: List<String>): List<List<Int>> {
        val groupSeparator = ",\\s,\\s".toRegex()
        val itemSeparator = ",\\s+".toRegex()

        val groups = input.joinToString()
        val elves = groups.split(groupSeparator).map { it.split(itemSeparator).map { it.toInt() } }//
        return(elves)
    }

    fun part1(input: List<String>): Int {
        val elves = getElvesFromInput(input)
        val calories = elves.map { it.sum() }
        val result = calories.max()

        return(result)
    }

    fun part2(input: List<String>): Int {
        val elves = getElvesFromInput(input)
        val calories = elves.map { it.sum() }
        return(calories.sortedDescending().take(3).sum())
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_sample")
    val input = readInput("Day01")
    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    println(part1(input))
    println(part2(input))
}
