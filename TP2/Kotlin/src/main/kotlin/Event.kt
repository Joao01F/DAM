package org.example

fun List<Event>.filterByUser(username: String) : List<Event> =
    filter { it.username == username }

fun List<Event>.totalSpent(username: String) : Double =
    filterIsInstance<Event.Purchase>().filter { it.username == username }.sumOf { it.amount }

fun processEvents(eventList: List<Event>, handler: (Event) -> Unit) =
    eventList.forEach { handler(it) }

fun handlerFun(event: Event) {
    when (event) {
        is Event.Login -> println("[LOGIN] ${event.username} logged in at t=${event.timestamp}")
        is Event.Purchase -> println("[PURCHASE] ${event.username} spent $${event.amount} at t=${event.timestamp}")
        is Event.Logout -> println("[LOGOUT] ${event.username} logged out at t=${event.timestamp}")
    }
}

sealed class Event {
    abstract val username: String

    class Login(override val username: String, val timestamp: Long) : Event() {
        override fun toString(): String {
            return "Login(username=$username, timestamp=$timestamp)"
        }
    }
    class Purchase(override val username: String, val amount: Double, val timestamp: Long) : Event() {
        override fun toString(): String {
            return "Purchase(username=$username, amount=$amount, timestamp=$timestamp)"
        }
    }
    class Logout(override val username: String, val timestamp: Long) : Event() {
        override fun toString(): String {
            return "Logout(username=$username, timestamp=$timestamp)"
        }
    }
}

fun main() {
    val events = listOf(
        Event.Login("alice", 1_000),
        Event.Purchase("alice", 49.99, 1_100),
        Event.Purchase("bob", 19.99, 1_200),
        Event.Login("bob", 1_050),
        Event.Purchase("alice", 15.00, 1_300),
        Event.Logout("alice", 1_400),
        Event.Logout("bob", 1_500)
    )

    processEvents(events, ::handlerFun)

    println("\nTotal spent by alice: $${String.format("%.2f", events.totalSpent("alice"))}")
    println("Total spent by bob: $${String.format("%.2f", events.totalSpent("bob"))}\n")

    val aliceEvents = events.filterByUser("alice")
    println("Events for alice:")
    aliceEvents.forEach { println("   $it") }
}