package com.notasapp.notas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.notasapp.notas.DB.Note
import com.notasapp.notas.DB.NoteSection
import com.notasapp.notas.DB.SectionNote
import com.notasapp.notas.Model.NoteSectionModel
import com.notasapp.notas.Model.SectionNoteModel
import com.notasapp.notas.Utilities.UtilsClass
import kotlinx.android.synthetic.main.activity_new_note.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.onComplete
import org.jetbrains.anko.uiThread

class NewNoteActivity : AppCompatActivity() {

    var idSections :Int  = 0
    var listItemsNote :MutableList<SectionNoteModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_note)
        supportActionBar?.hide()

        idSections =  intent.getStringExtra("id").toString().toInt()
        var title_sections = intent.getStringExtra("title_sections").toString()
        textView_title.setText(UtilsClass.Utils.converterText(title_sections))
        println("id new items--->"+ idSections)
        getListSectionsNote()


        btn_new_sections_note.setOnClickListener {
            val builder = AlertDialog.Builder(this)
                .create()
            val view = LayoutInflater.from(this).inflate(R.layout.dialog_customs_new_items, null)
            val editText = view.findViewById<TextInputEditText>(R.id.textFiel_text_items)
            val bnCancel = view.findViewById<Button>(R.id.btn_cancel)
            val btnAcep = view.findViewById<Button>(R.id.btn_ok)
            builder.setView(view)
            btnAcep.setOnClickListener {
                builder.dismiss()
                if(editText.text!!.trim().toString() != ""){
                    creatreNewItemsNote(editText.text!!.trim().toString())
                }
            }
            bnCancel.setOnClickListener {
                builder.dismiss()
            }
            builder.setCanceledOnTouchOutside(false)
            builder.show()
        }
    }


    private fun getListSectionsNote(){
        doAsync {
            val listSectionItems = Note.dbSectionNote.SectionNoteDao().getNoteSectionNote(idSections)
            uiThread {
                listItemsNote.clear()
                println("total-----"+ listSectionItems)
                if(listSectionItems.size == 0){
                    text_title_note.visibility = View.VISIBLE
                }else{
                    text_title_note.visibility = View.GONE
                    for(i in 0 until listSectionItems.size){
                        val items = SectionNoteModel(
                            listSectionItems[i].id,
                            listSectionItems[i].name,
                            listSectionItems[i].dateCreate,
                            listSectionItems[i].idSections,
                            listSectionItems[i].color
                        )
                        listItemsNote.add(items)
                    }
                }
            }
            onComplete {
                println("Lista total items section note---------->"+ listItemsNote)
                var _adapter = AdapterNoteSections(listSectionItems, this@NewNoteActivity,{deleteSection(it)},{updateSection(it)} )
                val rc_view = findViewById<RecyclerView>(R.id.rv_items_note)
                rc_view.layoutManager = LinearLayoutManager(this@NewNoteActivity)
                rc_view.adapter =  _adapter
            }
        }
    }


    fun deleteSection(section: Int){
        doAsync {
            Note.dbSectionNote.SectionNoteDao().deleteSectionNoteItem(section)
            uiThread {}
            onComplete {
                getListSectionsNote()
            }
        }
    }

    fun updateSection(model: SectionNote){
        doAsync {
            Note.dbSectionNote.SectionNoteDao().updateSectionsNote(model.name, UtilsClass.Utils.getCurrentDate(), model.id)
            onComplete {
                getListSectionsNote()
            }
        }
    }


    private fun creatreNewItemsNote(nameItems:String){
        doAsync {
            var sectionNote = SectionNote(0, nameItems, UtilsClass.Utils.getCurrentDate(), idSections ,UtilsClass.Utils.colorGenerate())
            Note.dbSectionNote.SectionNoteDao().insertSectionNote(sectionNote)
            uiThread {}
            onComplete {
                getListSectionsNote()
            }
        }
    }

}