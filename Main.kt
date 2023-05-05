package processor

import java.lang.Math.pow
import kotlin.math.pow
import kotlin.system.exitProcess

fun multiplyByConstant() {
    val (rowsOfMatrix, columnsOfMatrix) = readln().split(" ").map { it.toInt() }
    val matrix = List(rowsOfMatrix) { readln().split(" ").map { it.toDouble() } }
    val multiplier = readln().toDouble()

    val finalMatrix = MutableList(rowsOfMatrix) { MutableList(columnsOfMatrix) { 0.0 } }

    for (i in finalMatrix.indices) {
        for (j in finalMatrix[0].indices) {
            finalMatrix[i][j] = matrix[i][j] * multiplier
        }
    }

    println(finalMatrix.joinToString("\n") { row -> row.joinToString(" ") })
    println()
}

fun addMatrices() {
    println("Enter size of first matrix:")
    val (rowsOfMatrixA, columnsOfMatrixA) = readln().split(" ").map { it.toInt() }
    println("Enter first matrix:")
    val matrixA = List(rowsOfMatrixA) { readln().split(" ").map { it.toDouble() } }
    println("Enter size of second matrix:")
    val (rowsOfMatrixB, columnsOfMatrixB) = readln().split(" ").map { it.toInt() }
    println("Enter second matrix:")
    val matrixB = List(rowsOfMatrixB) { readln().split(" ").map { it.toDouble() } }

    if (rowsOfMatrixA != rowsOfMatrixB && columnsOfMatrixA != columnsOfMatrixB) {
        println("The operation cannot be performed.").also { return }
    }
    val finalMatrix = MutableList(rowsOfMatrixA) { MutableList(columnsOfMatrixA) { 0.0 } }

    for (i in finalMatrix.indices) {
        for (j in finalMatrix[0].indices) {
            finalMatrix[i][j] = matrixA[i][j] + matrixB[i][j]
        }
    }

    println(finalMatrix.joinToString("\n") { row -> row.joinToString(" ") })
    println()
}

fun multiplyMatrices() {
    println("Enter size of first matrix:")
    val (rowsOfMatrixA, columnsOfMatrixA) = readln().split(" ").map { it.toInt() }
    println("Enter first matrix:")
    val matrixA = List(rowsOfMatrixA) { readln().split(" ").map { it.toDouble() } }
    println("Enter size of second matrix:")
    val (rowsOfMatrixB, columnsOfMatrixB) = readln().split(" ").map { it.toInt() }
    println("Enter second matrix:")
    val matrixB = List(rowsOfMatrixB) { readln().split(" ").map { it.toDouble() } }

    if (columnsOfMatrixA != rowsOfMatrixB) println("The operation cannot be performed.").also { return }

    val finalMatrix = MutableList(rowsOfMatrixA) { MutableList(columnsOfMatrixB) { 0.0 } }

    for (i in matrixA.indices) {
        for (j in matrixB[0].indices) {
            var result = 0.0
            repeat(rowsOfMatrixB) {
                result += matrixA[i][it] * matrixB[it][j]
            }
            finalMatrix[i][j] = result
        }
    }

    println(finalMatrix.joinToString("\n") { row -> row.joinToString(" ") })
    println()
}

fun transpose() {
    println("1. Main diagonal\n" +
            "2. Side diagonal\n" +
            "3. Vertical line\n" +
            "4. Horizontal line")

    val choice = readln()
    println("Enter matrix size:")
    val (rowsOfMatrix, columnsOfMatrix) = readln().split(" ").map { it.toInt() }
    println("Enter matrix:")
    val matrix = List(rowsOfMatrix) { readln().split(" ") }

    val result = when(choice) {
        "1" -> transposeMainDiagonal(matrix)
        "2" -> transposeSideDiagonal(matrix)
        "3" -> transposeVerticalLine(matrix)
        else -> transposeHorizontalLine(matrix)
    }

    println(result.joinToString("\n") { row -> row.joinToString(" ") } + "\n")
}

fun transposeMainDiagonal(matrix : List<List<String>>) : List<List<String>> {
    val result = MutableList(matrix.size) { MutableList(matrix.size) { "" } }

    for (i in matrix.indices) {
        for (j in matrix[0].indices) {
            result[i][j] = matrix[j][i]
        }
    }
    return result
}

fun transposeSideDiagonal(matrix : List<List<String>>) : List<List<String>> {
    val result = MutableList(matrix.size) { MutableList(matrix.size) { "" } }

    for (i in matrix.indices) {
        for (j in matrix[0].indices) {
            result[i][j] = matrix[matrix.lastIndex - j][matrix.lastIndex - i]
        }
    }

    return result
}

fun transposeVerticalLine(matrix : List<List<String>>) : List<List<String>> {
    val result = MutableList(matrix.size) { MutableList(matrix.size) { "" } }

    for (i in matrix.indices) {
        for (j in matrix[0].indices) {
            result[i][j] = matrix[i][matrix.lastIndex - j]
        }
    }

    return result
}

fun transposeHorizontalLine(matrix : List<List<String>>) : List<List<String>> {
    val result = MutableList(matrix.size) { MutableList(matrix.size) { "" } }

    for (i in matrix.indices) {
        for (j in matrix[0].indices) {
            result[i][j] = matrix[matrix.lastIndex - i][j]
        }
    }

    return result
}

fun determine() {
    println("Enter matrix size:")
    val (rowsOfMatrix, columnsOfMatrix) = readln().split(" ").map { it.toInt() }
    println("Enter matrix:")
    val matrix = List(rowsOfMatrix) { readln().split(" ").map { it.toDouble() } }

    if (rowsOfMatrix != columnsOfMatrix) println("Matrix is not a square matrix").also { return }

    println(determinant(matrix))
}

// Counting determinant of matrix using Minor and Cofactor
fun determinant(matrix: List<List<Double>>): Double {
    var result = 0.0

    return if (matrix.size == 2) {
        matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0]
    } else {
        for (i in 0..matrix.lastIndex) {
            val list = matrix.map { it.toMutableList() }.toMutableList()
            list.removeAt(0)
            for (row in list) {
                row.removeAt(i)
            }

            result += matrix[0][i] * (-1.0).pow(2.0 + i) * determinant(list)
        }
        result
    }
}

fun main() {

    while (true) {
        println(
            "1. Add matrices\n" +
            "2. Multiply matrix by a constant\n" +
            "3. Multiply matrices\n" +
            "4. Transpose matrix\n" +
            "5. Calculate a determinant\n" +
            "0. Exit"
        )

        when (readln()) {
            "0" -> return
            "1" -> addMatrices()
            "2" -> multiplyByConstant()
            "3" -> multiplyMatrices()
            "4" -> transpose()
            "5" -> determine()
        }
    }

}
