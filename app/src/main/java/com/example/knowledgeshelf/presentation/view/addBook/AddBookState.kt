package com.example.knowledgeshelf.presentation.view.addBook

data class AddBookState(
  val name:String = "",
  val price:Double =0.0,
  val authorName:String = "",
  val stock:Int = 0,
  val description:String = "",
)
