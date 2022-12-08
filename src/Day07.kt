fun main() {
    val cdRegex = "\\\$\\scd\\s(.*)".toRegex() // Captures directory to cd into, or `..`
    val directoryRegex = "dir\\s(\\w+)".toRegex()
    val fileNameRegex = "(\\d+)\\s(\\w+\\.?\\w*)".toRegex()

    val totalSpace = 70000000
    val freeSpaceNeeded = 30000000

    open class FakeFile(var name: String, var dirName: String = "/", var size: Int = 0) {
        constructor(rawFileName: String, dirName: String) : this("", dirName, 0) {
            val (parsedSize, parsedName) = fileNameRegex.find(rawFileName)!!.destructured
            this.name = parsedName
            this.size = parsedSize.toInt()
        }

        constructor(rawFileName: String) : this("", "/", 0) {
            val (parsedSize, parsedName) = fileNameRegex.find(rawFileName)!!.destructured
            this.name = parsedName
            this.size = parsedSize.toInt()
        }

        override fun toString(): String {
            return "- $name (file, size=$size)"
        }
    }

    class Directory(
        name: String,
        dirName: String = "/",
        var files: MutableList<FakeFile> = mutableListOf<FakeFile>()
    ) : FakeFile(name, dirName, 0) {
        init {
            for (file in files) {
                file.dirName = this.name
            }
        }

        fun size(): Int {
            return files.fold(0) { a, e -> a + e.size }
        }

        fun add(file: FakeFile): List<FakeFile> {
            file.dirName = this.name
            files.add(file)
            return files
        }

        fun printFiles(offset: Int = 0) {
            val indent = " ".repeat(offset)
            println("$indent- $name (dir)")
            for (file in files) {
                val newOffset = offset + 2
                val newIndent = " ".repeat(newOffset)
                if (file is Directory) {
                    file.printFiles(newOffset)
                } else {
                    println("$newIndent$file")
                }
            }
        }

        fun sumFilesSizes(): Int {
            return files.fold(0) { totalSize, file ->
                if (file is Directory) {
                    totalSize + file.sumFilesSizes()
                } else {
                    totalSize + file.size
                }
            }
        }

        fun cappedSumFilesSizes(max: Int = Int.MAX_VALUE, topLevel: Boolean = true): Int {
            return files.fold(0) { totalSize, file ->
                if (file is Directory) {
                    var dirSize = file.sumFilesSizes()
                    if (dirSize <= max) {
                        totalSize + dirSize + file.cappedSumFilesSizes(max, false)
                    } else {
                        totalSize + 0 + file.cappedSumFilesSizes(max, false)
                    }
                } else {
                    totalSize + 0
                }
            }
        }

        // 8381165
        fun dirSizes(size: Int) : List<Int> {
            return files.fold(listOf<Int>()) { dirs, file ->
                if (file is Directory) {
                    val nestedSize = file.sumFilesSizes()
                    dirs.plus(nestedSize).plus(file.dirSizes(size))
                }
                else {
                    dirs
                }
            }.filter { it > size }
        }
    }

    class Nav(var dirs: MutableList<Directory> = mutableListOf<Directory>()) {
        fun pwd() : Directory {
            return dirs.last()
        }

        fun reverse() : Nav {
             dirs.removeLast()
            return this
        }

        fun advance(dir: FakeFile) : Nav {
            dirs.add(dir as Directory)
            return this
        }

        fun cd(command: String) : Nav {
            val (input, destination) = cdRegex.find(command)!!.groupValues
            if (destination == "..") {
                return this.reverse()
            } else {
                val toDir = pwd().files.find { it.name == destination }
                if (toDir != null) { this.advance(toDir) }
                return this
            }
        }
        fun createDirFromTerminalOutput(line: String) : Nav {
            val (input, dirName) = directoryRegex.find(line)!!.groupValues
            this.pwd().add(Directory(dirName))
            return(this)
        }

        fun createFileFromTerminalOutput(line: String) : Nav {
            val (input, size, fileName) = fileNameRegex.find(line)!!.groupValues
            val dirName = this.pwd().name
            this.pwd().add(FakeFile(fileName, dirName, size.toInt()))
            return(this)
        }
    }

    fun parseTerminalOutput(terminalOutput: List<String>): Directory {
        val root = Directory("/")
        val nav = Nav(mutableListOf<Directory>(root))
        for (line in terminalOutput) {
            when  {
                directoryRegex.matches(line) -> nav.createDirFromTerminalOutput(line)
                fileNameRegex.matches(line) -> nav.createFileFromTerminalOutput(line)
                cdRegex.matches(line) -> nav.cd(line)
            }
        }
        return root
    }

    val testInput = readInput("Day07_sample")
    val input = readInput("Day07")

    // test if implementation meets criteria from the description, like:
    val testFs = parseTerminalOutput(testInput)
    check(testFs.sumFilesSizes() == 48381165)
    check(testFs.cappedSumFilesSizes(100000) == 95437)
    // println(testFs.cappedSumFilesSizes(100000))
    // println(testFs.dirSizes(100000).min())

    // val currentFreeSpace = totalSpace - testFs.sumFilesSizes()
    // val spaceStillNeeded = freeSpaceNeeded - currentFreeSpace
    // check(spaceStillNeeded == 8381165)


    val fs = parseTerminalOutput(input)
    val currentFreeSpace = totalSpace - fs.sumFilesSizes()
    val spaceStillNeeded = freeSpaceNeeded - currentFreeSpace
    println(fs.dirSizes(spaceStillNeeded).min())

    // println(fs.cappedSumFilesSizes(100000))
    // println(fs.sumFilesSizes())
}