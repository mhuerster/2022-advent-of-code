import kotlin.collections.HashMap
import kotlin.collections.List

fun main() {
  val shapes: HashMap<String, String> = hashMapOf(
    "A" to "ROCK",
    "B" to "PAPER",
    "C" to "SCISSORS",
    "X" to "ROCK",
    "Y" to "PAPER",
    "Z" to "SCISSORS"
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

  fun part1(input: List<String>): Int {
    val finalScore = input.fold(0) { runningScore, round ->
      runningScore + scoreRound(round)
    }
    return finalScore
  }

  fun part2(input: List<String>): Int {
    return input.size
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day02_sample")
  check(part1(testInput) == 15)

  val input = readInput("Day02")
  println(part1(input))
  // println(part2(input))
}