package com.notasapp.notas

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.notasapp.notas.DB.Note
import com.notasapp.notas.DB.SectionNote
import com.notasapp.notas.Utilities.UtilsClass
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_new_note.*
import kotlinx.android.synthetic.main.activity_write.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.onComplete
import org.jetbrains.anko.uiThread


class WriteActivity : AppCompatActivity() {

    var idSections = 0
    var processUpdate = ""
    var idNote = 0
    var update: Boolean = false;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)

        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) actionBar.hide()
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LOW_PROFILE
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)

        idSections = intent.getStringExtra("idSection").toString().toInt()
        processUpdate = intent.getStringExtra("processUpdate").toString()

        if (processUpdate == "update") {
            txt_title.setText(intent.getStringExtra("titleSection"))
            rtEditText.setText(intent.getStringExtra("titleSectionContent"))
            idNote = intent.getStringExtra("idNote").toString().toInt()
            img_save.isEnabled = false
        }

        img_close.setOnClickListener {
            val intent = Intent(this@WriteActivity, NewNoteActivity::class.java)
            intent.putExtra("id", idSections.toString())
            startActivity(intent)
        }


        img_save.setOnClickListener {
            if (txt_title.text.toString().trim().isEmpty()) {
                Toast.makeText(
                    this@WriteActivity,
                    "Es necesario agregar un titulo",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                println("id------Update " + processUpdate)
                if (processUpdate == "update") {
                    updateNote()
                } else {
                    createNote()
                }
            }
        }

        txt_title.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s == null || s.isEmpty()) {
                    img_save.isEnabled = false
                    update = false
                    img_save.setImageDrawable(resources.getDrawable(R.drawable.save_not))
                } else {
                    img_save.isEnabled = true
                    img_save.setImageDrawable(resources.getDrawable(R.drawable.save_ok))
                }
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {}
        })

        rtEditText.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s == null || s.isEmpty()) {
                    img_save.isEnabled = false
                    update = false
                    img_save.setImageDrawable(resources.getDrawable(R.drawable.save_not))
                } else {
                    img_save.isEnabled = true
                    img_save.setImageDrawable(resources.getDrawable(R.drawable.save_ok))
                }
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {}
        })






    }

    fun updateNote() {
        doAsync {
            Note.dbSectionNote.SectionNoteDao().updateSectionsNote(
                txt_title.text.toString().trim(),
                rtEditText.text.toString(),
                UtilsClass.Utils.getCurrentDate(),
                idNote
            )
            onComplete {
                val intent = Intent(this@WriteActivity, NewNoteActivity::class.java)
                intent.putExtra("id", idSections.toString())
                startActivity(intent)
            }
        }
    }

    fun createNote() {
        doAsync {
            var sectionNote = SectionNote(
                0, txt_title.text.toString().trim(), rtEditText.text.toString(), UtilsClass.Utils.getCurrentDate(), idSections,
                UtilsClass.Utils.colorGenerate()
            )
            Note.dbSectionNote.SectionNoteDao().insertSectionNote(sectionNote)
            uiThread {}
            onComplete {
                val intent = Intent(this@WriteActivity, NewNoteActivity::class.java)
                intent.putExtra("id", idSections.toString())
                startActivity(intent)
            }
        }
    }


}