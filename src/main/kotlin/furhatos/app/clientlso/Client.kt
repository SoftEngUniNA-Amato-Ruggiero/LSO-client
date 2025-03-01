package furhatos.app.clientlso

import furhatos.app.clientlso.personality.PersonalityTest.Attributes
import mu.KotlinLogging
import org.json.JSONObject
import java.io.BufferedReader
import java.io.Closeable
import java.io.IOException
import java.net.Socket
import java.net.UnknownHostException
import kotlin.system.exitProcess

class Client : Closeable {
    companion object {
        private val ADDRESS = serverAddress ?: "127.0.0.1"
        private const val PORT = 9999
    }

    private var clientSocket: Socket
    private var reader: BufferedReader
    private val logger = KotlinLogging.logger {}

    init {
        try {
            clientSocket = Socket(ADDRESS, PORT)
            reader = BufferedReader(clientSocket.inputStream.reader())
        } catch (e: UnknownHostException) {
            logger.error("Server not found", e)
            exitProcess(1)
        }
    }

    override fun close() {
        try {
            reader.close()
            clientSocket.close()
        } catch (e: IOException) {
            logger.error("Error closing socket", e)
        }
    }

    fun getPersonalityJson(userAnswers: Map<Attributes, Int>): String {
        try {
            val userJson = JSONObject(userAnswers)
            clientSocket.outputStream.write(userJson.toString(4).toByteArray())

            val response = StringBuilder()
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                response.append(line).append("\n")
            }
            return response.toString()
        } catch (e: Exception) {
            logger.error(e.message, e)
            throw e
        }
    }
}

class ServerOfflineException : RuntimeException("Server is offline")