package com.example.tictactoe

import android.view.View
import java.io.InputStream


class FileHandler(inputStream : InputStream) {

    val lines = inputStream.bufferedReader().readLines()
    val iterator = lines.iterator()

    fun hasNextLine(): Boolean {
        return iterator.hasNext()
    }

    fun getNext(): String {
        return iterator.next()
    }

    fun getAll(): ArrayList<String> {
        return ArrayList(lines)
    }

}

