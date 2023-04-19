package processor

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

fun main() {

    while (true) {
        println(
            "1. Add matrices\n" +
            "2. Multiply matrix by a constant\n" +
            "3. Multiply matrices\n" +
            "0. Exit"
        )

        when (readln()) {
            "0" -> return
            "1" -> addMatrices()
            "2" -> multiplyByConstant()
            "3" -> multiplyMatrices()
        }
    }


}
