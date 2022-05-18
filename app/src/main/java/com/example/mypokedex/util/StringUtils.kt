package com.example.mypokedex.util

object StringUtils {

    fun getSubstring(baseString: String?, character: String, subStringIndex: Int) : String{
        return if (baseString != null)
            baseString.split(character)[subStringIndex]
        else ""
    }
}