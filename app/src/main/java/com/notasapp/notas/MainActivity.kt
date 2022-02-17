package com.notasapp.notas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.notasapp.notas.DB.Note
import com.notasapp.notas.DB.NoteSection
import com.notasapp.notas.Model.NoteSectionModel
import com.notasapp.notas.Utilities.UtilsClass
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.onComplete
import org.jetbrains.anko.uiThread
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    var listItems :MutableList<NoteSectionModel> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        getListSections()


        //Add new items sections
        btn_new_sections.setOnClickListener {
            val builder = AlertDialog.Builder(this)
              .create()
            val view = LayoutInflater.from(this).inflate(R.layout.dialog_customs_new_items, null)
            val editText = view.findViewById<TextInputEditText>(R.id.textFiel_text_items)
            val bnCancel = view.findViewById<Button>(R.id.btn_cancel)
            val btnAcep  = view.findViewById<Button>(R.id.btn_ok)
            builder.setView(view)

            btnAcep.setOnClickListener {
                if(editText.text!!.trim().toString() != ""){
                    builder.dismiss()
                    creatreNewItems(editText.text!!.trim().toString())
                }else{
                    Toast.makeText(this@MainActivity, "Es requerido agregar el nombre", Toast.LENGTH_SHORT ).show()
                }
            }
            bnCancel.setOnClickListener {
                builder.dismiss()
            }
            builder.setCanceledOnTouchOutside(false)
            builder.show()
        }


    }



    private fun getListSections(){
        doAsync {
            val listSectionItems = Note.dbNoteSection.noteSectionDao().getNoteSection()
            uiThread {
                listItems.clear()
                println("total-----"+ listSectionItems)
                if(listSectionItems.size == 0){
                    text_title.visibility = View.VISIBLE
                }else{
                    text_title.visibility = View.GONE
                    for(i in 0 until listSectionItems.size){
                        val items = NoteSectionModel(
                            listSectionItems[i].id,
                            listSectionItems[i].name,
                            listSectionItems[i].color,
                            listSectionItems[i].dateCreate
                        )
                        listItems.add(items)
                    }
                }
            }
            onComplete {
                println("Lista total items----------------->"+ listItems)
                var adapter = AdaperSections(listSectionItems, this@MainActivity,{deleteItem(it)},{updateItems(it)} )
                val rc_view = findViewById<RecyclerView>(R.id.rv_items)
                rc_view.layoutManager = LinearLayoutManager(this@MainActivity)
                rc_view.adapter =  adapter
            }
        }
    }

    fun deleteItem(section: Int){
        doAsync {
            //val idItem = contact.id
            println("Id Eliminar --> "+section)
            Note.dbNoteSection.noteSectionDao().deleteSectionItem(section)
            uiThread {
                getListSections()
            }
        }
    }

    fun updateItems(model: NoteSection){
        println("Data update to intems-->"+ model)
        doAsync {
            Note.dbNoteSection.noteSectionDao().getNoteSectionNoteUpdate(model.name, UtilsClass.Utils.getCurrentDate() , model.id)
            onComplete {
                getListSections()
            }
        }
    }


    private fun creatreNewItems(nameItems:String){
        doAsync {
            var noteSections = NoteSection(0, nameItems,UtilsClass.Utils.colorGenerate(), UtilsClass.Utils.getCurrentDate())
                Note.dbNoteSection.noteSectionDao().insertNoteSection(noteSections)
            uiThread {}
            onComplete {
                getListSections()
            }
        }
    }

}