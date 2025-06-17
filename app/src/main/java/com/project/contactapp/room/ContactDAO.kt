package com.project.contactapp.room

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

interface ContactDAO {

    @Insert
    suspend fun insertContact(contact: Contact): Long

    @Update
    suspend fun updateContact(contact: Contact)

    @Delete
    suspend fun deleteContact(contact: Contact)

    @Query("DELETE FROM contacts_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM contacts_table")
    fun getAllContactsInDB(): LiveData<List<Contact>>
}