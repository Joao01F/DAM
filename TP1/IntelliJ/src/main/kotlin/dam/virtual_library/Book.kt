package org.example.dam.virtual_library

abstract class Book(
    val title: String,
    val author: String,
    val publicationYear: Int,
    availableCopies: Int
) {
    init {
        println("Book \'$title\' by $author has been added to the library.")
    }
    val publicationEra: String
        get() = when {
            publicationYear < 1980 -> "Classic"
            publicationYear in 1980..2010 -> "Modern"
            else -> "Contemporary"
        }

    var availableCopies: Int = availableCopies
        set(value) {
            require(value >= 0) { "Error: Book cannot have negative stock!" }
            // availableCopies = value is infinite recursion
            // field is the value stored in memory
            field = value
        }

    override fun toString(): String {
        return "Title: $title | Author: $author | Publication Era: $publicationEra | Available Copies: $availableCopies"
    }

    abstract fun getStorageInfo(): String
}