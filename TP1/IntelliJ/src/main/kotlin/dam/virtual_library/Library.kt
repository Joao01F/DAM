package org.example.dam.virtual_library

class Library (
    val libName: String
){
    val books: MutableList<Book> = mutableListOf()

    companion object BookCounter {
        var numBooksCreated: Int = 0
        var numBooksInStock: Int = 0
        fun getTotalBooksCreated(): Int {
            return numBooksCreated
        }
        fun getTotalBooksInStock(): Int {
            return numBooksInStock
        }
    }

    fun addBook(book: Book) {
        books.add(book)
        numBooksCreated++
        numBooksInStock++
    }

    fun borrowBook(title: String) {
        val book = books.find { it.title == title }
        if (book != null && book.availableCopies > 0) {
            numBooksInStock--
            val numCopies = --book.availableCopies
            println("Successfully borrowed \'${book.title}\'. Copies remaining: $numCopies")
            if (numCopies == 0) println("Warning: Book is now out of stock!")
        } else
            println("No books titled \"$title\" were found in stock")
    }

    fun returnBook(title: String) {
        val book = books.find { it.title == title }
        if (book != null) {
            numBooksInStock++
            println("Book $title returned successfully. Copies available: ${++book.availableCopies}")
        } else
            println("Book \"$title\" not in collection. Please add it manually.")
    }

    fun showBooks() {
        println("--- Library Catalog ---")
        books.forEach { println("$it \n") }
    }

    fun searchByAuthor(author: String) {
        val searchResult = books.filter { it.author == author }
        if (searchResult.isEmpty()) println("No books found by author $author.")
        else {
            println("Books by $author:")
            searchResult.forEach {
                println(
                    "   - ${it.title} " +
                            "(${it.publicationEra}, " +
                            "${it.availableCopies} ${if (it.availableCopies == 1) "copy" else "copies"} available)"
                )
            }
        }
    }
}

fun main() {
    val library = Library("Central Library")

    val digitalBook = DigitalBook(
        "Kotlin in Action",
        "Dmitry Jemerov",
        2017,
        5,
        4.5,
        "PDF"
    )

    val physicalBook = PhysicalBook(
        "Clean Code",
        "Robert C. Martin",
        2008,
        3,
        650,
        true
    )

    val classicBook = PhysicalBook(
        "1984",
        "George Orwell",
        1949,
        2,
        400,
        false
    )

    library.addBook(digitalBook)
    library.addBook(physicalBook)
    library.addBook(classicBook)
    library.showBooks()
    println("\nTotal Books Created: " + Library.getTotalBooksCreated())
    println("Total Books In Stock: " + Library.getTotalBooksInStock())

    println("\n--- Borrowing Books ---")
    library.borrowBook("Clean Code")
    library.borrowBook("1984")
    library.borrowBook("1984")
    library.borrowBook("1984") // Should fail - no copies left
    println("\nTotal Books Created: " + Library.getTotalBooksCreated())
    println("Total Books In Stock: " + Library.getTotalBooksInStock())

    println("\n--- Returning Books ---")
    library.returnBook("1984")
    println("\nTotal Books Created: " + Library.getTotalBooksCreated())
    println("Total Books In Stock: " + Library.getTotalBooksInStock())

    println("\n--- Search by Author ---")
    library.searchByAuthor("George Orwell")
}