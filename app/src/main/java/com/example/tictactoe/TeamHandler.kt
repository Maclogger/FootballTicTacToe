package com.example.tictactoe

import android.view.View

class TeamHandler(val difficulty: Int?, view: View) {
    val allTeams = loadTeamsFromFile(view)
    var teamsA = Array(3){"empty"}
    var teamsB = Array(3){"empty"}

    init {
        if (difficulty == 0) {
            teamsA = allTeams.shuffled().take(teamsA.size).toTypedArray()
            for (i in 0..teamsA.size - 1) {
                teamsA[i] = teamsA[i] + "_small"
            }
            teamsB = teamsA
        } else if (difficulty == 1) {

            teamsA = allTeams.shuffled().take(teamsA.size).toTypedArray()
            for (i in 0..teamsA.size - 1) {
                teamsA[i] = teamsA[i] + "_small"
            }
            teamsB = allTeams.shuffled().take(teamsB.size).toTypedArray()
            for (i in 0..teamsB.size - 1) {
                teamsB[i] = teamsB[i] + "_small"
            }
        } else {
            println("Iná obtiažnosť zatiaľ nie je implementovaná!!!")
        }
    }


    private fun loadTeamsFromFile(view: View): ArrayList<String> {
        val sol = ArrayList<String>()
        val fileHandler = FileHandler(view)
        while (fileHandler.hasNextLine()) {
            sol.add(fileHandler.getNext())
        }
        return sol
    }

    fun getTeams(c: Char): Array<String>? {
        if (c == 'A') {
            return teamsA
        } else if (c == 'B') {
            return teamsB
        } else {
            println("Neplatný znak tímu. Možnosti sú iba 'A' a 'B'!!!")
            return null
        }
    }


}
