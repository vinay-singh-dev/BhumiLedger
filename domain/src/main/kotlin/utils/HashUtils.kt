package utils

fun sha256(input: String): String {
    val bytes = java.security.MessageDigest
        .getInstance("SHA-256")
        .digest(input.toByteArray())
    return bytes.joinToString("") { "%02x".format(it) }
}