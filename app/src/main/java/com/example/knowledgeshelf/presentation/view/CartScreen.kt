package com.example.knowledgeshelf.presentation.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.knowledgeshelf.presentation.view.components.CheckOutCardItem

@Composable
fun CartScreen(
    modifier: Modifier = Modifier,
    bookId: String,
    bookName: String,
    authorName: String,
    price: String,
    imageUrl: String
) {
    

    if (bookId.isEmpty()) {
        // Display a message or placeholder when no book is selected
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("No book selected. Please choose a book from the Home screen.")
        }
    } else {


        // Display book details as before
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            CheckOutCardItem(
                productName = bookName,
                authorName = authorName,
                price = price.toDouble(),
                image = imageUrl
            )
        }
    }
}