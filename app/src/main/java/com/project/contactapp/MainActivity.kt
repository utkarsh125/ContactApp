package com.project.contactapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.project.contactapp.repository.ContactRepository
import com.project.contactapp.room.ContactDatabase
import com.project.contactapp.ui.theme.ContactAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize database, repository, and viewmodel
        val database = ContactDatabase.getInstance(this)
        val repository = ContactRepository(database.contactDAO)
        val viewModelFactory = ContactViewModelFactory(repository)
        val contactViewModel = ViewModelProvider(this, viewModelFactory)[ContactViewModel::class.java]

        setContent {
            ContactAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ContactScreen(contactViewModel)
                }
            }
        }
    }
}