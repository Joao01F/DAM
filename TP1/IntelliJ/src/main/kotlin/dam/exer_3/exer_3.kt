package dam.exer_3

const val initHeight = 100.0
const val nextBounceScale = .6

fun main() {
    val bounces = generateSequence(initHeight) { it * nextBounceScale }
    // To round can also use BigDecimal(num).setScale(2, RoundingMode.HALF_UP) (need import)
    bounces.take(15).takeWhile { it > 1 }.toList().forEach { println("%.2f".format(it)) }
}