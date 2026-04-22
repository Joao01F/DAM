package dam.exer_1

fun main() {
    // "it" is the index in the IntArray
    val intArrSquares = IntArray(50) { squared(it+1)}
    val rangeMapSquares = (1..50).map { squared(it) }
    val arraySquares = Array(50) { squared(it+1) }

    println("\n" + "IntArray: " + intArrSquares.contentToString())
    println("\n" + "RangeMap: " + rangeMapSquares)
    println("\n" + "Array: " + arraySquares.contentToString())
}

fun squared(x: Int) = x * x