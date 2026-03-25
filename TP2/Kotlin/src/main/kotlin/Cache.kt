package org.example

class Cache<K : Any, V : Any> {
    val map = mutableMapOf<K, V>()

    fun put(key: K, value: V) =
        map.put(key, value)

    fun get(key: K) =
        map[key]

    fun size() =
        map.size

    fun evict(key: K) =
        map.remove(key)

    fun getOrPut(key: K, default: () -> V): V {
        var value = get(key)
        if (value == null) {
            value = default()
            put(key, value)
        }
        return value
    }

    fun transform(key: K, action: (V) -> V): Boolean {
        val value = get(key)
        if (value != null) {
            put(key, action(value))
            return true
        }
        return false
    }

    fun snapshot(): Map<K, V> =
        map.toMap()
}

fun main() {
    val cacheSI = Cache<String, Int>()
    cacheSI.put("one", 1)
    cacheSI.put("two", 2)
    cacheSI.put("tree", 1)

    val cacheIS = Cache<Int, String>()
    cacheIS.put(1, "Alice")
    cacheIS.put(2, "Bob")
    cacheIS.put(3, "3")


    println("--- Word frequency cache ---")
    println("Size: ${cacheSI.size()}")
    println("Frequency of \"one\": ${cacheSI.get("one")}")
    println(
        "getOrPut \"tree\": ${
            cacheSI.getOrPut(
                "tree",
                default = { 0 }
            )
        }")
    println(
        "getOrPut \"four\": ${
            cacheSI.getOrPut(
                "two",
                default = { 0 }
            )
        }")
    println("Size after getOrPut: ${cacheSI.size()}")
    println("Transform \"one\" (+1): ${cacheSI.transform("one", action = { it + 1 })}")
    println("Transform \"one\" (+1): ${cacheSI.transform("siz", action = { it + 1 })}")
    println("Snapshot: ${cacheSI.snapshot()}")
}