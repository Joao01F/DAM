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
        addStage("Trim") { it.map { line -> line.trim() } }
        addStage("Filter error") { it.filter { it.contains("ERROR") } }
        addStage("Uppercase") { it.map { line -> line.uppercase() } }
        addStage("Add index") {
            var index = 0
            it.map { line -> "${++index}. $line" }
        }
    }

    println("Pipeline stages:")
    pipeline.describe()
    val result = pipeline.execute(logs)
    println("\nResult:")
    result.forEach { println("  $it") }
}