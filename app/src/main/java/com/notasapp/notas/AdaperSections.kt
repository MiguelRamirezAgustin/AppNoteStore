package com.notasapp.notas

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.notasapp.notas.DB.NoteSection
import com.notasapp.notas.Utilities.UtilsClass
import kotlinx.android.synthetic.main.item_adapter_sections.view.*

class AdaperSections(val items:List<NoteSection>,
                     val context:Context) :RecyclerView.Adapter<AdaperSections.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_adapter_sections, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model_sections = items.get(position)
        holder.nameSections.text = UtilsClass.Utils.converterText(model_sections.name)
        holder.linear.setBackgroundColor(Color.parseColor(model_sections.color))
        holder.lyrics.text = model_sections.name.get(0).toString().toUpperCase()
        holder.lyrics.setTextColor(Color.parseColor(model_sections.color))
        holder.date_title.text = model_sections.dateCreate
    }

    override fun getItemCount(): Int {
       return items.size
    }

    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
         val nameSections = itemView.txtView_section_title
         val linear = itemView.linear_border
         val lyrics = itemView.textView_lyrics
         val date_title = itemView.txView_date_sections
    }
}