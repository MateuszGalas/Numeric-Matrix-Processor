package processor

import kotlin.system.exitProcess

fun main() {
    val (rowsOfMatrixA, columnsOfMatrixA) = readln().split(" ").map { it.toInt() }
    val matrixA = List(rowsOfMatrixA) { readln().split(" ").map { it.toInt() } }
    val (rowsOfMatrixB, columnsOfMatrixB) = readln().split(" ").map { it.toInt() }
    val matrixB = List(rowsOfMatrixB) { readln().split(" ").map { it.toInt() } }

    if (rowsOfMatrixA != rowsOfMatrixB && columnsOfMatrixA != columnsOfMatrixB) println("ERROR").also { exitProcess(1) }
    val finalMatrix = MutableList(rowsOfMatrixA) { MutableList(columnsOfMatrixA) { 0 } }

    for (i in finalMatrix.indices) {
        for (j in finalMatrix[0].indices) {
            finalMatrix[i][j] = matrixA[i][j] + matrixB[i][j]
        }
    }

    println(finalMatrix.joinToString("\n") { row -> row.joinToString(" ") })
}
