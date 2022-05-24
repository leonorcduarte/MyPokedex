package com.example.mypokedex.util

object StringUtils {

    fun getSubstring(baseString: String?, character: String, subStringIndex: Int) : String{
        return if (baseString != null)
            baseString.split(character)[subStringIndex]
        else ""
    }

    fun getFormattedId(id: String): String{
        val idNum: Int = id.toInt()
        return when {
            idNum < 10 -> "00$id"
            idNum in 10..99 -> "0$id"
            else -> id
        }
    }
}