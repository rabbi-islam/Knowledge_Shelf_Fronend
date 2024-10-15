package com.example.knowledgeshelf.presentation.view

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.knowledgeshelf.R
import com.example.knowledgeshelf.data.model.book.Books
import com.example.knowledgeshelf.domain.Resource
import com.example.knowledgeshelf.presentation.view.components.BookCard
import com.example.knowledgeshelf.presentation.viewmodel.BookViewmodel
import com.example.knowledgeshelf.utils.Utils
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewmodel: BookViewmodel = hiltViewModel()
) {
    val bookState by viewmodel.bookResult.collectAsState()
    val deleteBookState by viewmodel.deleteBookResult.collectAsState()
    val userInfoFromToken by viewmodel.userInfoFromToken.collectAsState()

    val context = LocalContext.current
    // Load user profile when the screen is launched
    LaunchedEffect(Unit) {
        viewmodel.getUserInfoFromJwtToken(context)
    }

    // Show deletion message if available
    LaunchedEffect(deleteBookState) {
        if (deleteBookState is Resource.Success) {
            //Utils.showColoredToast(context,(deleteBookState as Resource.Success).message!!)
            Toast.makeText(context, (deleteBookState as Resource.Success).message, Toast.LENGTH_SHORT).show()
        } else if (deleteBookState is Resource.Error) {
            // Show a Toast or Snackbar for the error message
            Toast.makeText(context, (deleteBookState as Resource.Error).message, Toast.LENGTH_SHORT).show()
        }
    }


    // Wrap the entire screen in a Box for alignment
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center // Center content in the box
    ) {
        when (bookState) {
            is Resource.Loading -> {
                // Center the CircularProgressIndicator
                CircularProgressIndicator()
            }
            is Resource.Error -> {
                // Show an error message
                Text(text = (bookState as Resource.Error).message)
            }
            is Resource.Success -> {
                val books = (bookState as Resource.Success<Books>).data!!.books // Extracting the list of books

                // Using LazyVerticalGrid to display books in a grid format
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2), // Specify 2 columns
                    contentPadding = PaddingValues(4.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(books) { book ->
                        BookCard(
                            bookImage = book.image,
                            bookName = book.name,
                            authorName = book.authorName,
                            price = "$${book.price}",
                            role = userInfoFromToken?.role.toString(),
                            onDeleteClick = {
                                viewmodel.deleteBook(book._id)
                            },
                            onBookClick = {
                            },
                            onCartClick = {}

                        )
                    }
                }
            }
            null -> Text(text = "No Book Found")
        }

        // Show loading indicator when deleting
        if (deleteBookState is Resource.Loading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(8.dp)) // Space between progress indicator and text
                    Text(text = "Deleting...", color = Color.White) // Message indicating deletion
                }
            }
        }
    }

}
