package com.example.knowledgeshelf.presentation.view

import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.rounded.Upload
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.knowledgeshelf.data.model.book.AddBookRequest
import com.example.knowledgeshelf.data.model.book.AddBookResponse
import com.example.knowledgeshelf.domain.Resource
import com.example.knowledgeshelf.presentation.viewmodel.BookViewmodel
import com.example.knowledgeshelf.utils.Utils
import com.example.knowledgeshelf.utils.Utils.uriToPngMultipart
import com.example.knowledgeshelf.utils.Validator
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.date_time.DateTimeDialog
import com.maxkeppeler.sheets.date_time.models.DateTimeSelection
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddBookScreen(
    modifier: Modifier = Modifier,
    viewmodel: BookViewmodel = hiltViewModel(),
//    onSubmit: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var authorName by remember { mutableStateOf("") }
    var stock by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    var imageError by remember { mutableStateOf("") }

    val addBookState by viewmodel.addBookResult.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(addBookState) {
        when (addBookState) {
            is Resource.Success -> {
                Toast.makeText(context, (addBookState as Resource.Success<AddBookResponse>).message ?: "Book added successfully", Toast.LENGTH_SHORT).show()
            }
            is Resource.Error -> {
                Toast.makeText(context, (addBookState as Resource.Error<AddBookResponse>).message ?: "Error Occurred", Toast.LENGTH_SHORT).show()
            }
            Resource.Loading -> {
                // Optionally show a loading indicator
            }
            null -> {
                // No action needed
            }
        }
    }




    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var imageFile by remember { mutableStateOf<File?>(null)}
    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {uri->
            selectedImageUri = uri
        }
    )


    val selectedDate = remember { mutableStateOf<LocalDate?>(LocalDate.now()) }
    val dateDialogState = rememberUseCaseState()
    val dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    DateTimeDialog(
        state = dateDialogState,
        selection = DateTimeSelection.Date { newDate ->
            selectedDate.value = newDate
        }
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        Text(
            text = "Add a New Book",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            ),
            modifier = Modifier.padding(bottom = 16.dp),
        )

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Book Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = RoundedCornerShape(20),
            singleLine = true
        )

        // Price
        OutlinedTextField(
            value = price,
            onValueChange = { price = it },
            label = { Text("Price") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = RoundedCornerShape(20),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        // Author Name
        OutlinedTextField(
            value = authorName,
            onValueChange = { authorName = it },
            label = { Text("Author Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = RoundedCornerShape(20),
            singleLine = true
        )

        // Stock
        OutlinedTextField(
            value = stock,
            onValueChange = { stock = it },
            label = { Text("Stock") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = RoundedCornerShape(20),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        // Description
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = RoundedCornerShape(20),
            singleLine = true
        )

        // Published Date - Open DatePickerDialog on click
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {


            Text(
                text = if(selectedDate.value==null) "Published Date:" else "Published Date: ${selectedDate.value!!.format(dateFormatter)}",
                style = MaterialTheme.typography.bodyLarge
            )

            IconButton(onClick = { dateDialogState.show() }) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Select Date"
                )
            }
        }
        //image
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = imageError.ifEmpty { "Upload Image" },
                color = if (imageError.isNotEmpty()) Color.Red else Color.Black, // Apply color conditionally
                style = MaterialTheme.typography.bodyLarge
            )
            IconButton(onClick = {
                singlePhotoPickerLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            }) {
                Icon(
                    imageVector = Icons.Rounded.Upload,
                    contentDescription = "Select due date"
                )
            }
        }


        // Image Preview (if image URL is available)

        // Image Preview
        if (selectedImageUri != null) {
            AsyncImage(
                model = selectedImageUri,
                contentDescription = "Book Image",
                modifier = Modifier
                    .size(100.dp)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
        }


        // Submit Button
        Button(

            onClick = {
                imageError = Validator.validateImage(selectedImageUri)


                if (imageError.isEmpty() && selectedImageUri != null){
                    val imagePart = uriToPngMultipart(selectedImageUri!!, context, "image")

                    // Call the ViewModel function to add the book
                    viewmodel.addBook(
                        name = name,
                        price = price.toDouble(),
                        authorName = authorName,
                        stock = stock.toInt(),
                        description = description,
                        image = imagePart!!,
                        publishedDate = selectedDate.value?.toString() ?: ""
                    )
                }




            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .background(MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(8.dp)),
            colors = ButtonDefaults.buttonColors(contentColor = Color.White)
        ) {
            if (addBookState is Resource.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp), // Adjust size as needed
                    color = Color.White, // Change to match your button text color if necessary
                    strokeWidth = 2.dp
                )
            } else {
                Text(text = "Add Book", fontWeight = FontWeight.Bold)
            }
//            Text(
//                text = if (addBookState is Resource.Loading)
//                    "Adding Book" else "Add Book", fontWeight = FontWeight.Bold)

        }
    }
     //Show loading indicator when deleting
//    if (addBookState is Resource.Loading) {
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color.Black.copy(alpha = 0.5f)),
//            contentAlignment = Alignment.Center
//        ) {
//            Column(
//                horizontalAlignment = Alignment.CenterHorizontally,
//                verticalArrangement = Arrangement.Center
//            ) {
//                CircularProgressIndicator()
//                Spacer(modifier = Modifier.height(8.dp)) // Space between progress indicator and text
//                Text(text = "Adding...", color = Color.White) // Message indicating deletion
//            }
//        }
//    }
}

@Preview(showBackground = true)
@Composable
fun AddBookScreenPreview() {
   // AddBookScreen()
}
