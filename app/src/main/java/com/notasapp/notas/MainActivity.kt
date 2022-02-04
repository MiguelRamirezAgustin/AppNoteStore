package com.notasapp.notas

import android.content.DialogInterface
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.notasapp.notas.DB.Note
import com.notasapp.notas.DB.NoteSection
import com.notasapp.notas.Model.SectionModel
import com.notasapp.notas.Utilities.UtilsClass
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.onComplete
import org.jetbrains.anko.uiThread
import java.io.Console
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    var listItems :MutableList<SectionModel> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        getEntity()


        //Add new items sections
        btn_new_sections.setOnClickListener {
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
                    creatreNewItems(editText.text!!.trim().toString())
                }
            }
            bnCancel.setOnClickListener {
                builder.dismiss()
            }
            builder.setCanceledOnTouchOutside(false)
            builder.show()
        }


    }



    private fun getEntity(){
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
                        val items = SectionModel(
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
                var adapter_ = AdaperSections(listSectionItems, this@MainActivity)
                val rc_view = findViewById<RecyclerView>(R.id.rv_items)
                rc_view.layoutManager = LinearLayoutManager(this@MainActivity)
                rc_view.adapter =  adapter_
            }
        }
    }


    private fun creatreNewItems(nameItems:String){
        doAsync {
            var noteSections = NoteSection(0, nameItems,UtilsClass.Utils.colorGenerate(), UtilsClass.Utils.getCurrentDate())
                Note.dbNoteSection.noteSectionDao().insertNoteSection(noteSections)
            uiThread {}
            onComplete {
                getEntity()
            }
        }
    }
}