package com.example.helloandroid.Page

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.SemanticsProperties.ImeAction
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.helloandroid.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPage(navController: NavController){
    val title = remember { mutableStateOf(TextFieldValue("")) }
    val date = remember { mutableStateOf(TextFieldValue("")) }
    val genre = remember { mutableStateOf(TextFieldValue("")) }
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

                    IconButton(modifier = Modifier.padding(start = 320.dp), onClick = {
//                        preferencesManager.saveData("jwt", "")
//                        navController.navigate("greeting")
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
                placeholder = { Text(text = "Type here...",style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF000000),)) })
            Text(
                text = "Date Of lunch",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight(600),
                    color = Color(0xFF00676C),
                )
            )
            OutlinedTextField(value = date.value,
                onValueChange = { newDate ->
                    date.value = newDate
                },
                placeholder = { Text(text = "Type here...",style = TextStyle(
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
            OutlinedTextField(value = date.value,
                onValueChange = { newGenre ->
                    genre.value = newGenre
                },
                placeholder = { Text(text = "Type here...",style = TextStyle(
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
            OutlinedTextField(value = date.value,
                onValueChange = { newGenre ->
                    genre.value = newGenre
                },
                placeholder = { Text(text = "Type here...",style = TextStyle(
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
            OutlinedTextField(value = date.value,
                onValueChange = { newGenre ->
                    genre.value = newGenre
                },
                placeholder = { Text(text = "Type here...",style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF000000),)) })
            Text(
                text = "Purchase Date ",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight(600),
                    color = Color(0xFF00676C),
                )
            )
            OutlinedTextField(value = date.value,
                onValueChange = { newGenre ->
                    genre.value = newGenre
                },
                placeholder = { Text(text = "Type here...",style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF000000),)) })

            Button(
                onClick = {  },
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