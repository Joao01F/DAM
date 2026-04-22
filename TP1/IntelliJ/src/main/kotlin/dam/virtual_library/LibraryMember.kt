package org.example.dam.virtual_library

data class LibraryMember(
    val name: String,
    val membershipID: Int,
    val borrowedBooks: MutableList<Book>
)
