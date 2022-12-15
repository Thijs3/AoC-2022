import java.util.*

fun main() {
    data class Cell(val row: Int, val col: Int, var depth: Int = 0)

    fun IntGrid.findFirstIndex(value: Int): Cell =
        rows.indexOfFirst { it.contains(value) }.let {
            Cell(
                it ,
                it.let { row -> rows[row].indexOfFirst { v -> v == value } }
            )
        }

    fun IntGrid.getUnvisitedNeighbours(cur: Cell, visited: List<Cell>): List<Cell> = listOfNotNull(
        if (!visited.contains(Cell(cur.row,cur.col - 1,0))
            && cur.col > 0
            && rows[cur.row][cur.col] - rows[cur.row][cur.col - 1] >= -1)
            Cell(cur.row,cur.col - 1,0) else null,
        if (!visited.contains(Cell(cur.row - 1, cur.col,0))
            && cur.row > 0
            && rows[cur.row][cur.col] - rows[cur.row - 1][cur.col] >= -1)
            Cell(cur.row - 1, cur.col,0) else null,
        if (!visited.contains(Cell(cur.row,cur.col + 1,0))
            && cur.col < cols.size - 1
            && rows[cur.row][cur.col] - rows[cur.row][cur.col + 1] >= -1)
            Cell(cur.row,cur.col + 1,0) else null,
        if (!visited.contains(Cell(cur.row + 1, cur.col,0))
            && cur.row < rows.size - 1
            && rows[cur.row][cur.col] - rows[cur.row + 1][cur.col] >= -1)
            Cell(cur.row + 1, cur.col,0) else null
    )

    fun bfsDistanceToGoal(grid: IntGrid, start: Cell, goal: Cell): Int? {
        val queue: Queue<Cell> = LinkedList()
        val visited = mutableListOf(start)
        var depth = 0
        queue.add(start)
        while (queue.size !=0) {
            val cur = queue.remove()
            if (cur.depth == depth) depth += 1
            if (cur.row == goal.row && cur.col == goal.col) return depth - 1
            for (n in grid.getUnvisitedNeighbours(cur, visited)) {
                visited.add(n)
                queue.add(Cell(n.row, n.col, depth))
            }
        }
        return null
    }


    fun part1(input: List<String>): Int {
        val grid = readGridFromChars(input)
        val start = grid.findFirstIndex(-14)
        val goal = grid.findFirstIndex(-28)
        grid.rows[start.row][start.col] = 1
        grid.rows[goal.row][goal.col] = 25
        return bfsDistanceToGoal(grid, start, goal) ?: -1
    }

    fun part2(input: List<String>): Int {
        val grid = readGridFromChars(input)
        val goal = grid.findFirstIndex(-28)
        grid.rows[goal.row][goal.col] = 25
        val distances: List<Int> = grid.rows.mapIndexed { row, r ->
            r.mapIndexed { col, c ->
                if (c == 0) bfsDistanceToGoal(grid, Cell(row, col), goal) else null
            }
        }.flatten()
            .filterNotNull()
        return distances.min()
    }

    val testInput = readInputLines("Day12_test")
    check(part1(testInput) == 31)
    check(part2(testInput) == 29)

    val input = readInputLines("Day12_input")
    println(part1(input))
    println(part2(input))
}