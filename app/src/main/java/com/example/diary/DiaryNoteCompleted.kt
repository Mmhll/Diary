package com.example.diary

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//Создание таблицы для SQLite, entity - аннотация, которая указывает room, что это именно таблица, а не просто data class с первичным ключом и полем
@Entity
data class DiaryNoteCompleted(
    @PrimaryKey var id: Int = 0,
    @ColumnInfo(name = "description") val description: String
)
