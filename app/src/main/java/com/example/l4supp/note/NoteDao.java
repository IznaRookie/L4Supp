package com.example.l4supp.note;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert
    void insert(com.example.l4supp.note.Note note);

    @Update
    void update(com.example.l4supp.note.Note note);

    @Delete
    void delete(com.example.l4supp.note.Note note);

    @Query("DELETE FROM note_table")
    void deleteAllNotes();

    @Query("SELECT * FROM note_table ORDER BY priority DESC")
    LiveData<List<com.example.l4supp.note.Note>> getAllNotes();
}