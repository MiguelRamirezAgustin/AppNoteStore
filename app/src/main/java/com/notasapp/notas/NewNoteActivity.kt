package com.notasapp.notas

import android.content.Intent
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
import com.notasapp.notas.Utilities.SharedPreference
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
        var sharedPreferences = SharedPreference(this@NewNoteActivity)

        textView_title.setText(UtilsClass.Utils.converterText(sharedPreferences.getStringTitle("titleSections").toString()))
        println("id new items--->"+ idSections)
        getListSectionsNote()

        btn_new_sections_note.setOnClickListener {
            val intent = Intent(this@NewNoteActivity, WriteActivity::class.java)
                intent.putExtra("idSection", idSections.toString())
                intent.putExtra("processUpdate", "updateNot")
            startActivity(intent)
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
                            listSectionItems[i].textContent,
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
                var adapter = AdapterNoteSections(listSectionItems, this@NewNoteActivity,{deleteSection(it)})
                val rc_view = findViewById<RecyclerView>(R.id.rv_items_note)
                    rc_view.layoutManager = LinearLayoutManager(this@NewNoteActivity)
                    rc_view.adapter =  adapter
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


}