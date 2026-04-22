package dam.exer_2

const val spaceStr = "separated by a space (\" \")"
const val exit = "Type \"menu\" to return to the menu."
val menu = String.format(
    "%nPlease choose the operation you want to do: %n" +
            "%n Arithmetic Operations:" +
            "%n 0 - Addition (+)" +
            "%n 1 - Subtraction (-)" +
            "%n 2 - Multiplication (*)" +
            "%n 3 - Division (/) %n" +
            "%n Boolean Operations:" +
            "%n 4 - AND (&&)" +
            "%n 5 - OR (||)" +
            "%n 6 - NOT (!) %n" +
            "%n Bitwise Shift Operations:" +
            "%n 7 - Left Shift (shl)" +
            "%n 8 - Right Shift (shr) %n" +
            "%n 9 - Exit %n%n"
)

fun main() {
    println("Welcome to the calculator app! \n")
    while (true) {
        print(menu)
        // readln is the new readLine that doesn't need null handling (?. (safe call) or !!. (assert not null))
        try {
            when (val input = readln().toInt()) {
                in 0..3 -> arithOp(input)
                in 4..6 -> boolOp(input)
                in 7..8 -> shiftOp(input)
                9 -> {
                    print("\nExiting...")
                    break
                }

                else -> print("There is no such operation.")
            }
        } catch (e: NumberFormatException) {
            print("Invalid Input. ${e.cause}")
        }
    }

}

fun arithOp(operator: Int) {
    while (true) {
        println("Please input two numbers, $spaceStr, to do the operation with. $exit")
        val inputStr = readln()
        // == works the same as java .equals(str)
        if (inputStr == "menu") break
        try {
            val input = inputStr.split(" ").map { it.toFloat() }
            if (input.size == 2) {
                if (operator == 3 && input[1] == 0f) {
                    println("Cannot divide by 0.")
                } else {
                    when (operator) {
                        0 -> println("Addition: ${input[0]} + ${input[1]} = ${input[0] + input[1]}")
                        1 -> println("Subtraction: ${input[0]} - ${input[1]} = ${input[0] - input[1]}")
                        2 -> println("Multiplication: ${input[0]} * ${input[1]} = ${input[0] * input[1]}")
                        3 -> println("Division: ${input[0]} / ${input[1]} = ${input[0] / input[1]}")
                    }
                }
                println()
            } else println("Invalid input.")
        } catch (e: Exception) {
            println("Invalid input. ${e.toString()}")
        }
    }
}

fun boolOp(operator: Int) {
    while (true) {
        println("Please input two numbers, $spaceStr, to do the operation with. $exit")
        val inputStr = readln()
        if (inputStr == "menu") break
        try {
            val input = inputStr.split(" ").map { it.toInt() }
            if (input.size > 2) println("Invalid input.")
            else {
                when (operator) {
                    4 -> {
                        // "and" for bitwise operation between Int (&& only for boolean)
                        println("AND: ${input[0]} && ${input[1]} = ${(input[0] and input[1])}")
                    }

                    5 -> {
                        // "or" for bitwise operation between Int (|| only for boolean)
                        println("OR: ${input[0]} || ${input[1]} = ${(input[0] or input[1])}")
                    }

                    6 -> {
                        // "not" for bitwise operation between Int (! only for boolean)
                        // Integer stored using 32 bits
                        // inv flips all
                        // leftmost is signal
                        // flip all again and add one to get the final value
                        if (input.size == 1)
                            println("NOT: !${input[0]} = ${input[0].inv()}") else println("Invalid input. Too many inputs.")
                    }
                }
            }
        } catch (e: Exception) {
            println("Invalid input. ${e.toString()}")
        }
    }
}

fun shiftOp(operator: Int) {
    while (true) {
        println("Please input the number to do the operation and how many bits to shift it by, $spaceStr. $exit")
        val inputStr = readln()
        if (inputStr == "menu") break
        try {
            val input = inputStr.split(" ")
            when (operator) {
                7 -> println(input[0].toInt() shl input[1].toInt())
                8 -> println(input[0].toInt() shr input[1].toInt())
            }
        } catch (e: Exception) {
            println("Invalid Input. ${e.toString()}")
        }
    }
}