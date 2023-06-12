package com.example.tictactoe

import android.view.View
import kotlin.random.Random

class Hra(view: View) {


    val view = view
    val teamHandler = TeamHandler(1, view)
    val policka = Array(4) { Array(4) { "empty" } }
    var hracNaRade = Random.nextInt(1)

    init {
        val teamsA = teamHandler.getTeams('A')
        val teamsB = teamHandler.getTeams('B')

        //nastavenie teamov
        if (teamsA != null && teamsB != null) {
            for (i in 1..teamsA.size) {
                policka[0][i] = teamsA[i - 1]
            }
            for (i in 1..teamsB.size) {
                policka[i][0] = teamsB[i - 1]
            }
        }
    }

}

