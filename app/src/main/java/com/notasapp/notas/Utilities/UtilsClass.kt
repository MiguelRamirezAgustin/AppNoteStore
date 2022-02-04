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

        fun colorGenerate():String{
            val colors = arrayOf(
                "#CD6155",
                "#8E44AD",
                "#2980B9",
                "#48C9B0",
                "#2ECC71",
                "#F1C40F",
                "#CA6F1E",
                "#707B7C",
                "#2E4053",
                "#935116",
                "#A6ACAF",
                "#34495E",
                "#1A5276",
                "#A9DFBF",
                "#AED6F1",
                "#F1C40F",
                "#E74C3C",
                "#3498DB"
            )
            return colors.random()
        }
    }

}