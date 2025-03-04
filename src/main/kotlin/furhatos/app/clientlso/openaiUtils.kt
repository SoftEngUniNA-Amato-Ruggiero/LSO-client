package furhatos.app.clientlso

fun getApiKey(): String {
    try {
        val filePath = System.getenv("OPENAI_API_KEY_FILE")
        return java.io.File(filePath).readText().trim()
    } catch (e: Exception) {
        throw RuntimeException("API key not found")
    }
}