package com.example.tictactoe

import android.util.Log
import android.view.View

/*
    zabezpečuje náhodný výber tímov z databázy podľa zvolenej náročnosti
 */
class TeamHandler(difficulty: Int?, view: View) {
    private val allTeams = loadTeamsFromFile(view)
    private var teamsA = Array(3){"empty"}
    private var teamsB = Array(3){"empty"}

    init {
        if (difficulty == 0) {
            //TODO - Pri obhajobe odkomentovať pre jednoduchšiu demonštráciu
            teamsA = allTeams.shuffled().take(teamsA.size).toTypedArray()
            //teamsA[0] = "arsenal"
            //teamsA[1] = "chelsea"
            //teamsA[2] = "liverpool"

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
            Log.d("ERROR!!!", "Iná obtiažnosť zatiaľ nie je implementovaná!!!")
        }
    }

    private fun loadTeamsFromFile(view: View): ArrayList<String> {
        val sol = ArrayList<String>()
        val fileHandler = FileHandler(view.context.resources.openRawResource(R.raw.teams))
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
            Log.d("chyba", "Neplatný znak tímu. Možnosti sú iba 'A' a 'B'!!!")
            return null
        }
    }

    fun getAllTeamNames(): ArrayList<String> {
        return allTeams
    }


}
