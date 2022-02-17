package com.notasapp.notas

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.shapes.OvalShape
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.notasapp.notas.DB.NoteSection
import com.notasapp.notas.DB.SectionNote
import kotlinx.android.synthetic.main.items_adapter_note_section.view.*





class AdapterNoteSections(
                    val items:List<SectionNote>,
                    val contex:Context,
                    val deleteSection:(id:Int) -> Unit,
                    val updateSection:(SectionNote)-> Unit
                    ):RecyclerView.Adapter<AdapterNoteSections.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(contex).inflate(R.layout.items_adapter_note_section, parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model_note = items.get(position)
        holder.titleNote.text  = model_note.name
        holder.dateNote.text   = model_note.dateCreate
        holder.lyricsNote.text = model_note.name.get(0).toString().toUpperCase()

        val shape = GradientDrawable()
        shape.shape = GradientDrawable.OVAL
        shape.setColor(Color.parseColor(model_note.color))
        shape.mutate()
        holder.lyricsNote.setBackground(shape)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(view:View):RecyclerView.ViewHolder(view) {
        val lyricsNote = view.textView_lyrics_note
        val dateNote   = view.txtView_date_sections_note
        val titleNote  = view.txtView_section_title_note
        val deleteNote = view.img_delete_note
        val editNote   = view.img_edit_note
    }
}