package com.example.notesapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class NoteAdapter(private var notes : List<Note>,context: Context): RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private val db : NoteDatabase = NoteDatabase(context)

    class NoteViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val titleTextView : TextView = itemView.findViewById(R.id.titleText)
        val descTextView : TextView = itemView.findViewById(R.id.desText)
        val updateButton : ImageView = itemView.findViewById(R.id.updateButton)
        val deleteButton : ImageView = itemView.findViewById(R.id.deleteButton)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item,parent,false)
        return NoteViewHolder(view)
    }

    override fun getItemCount(): Int = notes.size



    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.titleTextView.text = note.title
        holder.descTextView.text = note.des
        holder.updateButton.setOnClickListener {
            val intent = Intent(holder.itemView.context,UpdateActivity::class.java).apply {
                putExtra("note_id",note.id1)
            }

            holder.itemView.context.startActivity(intent)
        }
        holder.deleteButton.setOnClickListener {
            db.deleteNote(note.id1)
            refreshData(db.getAllNotes())
            Toast.makeText(holder.itemView.context,"Note deleted",Toast.LENGTH_SHORT).show()
        }
    }

    fun refreshData(newNotes : List<Note>){
        notes = newNotes
        notifyDataSetChanged()
    }

}