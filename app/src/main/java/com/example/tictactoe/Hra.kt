package com.example.tictactoe

import android.database.sqlite.SQLiteDatabase
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.GridLayout
import android.widget.ImageView
import kotlin.random.Random

/*
    Hra - jadro aplikácie, udržiava informácie o aktuálnom stave hry, hernom poli.
    Obsahuje atribúty ako rozhodca, ktorý rozhoduje o výsledku hry.
    Obsahuje atribút db, ktorý ma na starosti prácu s databázou
 */
class Hra(private val view: View, difficulty: Int?, private val gameFragment: GameFragment) {

    private val policka = Array(4) { Array(4) { "empty" } }
    private var hracNaRade = Random.nextInt(2)
    private val db: SQLiteDatabase
    private val databaseHelper: MyDatabaseHelper
    private val rozhodca = Rozhodca(this)

    fun getHracaNaRade() : Int {
        return hracNaRade
    }

    init {
        policka[0][0] = "koniec"
        val teamHandler = TeamHandler(difficulty, view)

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

        databaseHelper = MyDatabaseHelper(this.view.context, teamHandler.getAllTeamNames())
        db = databaseHelper.writableDatabase
    }


    fun napisaloSaSlovo(
        menoHracaOdUzivatela: String,
        kliknutePolicko: ImageView?,
        r: Int,
        s: Int,
        gridLayout: GridLayout
    ) {



        val nazovTimuA = policka[0][s].removeSuffix("_small")
        val nazovTimuB = policka[r][0].removeSuffix("_small")

        if (databaseHelper.jeToSpravneMeno(menoHracaOdUzivatela, nazovTimuA, nazovTimuB, db)) {
            policka[r][s] = if (hracNaRade == 0) "ocko" else "xko"
            kliknutePolicko?.setOnClickListener(null)
            kliknutePolicko?.setTag("polozene")
            gameFragment.spravnaOdpoved()
        } else {
            gameFragment.nespravnaOdpoved()
        }

        gameFragment.setKliknutePolicko(null)
        posunTah()
        gameFragment.aktualizujGui(this, view, gridLayout)
        val vysledokKola = rozhodca.skontroluj(3)
        if (vysledokKola != -1) {
            gameFragment.koniecHry(vysledokKola)
        }
    }

    private fun posunTah() {
        hracNaRade = (hracNaRade + 1) % 2
    }

    fun getPolicka(): Array<Array<String>> {
        return policka
    }
}

