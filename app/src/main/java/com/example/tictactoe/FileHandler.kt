package com.example.tictactoe

import android.view.View


class FileHandler(view: View) {

    val inputStream = view.context.resources.openRawResource(R.raw.teams)
    val lines = inputStream.bufferedReader().readLines()
    val iterator = lines.iterator()

    fun hasNextLine(): Boolean {
        return iterator.hasNext()
    }

    fun getNext(): String {
        return iterator.next()
    }
}

