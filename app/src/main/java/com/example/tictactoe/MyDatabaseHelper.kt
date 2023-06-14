package com.example.tictactoe

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class MyDatabaseHelper(private val context: Context,
                       private val allTeamNames: ArrayList<String>)
    : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "mydatabase.db"
        private const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Tu môžete vykonať SQL príkazy na vytvorenie tabuliek a iných databázových objektov
        db.execSQL("CREATE TABLE teams (teamName TEXT PRIMARY KEY)")
        db.execSQL("CREATE TABLE players (meno TEXT, teamName TEXT, FOREIGN KEY(teamName) REFERENCES teams(teamName), PRIMARY KEY(meno, teamName))")


        // Vloženie mien tímov do databázy
        val contentValues = ContentValues()
        for (teamName in allTeamNames) {
            contentValues.put("teamName", teamName)
            db.insert("teams", null, contentValues)
        }

        //Vloženie mien hráčov do databázy
        naplnTabulku(db, "arsenal")
        naplnTabulku(db, "birmingham_city")
        naplnTabulku(db, "blackpool")
        naplnTabulku(db, "chelsea")
        naplnTabulku(db, "liverpool")
        naplnTabulku(db, "manchester_city")
        naplnTabulku(db, "manchester_united")
        //TODO - časom pridať ďalšie tímy
    }

    private fun naplnTabulku(db: SQLiteDatabase, nazovTimu: String) {
        val contentValues = ContentValues()
        val resourceId = context.resources.getIdentifier(nazovTimu, "raw", context.packageName)
        val fileHandler = FileHandler(context.resources.openRawResource(resourceId))
        contentValues.clear()
        for (menoHraca in fileHandler.getAll()) {
            if (menoHraca == "" || nazovTimu == "") continue
            contentValues.put("name", menoHraca)
            db.execSQL("INSERT INTO players (meno, teamName) VALUES (?, ?)", arrayOf(menoHraca, nazovTimu))
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Tu môžete vykonať operácie potrebné pri zmene verzie databázy TODO
    }

    fun jeToSpravneMeno(
        menoHracaOdUzivatela: String,
        nazovTimuA: String,
        nazovTimuB: String,
        db: SQLiteDatabase
    ) : Boolean {
        val query = """
            SELECT COUNT(*)
            FROM players
            WHERE meno = ? AND (teamName = ? OR teamName = ?)
        """
        val cursor = db.rawQuery(query, arrayOf(menoHracaOdUzivatela, nazovTimuA, nazovTimuB))
        cursor.moveToFirst()
        val count = cursor.getInt(0)
        cursor.close()
        if (nazovTimuA == nazovTimuB) return count >= 1
        return count >= 2
    }
}
