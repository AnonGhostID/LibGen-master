package com.example.helloandroid.Page

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.helloandroid.R
import com.example.helloandroid.data.AddBookData
import com.example.helloandroid.data.AddBookDataWrapper
import com.example.helloandroid.response.AddBookRespon
import com.example.helloandroid.service.AddBookService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPage(navController: NavController){
    val title = remember { mutableStateOf(TextFieldValue("")) }
    val author = remember { mutableStateOf(TextFieldValue("")) } // Renamed from 'date'
    val description = remember { mutableStateOf(TextFieldValue("")) } // Renamed from 'genre'
    val genre = remember { mutableStateOf(TextFieldValue("")) } // New state for 'Genre'
    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.tnadam.me/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(AddBookService::class.java)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Add Page",
                            fontWeight = FontWeight.Bold,
                            fontSize = 28.sp,
                            fontFamily = FontFamily(Font(R.font.poppins_black)),
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
            horizontalAlignment = Alignment.Start
        ) {


            Text(
                text = "Title",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight(600),
                    color = Color(0xFF00676C),
                )
            )
            OutlinedTextField(value = title.value,
                onValueChange = { newTitle ->
                    title.value = newTitle
                },
                placeholder = { Text(text = "Ketik Nama Buku...",style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF000000),)) })

            Text(
                text = "Genre ",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight(600),
                    color = Color(0xFF00676C),
                )
            )
            OutlinedTextField(value = genre.value,
                onValueChange = { newGenre ->
                    genre.value = newGenre
                },
                placeholder = { Text(text = "Ketik Genre ",style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF000000),)) })
            Text(
                text = "Author ",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight(600),
                    color = Color(0xFF00676C),
                )
            )
            OutlinedTextField(value = author.value,
                onValueChange = { newAuthor ->
                    author.value = newAuthor
                },
                placeholder = { Text(text = "Ketik Authornya",style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF000000),)) })

            Text(
                text = "Description ",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight(600),
                    color = Color(0xFF00676C),
                )
            )
            OutlinedTextField(value = description.value,
                onValueChange = { newDescription ->
                    description.value = newDescription
                },
                placeholder = { Text(text = "Ketik desc",style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF000000),)) })


            Button(
                onClick = {
                    val bookData = AddBookData(
                        Genre = genre.value.text,
                        Description = description.value.text,
                        Author = author.value.text,
                        nama = title.value.text
                    )
                    val call = service.getData(AddBookDataWrapper(bookData)) // Wrap the book data
                    call.enqueue(object : Callback<AddBookRespon> {
                        override fun onResponse(call: Call<AddBookRespon>, response: Response<AddBookRespon>) {
                            if (response.isSuccessful) {
                                navController.navigate("homepage") // Replace "homepage" with the route of your homepage
                            } else {
                                // Handle error
                            }
                        }

                        override fun onFailure(call: Call<AddBookRespon>, t: Throwable) {
                            // Handle failure
                        }
                    })
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White
                )
            ) {
                Text(text = "Submit")
            }




        }
        }
}