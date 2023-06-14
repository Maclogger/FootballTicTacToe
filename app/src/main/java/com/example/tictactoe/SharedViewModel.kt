package com.example.tictactoe

import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
/*
    Slúži na udržiavanie stavu aktuálneho štýlu medzi fragmentami aplikácie.
 */
class SharedViewModel : ViewModel() {
    private var currentMainStyle = MutableLiveData(R.style.mainGreenStyle)
    private var currentSecondaryStyle = MutableLiveData(R.style.secondaryGreenStyle)

    fun getCurrentMainStyle(): MutableLiveData<Int> {
        return currentMainStyle
    }

    fun setCurrentMainStyle(style: Int) {
        currentMainStyle.value = style
    }

    fun getCurrentSecondaryStyle(): MutableLiveData<Int> {
        return currentSecondaryStyle
    }

    fun setCurrentSecondaryStyle(style: Int) {
        currentSecondaryStyle.value = style
    }
    init {
        Log.d("SharedViewModel", "Init block executed")
        when (AppCompatDelegate.getDefaultNightMode()) {
            AppCompatDelegate.MODE_NIGHT_YES -> {
                Log.d("utoroooook", "MODE_NIGHT_YES")
                currentMainStyle.value = R.style.mainDarkStyle
                currentSecondaryStyle.value = R.style.secondaryDarkStyle
            }
            AppCompatDelegate.MODE_NIGHT_NO -> {
                Log.d("utoroooook", "MODE_NIGHT_NO")
                currentMainStyle.value = R.style.mainGreenStyle
                currentSecondaryStyle.value = R.style.secondaryGreenStyle
            }
        }
    }

}
