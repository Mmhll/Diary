package com.example.diary

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder


//Это адаптер для RecyclerView, чтобы выводились записи из SQLite в список, без него работать не будет, скажешь, что нашёл решение вот тут
//https://developer.android.com/develop/ui/views/layout/recyclerview
class RecyclerViewAdapter(private val dataSet: Array<DiaryNoteUncompleted>, private val context: Context): RecyclerView.Adapter<RecyclerViewAdapter.VH>() {

    private lateinit var myListener: onItemClickListener
    private lateinit var myLongListener: onLongItemClickListener

    //Добавление интерфейса для клика на элемент recyclerView
    interface onItemClickListener{
        fun onItemClick(position : Int)
    }

    //Инициализация слушателя для клика внутри recyclerViewAdapter, передаётся из внешнего файла (activity)
    fun setOnItemClickListener(listener: onItemClickListener){
        myListener = listener
    }

    //Добавление интерфейса для длительного клика на элемент recyclerView
    interface onLongItemClickListener{
        fun onLongItemClick(position: Int) : Boolean
    }

    //Инициализация слушателя для длительного клика внутри recyclerViewAdapter, передаётся из внешнего файла (activity)
    fun setOnLongItemClickListener(listener: onLongItemClickListener){
        myLongListener = listener
    }

    //ВАЖНО, ИНТЕРФЕЙСЫ И ФУНКЦИИ ВЫШЕ ТЫ НАШЁЛ НА stackOverFlow


    //Добавление textView из recycler_view_item, для того, чтобы изменять каждый элемент на запись из базы данных
    class VH(itemView: View, listener: onItemClickListener, longListener : onLongItemClickListener) : ViewHolder(itemView) {
        val textView :TextView
        //При инициализации адаптера будет браться textView из recycler_view_item по id для дальнейшего изменения
        init {
            textView = itemView.findViewById(R.id.textView)
            //Инициализация методов выше для адаптера
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
            itemView.setOnLongClickListener {
                longListener.onLongItemClick(adapterPosition)
            }
        }
    }

    //Привязка recycler_view_item к этому адаптеру, чтобы все записи имели подобный вид, а также onItemClick и onItemLongClick
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(LayoutInflater.from(context).inflate(R.layout.recycler_view_item, parent, false), myListener, myLongListener)
    }


    //Действия с экземплярами recyclerViewItem, чтобы при нажатии на item происходил вывод полной информации и была возможность добавить в список выполненных, либо просто удалить
    //А при длительном нажатии(удерживании) был перевод в список выполненных
    override fun onBindViewHolder(holder: VH, position: Int) {

        //Смена текста для элемента на тот, что находится в базе данных
        holder.textView.text = dataSet[position].description
    }

    //Возвращает количество элементов, которые будут в recyclerView
    override fun getItemCount(): Int {
        return dataSet.size
    }
}