package utils

import java.io.File
import java.security.MessageDigest

fun sha256(file: File): String {
    val digest = MessageDigest.getInstance("SHA-256")
    val fileBytes = file.readBytes()
        val hashBytes = digest.digest(fileBytes)
    return hashBytes.joinToString("") {
     "%02x".format(it) }
}

fun sha256(input : String):String {
    val bytes = MessageDigest
        .getInstance("SHA-256")
        .digest(input.toByteArray())
        return bytes.joinToString("") {
        "%02x".format(it)
}
}