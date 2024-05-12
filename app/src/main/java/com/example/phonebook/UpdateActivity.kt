package com.example.phonebook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.phonebook.databinding.ActivityUpdateBinding

class UpdateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateBinding
    private lateinit var db: ContactDatabaseHelper
    private var contactId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = ContactDatabaseHelper(this)

        contactId = intent.getIntExtra("contact_id", -1)
        if (contactId==-1){
            finish()
            return
        }

        val contact = db.getContactByID(contactId)
        binding.updateTitleEditTxt.setText(contact.name)
        binding.updateContentEditTxt.setText(contact.number)
        binding.updateSaveBtn.setOnClickListener {
            val newName = binding.updateTitleEditTxt.text.toString()
            val newNumber = binding.updateContentEditTxt.text.toString()
            val updateContact = Contact(contactId, newName,newNumber)
            if (newName.isNotEmpty() || newNumber.isNotEmpty()) {
                db.updateContact(updateContact)
                finish()
                Toast.makeText(this@UpdateActivity, "Changes Saved", Toast.LENGTH_SHORT).show()
            }else{
                db.deleteContact(contactId)
                Toast.makeText(this@UpdateActivity, "Deleted", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

    }
}