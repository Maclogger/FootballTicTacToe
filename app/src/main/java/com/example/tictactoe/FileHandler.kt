package com.example.tictactoe

import android.view.View
import java.io.InputStream


class FileHandler(inputStream : InputStream) {

    private val lines = inputStream.bufferedReader().readLines()
    private val iterator = lines.iterator()

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

