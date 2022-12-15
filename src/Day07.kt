private typealias Commands = List<List<String>>
fun main() {
    data class File(val name: String, val size: Long)

    class Directory(val name: String, val parent: Directory?) {
        val children: MutableList<Directory> = mutableListOf()
        val files: MutableList<File> = mutableListOf()
        var size: Long = 0

        fun calculateSizeOfAllNested(): Long {
            val size = files.sumOf { it.size } + children.sumOf { it.calculateSizeOfAllNested() }
            this.size = size
            return size
        }
    }
    class FileSystem {
        val root: Directory = Directory("root", null)
        var currentDirectory: Directory = root
        fun createDirectory(name: String) { currentDirectory.children.add(Directory(name, currentDirectory)) }
        fun changeDirectory(name: String) {
            currentDirectory = when (name) {
                "/" -> root
                ".." -> currentDirectory.parent ?: throw Error("Can't switch to null")
                else -> currentDirectory.children.first { directory ->
                    directory.name == name
                }
            }
        }

        fun flatMapDirectories(): List<Directory> {
            val directories = mutableListOf<Directory>()
            fun getAllDirectoriesRec(dir: Directory) {
                directories.add(dir)
                dir.children.forEach { child -> getAllDirectoriesRec(child) }
            }
            getAllDirectoriesRec(root)
            return directories.toList()
        }
    }

    var currentFileSystem = FileSystem()

    fun Commands.execute(): FileSystem {
        val fileSystem = FileSystem()
        with(fileSystem) {
            forEach { command ->
                when (command[0]) {
                    "dir" -> if (currentDirectory.children.none { it.name == command[1] }) createDirectory(command[1])
                    "$" -> if (command[1] == "cd") changeDirectory(command[2])
                    else -> currentDirectory.files.add(File(command[1], command[0].toLong()))
                }
            }
            root.calculateSizeOfAllNested()
        }
        currentFileSystem = fileSystem
        return fileSystem
    }

    fun part1(commands: Commands): Long = commands
        .execute()
        .flatMapDirectories()
        .filter { it.size <= 100000L }
        .sumOf { it.size }

    fun part2(fileSystem: FileSystem): Long = fileSystem
        .flatMapDirectories()
        .map { it.size }
        .sorted()
        .first { it > 30000000L - (70000000L - fileSystem.root.size) }

    //val testInput = readInputWords("Day07_test")
    //check(part1(testInput) == 95437L)
    //check(part2(currentFileSystem) == 24933642L)

    val input = readInputWords("Day07_input")
    println(part1(input))
    println(part2(currentFileSystem))
}
