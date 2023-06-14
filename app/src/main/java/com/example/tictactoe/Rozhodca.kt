package com.example.tictactoe

class Rozhodca(hra: Hra) {
    private val policka = hra.getPolicka()
    //0 = O, 1 = X, 2 = remíza, -1 = nerozhodnuté
    fun skontroluj(pocetVitaznych: Int): Int {
        val velkost = policka.size

        // Kontrola riadkov
        for (riadok in policka) {
            for (i in 0..velkost - pocetVitaznych) {
                if (riadok.slice(i until i + pocetVitaznych).all { it == "xko" }) return 1
                if (riadok.slice(i until i + pocetVitaznych).all { it == "ocko" }) return 0
            }
        }

        // Kontrola stĺpcov
        for (i in 0 until velkost) {
            for (j in 0..velkost - pocetVitaznych) {
                if ((j until j + pocetVitaznych).all { policka[it][i] == "xko" }) return 1
                if ((j until j + pocetVitaznych).all { policka[it][i] == "ocko" }) return 0
            }
        }

        // Kontrola diagonál
        for (i in 0..velkost - pocetVitaznych) {
            for (j in 0..velkost - pocetVitaznych) {
                if ((0 until pocetVitaznych).all { policka[i + it][j + it] == "xko" }) return 1
                if ((0 until pocetVitaznych).all { policka[i + it][j + it] == "ocko" }) return 0
                if ((0 until pocetVitaznych).all { policka[i + it][j + pocetVitaznych - it - 1] == "xko" }) return 1
                if ((0 until pocetVitaznych).all { policka[i + it][j + pocetVitaznych - it - 1] == "ocko" }) return 0
            }
        }

        // Kontrola remízy
        if (policka.all { riadok -> riadok.all { it != "empty" } }) return 2

        // Nerozhodnuté
        return -1
    }
}
