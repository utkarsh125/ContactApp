package com.project.contactapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.contactapp.repository.ContactRepository
import com.project.contactapp.room.Contact
import kotlinx.coroutines.launch

class ContactViewModel(private val repository: ContactRepository): ViewModel() {

    val users = repository.contacts
    private var isUpdateOrDelete = false
    private lateinit var contactToUpdateOrDelete: Contact

    val inputName = MutableLiveData<String?>()
    val inputEmail = MutableLiveData<String?>()
    val saveOrUpdateButtonText = MutableLiveData<String>()
    val clearAllOrDeleteButtonText = MutableLiveData<String>()

    init {
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
    }

    fun insert(contact: Contact) = viewModelScope.launch {
        repository.insert(contact)
    }

    fun delete(contact: Contact) = viewModelScope.launch {
        repository.delete(contact)
        inputName.value = null
        inputEmail.value = null
        isUpdateOrDelete = false
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
    }

    fun update(contact: Contact) = viewModelScope.launch {
        repository.update(contact)
        inputName.value = null
        inputEmail.value = null
        isUpdateOrDelete = false
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
    }

    fun clearAll() = viewModelScope.launch {
        repository.deleteAll()
    }

    fun saveOrUpdate() {
        if(isUpdateOrDelete){
            contactToUpdateOrDelete.contact_name = inputName.value!!
            contactToUpdateOrDelete.contact_email = inputEmail.value!!
            update(contactToUpdateOrDelete)
        }else{
            val name = inputName.value!!
            val email = inputEmail.value!!
            insert(Contact(0, name, email))
            inputName.value = null
            inputEmail.value = null
        }
    }

    fun clearAllOrDelete(){
        if(isUpdateOrDelete){
            delete(contactToUpdateOrDelete)
        }else{
            clearAll()
        }
    }

    fun initUpdateAndDelete(contact: Contact){
        inputName.value = contact.contact_name
        inputEmail.value = contact.contact_email
        isUpdateOrDelete = true
        contactToUpdateOrDelete = contact
        saveOrUpdateButtonText.value = "Update"
        clearAllOrDeleteButtonText.value = "Delete"
    }
}