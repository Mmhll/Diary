package com.example.diary

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

//Dao - аннотация для room, в которой при помощи
// sql команд происходят различные запросы к базе данных SQLite
@Dao
interface UncompletedDao {
    //Вызов всех записей
    @Query("SELECT * FROM DiaryNoteUncompleted")
    fun getNotes() : Array<DiaryNoteUncompleted>
    //Вызов конкретной записи
    @Query("SELECT * FROM DiaryNoteUncompleted WHERE id = :noteId")
    fun getCurrentNote(noteId : Int) : DiaryNoteUncompleted
    //Добавление записи
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun putNote(note : DiaryNoteUncompleted)
    //Удаление записи
    @Query("DELETE FROM DiaryNoteUncompleted WHERE id = :id")
    fun deleteById(id : Int)
}
