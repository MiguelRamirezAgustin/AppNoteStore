package com.notasapp.notas

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.shapes.OvalShape
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.notasapp.notas.DB.NoteSection
import com.notasapp.notas.DB.SectionNote
import com.notasapp.notas.Utilities.UtilsClass
import kotlinx.android.synthetic.main.items_adapter_note_section.view.*


class AdapterNoteSections(
                    val items:List<SectionNote>,
                    val context:Context,
                    val deleteSection:(id:Int) -> Unit,
                    val updateSection:(SectionNote)-> Unit
                    ):RecyclerView.Adapter<AdapterNoteSections.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.items_adapter_note_section, parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model_note = items.get(position)
        holder.titleNote.text  = model_note.name
        holder.dateNote.text   = model_note.dateCreate
        holder.lyricsNote.text = model_note.name.get(0).toString().toUpperCase()

        val shape   = GradientDrawable()
        shape.shape = GradientDrawable.OVAL
        shape.setColor(Color.parseColor(model_note.color))
        shape.mutate()
        holder.lyricsNote.setBackground(shape)

        holder.deleteNote.setOnClickListener {
            val builder = AlertDialog.Builder(context)
                .create()
            val view  = LayoutInflater.from(context).inflate(R.layout.dialog_customs_new_items, null)
            val textView_title = view.findViewById<TextView>(R.id.txtview_title)
            val textView_title_delete = view.findViewById<TextView>(R.id.txtview_title_delete)
            textView_title.text = "¿Esta seguro de eliminar esta nota?"
            val editText = view.findViewById<TextInputEditText>(R.id.textFiel_text_items)
            editText.visibility = View.GONE
            textView_title_delete.visibility = View.VISIBLE
            textView_title_delete.text =  UtilsClass.Utils.converterText(model_note.name)
            val bnCancel = view.findViewById<Button>(R.id.btn_cancel)
            val btnAcept = view.findViewById<Button>(R.id.btn_ok)
            builder.setView(view)

            btnAcept.setOnClickListener {
                builder.dismiss()
                deleteSection(model_note.id)
            }

            bnCancel.setOnClickListener {
                builder.dismiss()
            }
            builder.setCanceledOnTouchOutside(false)
            builder.show()
        }

        // New item update
        holder.updateNote.setOnClickListener {
            val builder = androidx.appcompat.app.AlertDialog.Builder(context)
                .create()
            val view = LayoutInflater.from(context).inflate(R.layout.dialog_customs_edit_items, null)
            val img_dismiss     = view.findViewById<ImageView>(R.id.img_dismiss)
            val btn_update      = view.findViewById<Button>(R.id.btn_update)
            val editText_update = view.findViewById<TextInputEditText>(R.id.textFiel_text_items_update)
            editText_update.setText(model_note.name)
            builder.setView(view)

            btn_update.setOnClickListener {
                if(editText_update.text.toString().trim().isEmpty()){
                    Toast.makeText(context, "Es requerido agregar un texto", Toast.LENGTH_SHORT ).show()
                }else{
                    var model = SectionNote(model_note.id, editText_update.text.toString(), "", 0,"")
                    builder.dismiss()
                    updateSection(model)
                }
            }

            img_dismiss.setOnClickListener {
                builder.dismiss()
            }
            builder.setCanceledOnTouchOutside(false)
            builder.show()
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(view:View):RecyclerView.ViewHolder(view) {
        val lyricsNote = view.textView_lyrics_note
        val dateNote   = view.txtView_date_sections_note
        val titleNote  = view.txtView_section_title_note
        val deleteNote = view.img_delete_note
        val updateNote   = view.img_edit_note
    }
}