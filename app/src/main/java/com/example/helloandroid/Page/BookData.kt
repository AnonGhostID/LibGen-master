package com.example.helloandroid.Page

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
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
import kotlinx.coroutines.withContext

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.net.URL



@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun BookData(navController: NavController) {

    val coroutineScope = rememberCoroutineScope()
    var imageUrl by remember { mutableStateOf("") }

    LaunchedEffect(key1 = Unit) {
        imageUrl = coroutineScope.async { fetchImageUrl() }.await()
    }

    val specificTextData by produceState(initialValue = "", producer = {
        value = fetchSpecificTextData("Buku Gambar")
    })



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

                    painter = rememberImagePainter(data = imageUrl),
                    contentDescription = "image description",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .width(142.dp)
                        .height(131.dp)
                )

                Spacer(modifier = Modifier.padding(12.dp))
                Column {
                    Text(
                        text = specificTextData,
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight(400),
                            color = Color(0xFF000000),
                        )
                    )
                    Text(
                        text = "Date of Launch",
                        style = TextStyle(
                            fontSize = 13.sp,
                            fontWeight = FontWeight(600),
                            color = Color(0xFF00676C),
                        )
                    )
                    Text(
                        text = "12/07/2002",
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
                        text = "Rizqy Azmi PT.Ph.ind",
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
                text = "Mythical, Glory, Bintang 100, kapan ya, bismillah",
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
                text = "Description : orem ipsum dolor sitamet orem ipsum dolor sitamet orem ipsum dolor sitamet orem ipsum dolor sitamet orem ipsum dolor sitamet orem ipsum dolor sitamet orem ipsum dolor sitamet orem ipsum dolor sitamet orem ipsum ",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight(600),
                    color = Color.Black,
                )
            )
            Spacer(modifier = Modifier.padding(12.dp))
            Text(
                text = "Date of Purchase : ",
                style = TextStyle(
                    fontSize = 23.sp,
                    fontWeight = FontWeight(600),
                    color = Color(0xFF00676C),
                )
            )
            Text(
                text = "22/07/2001",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight(600),
                    color = Color.Black,
                )
            )

        }
    }
}
suspend fun fetchImageUrl(): String {
    val url = "https://api.tnadam.me/api/products?populate=*"
    val response = withContext(Dispatchers.IO) { URL(url).readText() }
    val apiResponse = Json { ignoreUnknownKeys = true }.decodeFromString<ApiResponse>(response)
    return "https://api.tnadam.me" + apiResponse.data.first().attributes.img.data.attributes.url
}

suspend fun fetchSpecificTextData(targetData: String): String {
    val url = "https://api.tnadam.me/api/products?populate=*"
    val response = withContext(Dispatchers.IO) { URL(url).readText() }
    val apiResponse = Json { ignoreUnknownKeys = true }.decodeFromString<ApiResponse>(response)
    val targetItem = apiResponse.data.find { it.attributes.data == targetData }
    return targetItem?.attributes?.data ?: "Data not found"
}
