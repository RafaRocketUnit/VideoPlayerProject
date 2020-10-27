package com.globant.videoplayerproject.utils

import android.content.Context
import android.widget.Toast
import java.text.SimpleDateFormat

class Utils {

    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun formatDate(date: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val outputFormat = SimpleDateFormat("HH:mm • EEE, M/d")
        val dateObject = inputFormat.parse(date)
        return outputFormat.format(dateObject)
    }

    fun adaptImageUrl(imageUrl: String): String{
        return imageUrl.replace("{width}x{height}", "150x170")
    }

    fun adaptImageUrlVideos(imageUrl: String): String{
        return imageUrl.replace("%{width}x%{height}", "150x170")
    }

    fun adaptTypeToken(token: String): String{
        val type = token.replace("b", "B")
        return type.plus("")
    }
}