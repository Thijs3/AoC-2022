fun main() {
    data class File(val name: String, val size: Long)

    class Directory(val name: String, val parent: Directory?) {
        val children: MutableList<Directory> = mutableListOf()
        val files: MutableList<File> = mutableListOf()
        var size: Long = 0

        fun addFile(name: String, size: Long) = if (files.none { it.name == name }) {
            files.add(File(name, size))
        } else {
            throw Error("trying to add file with duplicate name")
        }
        fun calculateSizeOfAllNested(): Long {
            val s = files.sumOf { it.size } + children.sumOf { it.calculateSizeOfAllNested() }
            size = s
            return s
        }
    }
    class FileSystem {
        val root: Directory = Directory("root", null)
        var currentDirectory: Directory = root
        fun createDirectory(name: String) { currentDirectory.children.add(Directory(name, currentDirectory)) }
        fun changeDirectory(name: String) {
            currentDirectory = when (name) {
                "/" -> root
                ".." -> currentDirectory.parent ?: throw Error("not able to change to parent of directory $currentDirectory with no parent")
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

        fun executeCommands(commands: List<List<String>>) {
            commands.forEach { command ->
                when (command[0]) {
                    "dir" -> if (currentDirectory.children.none { it.name == command[1] }) createDirectory(command[1])
                    "$" -> if (command[1] == "cd") changeDirectory(command[2])
                    else -> currentDirectory.addFile(command[1], command[0].toLong())
                }
            }
            root.calculateSizeOfAllNested()
        }
    }

    fun part1(fileSystem: FileSystem): Long = fileSystem
        .flatMapDirectories()
        .filter { it.size <= 100000L }
        .sumOf { it.size }

    fun part2(fileSystem: FileSystem): Long = fileSystem
        .flatMapDirectories()
        .map { it.size }
        .sorted()
        .first { it > 30000000L - (70000000L - fileSystem.root.size) }

    val testCommands = readInputWords("Day07_test")
    val testInput = FileSystem().also { it.executeCommands(testCommands) }
    check(part1(testInput) == 95437L)
    check(part2(testInput) == 24933642L)

    val commands = readInputWords("Day07_input")
    val input = FileSystem().also { it.executeCommands(commands) }
    println(part1(input))
    println(part2(input))
}
