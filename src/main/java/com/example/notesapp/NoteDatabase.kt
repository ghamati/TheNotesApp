package com.example.notesapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class NoteDatabase(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME,null,
    DATABASE_VERSION) {
    companion object{
        private const val DATABASE_NAME = "notesapp.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "notes"
        private const val COLUM_ID = "id"
        private const val COLUM_TITLE = "title"
        private const val COLUM_DESCRIPTION = "description"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME ($COLUM_ID INTEGER PRIMARY KEY,$COLUM_TITLE TEXT ,$COLUM_DESCRIPTION TEXT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }
    fun insertNote(note: Note){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUM_TITLE,note.title)
            put(COLUM_DESCRIPTION,note.des)
        }

        db.insert(TABLE_NAME,null,values)
        db.close()
    }

    fun getAllNotes(): List<Note> {
        val noteList = mutableListOf<Note>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query,null)

        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUM_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUM_TITLE))
            val desc = cursor.getString(cursor.getColumnIndexOrThrow(COLUM_DESCRIPTION))

            val note = Note(id,title,desc)
            noteList.add(note)
        }
        cursor.close()
        db.close()
        return noteList
    }

    fun updateNote(note: Note){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUM_TITLE,note.title)
            put(COLUM_DESCRIPTION,note.des)
        }

        val whereClause = "$COLUM_ID = ?"
        val whereArgs = arrayOf(note.id1.toString())
        db.update(TABLE_NAME,values,whereClause,whereArgs)
        db.close()
    }

    fun getNoteByID(noteId : Int):Note{
        val db = writableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUM_ID = $noteId"
        val cursor = db.rawQuery(query,null)
        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUM_ID))
        val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUM_TITLE))
        val desc = cursor.getString(cursor.getColumnIndexOrThrow(COLUM_DESCRIPTION))

        cursor.close()
        db.close()
        return Note(id,title,desc)
    }

    fun deleteNote(noteId: Int){
        val db = writableDatabase
        val whereClause = "$COLUM_ID = ?"
        val whereArgs = arrayOf(noteId.toString())
        db.delete(TABLE_NAME,whereClause,whereArgs)
        db.close()
    }
}