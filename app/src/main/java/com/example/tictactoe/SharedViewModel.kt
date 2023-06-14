package com.example.tictactoe

import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    var currentMainStyle = MutableLiveData(R.style.mainGreenStyle)
    var currentSecondaryStyle = MutableLiveData(R.style.secondaryGreenStyle)

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
