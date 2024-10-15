package com.example.knowledgeshelf.presentation.view.addBook

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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.rounded.Upload
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.knowledgeshelf.data.model.book.AddBookResponse
import com.example.knowledgeshelf.data.model.book.BookRequest
import com.example.knowledgeshelf.domain.Resource
import com.example.knowledgeshelf.presentation.viewmodel.BookViewmodel
import com.example.knowledgeshelf.utils.Utils.uriToPngMultipart
import com.example.knowledgeshelf.utils.Validator
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.date_time.DateTimeDialog
import com.maxkeppeler.sheets.date_time.models.DateTimeSelection
import kotlinx.coroutines.delay
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddBookScreen(
    modifier: Modifier = Modifier,
    viewmodel: BookViewmodel = hiltViewModel(),
) {
    val state = viewmodel.state

    var imageError by remember { mutableStateOf("") }

    val addBookState by viewmodel.addBookResult.collectAsState()
    val context = LocalContext.current
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    //to open keyword automatically when screen launch
    LaunchedEffect(key1=Unit) {
        delay(500)
        focusRequester.requestFocus()
    }

    LaunchedEffect(addBookState) {
        when (addBookState) {
            is Resource.Success -> {
                Toast.makeText(context, (addBookState as Resource.Success<AddBookResponse>).message ?: "Book added successfully", Toast.LENGTH_SHORT).show()
                Log.d("parameter", (addBookState as Resource.Success<AddBookResponse>).message!!)
            }
            is Resource.Error -> {
                Toast.makeText(context, (addBookState as Resource.Error<AddBookResponse>).message ?: "Error Occurred", Toast.LENGTH_SHORT).show()
                Log.d("parameter", (addBookState as Resource.Error<AddBookResponse>).message)
            }
            Resource.Loading -> {}
            null -> {}
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
            value = state.name,
            onValueChange = { viewmodel.onEvent(AddBookEvent.NameChanged(it)) },
            label = { Text("Book Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .focusRequester(focusRequester),
            shape = RoundedCornerShape(20),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Next) }
            )
        )

        // Price
        OutlinedTextField(
            value = state.price.toString(),
            onValueChange = {viewmodel.onEvent(AddBookEvent.PriceChanged(it))},
            label = { Text("Price") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = RoundedCornerShape(20),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number,imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Next) }
            )
        )

        // Author Name
        OutlinedTextField(
            value = state.authorName,
            onValueChange = {viewmodel.onEvent(AddBookEvent.AuthorNameChanged(it)) },
            label = { Text("Author Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = RoundedCornerShape(20),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Next) }
            )
        )

        // Stock
        OutlinedTextField(
            value = state.stock.toString(),
            onValueChange = { viewmodel.onEvent(AddBookEvent.StockChanged(it))},
            label = { Text("Stock") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = RoundedCornerShape(20),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Next) }
            )
        )

        // Description
        OutlinedTextField(
            value = state.description,
            onValueChange = { viewmodel.onEvent(AddBookEvent.DescriptionChanged(it)) },
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

                   val bookRequest = BookRequest(
                       name = state.name,
                       price = state.price,
                       authorName = state.authorName,
                       stock = state.stock,
                       description = state.description,
                       image = imagePart!!,
                       publishedDate = selectedDate.value?.toString() ?: ""
                   )
                    viewmodel.addBook(bookRequest, image = imagePart)
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

        }
    }

}

