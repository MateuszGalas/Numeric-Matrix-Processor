package processor

import java.lang.Math.pow
import kotlin.math.absoluteValue
import kotlin.math.pow
import kotlin.system.exitProcess

fun matrix(name: String = "") : MutableList<MutableList<Double>> {
    println("Enter size of ${name}matrix:")
    val (rowsOfMatrixA, columnsOfMatrixA) = readln().split(" ").map { it.toInt() }
    println("Enter ${name}matrix:")
    return MutableList(rowsOfMatrixA) { readln().split(" ").map { it.toDouble() }.toMutableList() }
}

fun multiplyByConstant(matrix: MutableList<MutableList<Double>>, multiplier: Double): MutableList<MutableList<Double>> {
    val finalMatrix = MutableList(matrix.size) { MutableList(matrix[0].size) { 0.0 } }

    for (i in finalMatrix.indices) {
        for (j in finalMatrix[0].indices) {
            if (matrix[i][j] == 0.0) continue
            finalMatrix[i][j] = matrix[i][j] * multiplier
        }
    }
    return finalMatrix
}

fun addMatrices() {
    val matrixA = matrix("first ")
    val matrixB = matrix("second ")

    if (matrixA.size != matrixB.size && matrixA[0].size != matrixB[0].size) {
        println("The operation cannot be performed.").also { return }
    }
    val finalMatrix = MutableList(matrixA.size) { MutableList(matrixA[0].size) { 0.0 } }

    for (i in finalMatrix.indices) {
        for (j in finalMatrix[0].indices) {
            finalMatrix[i][j] = matrixA[i][j] + matrixB[i][j]
        }
    }

    println(finalMatrix.joinToString("\n") { row -> row.joinToString(" ") })
    println()
}

fun multiplyMatrices() {
    val matrixA = matrix("first ")
    val matrixB = matrix("second ")

    if (matrixA[0].size != matrixB.size) println("The operation cannot be performed.").also { return }

    val finalMatrix = MutableList(matrixA.size) { MutableList(matrixB[0].size) { 0.0 } }

    for (i in matrixA.indices) {
        for (j in matrixB[0].indices) {
            var result = 0.0
            repeat(matrixB.size) {
                result += matrixA[i][it] * matrixB[it][j]
            }
            finalMatrix[i][j] = result
        }
    }

    println(finalMatrix.joinToString("\n") { row -> row.joinToString(" ") })
    println()
}

fun transpose() {
    println(
        "1. Main diagonal\n" +
                "2. Side diagonal\n" +
                "3. Vertical line\n" +
                "4. Horizontal line"
    )

    val choice = readln()
    val matrix = matrix("")

    val result = when (choice) {
        "1" -> transposeMainDiagonal(matrix)
        "2" -> transposeSideDiagonal(matrix)
        "3" -> transposeVerticalLine(matrix)
        else -> transposeHorizontalLine(matrix)
    }

    println(result.joinToString("\n") { row -> row.joinToString(" ") } + "\n")
}

fun transposeMainDiagonal(matrix: MutableList<MutableList<Double>>): MutableList<MutableList<Double>> {
    val result = MutableList(matrix.size) { MutableList(matrix.size) { 0.0 } }

    for (i in matrix.indices) {
        for (j in matrix[0].indices) {
            result[i][j] = matrix[j][i]
        }
    }
    return result
}

fun transposeSideDiagonal(matrix: MutableList<MutableList<Double>>): MutableList<MutableList<Double>> {
    val result = MutableList(matrix.size) { MutableList(matrix.size) { 0.0 } }

    for (i in matrix.indices) {
        for (j in matrix[0].indices) {
            result[i][j] = matrix[matrix.lastIndex - j][matrix.lastIndex - i]
        }
    }

    return result
}

fun transposeVerticalLine(matrix: MutableList<MutableList<Double>>): MutableList<MutableList<Double>> {
    val result = MutableList(matrix.size) { MutableList(matrix.size) { 0.0 } }

    for (i in matrix.indices) {
        for (j in matrix[0].indices) {
            result[i][j] = matrix[i][matrix.lastIndex - j]
        }
    }

    return result
}

fun transposeHorizontalLine(matrix: MutableList<MutableList<Double>>): MutableList<MutableList<Double>> {
    val result = MutableList(matrix.size) { MutableList(matrix.size) { 0.0 } }

    for (i in matrix.indices) {
        for (j in matrix[0].indices) {
            result[i][j] = matrix[matrix.lastIndex - i][j]
        }
    }

    return result
}

fun determine() {
    val matrix = matrix()
    if (matrix.size != matrix[0].size) println("Matrix is not a square matrix").also { return }
    println(determinant(matrix))
}

// Counting determinant of matrix using Minor and Cofactor
fun determinant(matrix: List<List<Double>>): Double {
    var result = 0.0

    if (matrix.size == 2) {
        result = matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0]
    } else {
        for (i in 0..matrix.lastIndex) {
            val list = matrix.map { it.toMutableList() }.toMutableList()
            list.removeAt(0)
            for (row in list) {
                row.removeAt(i)
            }
            result += matrix[0][i] * (-1.0).pow(2.0 + i) * determinant(list)
        }
    }
    return if (result == -0.0) result.absoluteValue else result
}

fun inverse() {
    val matrix = matrix()
    val det = determinant(matrix)
    if (det == 0.0) println("This matrix doesn't have an inverse.").also { return }

    var result = mutableListOf<MutableList<Double>>()

    for (i in 0..matrix.lastIndex) {
        result.add(mutableListOf())
        for (j in 0..matrix.lastIndex) {
            val list = matrix.map { it.toMutableList() }.toMutableList()
            list.removeAt(i)
            for (row in list) {
                row.removeAt(j)
            }
            val deter = determinant(list) * (-1.0).pow(2.0 + i + j)
            result[i].add(deter)
        }
    }
    result = transposeMainDiagonal(result)
    result = multiplyByConstant(result, 1.0 / det)
    println(result.joinToString("\n") { row -> row.joinToString(" ") })
    println()
}

fun main() {
    while (true) {
        println(
            "1. Add matrices\n" +
                    "2. Multiply matrix by a constant\n" +
                    "3. Multiply matrices\n" +
                    "4. Transpose matrix\n" +
                    "5. Calculate a determinant\n" +
                    "6. Inverse matrix\n" +
                    "0. Exit"
        )

        when (readln()) {
            "0" -> return
            "1" -> addMatrices()
            "2" -> {
                multiplyByConstant(matrix(""), readln().toDouble())
                    .joinToString("\n") { row -> row.joinToString(" ") }.let(::println)
            }

            "3" -> multiplyMatrices()
            "4" -> transpose()
            "5" -> determine()
            "6" -> inverse()
        }
    }

}
