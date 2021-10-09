package com.openclassrooms.realestatemanager.utils

import android.content.Context
import android.widget.Toast
import java.io.*

class StorageUtils {
    private fun createOrGetFile(destination: File, fileName: String, folderName: String): File? {
        val folder = File(destination, folderName)
        return File(folder, fileName)
    }

    private fun readOnFile(context: Context, file: File): String? {
        var result: String? = null
        if (file.exists()) {
            val br: BufferedReader
            try {
                br = BufferedReader(FileReader(file))
                try {
                    val sb = StringBuilder()
                    var line: String = br.readLine()
                    while (line != null) {
                        sb.append(line)
                        sb.append("\n")
                        line = br.readLine()
                    }
                    result = sb.toString()
                } finally {
                    br.close()
                }
            } catch (e: IOException) {
                Toast.makeText(
                    context,
                    "context.getString(R.string.error_happened)",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        return result
    }

    private fun writeOnFile(context: Context, text: String, file: File) {
        try {
            file.parentFile.mkdirs()
            val fos = FileOutputStream(file)
            val w: Writer = BufferedWriter(OutputStreamWriter(fos))
            try {
                w.write(text)
                w.flush()
                fos.getFD().sync()
            } finally {
                w.close()
                Toast.makeText(context, "context.getString(R.string.saved)", Toast.LENGTH_LONG).show()
            }
        } catch (e: IOException) {
            Toast.makeText(context, "context.getString(R.string.error_happened)", Toast.LENGTH_LONG)
                .show()
        }
    }
}