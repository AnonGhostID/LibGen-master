package com.example.helloandroid

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.helloandroid.Page.AddPage
import com.example.helloandroid.Page.BookData
import com.example.helloandroid.Page.EditUser
import com.example.helloandroid.Page.HomePage
import com.example.helloandroid.Page.Register
import com.example.helloandroid.data.LoginData
import com.example.helloandroid.response.LoginRespon
import com.example.helloandroid.service.LoginService
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {

    @SuppressLint("NewApi")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //val preferencesManager = remember { PreferencesManager(context = LocalContext.current) }
            val sharedPreferences: SharedPreferences = LocalContext.current.getSharedPreferences("auth", Context.MODE_PRIVATE)
            val navController = rememberNavController()

            var startDestination: String
            var jwt = sharedPreferences.getString("jwt", "")
            if(jwt.equals("")){
                startDestination = "greeting"
            }else{
                startDestination = "homepage"
            }


            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            val scope = rememberCoroutineScope()
            ModalNavigationDrawer(
                drawerState = drawerState,
                drawerContent = {
                    ModalDrawerSheet {
                        Text("LibGen Menu", fontFamily = FontFamily(Font(R.font.poppins_semibold)), modifier = Modifier.padding(16.dp))
                        Divider()
                        NavigationDrawerItem(
                            label = { Text(text = "Add User") },
                            selected = false,
                            onClick = {
                                navController.navigate("register")
                                scope.launch {
                                    drawerState.close()
                                }

                            }
                        )
                        // ...other drawer items
                    }
                }
            ) {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(text = "LibGen", fontFamily = FontFamily(Font(R.font.poppins_semibold))) },
                            navigationIcon = {
                                IconButton(onClick = {
                                    scope.launch {
                                        drawerState.apply {
                                            if (isClosed) open() else close()
                                        }
                                    }
                                }) {
                                    Icon(imageVector = Icons.Outlined.Menu, contentDescription = "")
                                }
                            }
                        )},
//                    floatingActionButton = {
//                        ExtendedFloatingActionButton(
//                            text = { Text("Show drawer") },
//                            icon = { Icon(Icons.Filled.Add, contentDescription = "") },
//                            onClick = {
//                                scope.launch {
//                                    drawerState.apply {
//                                        if (isClosed) open() else close()
//                                    }
//                                }
//                            }
//                        )
//                    }
                ) { contentPadding ->
                    // Screen content
                    NavHost(navController, startDestination = startDestination, modifier = Modifier.padding(contentPadding)) {
                        composable(route = "greeting") {
                            Greeting(navController)
                        }
                        composable(route = "homepage") {
                            HomePage(navController)
                        }
                        composable(route = "register") {
                            Register(navController)
                        }
                        composable(route = "Addpage") {
                            AddPage(navController)
                        }
                        composable(route = "BookData/{textData}") { backStackEntry ->
                            val textData = backStackEntry.arguments?.getString("textData")
                            BookData(navController, textData ?: "")
                        }
                        composable(
                            route = "edituser/{userid}/{username}/{email}",
                        ) {backStackEntry ->
                            EditUser(navController, backStackEntry.arguments?.getString("userid"), backStackEntry.arguments?.getString("username"), backStackEntry.arguments?.getString("email"))
                        }
                    }

                }
            }

//            NavHost(navController, startDestination = startDestination) {
//                composable(route = "greeting") {
//                    Greeting(navController)
//                }
//                composable(route = "homepage") {
//                    HomePage(navController)
//                }
//                composable(route = "register") {
//                    Register(navController)
//                }
//                composable(
//                    route = "edituser/{userid}/{username}/{email}",
//                ) {backStackEntry ->
//                    EditUser(navController, backStackEntry.arguments?.getString("userid"), backStackEntry.arguments?.getString("username"), backStackEntry.arguments?.getString("email"))
//                }
//            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Greeting(navController: NavController, context: Context = LocalContext.current) {

    val baseColor = Color(0xFF00676C)
    val imagePainter = painterResource(id = R.drawable.homelogo)
    val preferencesManager = remember { PreferencesManager(context = context) }
    var username by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    val eyeOpen = painterResource(id = R.drawable.view)
    val eyeClose = painterResource(id = R.drawable.hidden)
    var passwordVisibility by remember { mutableStateOf(false) }
    var baseUrl = "https://api.tnadam.me/api/"
    var jwt by remember { mutableStateOf("") }

    jwt = preferencesManager.getData("jwt")
    Scaffold (
        topBar = {
            TopAppBar(title = { Text(text = "Login", fontWeight = FontWeight.Bold, fontSize = 28.sp)},
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color.White
                ),
            )
        }
    ){ innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = imagePainter,
                contentDescription = null,
                alignment = Alignment.Center,
                modifier = Modifier
                    .height(240.dp)
                    .width(250.dp)
                    .offset(x = (-26).dp)
            )

           OutlinedTextField(
                value = username,
                onValueChange = { newText ->
                    username = newText
                },
                label = { Text(text = "Username") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 6.dp),
                leadingIcon = {
                    Icon(
                        Icons.Default.AccountCircle,
                        contentDescription = null,
                    )
                }
            )

            OutlinedTextField(
                value = password,
                onValueChange = { newText ->
                    password = newText
                },
                label = { Text(text = "Password") },
                visualTransformation =
                if (passwordVisibility) VisualTransformation.None
                else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 38.dp),
                leadingIcon = {
                    Icon(
                        Icons.Default.Lock,
                        contentDescription = null,
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                        val icon = if (passwordVisibility) eyeOpen else eyeClose
                        Image(
                            painter = icon,
                            contentDescription = if (passwordVisibility) "Hide Password" else "Show Password",
                            modifier = Modifier.size(24.dp),
                        )
                    }
                }
            )

            ElevatedButton(onClick = {
                //navController.navigate("pagetwo")
                val retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(LoginService::class.java)
                val call = retrofit.getData(LoginData(username.text, password.text))
                call.enqueue(object : Callback<LoginRespon>{
                    override fun onResponse(call: Call<LoginRespon>, response: Response<LoginRespon>) {
                        print(response.code())
                        if(response.code() == 200){
                            jwt = response.body()?.jwt!!
                            preferencesManager.saveData("jwt", jwt)
                            navController.navigate("homepage")
                        }else if(response.code() == 400){
                            print("error login")
                            var toast = Toast.makeText(context, "Username atau password salah", Toast.LENGTH_SHORT).show()

                        }
                    }

                    override fun onFailure(call: Call<LoginRespon>, t: Throwable) {
                        print(t.message)
                    }
                })
            },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 6.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = baseColor,
                    contentColor = Color.White),
                ) {
                Text(text = "Login", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp )
            }
//            Text(text = jwt)

            Row {
                Text(text = "Don't have an account ? ", color = Color.Gray)
                ClickableText(text = AnnotatedString("Sign Up"),onClick = {navController.navigate("register")})
            }
        }
    }

}

