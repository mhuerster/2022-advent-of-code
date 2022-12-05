import kotlin.collections.HashMap
import kotlin.collections.List

fun main() {
  val shapes: HashMap<String, String> = hashMapOf(
    "A" to "ROCK",
    "B" to "PAPER",
    "C" to "SCISSORS",
    // below values only used for part 1
    "X" to "ROCK",
    "Y" to "PAPER",
    "Z" to "SCISSORS"
  )

  val targetOutcomes: HashMap<String, String> = hashMapOf(
    "X" to "LOSE",
    "Y" to "DRAW",
    "Z" to "WIN"
  )

  val rockPaperScissors: HashMap<String, Int> = hashMapOf(
    "ROCK" to 1,
    "PAPER" to 2,
    "SCISSORS" to 3
  )
  val scores: HashMap<String, Int> = hashMapOf(
    "WIN" to 6,
    "DRAW" to 3,
    "LOSE" to 0,
    "INVALID" to -1
  )

  // Given an initial move, return the result based on the responding move
  fun rock(move: String): Int {
    val outcome = when (shapes[move]) {
      "ROCK" -> scores["DRAW"]
      "PAPER" -> (scores["LOSE"])
      "SCISSORS" -> (scores["WIN"])
      else -> (scores["INVALID"])
    }

    return outcome?.toInt()!! + rockPaperScissors.get("ROCK")!!.toInt()
  }

  // Given an initial move, return the result based on the responding move
  fun paper(move: String): Int {
    val outcome = when (shapes[move]) {
      "ROCK" -> scores["WIN"]
      "PAPER" -> scores["DRAW"]
      "SCISSORS" -> scores["LOSE"]
      else -> scores["INVALID"]
    }

    return outcome?.toInt()!! + rockPaperScissors.get("PAPER")!!.toInt()
  }

  // Given an initial move, return the result based on the responding move
  fun scissors(move: String): Int {
    val outcome = when (shapes[move]) {
      "ROCK" -> scores["LOSE"]
      "PAPER" -> scores["WIN"]
      "SCISSORS" -> scores["DRAW"]
      else -> scores["INVALID"]
    }

    return outcome?.toInt()!! + rockPaperScissors.get("SCISSORS")!!.toInt()
  }

  // Given your opponent's move, return the move you need to play to win the round
  fun win(oppenentMove: String): String {
    val yourMove = when(shapes[oppenentMove]) {
      "ROCK" -> "B"
      "PAPER" -> "C"
      "SCISSORS" -> "A"
      else -> ""
    }

    return yourMove.toString()!!
  }

  // Given your opponent's move, return the move you need to play to lose the round
  fun lose(oppenentMove: String): String {
    val yourMove = when(shapes[oppenentMove]) {
      "ROCK" -> "C"
      "PAPER" -> "A"
      "SCISSORS" -> "B"
      else -> ""
    }

    return yourMove.toString()!!
  }
  // Given your opponent's move, return the move you need to play for the round to end in a draw
  fun draw(oppenentMove: String): String {
    val yourMove = when(shapes[oppenentMove]) {
      "ROCK" -> "A"
      "PAPER" -> "B"
      "SCISSORS" -> "C"
      else -> ""
    }

    return yourMove.toString()!!
  }

  // Given an opponent's move and player's move, return the score for that round
  fun scoreRound(round: String): Int {
    val (yourMove, opponentMove) = round.split(" ")
    val score = when (shapes[opponentMove]) {
      "ROCK" -> rock(yourMove)
      "PAPER" -> paper(yourMove)
      "SCISSORS" -> scissors(yourMove)
      else -> scores["INVALID"]
    }
    return score!!.toInt()
  }

  fun getMoves(round: String): String {
    val (opponentMove, desiredScore) = round.split(" ")
    val yourMove = when (targetOutcomes[desiredScore]) {
      "WIN" -> win(opponentMove)
      "LOSE" -> lose(opponentMove)
      "DRAW" -> draw(opponentMove)
      else -> ""
    }

    val moves = listOf(opponentMove, yourMove)
    return (moves.joinToString(" "))
  }

  fun part1(input: List<String>): Int {
    val finalScore = input.fold(0) { runningScore, round ->
      runningScore + scoreRound(round)
    }
    return finalScore
  }

  fun part2(input: List<String>): Int {
    val finalScore = input.fold(0) { runningScore, round ->
      runningScore + (scoreRound(getMoves(round)))
    }
    return finalScore
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day02_sample")
  check(part1(testInput) == 15)
  check(part2(testInput) == 12)

  val input = readInput("Day02")
  println(part1(input))
  println(part2(input))
}