package com.example.tictactoe

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import android.widget.Toast

/**
 * Implementation of App Widget functionality.
 */
class MyTicTacToeWidget : AppWidgetProvider() {
    private var playerTurn = 0
    private val policka = Array(3) { Array(3) { "empty" } }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        loadPlayerTurn(context)
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
        resetujSa(context, -1)
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        loadPlayerTurn(context)
        loadPolicka(context!!)
        if (intent?.action == "com.example.tictactoe.ACTION_CELL_CLICKED") {
            val cellNumber = intent.getIntExtra("CELL_NUMBER", -1)
            if (cellNumber != -1) {
                val row = (cellNumber - 1) / 3
                val col = (cellNumber - 1) % 3
                if (policka[row][col] == "empty") {
                    policka[row][col] = if (playerTurn == 0) "xko" else "ocko"
                    val views = RemoteViews(context?.packageName, R.layout.my_tic_tac_toe_widget)
                    val cellId = "cell_$cellNumber"
                    val resId =
                        context?.resources?.getIdentifier(cellId, "id", context.packageName) ?: 0
                    views.setImageViewResource(
                        resId,
                        if (playerTurn == 0) R.drawable.xko else R.drawable.ocko
                    )
                    playerTurn = (playerTurn + 1) % 2
                    savePlayerTurn(context!!)
                    savePolicka(context!!)
                    val appWidgetManager = AppWidgetManager.getInstance(context)
                    appWidgetManager.updateAppWidget(
                        ComponentName(context!!, MyTicTacToeWidget::class.java),
                        views
                    )
                    val vysledokKola = RozhodcaWidgetu().skontroluj(policka)
                    if (vysledokKola in (0..2)) {
                        resetujSa(context!!, vysledokKola)
                    }
                }
            }
        }
    }

    fun resetujSa(context: Context, vysledokKola: Int) {
        if (vysledokKola == 0) {
            Toast.makeText(context, "Vyhralo O!!!", Toast.LENGTH_LONG).show()
        } else if (vysledokKola == 1) {
            Toast.makeText(context, "Vyhralo X!!!", Toast.LENGTH_LONG).show()
        } else if (vysledokKola == 2) {
            Toast.makeText(context, "Remíza", Toast.LENGTH_LONG).show()
        }

        // Resetovanie stavu políčok
        for (i in 0 until 3) {
            for (j in 0 until 3) {
                policka[i][j] = "empty"
            }
        }

        // Uloženie stavu políčok
        val sharedPreferences = context.getSharedPreferences("MyTicTacToeWidget", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            for (i in 0 until 3) {
                for (j in 0 until 3) {
                    putString("cell_${i}_${j}", "empty")
                }
            }
            apply()
        }

        // Aktualizácia widgetu
        val appWidgetManager = AppWidgetManager.getInstance(context)
        val appWidgetIds = appWidgetManager.getAppWidgetIds(ComponentName(context, MyTicTacToeWidget::class.java))
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }


    private fun savePolicka(context: Context) {
        // Uloženie stavu políčok
        val sharedPreferences = context.getSharedPreferences("MyTicTacToeWidget", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            for (i in 0 until 3) {
                for (j in 0 until 3) {
                    putString("cell_${i}_${j}", policka[i][j])
                }
            }
            apply()
        }
    }

    private fun loadPolicka(context: Context) {
        // Načítanie stavu políčok
        val sharedPreferences = context.getSharedPreferences("MyTicTacToeWidget", Context.MODE_PRIVATE)
        for (i in 0 until 3) {
            for (j in 0 until 3) {
                policka[i][j] = sharedPreferences.getString("cell_${i}_${j}", "empty") ?: "empty"
            }
        }
    }

    /**/
    private fun savePlayerTurn(context: Context) {
        val sharedPreferences = context.getSharedPreferences("MyTicTacToeWidget", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putInt("playerTurn", playerTurn)
            apply()
        }
    }

    private fun loadPlayerTurn(context: Context?) {
        val sharedPreferences = context?.getSharedPreferences("MyTicTacToeWidget", Context.MODE_PRIVATE)
        if (sharedPreferences != null) {
            playerTurn = sharedPreferences.getInt("playerTurn", 0)
        }
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.my_tic_tac_toe_widget)

    // Nastavenie obrázka pre každé políčko na predvolený obrázok
    for (i in 1..9) {
        val cellId = "cell_$i"
        val resId = context.resources.getIdentifier(cellId, "id", context.packageName)
        views.setImageViewResource(resId, R.drawable.empty)
    }

    for (i in 1..9) {
        val cellIntent = Intent(context, MyTicTacToeWidget::class.java).apply {
            action = "com.example.tictactoe.ACTION_CELL_CLICKED"
            putExtra("CELL_NUMBER", i)
        }
        val cellPendingIntent = PendingIntent.getBroadcast(
            context,
            i,
            cellIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        val cellId = "cell_$i"
        val resId = context.resources.getIdentifier(cellId, "id", context.packageName)
        views.setOnClickPendingIntent(resId, cellPendingIntent)
    }
    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}


