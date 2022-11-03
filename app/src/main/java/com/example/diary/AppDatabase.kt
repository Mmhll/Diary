package com.example.diary

import androidx.room.Database
import androidx.room.RoomDatabase


//Добавление базы данных в проект, entities - дата классы, version изменяется только если ты изменяешь
//базу данных (добавляешь новые столбцы в таблицах, создаёшь новые таблицы, удаляешь таблицы)
//Нашёл ты реализацию вот здесь если чо //https://developer.android.com/training/data-storage/room
@Database(entities = [DiaryNoteCompleted::class, DiaryNoteUncompleted::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun uncompletedDao() : UncompletedDao
    abstract fun completedDao() : CompletedDao
}