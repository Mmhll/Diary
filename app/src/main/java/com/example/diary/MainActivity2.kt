package com.example.diary

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.room.Room

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        //Запуск базы данных allowMainThreadQueries нужно для того, чтобы все запросы выполнялись в основном потоке
        val db = Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "Diary").allowMainThreadQueries().build()
        //Добавление элементов из xml файла, обращение к ним по id
        val button: Button = findViewById(R.id.button3)
        val editText: TextView = findViewById(R.id.editText)

        //Получение последнего id из базы данных для того, чтобы увеличить на 1,
        // потому что autoGenerate в room работает не всегда хорошо

        var lastId = 0

        //try/catch для того, чтобы приложение не вылетало, если записей нет

        try {
            lastId = db.uncompletedDao().getNotes()[db.uncompletedDao().getNotes().size - 1].id + 1
        }
        catch (_: Exception){

        }
        button.setOnClickListener {
            if (editText.text.toString().isNotEmpty()){
                //Добавление новой записи в ежедневник
                db.uncompletedDao().putNote(
                    DiaryNoteUncompleted(lastId,  description = editText.text.toString())
                )
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}