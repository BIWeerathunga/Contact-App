package com.example.phonebook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.phonebook.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding
    private lateinit var db: ContactDatabaseHelper
    private lateinit var contactsAdapter: ContactsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        db =  ContactDatabaseHelper(this)
        contactsAdapter = ContactsAdapter(db.getAllContacts(), this)

        //binding.notesRCV.layoutManager = LinearLayoutManager(this)
        binding.notesRCV.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        binding.notesRCV.adapter = contactsAdapter



        binding.floatBtn.setOnClickListener{
            val intent = Intent(this, AddContactsActivity::class.java)
            startActivity(intent)
        }


    }

    override fun onResume(){
        super.onResume()
        contactsAdapter.refreshData(db.getAllContacts())
    }

}