package com.globant.videoplayerproject.utils

import android.content.Context
import android.widget.Toast

class Utils() {

    fun showToast(context: Context,message: String){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}