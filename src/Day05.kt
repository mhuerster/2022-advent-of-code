fun main() {
  // TODO: parse start state from file
  val sampleInitialState = mutableMapOf(
    1 to "ZN",
    2 to "MCD",
    3 to "P",
  )
  val initialState = mutableMapOf(
    1 to "NRGP",
    2 to "JTBLFGDC",
    3 to "MSV",
    4 to "LSRCZP",
    5 to "PSLVCWDQ",
    6 to "CTNWDMS",
    7 to "HDGWP",
    8 to "ZLPHSCMV",
    9 to "RPFLWGZ",
  )
  val instructionPattern = "move\\s(\\d+)\\D+(\\d+)\\D+(\\d+)".toRegex()

  fun parseInstruction(instruction: String): List<Int> {
    val matches = instructionPattern.find(instruction)!!
    val (a, b, c) = matches.destructured
    val components = listOf(a, b, c) // TODO: get rid of intermediate vals
    return (components.map { it.toInt() })
  }

  fun executeInstruction9000(instruction: List<Int>, stacks: MutableMap<Int, String>): MutableMap<Int, String> {
    val (quantity, fromStackIndex, toStackIndex) = instruction
    val fromStack = stacks.get(fromStackIndex)
    val toStack = stacks.get(toStackIndex)
    val cratesToMove = fromStack!!.takeLast(quantity).reversed()

    val newToStack = toStack!!.plus(cratesToMove)
    val newFromStack = fromStack.dropLast(quantity)
    val changedStacks = mapOf(
      fromStackIndex to newFromStack,
      toStackIndex to newToStack
    )
    val newStacks = stacks.plus(changedStacks).toMutableMap()
    return(newStacks)
  }

  fun executeInstruction9001(instruction: List<Int>, stacks: MutableMap<Int, String>): MutableMap<Int, String> {
    val (quantity, fromStackIndex, toStackIndex) = instruction
    val fromStack = stacks.get(fromStackIndex)
    val toStack = stacks.get(toStackIndex)
    val cratesToMove = fromStack!!.takeLast(quantity)

    val newToStack = toStack!!.plus(cratesToMove)
    val newFromStack = fromStack.dropLast(quantity)
    val changedStacks = mapOf(
      fromStackIndex to newFromStack,
      toStackIndex to newToStack
    )
    val newStacks = stacks.plus(changedStacks).toMutableMap()
    return(newStacks)
  }

  fun part1(input: List<String>, initialState: MutableMap<Int, String>): String {
    var newStacks = initialState
    for (line in input) {
      if (instructionPattern.matches(line)) {
        val instruction = (parseInstruction(line))
        newStacks = executeInstruction9000(instruction, newStacks)
      }
    }

    return(newStacks.values.map { it.last() }.joinToString(""))
  }

  fun part2(input: List<String>, initialState: MutableMap<Int, String>): String {
    var newStacks = initialState
    for (line in input) {
      if (instructionPattern.matches(line)) {
        val instruction = (parseInstruction(line))
        newStacks = executeInstruction9001(instruction, newStacks)
      }
    }

    return(newStacks.values.map { it.last() }.joinToString(""))
  }

  val testInput = readInput("Day05_sample")
  val input = readInput("Day05")

  // test if implementation meets criteria from the description, like:
  println(part1(testInput, sampleInitialState))
  check(part1(testInput, sampleInitialState) == "CMZ")
  println(part1(input, initialState))

  // check(part2(testInput)
  println(part2(testInput, sampleInitialState))
  check(part2(testInput, sampleInitialState) == "MCD")
  println(part2(input, initialState))
}