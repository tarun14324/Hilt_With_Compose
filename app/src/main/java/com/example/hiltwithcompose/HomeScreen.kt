package com.example.hiltwithcompose

import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun HomeScreen(state: ScaffoldState, navController: NavHostController, viewModel: UsersViewModel) {
    Scaffold(scaffoldState = state, contentColor = Color.White,
        backgroundColor = Color.White,
        topBar = {
            TopNavigationBar(onSearchItemClicked = {}, navController)
        },
        content = { paddingValues ->
            MainScreenContent(viewModel = viewModel, paddingValues)
        })
}

@Composable
fun TopNavigationBar(onSearchItemClicked: (String) -> (Unit), navController: NavHostController) {
    var showMenu by remember {
        mutableStateOf(false)
    }
    val activity = (LocalContext.current as? Activity)
    Column(
        verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        TopAppBar(backgroundColor = Color.Blue, contentColor = Color.White, title = {
            Text(text = stringResource(id = R.string.app_name))
        }, actions = {
            IconButton(onClick = {
                onSearchItemClicked(Routes.Search.toString())
            }) {
                Icon(Icons.Default.Search, "")
            }
            IconButton(onClick = { showMenu = !showMenu }) {
                Icon(Icons.Default.MoreVert, "")
            }
            DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
                DropdownMenuItem(onClick = { navController.navigate(Routes.Login.toString()) }) {
                    Icon(Icons.Filled.Login, "")
                    Text(text = "Login", Modifier.padding(horizontal = 10.dp), color = Color.Black)
                }
                DropdownMenuItem(onClick = {
                    activity?.finish()
                }) {
                    Icon(Icons.Default.Close, "")
                    Text(
                        text = "Close App",
                        Modifier.padding(horizontal = 10.dp),
                        color = Color.Black
                    )
                }
            }
        })
    }
}

@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun Screens(viewModel: UsersViewModel) {
    val state = rememberScaffoldState()
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.Login.toString()) {
        composable(Routes.Login.toString()) {
            LoginPage(navController = navController)
        }
        composable(Routes.Register.toString()) {
            RegisterPage(navController)
        }
        composable(Routes.ForgotPassword.toString()) {
            ForgotPasswordPage(navController)
        }
        composable(Routes.Main.toString()) {
            HomeScreen(state = state, navController, viewModel)
        }
    }
}

sealed class Routes {
    object Login : Routes()
    object Register : Routes()
    object ForgotPassword : Routes()
    object Main : Routes()
    object Search : Routes()
}



