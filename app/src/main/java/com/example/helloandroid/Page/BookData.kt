package com.example.helloandroid.Page

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.helloandroid.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import java.net.URL
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun BookData(navController: NavController, textData: String) {

    val coroutineScope = rememberCoroutineScope()
    var imageUrl by remember { mutableStateOf("") }
    var book by remember { mutableStateOf<ProductData?>(null) }

    LaunchedEffect(key1 = textData) {
        imageUrl = fetchImageUrl(textData)
    }

    LaunchedEffect(key1 = textData) {
        book = coroutineScope.async { fetchBookData(textData) }.await()
    }


    if (book != null) {
        val publishedAt = LocalDateTime.parse(book!!.attributes.publishedAt, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
        val formattedPublishedAt = publishedAt.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Data Buku",
                            fontWeight = FontWeight.Bold,
                            fontSize = 28.sp,
                            fontFamily = FontFamily(Font(R.font.poppins_black)),
                        )
                    }

                    IconButton(modifier = Modifier.padding(start = 320.dp), onClick = {
                        navController.navigate("greeting")
                    }) {
                        Icon(
                            Icons.Default.ExitToApp,
                            contentDescription = "Sign Out",
                            tint = Color.Gray
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color.White
                ),
            )
        }

    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(
                top = innerPadding.calculateTopPadding(),
                bottom = innerPadding.calculateBottomPadding(),
                start = 12.dp,
                end = 12.dp
            )
        ) {
            Text(
                text = "BookData",
                style = TextStyle(
                    fontSize = 21.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF000000),
                )
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Row {
                Image(
                    painter = if (imageUrl == "profil") {
                        painterResource(R.drawable.profil)
                    } else {
                        rememberImagePainter(data = imageUrl)
                    },
                    contentDescription = "image description",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .width(142.dp)
                        .height(131.dp)
                )


                Spacer(modifier = Modifier.padding(12.dp))
                Column {
                    Text(
                        text = textData,
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight(400),
                            color = Color(0xFF000000),
                        )
                    )
                    Text(
                        text = "Date of Publish",
                        style = TextStyle(
                            fontSize = 13.sp,
                            fontWeight = FontWeight(600),
                            color = Color(0xFF00676C),
                        )
                    )
                    Text(
                        text = formattedPublishedAt,
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight(400),
                            color = Color(0xFF000000),
                        )
                    )
                    Text(
                        text = "Author",
                        style = TextStyle(
                            fontSize = 13.sp,
                            fontWeight = FontWeight(600),
                            color = Color(0xFF00676C),
                        )
                    )
                    Text(
                        text = book!!.attributes.Author,
                        style = TextStyle(
                            fontSize = 17.sp,
                            fontWeight = FontWeight(400),
                            color = Color(0xFF000000),
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.padding(12.dp))
            Text(
                text = "Genre : ",
                style = TextStyle(
                    fontSize = 23.sp,
                    fontWeight = FontWeight(600),
                    color = Color(0xFF00676C),
                )
            )
            Text(
                text = book!!.attributes.Genre,
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight(600),
                    color = Color.Black,
                )
            )
            Spacer(modifier = Modifier.padding(12.dp))
            Text(
                text = "Description : ",
                style = TextStyle(
                    fontSize = 23.sp,
                    fontWeight = FontWeight(600),
                    color = Color(0xFF00676C),
                )
            )
            Text(
                text = book!!.attributes.Description,
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight(600),
                    color = Color.Black,
                )
            )
            Spacer(modifier = Modifier.padding(12.dp))

            Button(
                onClick = {
                    coroutineScope.launch {
                        deleteData(book)
                        delay(2000)  // delay for 2 seconds
                        navController.navigate("homepage")
                    }
                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("Delete Data")
            }



        }
    }
}
}
suspend fun fetchImageUrl(bookName: String): String {
    val url = "https://api.tnadam.me/api/products?populate=*"
    val response = withContext(Dispatchers.IO) { URL(url).readText() }
    val apiResponse = Json { ignoreUnknownKeys = true }.decodeFromString<ApiResponse>(response)
    val book = apiResponse.data.firstOrNull { it.attributes.nama == bookName }
    return if (book != null && book.attributes.img?.data?.attributes?.url != null) {
        "https://api.tnadam.me" + book.attributes.img.data.attributes.url
    } else {
        "profil" // return the name of the local image resource if the book is not found or the image URL is null
    }
}



suspend fun fetchBookData(bookName: String): ProductData? {
    val url = "https://api.tnadam.me/api/products?populate=*"
    val response = withContext(Dispatchers.IO) { URL(url).readText() }
    val apiResponse = Json { ignoreUnknownKeys = true }.decodeFromString<ApiResponse>(response)
    return apiResponse.data.firstOrNull { it.attributes.nama == bookName }
}

suspend fun deleteData(book: ProductData?) {
    if (book != null) {
        val url = "https://api.tnadam.me/api/products/${book.id}"
        withContext(Dispatchers.IO) {
            val request = Request.Builder()
                .url(url)
                .delete()
                .build()

            val client = OkHttpClient()
            val response = client.newCall(request).execute()

            if (!response.isSuccessful) {
                // TODO: Handle error
            }
        }
    }
}

