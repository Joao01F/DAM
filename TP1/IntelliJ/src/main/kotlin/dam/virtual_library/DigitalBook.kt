package org.example.dam.virtual_library

class DigitalBook(
    title: String,
    author: String,
    publicationYear: Int,
    availableCopies: Int,
    val fileSize: Double,
    val format: String
) : Book(title, author, publicationYear, availableCopies) {

    override fun getStorageInfo(): String {
        return "Stored Digitally: $fileSize MB, Format: $format"
    }

    override fun toString(): String {
        return super.toString() + "\n" + getStorageInfo()
    }
}