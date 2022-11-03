package com.example.diary

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.room.Room

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Запуск базы данных allowMainThreadQueries нужно для того, чтобы все запросы выполнялись в основном потоке
        val db = Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "Diary").allowMainThreadQueries().build()
        //Добавление элементов из xml файла, обращение к ним по id
        val button: Button = findViewById(R.id.button)
        val button2: Button = findViewById(R.id.button2)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        //Получение записей о невыполненных задачах
        val array = db.uncompletedDao().getNotes()
        //Создание адаптера для recyclerView
        val adapter = RecyclerViewAdapter(array, this)
        //Добавление к адаптеру обычного нажатия на элемент списка
        adapter.setOnItemClickListener(object: RecyclerViewAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                //Для показа диалогового окна
                AlertDialog.Builder(this@MainActivity)
                    .setMessage(array[position].description)
                    .setPositiveButton("Выполнено"
                    ) { _, _ ->
                        //Здесь происходит перевод элемента из таблицы невыполненных задач в выполненные
                        db.completedDao().putNote(DiaryNoteCompleted(
                            array[position].id,
                            array[position].description))
                        //Удаление из невыполненных
                        db.uncompletedDao().deleteById(array[position].id)
                        //Пересоздание activity для вывода измененного списка
                        recreate()
                    }
                    .setNegativeButton("Удалить") { _, _ ->
                        //Удаление из таблицы невыполненных задач выбранную задачу
                        db.uncompletedDao().deleteById(array[position].id)
                        //Пересоздание activity для вывода измененного списка
                        recreate()
                    }
                    .show()
                }
        })
        //Добавление к адаптеру длительного нажатия
        adapter.setOnLongItemClickListener(object :RecyclerViewAdapter.onLongItemClickListener{
            override fun onLongItemClick(position: Int): Boolean {
                //Здесь происходит перевод элемента из таблицы невыполненных задач в выполненные
                db.completedDao().putNote(DiaryNoteCompleted(
                    array[position].id,
                    array[position].description))
                //Удаление из таблицы невыполненных задач выбранную задачу
                db.uncompletedDao().deleteById(array[position].id)
                //Пересоздание activity для вывода измененного списка
                recreate()
                return true
            }

        })
        //Привязка адаптера к recyclerView
        recyclerView.adapter = adapter

        //Добавление разделяющих полос для каждого элемента
//        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        //Если будет вопрос, почему выбрал не onClick, а setOnClickListener, то скажи,
        //что увидел на сайте андроида, что делают именно так
        button.setOnClickListener {
            //intent нужен для перехода на другую activity, в первом параметре указано this, это контекст, а MainActivity2::class.java - это
            //activity, в которую осуществляется переход, после чего startActivity(intent) запускает переход
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
            //Завершение текущей activity
            finish()
        }
        button2.setOnClickListener {
            val intent = Intent(this, MainActivity3::class.java)
            startActivity(intent)
            //Завершение текущей activity
            finish()
        }
    }
}