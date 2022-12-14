enum class Direction { NORTH, SOUTH, EAST, WEST }

fun main() {
  class Tree(val height: Int, val y: Int, val x: Int) {
    override fun toString() : String {
      return "$height at [$y, $x]"
    }

    fun viewingDistance(direction: Direction, trees: List<List<Tree>>) : Int {
      val neighbors = when (direction) {
        Direction.NORTH -> northernNeighbors(trees).asReversed()
        Direction.SOUTH -> southernNeighbors(trees)
        Direction.EAST -> easternNeighbors(trees)
        Direction.WEST -> westernNeighbors(trees).asReversed()
      }

      val visibleNeighbors = neighbors.takeWhile { it.height < this.height || it.isEdge(trees) }

      if (visibleNeighbors.size == neighbors.size) {
        return visibleNeighbors.size
      }

      if (neighbors.elementAt(visibleNeighbors.size).height >= this.height) {
        return visibleNeighbors.size + 1
      } else {
        return visibleNeighbors.size
      }
    }

    fun scenicScore(trees: List<List<Tree>>) : Int {
      return viewingDistance(Direction.NORTH, trees)
        .times(viewingDistance(Direction.SOUTH, trees))
        .times(viewingDistance(Direction.EAST, trees))
        .times(viewingDistance(Direction.WEST, trees))
    }

    // same x value as Tree, lesser y value
    fun northernNeighbors(trees: List<List<Tree>>): List<Tree> {
      return trees.take(this.y).flatten().filter { it.x == this.x }
    }

    // same x value as Tree, greater y value
    fun southernNeighbors(trees: List<List<Tree>>): List<Tree> {
      return trees.slice((this.y+1)..trees.lastIndex).flatten().filter { it.x == this.x }
    }

    // same y value as Tree, greater x value
    fun easternNeighbors(trees: List<List<Tree>>): List<Tree> {
      return trees[this.y].filter { it.x > this.x }
    }

    // same y value as Tree, lesser x value
    fun westernNeighbors(trees: List<List<Tree>>): List<Tree> {
      return trees[this.y].filter { it.x < this.x }
    }

    /*
    A tree is visible if all of the other trees between it and an edge of the grid are shorter
    than it. Only consider trees in the same row or column; that is, only look up, down, left,
    or right from any given tree. One neighbor in each direction must be taller for a tree to
    to be invisible.
     */
    fun isVisible(trees: List<List<Tree>>) : Boolean {
      if (isEdge(trees)) { return true}

      when {
        northernNeighbors(trees).filter { it.height >= height }.isEmpty() -> return true
        southernNeighbors(trees).filter { it.height >= height }.isEmpty() -> return true
        easternNeighbors(trees).filter { it.height >= height }.isEmpty() -> return true
        westernNeighbors(trees).filter { it.height >= height }.isEmpty() -> return true
        else -> return false
      }
    }

    // assumes minX and minY are both 0
    fun isEdge(trees: List<List<Tree>>) : Boolean {
      val maxX = trees.first().lastIndex
      val maxY = trees.lastIndex
      when {
        (x == 0) -> return true
        (y == 0) -> return true
        (x == maxX) -> return true
        (y == maxY) -> return true
        else -> return false
      }
    }
  }

  fun part1(input: List<String>): Int {
    val trees = input.mapIndexed { y, element ->
      element.toCharArray().mapIndexed { x, height ->
        Tree(height.digitToInt(), y, x) }
    }

    val visibleTrees = trees.flatten().filter { it.isVisible(trees)}

    return visibleTrees.size
  }

  fun part2(input: List<String>): Int {
    val trees = input.mapIndexed { y, element ->
      element.toCharArray().mapIndexed { x, height ->
        Tree(height.digitToInt(), y, x) }
    }

    return trees.flatten().map { it.scenicScore(trees) }.max()
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day09_sample")
  // println(part1(testInput))
  check(part1(testInput) == 21)
  // println(part2(testInput))
  check(part2(testInput) == 8)

  val input = readInput("Day09")
  // println(part1(input))
  check(part1(input) == 1711)
  check(part2(input) < 2322000)
  check(part2(input) > 267904)
  println(part2(input))
}