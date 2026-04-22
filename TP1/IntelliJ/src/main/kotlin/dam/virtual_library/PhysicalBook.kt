package org.example.dam.virtual_library

class PhysicalBook(
    title: String,
    author: String,
    publicationYear: Int,
    availableCopies: Int,
    val weight: Int,
    val hasHardcover: Boolean = true
) : Book(title, author, publicationYear, availableCopies) {

    override fun getStorageInfo(): String {
        return "Physical Book: ${weight}g, Hardcover: ${if (hasHardcover) "Yes" else "No"}"
    }

    override fun toString(): String {
        return super.toString() + "\n" + getStorageInfo()
    }
}