package com.example.noteapp.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.noteapp.models.Note;

import java.util.List;
@Dao
public interface NoteDao {

    @Query("SELECT * FROM note")
    List<Note> getAll();


    @Query("SELECT*FROM note")
    LiveData<List<Note>>getAllLive();

    @Insert
    void insert(Note note);

   @Delete
    void delete(Note note);

   @Update
   void update(Note note);

   @Query("SELECT*FROM note ORDER BY title ASC")
    List<Note>sortAll();
}
