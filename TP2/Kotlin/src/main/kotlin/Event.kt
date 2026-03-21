package org.example

fun List<Event>.filterByUser(username: String) =
    filter { it.username == username }

fun List<Event>.totalSpent(username: String) =
    filterIsInstance<Event.Purchase>().filter { it.username == username }.sumOf { it.amount }

fun processEvents(eventList: List<Event>, handler: (Event) -> Unit) =
    eventList.forEach { handler(it) }

sealed class Event {
    abstract val username: String

    class Login(override val username: String, val timestamp: Long) : Event()
    class Purchase(override val username: String, val amount: Double, val timestamp: Long) : Event()
    class Logout(override val username: String, val timestamp: Long) : Event()
}

fun main() {
    val events = listOf(
        Event.Login(" alice ", 1_000),
        Event.Purchase(" alice ", 49.99, 1_100),
        Event.Purchase("bob ", 19.99, 1_200),
        Event.Login("bob ", 1_050),
        Event.Purchase(" alice ", 15.00, 1_300),
        Event.Logout(" alice ", 1_400),
        Event.Logout("bob ", 1_500)
    )
}