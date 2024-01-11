package com.example.helloandroid.Page

import android.content.Context
import android.media.Image
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.helloandroid.PreferencesManager
import com.example.helloandroid.R
import com.example.helloandroid.response.UserRespon
import com.example.helloandroid.service.UserService
import com.example.helloandroid.service.ProdukService
import com.example.helloandroid.response.ProdukRespon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URL

@Serializable
data class ApiResponse(
    val data: List<ProductData>
)

@Serializable
data class ProductData(
    val id: Int,
    val attributes: ProductAttributes
)

@Serializable
data class ProductAttributes(
    val data: String,
    val img: ImageData
)

@Serializable
data class ImageData(
    val data: ImageAttributesData
)

@Serializable
data class ImageAttributesData(
    val attributes: ImageAttributes
)

@Serializable
data class ImageAttributes(
    val url: String? = null
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(navController: NavController, context: Context = LocalContext.current) {

    val textDataList by produceState(initialValue = listOf<String>(), producer = {
        value = fetchAllTextData()
    })

    //var listUser: List<UserRespon> = remember
    val preferencesManager = remember { PreferencesManager(context = context) }
    val listUser = remember { mutableStateListOf<UserRespon>() }
    //var listUser: List<UserRespon> by remember { mutableStateOf(List<UserRespon>()) }
    var selectedItem by remember { mutableStateOf(0) }
    val items = listOf("Bookdata", "Addpage")
    var baseUrl = "https://api.tnadam.me/api/"

    val screens = listOf("Screen 1", "Screen 2", "Screen 3")
    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(UserService::class.java)
    val call = retrofit.getData()
    call.enqueue(object : Callback<List<UserRespon>> {
        override fun onResponse(
            call: Call<List<UserRespon>>,
            response: Response<List<UserRespon>>
        ) {
            if (response.code() == 200) {
                //kosongkan list User terlebih dahulu
                listUser.clear()
                response.body()?.forEach { userRespon ->
                    listUser.add(userRespon)
                }
            } else if (response.code() == 400) {
                print("error login")
                var toast = Toast.makeText(
                    context,
                    "Username atau password salah",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        override fun onFailure(call: Call<List<UserRespon>>, t: Throwable) {
            print(t.message)
        }

    })


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Home Page",
                            fontWeight = FontWeight.Bold,
                            fontSize = 28.sp,
                            fontFamily = FontFamily(Font(R.font.poppins_black)),
                        )
                    }

                    IconButton(modifier = Modifier.padding(start = 320.dp), onClick = {
                        preferencesManager.saveData("jwt", "")
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
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f))
            NavigationBar(
                modifier = Modifier.fillMaxWidth(),
                containerColor = Color.Gray, // Customize as needed
                contentColor = Color.Black, // Customize as needed
                tonalElevation = 41.dp // Customize as needed
            ) {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = { Icon(Icons.Filled.Add, contentDescription = item) },
                        label = { Text(text = item) },
                        selected = selectedItem == index,
                        onClick = { navController.navigate(item) }
                    )
                }
            }
        }


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            textDataList.chunked(2).forEach { rowItems ->
                Row(
                    horizontalArrangement = Arrangement.Center,
                ) {
                    rowItems.forEach { textData ->
                        Card(
                            onClick = { navController.navigate("Bookdata")},
                            modifier = Modifier
                                .padding(10.dp)
                                .size(width = 185.dp, height = 130.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.Green,
                            )
                        ) {
                            Column(
                            ) {
                                // Example content:
                                Text(
                                    text = textData,
                                    modifier = Modifier
                                        .padding(16.dp),
                                    color = Color.Black,
                                    textAlign = TextAlign.Center,
                                )

                                // Add other elements as needed
                            }
                        }
                    }
                }
            }
        }
    }
}


suspend fun fetchAllTextData(): List<String> {
    val url = "https://api.tnadam.me/api/products?populate=*"
    val response = withContext(Dispatchers.IO) { URL(url).readText() }
    val apiResponse = Json { ignoreUnknownKeys = true }.decodeFromString<ApiResponse>(response)
    return apiResponse.data.map { it.attributes.data }
}
