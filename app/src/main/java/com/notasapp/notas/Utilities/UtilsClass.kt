package com.notasapp.notas.Utilities

import java.text.SimpleDateFormat
import java.util.*

class UtilsClass {

    object Utils {
        fun getCurrentDate():String{
            val sdf = SimpleDateFormat("MM/dd/yyyy")
            return sdf.format(Date())
        }

        fun converterText(str:String):String{
            if (str == null || str.isEmpty()) return str;
            else return str.substring(0, 1).toUpperCase() + str.substring(1);
        }
    }

}