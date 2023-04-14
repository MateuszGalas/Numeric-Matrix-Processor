package processor

fun main() {
    val (rowsOfMatrix, columnsOfMatrix) = readln().split(" ").map { it.toInt() }
    val matrix = List(rowsOfMatrix) { readln().split(" ").map { it.toInt() } }
    val multiplier = readln().toInt()

    val finalMatrix = MutableList(rowsOfMatrix) { MutableList(columnsOfMatrix) { 0 } }

    for (i in finalMatrix.indices) {
        for (j in finalMatrix[0].indices) {
            finalMatrix[i][j] = matrix[i][j] * multiplier
        }
    }

    println(finalMatrix.joinToString("\n") { row -> row.joinToString(" ") })
}
