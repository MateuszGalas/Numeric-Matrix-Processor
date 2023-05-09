package processor

import kotlin.math.absoluteValue
import kotlin.math.pow
import kotlin.system.exitProcess

// Function to print a matrix
fun List<List<Double>>.printMatrix() {
    println(this.joinToString("\n") { row -> row.joinToString(" ") })
    println()
}

// Function to read a matrix from input
fun matrix(name: String = "") : MutableList<MutableList<Double>> {
    println("Enter size of ${name}matrix:")
    val (rowsOfMatrixA, _) = readln().split(" ").map { it.toInt() }
    println("Enter ${name}matrix:")
    return MutableList(rowsOfMatrixA) { readln().split(" ").map { it.toDouble() }.toMutableList() }
}

// Function to multiply a matrix by a constant
fun multiplyByConstant(matrix: List<List<Double>>, multiplier: Double): MutableList<MutableList<Double>> {
    return matrix.map { it.map { element -> element * multiplier }.toMutableList() }.toMutableList()
}

// Function to add two matrices
fun addMatrices(): List<List<Double>> {
    // Read two matrices from input
    val matrixA = matrix("first ")
    val matrixB = matrix("second ")

    // Check if matrices have the same dimensions
    if (matrixA.size != matrixB.size && matrixA[0].size != matrixB[0].size) {
        println("The operation cannot be performed.").also { exitProcess(1) }
    }
    // Add matrices and return the result
    return matrixA.zip(matrixB).map { rowPair -> rowPair.first.zip(rowPair.second).map { it.first + it.second } }
}

// Function to multiply two matrices
fun multiplyMatrices() {
    // Read two matrices from input
    val matrixA = matrix("first ")
    val matrixB = matrix("second ")

    // Check if matrices have compatible dimensions for multiplication
    if (matrixA[0].size != matrixB.size) println("The operation cannot be performed.").also { return }

    // Initialize the final matrix with zeros
    val finalMatrix = MutableList(matrixA.size) { MutableList(matrixB[0].size) { 0.0 } }

    // Perform matrix multiplication
    for (i in matrixA.indices) {
        for (j in matrixB[0].indices) {
            var result = 0.0
            repeat(matrixB.size) { result += matrixA[i][it] * matrixB[it][j] }
            finalMatrix[i][j] = result
        }
    }
    // Print the result
    finalMatrix.printMatrix()
}

// Function to transpose a matrix
fun transpose(): MutableList<MutableList<Double>> {
    // Print menu for transpose options
    println(
        "1. Main diagonal\n" +
                "2. Side diagonal\n" +
                "3. Vertical line\n" +
                "4. Horizontal line"
    )

    // Read transpose option and matrix from input
    val choice = readln()
    val matrix = matrix("")

    // Perform the selected transpose operation and return the result
    return when (choice) {
        "1" -> transposeMainDiagonal(matrix)
        "2" -> transposeSideDiagonal(matrix)
        "3" -> transposeVerticalLine(matrix)
        else -> transposeHorizontalLine(matrix)
    }
}

// Function to transpose a matrix by swapping elements along the main diagonal
fun transposeMainDiagonal(matrix: MutableList<MutableList<Double>>): MutableList<MutableList<Double>> {
    // Create a new matrix with swapped indices
    val result = MutableList(matrix.size) { MutableList(matrix.size) { 0.0 } }

    // Swap elements along the main diagonal
    for (i in matrix.indices) {
        for (j in matrix[0].indices) {
            result[i][j] = matrix[j][i]
        }
    }

    // Return the transposed matrix
    return result
}

// Function to transpose a matrix by swapping elements along the side diagonal
fun transposeSideDiagonal(matrix: MutableList<MutableList<Double>>): MutableList<MutableList<Double>> {
    // Create a new matrix with swapped indices
    val result = MutableList(matrix.size) { MutableList(matrix.size) { 0.0 } }

    // Swap elements along the side diagonal
    for (i in matrix.indices) {
        for (j in matrix[0].indices) {
            result[i][j] = matrix[matrix.lastIndex - j][matrix.lastIndex - i]
        }
    }

    // Return the transposed matrix
    return result
}

