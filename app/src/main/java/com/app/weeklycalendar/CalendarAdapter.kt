package com.app.weeklycalendar

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class CalendarAdapter(private val listener: (calendarDateModel: CalendarDateModel, position: Int) -> Unit) :
    RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {

    private var list = emptyList<CalendarDateModel>()
    var adapterPosition = -1

    interface onItemClickListener {
        fun onItemClick(text: String, date: String, day: String)
    }

    private var mListener: onItemClickListener? = null

    fun setOnItemClickListener(listener: onItemClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.date_layout, parent, false)
        val viewHolder = CalendarViewHolder(view)
        view.setOnClickListener {
            val position = viewHolder.adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                adapterPosition = position
                notifyItemRangeChanged(0, list.size)

                val itemList = list[position]
                val text = itemList.calendarYear.toString()
                val date = itemList.calendarDate
                val day = itemList.calendarDay
                mListener?.onItemClick(text, date, day)
            }
        }
        return viewHolder
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        val itemList = list[position]
        with(holder) {
            calendarDay.text = itemList.calendarDay
            calendarDate.text = itemList.calendarDate

            val textColor = if (position == adapterPosition) R.color.white else R.color.black
            calendarDay.setTextColor(ContextCompat.getColor(itemView.context, textColor))
            calendarDate.setTextColor(ContextCompat.getColor(itemView.context, textColor))

            val backgroundResource =
                if (position == adapterPosition) R.drawable.rectangle_fill else R.drawable.rectangle_outline
            linear.background = ContextCompat.getDrawable(itemView.context, backgroundResource)
        }
    }

    override fun getItemCount(): Int = list.size

    class CalendarViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val calendarDay = itemView.findViewById<TextView>(R.id.tv_calendar_day)
        val calendarDate = itemView.findViewById<TextView>(R.id.tv_calendar_date)
        val linear = itemView.findViewById<LinearLayout>(R.id.linear_calendar)
    }

    fun setData(calendarList: List<CalendarDateModel>) {
        list = calendarList
        notifyDataSetChanged()
    }
}
