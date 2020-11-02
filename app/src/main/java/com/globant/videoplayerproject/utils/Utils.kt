package com.globant.videoplayerproject.utils

import android.content.Context
import android.widget.Toast
import java.text.SimpleDateFormat

class Utils {

    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun formatDate(date: String): String {
        val inputFormat = SimpleDateFormat(ACTUAL_DATE_FORMAT)
        val outputFormat = SimpleDateFormat(REQUIRED_DATE_FORMAT)
        val dateObject = inputFormat.parse(date)
        return outputFormat.format(dateObject)
    }

    fun adaptImageUrl(imageUrl: String): String{
        return imageUrl.replace(ACTUAL_WIDTH_HEIGHT_IMAGE, REQUIRED_WIDTH_HEIGHT)
    }

    fun adaptImageUrlVideos(imageUrl: String): String{
        return imageUrl.replace(ACTUAL_WIDTH_HEIGHT_VIDEOS, REQUIRED_WIDTH_HEIGHT)
    }

    fun adaptTypeToken(token: String): String{
        val type = token.replace(ACTUAL_AUTHENTICATION_TYPE, CORRECT_AUTHENTICATION_TYPE)
        return type.plus("")
    }
}