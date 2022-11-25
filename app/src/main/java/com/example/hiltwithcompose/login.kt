package com.example.hiltwithcompose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun LoginPage(navController: NavHostController) {
    Column(
        verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        Image(
            painter = painterResource(id = R.drawable.facebook_image),
            contentDescription = "facebook logo image"
        )
        Spacer(modifier = Modifier.height(10.dp))
        TextInputField(label = "Email")
        Spacer(modifier = Modifier.height(10.dp))
        TextInputFieldPassword(label = "Password")
        Spacer(modifier = Modifier.height(20.dp))
        ButtonWithBackground(label = "Login") {
            navController.navigate(Routes.Main.toString())
        }
        Spacer(modifier = Modifier.height(20.dp))
        ButtonWithOutBackground(label = "Forgot Password", color = Color.Blue) {
            navController.navigate(Routes.ForgotPassword.toString())
        }
        Spacer(modifier = Modifier.height(50.dp))
        ButtonWithBorder(label = "Register", color = Color.Blue) {
            navController.navigate(Routes.Register.toString())
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextInputField(label: String, input: String = "") {
    var userInput by remember {
        mutableStateOf(input)
    }
    OutlinedTextField(
        value = userInput,
        onValueChange = {
            userInput = it
        },
        placeholder = { Text(" enter ${label.lowercase()}", color = Color.DarkGray) },
        label = { Text(text = label) },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextInputFieldPassword(
    label: String
) {
    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    OutlinedTextField(
        value = password,
        onValueChange = { password = it },
        label = { Text(label) },
        singleLine = true,
        placeholder = { Text(" enter ${label.lowercase()}", color = Color.DarkGray) },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            val image = if (passwordVisible) Icons.Filled.Visibility
            else Icons.Filled.VisibilityOff
            val description = if (passwordVisible) "Hide password" else "Show password"

            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(imageVector = image, description)
            }
        },
    )
}

@Composable
fun ButtonWithBackground(
    label: String, color: Color = Color.White, onButtonClicked: () -> (Unit)
) {
    IconButton(
        onClick = { onButtonClicked() },
        modifier = Modifier
            .fillMaxWidth(.7f)
            .background(Color.Blue)
            .clip(
                CircleShape
            )
    ) {
        Text(text = label, color = color, style = MaterialTheme.typography.subtitle1)
    }
}


@Composable
fun ButtonWithOutBackground(
    label: String,
    color: Color = Color.White,
    onButtonClicked: () -> (Unit)
) {
    IconButton(
        onClick = { onButtonClicked() },
    ) {
        Text(text = label, color = color, style = MaterialTheme.typography.subtitle1)
    }
}

@Composable
fun ButtonWithBorder(
    label: String,
    color: Color = Color.White,
    onButtonClicked: () -> Unit,
) {
    IconButton(
        onClick = { onButtonClicked() },
        modifier = Modifier
            .fillMaxWidth(.7f)
            .border(1.dp, Color.LightGray, shape = RectangleShape)
    ) {
        Text(text = label, color = color, style = MaterialTheme.typography.subtitle1)
    }
}
