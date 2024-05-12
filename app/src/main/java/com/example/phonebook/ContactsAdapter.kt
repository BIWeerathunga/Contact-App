package com.example.phonebook

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView


class ContactsAdapter (private var contacts: List<Contact>, context: Context):
    RecyclerView.Adapter<ContactsAdapter. ContactViewHolder>() {

    private val db: ContactDatabaseHelper = ContactDatabaseHelper(context)

    class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val numberTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val nameTextView: TextView = itemView.findViewById(R.id.contentTextVew)
        val updateButton: ImageView = itemView.findViewById(R.id.updateButton)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)
        val callButton: ImageView = itemView.findViewById(R.id.callButton)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):  ContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return  ContactViewHolder(view)
    }

    override fun getItemCount(): Int = contacts.size

    override fun onBindViewHolder(holder:  ContactViewHolder, position: Int) {
        val contact = contacts[position]
        holder.numberTextView.text = contact.number
        holder.nameTextView.text = contact.name

        holder.updateButton.setOnClickListener {
            val intent = Intent(holder.itemView.context, UpdateActivity::class.java).apply {
                putExtra("contact_id", contact.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        holder.deleteButton.setOnClickListener {
            db.deleteContact(contact.id)
            refreshData(db.getAllContacts())
            Toast.makeText(holder.itemView.context, "Note Deleted", Toast.LENGTH_SHORT).show()
        }
        /*holder.callButton.setOnClickListener {
            // When call button is clicked
            val number = contact.number // Assuming contact.number contains the phone number

            // Create an intent with ACTION_DIAL to open the dialer
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:$number") // Set the phone number to dial

            // Start the dialer activity using the context of the item view
            holder.itemView.context.startActivity(intent)
        }*/
        holder.callButton.setOnClickListener {
            val number = contact.number // Assuming contact.number contains the phone number

            // Remove any non-digit characters from the phone number
            val formattedNumber = number.replace("[^0-9]".toRegex(), "")

            // Create an intent with ACTION_DIAL to open the dialer
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:$formattedNumber") // Set the formatted phone number to dial

            // Start the dialer activity using the context of the item view
            holder.itemView.context.startActivity(intent)
        }


    }

    fun refreshData(newNotes: List<Contact>){
        contacts = newNotes
        notifyDataSetChanged()
    }
}