package com.example.tictactoe

class RozhodcaWidgetu() {
    fun skontroluj(policka: Array<Array<String>>): Int {
        // Kontrola riadkov
        for (riadok in policka) {
            if (riadok.all { it == "xko" }) return 1
            if (riadok.all { it == "ocko" }) return 0
        }

        // Kontrola stĺpcov
        for (i in 0 until 3) {
            if ((0 until 3).all { policka[it][i] == "xko" }) return 1
            if ((0 until 3).all { policka[it][i] == "ocko" }) return 0
        }

        // Kontrola diagonál
        if ((0 until 3).all { policka[it][it] == "xko" }) return 1
        if ((0 until 3).all { policka[it][it] == "ocko" }) return 0
        if ((0 until 3).all { policka[it][2 - it] == "xko" }) return 1
        if ((0 until 3).all { policka[it][2 - it] == "ocko" }) return 0

        // Kontrola remízy
        if (policka.all { riadok -> riadok.all { it != "empty" } }) return 2

        // Nerozhodnuté
        return -1
    }
}
