package com.project.contactapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.contactapp.room.Contact

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactScreen(viewModel: ContactViewModel) {

    // Observe LiveData from ViewModel
    val contacts by viewModel.users.observeAsState(emptyList())
    val inputName by viewModel.inputName.observeAsState("")
    val inputEmail by viewModel.inputEmail.observeAsState("")
    val saveButtonText by viewModel.saveOrUpdateButtonText.observeAsState("Save")
    val clearButtonText by viewModel.clearAllOrDeleteButtonText.observeAsState("Clear All")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        // Title
        Text(
            text = "Contact Manager",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        // Input Section
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                // Name Input
                OutlinedTextField(
                    value = inputName ?: "",
                    onValueChange = { viewModel.inputName.value = it },
                    label = { Text("Contact Name") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                // Email Input
                OutlinedTextField(
                    value = inputEmail ?: "",
                    onValueChange = { viewModel.inputEmail.value = it },
                    label = { Text("Contact Email") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                // Buttons Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Save/Update Button
                    Button(
                        onClick = { viewModel.saveOrUpdate() },
                        modifier = Modifier.weight(1f),
                        enabled = !inputName.isNullOrBlank() && !inputEmail.isNullOrBlank()
                    ) {
                        Text(saveButtonText ?: "Save")
                    }

                    // Clear All/Delete Button
                    OutlinedButton(
                        onClick = { viewModel.clearAllOrDelete() },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(clearButtonText ?: "Clear All")
                    }
                }
            }
        }

        // Contacts List
        Card(
            modifier = Modifier.fillMaxWidth().weight(1f),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Contacts (${contacts.size})",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                if (contacts.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No contacts yet.\nAdd your first contact above!",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(contacts) { contact ->
                            ContactItem(
                                contact = contact,
                                onEditClick = { viewModel.initUpdateAndDelete(contact) },
                                onDeleteClick = { viewModel.delete(contact) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ContactItem(
    contact: Contact,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = contact.contact_name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = contact.contact_email,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                IconButton(onClick = onEditClick) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Contact",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                IconButton(onClick = onDeleteClick) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Contact",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}