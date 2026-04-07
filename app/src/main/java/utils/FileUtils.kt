package utils

import android.content.Context
import android.net.Uri
import java.io.File

object FileUtils {

    fun copyPdfToAppStorage(
        context: Context,
        uri: Uri
    ): File {

        val file =
            File(
                context.filesDir,
                "doc_${System.currentTimeMillis()}.pdf"
            )

        context.contentResolver
            .openInputStream(uri)
            ?.use { input ->

                file.outputStream().use { output ->
                    input.copyTo(output)
                }
            }

        return file
    }
}