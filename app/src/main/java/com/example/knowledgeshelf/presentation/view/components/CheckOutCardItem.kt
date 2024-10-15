package com.example.knowledgeshelf.presentation.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.knowledgeshelf.R

@Composable
fun CheckOutCardItem(
    productName: String,
    authorName: String,
    initialQuantity: Int = 1,
    price: Double,
    image:String,
    backgroundColor: Color = Color.Blue
) {
    var quantity by remember { mutableStateOf(initialQuantity) }
    val totalPrice = price * quantity
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(4.dp)),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFE57D) // Set the background color of the card
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Product Image
            AsyncImage(
                model = image,
                contentDescription = "Product Image",
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp)), // Slightly rounded corners for the image
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Product details (Name, Author, Quantity)
            Column(
                modifier = Modifier.weight(1f)
            ) {
                // Product Name
                Text(
                    text = productName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                // Author Name
                Text(
                    text = authorName,
                    fontSize = 14.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Quantity Selector
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Decrease Button
                    Box(
                        modifier = Modifier
                            .size(30.dp) // Background size
                            .clip(CircleShape)
                            .background(Color(0xFFFFD54F)) // Background color
                    ) {
                        IconButton(
                            onClick = {
                                if (quantity > 1) {
                                    quantity-- // Decrease quantity
                                }
                            },
                            modifier = Modifier.align(Alignment.Center) // Center the icon in the box
                        ) {
                            Icon(
                                imageVector = Icons.Default.Remove,
                                contentDescription = "Decrease Quantity",
                                tint = Color.White, // White color for better visibility
                                modifier = Modifier.size(16.dp) // Reduced icon size
                            )
                        }
                    }

                    // Quantity Number
                    Text(
                        text = quantity.toString(),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )

                    // Increase Button
                    Box(
                        modifier = Modifier
                            .size(30.dp) // Background size
                            .clip(CircleShape)
                            .background(Color(0xFFFFD54F)) // Background color
                    ) {
                        IconButton(
                            onClick = {
                                if (quantity < 10) {
                                    quantity++
                                }
                            },
                            modifier = Modifier.align(Alignment.Center) // Center the icon in the box
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Increase Quantity",
                                tint = Color.White, // White color for better visibility
                                modifier = Modifier.size(16.dp) // Reduced icon size
                            )
                        }
                    }
                }
            }

            // Price
            Text(
                text = "\$${"%.2f".format(totalPrice)}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}


