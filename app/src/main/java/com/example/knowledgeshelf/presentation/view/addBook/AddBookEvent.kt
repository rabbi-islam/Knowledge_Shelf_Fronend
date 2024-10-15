package com.example.knowledgeshelf.presentation.view.addBook

sealed class AddBookEvent {
    data class NameChanged(val name: String) : AddBookEvent()
    data class PriceChanged(val price: String) : AddBookEvent()
    data class AuthorNameChanged(val authorName: String) : AddBookEvent()
    data class StockChanged(val stock: String) : AddBookEvent()
    data class DescriptionChanged(val description: String) : AddBookEvent()
    //data object Submit : AddBookEvent()
}