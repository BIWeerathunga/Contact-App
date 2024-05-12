package com.example.phonebook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.phonebook.databinding.ActivityAddContactsBinding

class AddContactsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddContactsBinding
    private lateinit var db: ContactDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddContactsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = ContactDatabaseHelper(this)
        binding.saveBtn.setOnClickListener {

            val name = binding.titleEditTxt.text.toString()
            val number = binding.contentEditTxt.text.toString()

            if (name.isNotEmpty() || number.isNotEmpty()){
                val contact = Contact(0, name, number)
                db.insertContact(contact)
                finish()
                Toast.makeText(this@AddContactsActivity, "Contact Saved", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this@AddContactsActivity, "Contact Empty!", Toast.LENGTH_SHORT).show()
            }
        }

    }
}