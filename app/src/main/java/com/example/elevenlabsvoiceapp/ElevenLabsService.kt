package com.example.elevenlabsvoiceapp

import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.IOException

class ElevenLabsService(private val apiKey: String) {
    private val client = OkHttpClient()
    private val gson = Gson()

    // Text-to-Speech
    suspend fun textToSpeech(text: String, voiceId: String): ByteArray? = withContext(Dispatchers.IO) {
        val json = """{"text":"$text","model_id":"eleven_monolingual_v1"}"""
        val body = json.toRequestBody("application/json".toMediaType())

        val request = Request.Builder()
            .url("https://api.elevenlabs.io/v1/text-to-speech/$voiceId")
            .addHeader("xi-api-key", apiKey)
            .post(body)
            .build()

        try {
            client.newCall(request).execute().use { response ->
                if (response.isSuccessful) response.body?.bytes() else null
            }
        } catch (e: IOException) {
            null
        }
    }

    // Speech-to-Speech
    suspend fun speechToSpeech(audioFile: File, voiceId: String): ByteArray? = withContext(Dispatchers.IO) {
        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart(
                "audio",
                audioFile.name,
                audioFile.readBytes().toRequestBody("audio/mpeg".toMediaType())
            )
            .build()

        val request = Request.Builder()
            .url("https://api.elevenlabs.io/v1/speech-to-speech/$voiceId")
            .addHeader("xi-api-key", apiKey)
            .post(requestBody)
            .build()

        try {
            client.newCall(request).execute().use { response ->
                if (response.isSuccessful) response.body?.bytes() else null
            }
        } catch (e: IOException) {
            null
        }
    }
}