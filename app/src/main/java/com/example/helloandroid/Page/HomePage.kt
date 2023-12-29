package com.example.helloandroid.Page

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.helloandroid.PreferencesManager
import com.example.helloandroid.R
import com.example.helloandroid.data.LoginData
import com.example.helloandroid.response.LoginRespon
import com.example.helloandroid.response.UserRespon
import com.example.helloandroid.service.LoginService
import com.example.helloandroid.service.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(navController: NavController, context: Context = LocalContext.current) {
    //var listUser: List<UserRespon> = remember
    val preferencesManager = remember { PreferencesManager(context = context) }
    val listUser = remember { mutableStateListOf<UserRespon>() }
    //var listUser: List<UserRespon> by remember { mutableStateOf(List<UserRespon>()) }
    var search by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var baseUrl = "http://10.0.2.2:1337/api/"
    //var baseUrl = "http://10.217.17.11:1337/api/"
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
//    fun loadData(search: String?){
//        val call = retrofit.getData(search, "*", "username:desc")
//        call.enqueue(object : Callback<List<UserRespon>> {
//            override fun onResponse(
//                call: Call<List<UserRespon>>,
//                response: Response<List<UserRespon>>
//            ) {
//                if (response.code() == 200) {
//                    //kosongkan list User terlebih dahulu
//                    listUser.clear()
//                    response.body()?.forEach{ userRespon ->
//                        listUser.add(userRespon)
//                        val x = userRespon.prodi?.namaProdi
//                        val y = ""
//                    }
//                } else if (response.code() == 400) {
//                    print("error login")
//                    var toast = Toast.makeText(
//                        context,
//                        "Username atau password salah",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            }
//
//            override fun onFailure(call: Call<List<UserRespon>>, t: Throwable) {
//                print(t.message)
//            }
//
//        })
//    }
//
//    loadData(null)

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
                .padding(innerPadding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = search,
                onValueChange = { newText -> search = newText },
                label = {
                    Text(
                        text = "search"
                    )
                },
                modifier = Modifier
                    .padding(bottom = 1.dp),
                trailingIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = "Search"
                        )
                    }
                }
            )
            LazyColumn {
                listUser.forEach { user ->
                    item {
                        Row(
                            modifier = Modifier
                                .padding(10.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Text(
                                text = user.username,
                                fontSize = 18.sp,
                                fontFamily = FontFamily(Font(R.font.poppins_black)),
                            )

                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                IconButton(onClick = {
                                    navController.navigate("edituser/" + user.id + "/" + user.username + "/" + user.email)
                                }) {
                                    Icon(
                                        Icons.Default.Edit,
                                        contentDescription = "Edit",
                                        tint = Color.Blue
                                    )
                                }

                                IconButton(onClick = {
                                    val retrofit = Retrofit.Builder()
                                        .baseUrl(baseUrl)
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build()
                                        .create(UserService::class.java)
                                    val call = retrofit.delete(user.id)
                                    call.enqueue(object : Callback<UserRespon> {
                                        override fun onResponse(
                                            call: Call<UserRespon>,
                                            response: Response<UserRespon>
                                        ) {
                                            print(response.code())
                                            if (response.code() == 200) {
                                                listUser.remove(user)
                                            } else if (response.code() == 400) {
                                                print("error login")
                                                var toast = Toast.makeText(
                                                    context,
                                                    "Username atau password salah",
                                                    Toast.LENGTH_SHORT
                                                ).show()

                                            }
                                        }

                                        override fun onFailure(
                                            call: Call<UserRespon>,
                                            t: Throwable
                                        ) {
                                            print(t.message)
                                        }

                                    })
                                }) {
                                    Icon(
                                        Icons.Default.Delete,
                                        contentDescription = null,
                                        tint = Color.Red
                                    )
                                }
                            }
                        }
                    }
                }

            }
        }
    }
}