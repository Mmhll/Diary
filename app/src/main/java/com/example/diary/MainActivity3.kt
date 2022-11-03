package com.example.diary

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room

class MainActivity3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        //Запуск базы данных allowMainThreadQueries нужно для того, чтобы все запросы выполнялись в основном потоке
        val db = Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "Diary").allowMainThreadQueries().build()
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView2)
        //Получение записей о невыполненных задачах
        val array = db.completedDao().getNotes()
        //Создание адаптера для recyclerView
        val adapter = RecyclerViewAdapter2(array, this)
        //Добавление к адаптеру обычного нажатия на элемент списка
        adapter.setOnItemClickListener(object: RecyclerViewAdapter2.onItemClickListener{
            override fun onItemClick(position: Int) {
                //Для показа диалогового окна
                AlertDialog.Builder(this@MainActivity3)
                    .setMessage(array[position].description)
                    .setPositiveButton("Невыполнено"
                    ) { _, _ ->
                        //Здесь происходит перевод элемента из таблицы выполненных задач в невыполненные
                        db.uncompletedDao().putNote(DiaryNoteUncompleted(
                            array[position].id,
                            array[position].description))
                        //Удаление из выполненных
                        db.completedDao().deleteById(array[position].id)
                        //Пересоздание activity для вывода измененного списка
                        recreate()
                    }
                    .setNegativeButton("Удалить") { _, _ ->
                        //Удаление из таблицы выполненных задач выбранную задачу
                        db.completedDao().deleteById(array[position].id)
                        //Пересоздание activity для вывода измененного списка
                        recreate()
                    }
                    .show()
            }
        })
        //Добавление к адаптеру длительного нажатия
        adapter.setOnLongItemClickListener(object :RecyclerViewAdapter2.onLongItemClickListener{
            override fun onLongItemClick(position: Int): Boolean {
                //Здесь происходит перевод элемента из таблицы выполненных задач в невыполненные
                db.uncompletedDao().putNote(DiaryNoteUncompleted(
                    array[position].id,
                    array[position].description))
                //Удаление из таблицы выполненных задач выбранную задачу
                db.completedDao().deleteById(array[position].id)
                //Пересоздание activity для вывода измененного списка
                recreate()
                return true
            }

        })
        //Привязка адаптера к recyclerView
        recyclerView.adapter = adapter

        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}