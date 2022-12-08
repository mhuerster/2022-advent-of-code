fun main() {
  class Tree(val height: Int, val y: Int, val x: Int) {
    override fun toString() : String {
      return "$height at [$y, $x]"
    }

    // same x value as Tree, lesser y value
    fun northernNeighbors(tree: Tree, trees: List<List<Tree>>): List<Tree> {
      return trees.take(tree.y).flatten()
        .filter { it.x == tree.x }
        .filter { it.height >= tree.height }
      }

    // same x value as Tree, greater y value
    fun southernNeighbors(tree: Tree, trees: List<List<Tree>>): List<Tree> {
      val allSouthernTrees = trees.slice((tree.y+1)..trees.lastIndex)
      return allSouthernTrees.flatten()
        .filter { it.x == tree.x }
        .filter { it.height >= tree.height }
    }

    // same y value as Tree, greater x value
    fun easternNeighbors(tree: Tree, trees: List<List<Tree>>): List<Tree> {
      return trees[tree.y].filter { it.x > tree.x }.filter { it.height >= tree.height }
    }

    // same y value as Tree, lesser x value
    fun westernNeighbors(tree: Tree, trees: List<List<Tree>>): List<Tree> {
      return trees[tree.y].filter { it.x < tree.x }.filter { it.height >= tree.height }
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
        northernNeighbors(this, trees).isEmpty() -> return true
        southernNeighbors(this, trees).isEmpty() -> return true
        easternNeighbors(this, trees).isEmpty() -> return true
        westernNeighbors(this, trees).isEmpty() -> return true
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
    return input.size
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day09_sample")
  println(part1(testInput))
  check(part1(testInput) == 21)
  // println(part2(testInput))
  // check(part2(testInput) == 1)

  val input = readInput("Day09")
  println(part1(input))
  // println(part2(input))
}