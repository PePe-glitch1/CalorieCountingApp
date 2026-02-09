package com.example.caloriecountingapp.screenElements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.caloriecountingapp.viewModels.UserViewModel
import java.time.LocalDate

@Composable
fun TestAddUser() {
    val userViewModel: UserViewModel = hiltViewModel()

    val user by userViewModel.user.collectAsStateWithLifecycle()
    val isLoading by userViewModel.isLoading.collectAsStateWithLifecycle()
    val errorMessage by userViewModel.errorMessage.collectAsStateWithLifecycle()

    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var bornDataText by remember { mutableStateOf("") }
    var bornData by remember { mutableStateOf<LocalDate?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Text(if (user == null) "Create User" else "Update User",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.Black,
            fontSize = 20.sp)

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        OutlinedTextField(
            value = bornDataText,
            onValueChange = { input ->
                bornDataText = input
                bornData = try {
                    LocalDate.parse(input)
                } catch (e: Exception) {
                    null
                }
            },
            label = { Text("Born Date (YYYY-MM-DD)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Button(
            onClick = {
                if (user == null) {
                    if (username.isNotBlank() && email.isNotBlank() && bornData != null) {
                        userViewModel.saveUser(
                            username = username,
                            email = email,
                            bornData = bornData!!
                        )
                        username = ""
                        email = ""
                        bornDataText = ""
                        bornData = null
                    }
                } else {
                    userViewModel.updateUser(
                        username = username.takeIf { it.isNotBlank() },
                        email = email.takeIf { it.isNotBlank() },
                        bornData = bornData
                    )
                    username = ""
                    email = ""
                    bornDataText = ""
                    bornData = null
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        ) {
            Text(if (user == null) "Create User" else "Update User")
        }

        if (isLoading) {
            Text(text = "Loading...")
        }

        errorMessage?.let { error ->
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer
                )
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = error,
                        color = MaterialTheme.colorScheme.onErrorContainer,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        user?.let { user ->
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "User: ${user.username}, " +
                            "Email: ${user.email}, " +
                            "Born: ${user.bornData}, " +
                            "Age: ${user.age}"
                )
            }
        }
    }

}