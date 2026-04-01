package org.example

class Pipeline {
    val steps = mutableMapOf<String, (List<String>) -> List<String>>()

    fun addStage(name: String, transform: (List<String>) -> List<String>) =
        steps.put(name, transform)

    fun execute(input: List<String>): List<String> {
        var result = input
        steps.forEach { result = it.value(result) }
        return result
    }

    fun describe() {
        var index = 0
        steps.forEach { println("   ${++index}. ${it.key}") }
    }
}

fun buildPipeline(block: Pipeline.() -> Unit): Pipeline {
    val pipeline = Pipeline()
    pipeline.block()
    return pipeline
}

fun main() {
    val logs = listOf(
        " INFO : server started ",
        " ERROR : disk full ",
        " DEBUG : checking config ",
        " ERROR : out of memory ",
        " INFO : request received ",
        " ERROR : connection timeout "
    )

    val pipeline = buildPipeline {
        addStage("Trim") { it.map { it.trim() } }
        //addStage("Trim") { it.map { line -> line.trim() } }
        //addStage("Trim") { it.map(String::trim) }

        addStage("Filter error") { it.filter { it.contains("ERROR") } }
        //addStage("Filter error") { it.filter { line -> line.contains("ERROR") } }  // explicit name
        //addStage("Filter error") { it.filter { "ERROR" in it } }  // using 'in' operator

        addStage("Uppercase") { it.map { line -> line.uppercase() } }
        //addStage("Uppercase") { it.map(String::uppercase) }

        addStage("Add index") {
            var index = 0
            it.map { line -> "${++index}. $line" }
        }
        //addStage("Add index") { it.mapIndexed { index, line -> "${index + 1}. $line" } }
    }

    println("Pipeline stages:")
    pipeline.describe()
    val result = pipeline.execute(logs)
    println("\nResult:")
    result.forEach { println("  $it") }
}