package com.example.petproject.utils

import android.content.Context
import android.os.Build
import android.os.Environment
import com.example.petproject.App
import java.io.File

object Utils {

    /**
     * Получить путь до карты памяти (Только для API 19 KitKat)
     * более старые версии вернет только локальное хранилище во встроенной памяти
     * @return
     */
    fun getExternalStorageDirectorySD(): File? {
        val fileESD = Environment.getExternalStorageDirectory()
        val sFileESD = fileESD.toString()
        val context: Context = App.appContext
        try {
            for (file in context.getExternalFilesDirs(null)) {
                if (file != null) {
                    val sFile = file.toString()
                    if (!sFile.contains(sFileESD)) return file
                }
            }
        } catch (e: Exception) {
        }
        val result = context.getExternalFilesDir(null)
        return result ?: context.cacheDir
    }
}