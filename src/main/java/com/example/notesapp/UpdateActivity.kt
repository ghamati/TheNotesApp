package com.example.notesapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.notesapp.databinding.ActivityUpdateBinding

class UpdateActivity : AppCompatActivity() {

    private lateinit var binding : ActivityUpdateBinding
    private lateinit var db : NoteDatabase
    private var noteId : Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        db = NoteDatabase(this)

        noteId = intent.getIntExtra("note_id",-1)
        if (noteId == -1) {
            finish()
            return
        }
        val note = db.getNoteByID(noteId)
        binding.edittitleText.setText(note.title)
        binding.editdesText.setText(note.des)

        binding.updateButton.setOnClickListener {
            val newtitle = binding.edittitleText.text.toString()
            val newdesc = binding.editdesText.text.toString()
            val updateNote = Note(noteId,newtitle,newdesc)
            db.updateNote(updateNote)
            finish()
            Toast.makeText(this,"Edited successfully",Toast.LENGTH_SHORT).show()
        }

    }
}