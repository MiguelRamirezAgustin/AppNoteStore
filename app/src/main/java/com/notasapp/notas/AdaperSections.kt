package com.notasapp.notas

import android.content.Context
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.notasapp.notas.DB.NoteSection
import com.notasapp.notas.Utilities.UtilsClass
import kotlinx.android.synthetic.main.item_adapter_sections.view.*

class AdaperSections(val items:List<NoteSection>,
                     val context:Context,
                     val deleteSections: (id:Int) -> Unit,
                     val updateItems: (NoteSection) -> Unit) :RecyclerView.Adapter<AdaperSections.ViewHolder>() {


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
            val textView_title_delete = view.findViewById<TextView>(R.id.txtview_title_delete)
                textView_title.text = "¿Esta seguro de eliminar la sesion?"
            val editText = view.findViewById<TextInputEditText>(R.id.textFiel_text_items)
                editText.visibility = View.GONE
                textView_title_delete.visibility = View.VISIBLE
                textView_title_delete.text =  UtilsClass.Utils.converterText(model_sections.name)
            val bnCancel = view.findViewById<Button>(R.id.btn_cancel)
            val btnAcept = view.findViewById<Button>(R.id.btn_ok)
                builder.setView(view)
                btnAcept.setOnClickListener {
                    builder.dismiss()
                    deleteSections(model_sections.id)
                }
                bnCancel.setOnClickListener {
                    builder.dismiss()
                }
                builder.setCanceledOnTouchOutside(false)
                builder.show()
        }

        holder.update.setOnClickListener {
            val builder = AlertDialog.Builder(context)
                .create()
            val view = LayoutInflater.from(context).inflate(R.layout.dialog_customs_edit_items, null)
            val img_dismiss = view.findViewById<ImageView>(R.id.img_dismiss)
            val btn_update = view.findViewById<Button>(R.id.btn_update)
            val editText_update = view.findViewById<TextInputEditText>(R.id.textFiel_text_items_update)
                editText_update.setText(model_sections.name)
            editText_update.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    if(s.toString().equals("")){
                        btn_update.isEnabled = false
                    }else{
                        btn_update.isEnabled = true
                    }
                }
                override fun beforeTextChanged(s: CharSequence, start: Int,
                                               count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence, start: Int,
                                           before: Int, count: Int) {}
            })

                builder.setView(view)
                btn_update.setOnClickListener {
                    builder.dismiss()
                    var model = NoteSection(
                        model_sections.id,
                        editText_update.text.toString(),
                        "",
                        ""
                    )
                    updateItems(model)
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

    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
         val nameSections = itemView.txtView_section_title
         val linear = itemView.linear_border
         val lyrics = itemView.textView_lyrics
         val date_title = itemView.txView_date_sections
         val delete =  itemView.img_delete
         val update =  itemView.img_edit
    }
}