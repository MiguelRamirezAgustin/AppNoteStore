package com.notasapp.notas

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.notasapp.notas.DB.NoteSection
import com.notasapp.notas.Utilities.UtilsClass
import kotlinx.android.synthetic.main.item_adapter_sections.view.*

class AdaperSections(val items:List<NoteSection>,
                     val context:Context,
                     val deleteSections: (id:Int) -> Unit) :RecyclerView.Adapter<AdaperSections.ViewHolder>() {


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

        // Event show DialogAler to Delete
        holder.delete.setOnClickListener {
            val builder = AlertDialog.Builder(context)
                .create()
            val view = LayoutInflater.from(context).inflate(R.layout.dialog_customs_new_items, null)
            val textView_title = view.findViewById<TextView>(R.id.txtview_title)
                textView_title.text = "¿Esta seguro de eliminar la sesion?"
            val editText = view.findViewById<TextInputEditText>(R.id.textFiel_text_items)
                editText.visibility = View.GONE
            val bnCancel = view.findViewById<Button>(R.id.btn_cancel)
            val btnAcep = view.findViewById<Button>(R.id.btn_ok)
            builder.setView(view)
            btnAcep.setOnClickListener {
                builder.dismiss()
                deleteSections(model_sections.id)

            }
            bnCancel.setOnClickListener {
                builder.dismiss()
            }
            builder.setCanceledOnTouchOutside(false)
            builder.show()
        }
    }

    override fun getItemCount(): Int {
       return items.size
    }

    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
         val nameSections = itemView.txtView_section_title
         val linear = itemView.linear_border
         val lyrics = itemView.textView_lyrics
         val date_title = itemView.txView_date_sections
         val delete =  itemView.img_delete
         val editar =  itemView.img_edit
    }
}