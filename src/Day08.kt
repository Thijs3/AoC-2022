fun main() {
    fun IntGrid.isCellVisibleFromLeft(row: Int, col: Int) = rows[row].take(col)
        .all { value -> value < rows[row][col] }

    fun IntGrid.isCellVisibleFromRight(row: Int, col: Int) = rows[row].takeLast(cols.size - 1 - col)
        .all { value -> value < rows[row][col] }

    fun IntGrid.isCellVisibleFromTop(row: Int, col: Int) = cols[col].take(row)
        .all { value -> value < rows[row][col]}

    fun IntGrid.isCellVisibleFromBottom(row: Int, col: Int) = cols[col].takeLast(rows.size - 1 - row)
        .all { value -> value < rows[row][col] }

    fun IntGrid.isCellVisibleInRow(row: Int, col: Int): Boolean =
        isCellVisibleFromLeft(row, col) || isCellVisibleFromRight(row, col)

    fun IntGrid.isCellVisibleInColumn(row: Int, col: Int): Boolean =
        isCellVisibleFromTop(row, col) || isCellVisibleFromBottom(row, col)

    fun IntGrid.isCellVisible(row: Int, col: Int): Boolean {
        if (row == 0 || col == 0 || row == rows.size - 1 || col == cols.size - 1) return true
        return isCellVisibleInRow(row, col) || isCellVisibleInColumn(row, col)
    }

    fun IntGrid.calculateScenicScore(row: Int, col: Int): Long {
        val left: Long = rows[row].mapIndexed { index, i -> index to i }
            .lastOrNull { (ind, height) -> ind < col && height >= rows[row][col] }?.first?.toLong() ?: 0L
        val right: Long = rows[row].mapIndexed { index, i -> index to i }
            .firstOrNull { (ind, height) -> ind > col && height >= rows[row][col] }?.first?.toLong() ?: (cols.size - 1).toLong()
        val top: Long = cols[col].mapIndexed { index, i -> index to i }
            .lastOrNull { (ind, height) -> ind < row && height >= rows[row][col] }?.first?.toLong() ?: 0L
        val bottom: Long = cols[col].mapIndexed { index, i -> index to i }
            .firstOrNull { (ind, height) -> ind > row && height >= rows[row][col] }?.first?.toLong() ?: (rows.size - 1).toLong()
        return (col - left) * (right - col) * (row - top) * (bottom - row)
    }

    fun part1(grid: IntGrid): Int = grid.rows.mapIndexed { row, values ->
        List(values.size) { col ->
            grid.isCellVisible(row, col)
        }
    }.flatten()
        .count()

    fun part2(grid: IntGrid): Long = grid.rows.mapIndexed { row, values ->
        List(values.size) { col ->
            grid.calculateScenicScore(row, col)
        }
    }.flatten()
        .max()


    val testInput = readIntGrid("Day08_test")
    check(part1(testInput) == 21)
    check(part2(testInput) == 8L )

    val input = readIntGrid("Day08_input")
    println(part1(input))
    println(part2(input))
}
