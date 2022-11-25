package com.example.hiltwithcompose

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.RadioButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterPage(navController: NavController) {
    var dateOfBirth by rememberSaveable { mutableStateOf("") }
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Spacer(modifier = Modifier.height(100.dp))
        Text(
            stringResource(id = R.string.register),
            style = MaterialTheme.typography.displaySmall
        )
        Spacer(modifier = Modifier.height(20.dp))
        TextInputField(label = "Email")
        Spacer(modifier = Modifier.height(10.dp))
        TextInputFieldPassword(label = "Password")
        Spacer(modifier = Modifier.height(10.dp))
        TextInputFieldPassword(label = "Confirm password")
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(value = dateOfBirth,
            onValueChange = { dateOfBirth = it },
            label = { Text("DateOfBirth") },
            singleLine = true,
            leadingIcon = {
                IconButton(onClick = { }) {
                    Icon(imageVector = Icons.Default.CalendarToday, "calendar")
                }
            },
            placeholder = { androidx.compose.material.Text("02-02-2002", color = Color.DarkGray) })
        Spacer(modifier = Modifier.height(10.dp))
        GenderSelectionRadioButtonGroup()
        Spacer(modifier = Modifier.height(10.dp))
        TermsCheckBox()
        Spacer(modifier = Modifier.height(10.dp))
        ButtonWithBackground(label = "Register") {
            navController.navigate(Routes.Login.toString())
        }
        Spacer(modifier = Modifier.height(30.dp))
        ButtonWithBorder(label = "Back", color = Color.Blue) {
            navController.navigate(Routes.Login.toString())
        }
    }
}

@Composable
fun TermsCheckBox() {
    var isChecked by rememberSaveable { mutableStateOf(false) }
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(checked = isChecked, onCheckedChange = {
            isChecked = !isChecked
        })
        Text(text = "Terms & Conditions applicable.......")
    }
}

@Composable
fun GenderSelectionRadioButtonGroup() {
    val radioOptions = listOf("Male", "Female")
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }
    Row(
        modifier = Modifier.wrapContentWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        radioOptions.forEach { gender ->
            Row(modifier = Modifier
                .selectable(
                    selected = (gender == selectedOption),
                    onClick = {
                        onOptionSelected(gender)
                    }
                ),
                verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = (gender == selectedOption),
                    onClick = {
                        onOptionSelected(gender)
                    }
                )
                Text(
                    text = gender,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

    }
}
