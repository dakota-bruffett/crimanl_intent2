package com.example.crimanlintent

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Point
import java.nio.file.Path

class picturesutils {
    fun getscaledBitMap(path: String,activity: Activity) {
        val size = Point()
        activity.windowManager.defaultDisplay.getSize(size)
        return  getScaledBitMap(path, size.x, size.y)
    }
    fun getScaledBitMap(path: String, destWidth: Int, destHeight: Int) {
        var options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(path, options)

        val srcWidth = options.outWidth.toFloat()
        val srcHeight = options.outHeight.toFloat()

        var inSampleSize = 1
        if (srcHeight > destHeight || srcWidth > destWidth) {
            val heightScale = srcHeight / destHeight
            val widthScale = srcWidth / destWidth

            val sampleScale = if (heightScale > widthScale) {
                heightScale
            } else {
                widthScale
            }
            inSampleSize = Math.round(sampleScale)
        }
        options = BitmapFactory.Options()
        options.inSampleSize = inSampleSize

    }

}