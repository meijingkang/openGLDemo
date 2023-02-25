package com.example.opengldemo.utils

import android.content.Context
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.StringBuilder

class TextResourceReader {
    companion object{
        fun readTextFileFromResource(context: Context, id: Int): String {
//            return context.resources.openRawResource(id).reader().readText()
            val stringBuilder = StringBuilder();
            val bufferedReader =
                BufferedReader(InputStreamReader(context.resources.openRawResource(id)))
            var nextLine:String?= null
            while ((bufferedReader.readLine().also { nextLine = it })!= null) {
                stringBuilder.append(nextLine)
                stringBuilder.append("\n")
            }
            return stringBuilder.toString()

        }
    }
}