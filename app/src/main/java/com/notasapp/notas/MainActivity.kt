package com.notasapp.notas

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.notasapp.notas.DB.Note
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        getEntity()

        rv_items.setOnClickListener {

        }


        //Add new items sections
        btn_new_sections.setOnClickListener {
            val builder = AlertDialog.Builder(this)
              .create()
            val view = LayoutInflater.from(this).inflate(R.layout.dialog_customs_new_items, null)
            val buttonCancel = view.findViewById<Button>(R.id.btn_cancel)
            val buttonAcept = view.findViewById<Button>(R.id.btn_ok)
            builder.setView(view)
            buttonCancel.setOnClickListener {
                builder.dismiss()
            }
            buttonAcept.setOnClickListener {
                builder.dismiss()
            }
            builder.setCanceledOnTouchOutside(false)
            builder.show()
        }
    }



    private fun getEntity(){
        doAsync {
            val listEntity = Note.dbNoteSection.noteSectionDao().getNoteSection()
            uiThread {
                if(listEntity.size == 0){
                    text_title.visibility = View.VISIBLE
                }else{

                }
            }
        }
    }
}