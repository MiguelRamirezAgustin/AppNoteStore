package com.notasapp.notas

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.notasapp.notas.DB.NoteSection
import kotlinx.android.synthetic.main.item_adapter_sections.view.*

class AdaperSections(val items:List<NoteSection>,
                     val context:Context) :RecyclerView.Adapter<AdaperSections.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_adapter_sections, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model_sections = items.get(position)
        holder.nameSections.text = model_sections.name
    }

    override fun getItemCount(): Int {
       return items.size
    }

    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
         val nameSections = itemView.txtView_section_title
    }
}