package com.project.contactapp.repository

import com.project.contactapp.room.Contact
import com.project.contactapp.room.ContactDAO


//Repository: acts as bridge between the ViewModel and Data Source
class ContactRepository(private val contactDAO: ContactDAO) {

    val contacts = contactDAO.getAllContactsInDB()

    suspend fun insert(contact: Contact): Long{
        return contactDAO.insertContact(contact)
    }

    suspend fun update(contact: Contact){

        return contactDAO.updateContact(
            contact
        )
    }

    suspend fun delete(contact: Contact){
        return contactDAO.deleteContact(
            contact
        )
    }


    suspend fun deleteAll(){
        return contactDAO.deleteAll()
    }


}