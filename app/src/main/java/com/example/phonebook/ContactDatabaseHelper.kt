package com.example.phonebook

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ContactDatabaseHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object{
        private const val DATABASE_NAME= "contactsapp.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "allcontacts"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_NUMBER = "number"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery ="CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_NAME TEXT, $COLUMN_NUMBER TEXT)"
        db?.execSQL(createTableQuery)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun insertContact(contact: Contact){
        val db =writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, contact.name)
            put(COLUMN_NUMBER, contact.number)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }


    fun getAllContacts():List<Contact>{
        val contactList = mutableListOf<Contact>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
            val number = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NUMBER))

            val contact = Contact(id, name, number)
            contactList.add(contact)

        }
        cursor.close()
        db.close()
        return  contactList
    }

    fun updateContact(contact: Contact){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, contact.name)
            put(COLUMN_NUMBER, contact.number)
        }
        val whereClause = "$COLUMN_ID= ?"
        val whereArgs = arrayOf(contact.id.toString())
        db.update(TABLE_NAME, values, whereClause, whereArgs)
        db.close()
    }

    fun getContactByID(contactId:Int):Contact{
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $contactId"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()


        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
        val number = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NUMBER))

        cursor.close()
        db.close()
        return Contact(id, name,number)
    }

    fun deleteContact(contactId:Int){
        val db = writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(contactId.toString())
        db.delete(TABLE_NAME, whereClause, whereArgs)
        db.close()
    }

}