// Function to transpose a matrix by swapping elements along the vertical line
fun transposeVerticalLine(matrix: MutableList<MutableList<Double>>): MutableList<MutableList<Double>> {
    // Create a new matrix with swapped indices
    val result = MutableList(matrix.size) { MutableList(matrix.size) { 0.0 } }

    // Swap elements along the vertical line
    for (i in matrix.indices) {
        for (j in matrix[0].indices) {
            result[i][j] = matrix[i][matrix.lastIndex - j]
        }
    }

    // Return the transposed matrix
    return result
}

// Function to transpose a matrix by swapping elements along the horizontal line
fun transposeHorizontalLine(matrix: MutableList<MutableList<Double>>): MutableList<MutableList<Double>> {
    // Create a new matrix with swapped indices
    val result = MutableList(matrix.size) { MutableList(matrix.size) { 0.0 } }

    // Swap elements along the horizontal line
    for (i in matrix.indices) {
        for (j in matrix[0].indices) {
            result[i][j] = matrix[matrix.lastIndex - i][j]
        }
    }

    // Return the transposed matrix
    return result
}

// Function to determine the determinant of a square matrix
fun determine() {
    // Get the matrix from user input
    val matrix = matrix()

    // Check if the matrix is square
    if (matrix.size != matrix[0].size) {
        println("Matrix is not a square matrix").also { return }
    }

    // Print the determinant
    println(determinant(matrix))
}

// Function to calculate the determinant of a square matrix using the minor and cofactor method
fun determinant(matrix: List<List<Double>>): Double {
    var result = 0.0

    // Base case for a 2x2 matrix
    if (matrix.size == 2) {
        result = matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0]
    } else {
        // Recursive case for larger matrices
        for (i in 0..matrix.lastIndex) {
            val list = matrix.map { it.toMutableList() }.toMutableList()
            list.removeAt(0)
            for (row in list) {
                row.removeAt(i)
            }
            result += matrix[0][i] * (-1.0).pow(2.0 + i) * determinant(list)
        }
    }
    // If the result is -0.0, return its absolute value to avoid negative zero.
    return if (result == -0.0) result.absoluteValue else result
}

// This function returns the inverse of a matrix.
fun inverse() : MutableList<MutableList<Double>> {
    // Create a new matrix using the matrix() function.
    val matrix = matrix()
    // Calculate the determinant of the matrix.
    val det = determinant(matrix)
    // If the determinant is zero, the matrix has no inverse. Exit the program with an error message.
    if (det == 0.0) println("This matrix doesn't have an inverse.").also { exitProcess(1) }

    // Create an empty list of mutable lists to store the inverse matrix.
    var result = mutableListOf<MutableList<Double>>()

    // Iterate through the matrix to calculate the elements of the inverse matrix.
    for (i in 0..matrix.lastIndex) {
        result.add(mutableListOf())
        for (j in 0..matrix.lastIndex) {
            // Create a new list of mutable lists that is a copy of the input matrix.
            val list = matrix.map { it.toMutableList() }.toMutableList()
            // Remove the ith row and the jth column from the list of mutable lists to create the minor matrix.
            list.removeAt(i)
            for (row in list) {
                row.removeAt(j)
            }
            // Calculate the determinant of the minor matrix, multiply it by (-1)^(i+j), and add it to the result list.
            val deter = determinant(list) * (-1.0).pow(2.0 + i + j)
            result[i].add(deter)
        }
    }
    // Transpose the result matrix and divide all of its elements by the determinant.
    result = transposeMainDiagonal(result)
    return multiplyByConstant(result, 1.0 / det)
}

// The main function contains a menu that allows the user to perform various matrix operations.
fun main() {
    while (true) {
        // Print the menu options.
        println(
            "1. Add matrices\n" +
                    "2. Multiply matrix by a constant\n" +
                    "3. Multiply matrices\n" +
                    "4. Transpose matrix\n" +
                    "5. Calculate a determinant\n" +
                    "6. Inverse matrix\n" +
                    "0. Exit"
        )
        // Read the user choice and call a function.
        when (readln()) {
            "0" -> return
            "1" -> addMatrices().printMatrix()
            "2" -> multiplyByConstant(matrix(""), readln().toDouble()).printMatrix()
            "3" -> multiplyMatrices()
            "4" -> transpose().printMatrix()
            "5" -> determine()
            "6" -> inverse().printMatrix()
        }
    }

}
