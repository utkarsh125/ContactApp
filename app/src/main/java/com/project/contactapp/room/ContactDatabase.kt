package com.project.contactapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase



@Database(entities = [Contact::class], version = 1)
abstract class ContactDatabase: RoomDatabase() {

    //DAOs are responsible for defining methods that interact with the database.

    abstract val contactDAO: ContactDAO

    //Singleton design pattern

    companion object{

        @Volatile
        private var INSTANCE: ContactDatabase? = null

        fun getInstance(context: Context): ContactDatabase {
            synchronized(this){

                var instance = INSTANCE

                if(instance == null){
                    //creating the database object
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ContactDatabase::class.java,
                        "contacts_database"
                    ).build()
                }

                INSTANCE = instance
                return instance

            }
        }
    }
}