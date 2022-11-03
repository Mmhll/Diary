package com.example.diary

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

//Dao - аннотация для room, в которой при помощи
// sql команд происходят различные запросы к базе данных SQLite
@Dao
interface CompletedDao {
    //Вызов всех записей
    @Query("SELECT * FROM DiaryNoteCompleted")
    fun getNotes() : Array<DiaryNoteCompleted>
    //Вызов конкретной записи
    @Query("SELECT * FROM DiaryNoteCompleted WHERE id = :noteId")
    fun getCurrentNote(noteId : Int) : DiaryNoteCompleted
    //Добавление записи
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun putNote(note : DiaryNoteCompleted)
    //Удаление записи
    @Query("DELETE FROM DiaryNoteCompleted WHERE id = :id")
    fun deleteById(id : Int)
}
