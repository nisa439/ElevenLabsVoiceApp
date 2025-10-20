package com.example.elevenlabsvoiceapp

import android.app.Application
import android.media.MediaPlayer
import android.media.MediaRecorder
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

class VoiceViewModel(application: Application) : AndroidViewModel(application) {

    // API Key - BURAYA KENDİ API KEY'İNİ YAZ
    private val apiKey = "6120e3be71094f6fd8f4f716a42c3eb7f1685de7a5612bc9e69d17b25282e02f"
    private val elevenLabsService = ElevenLabsService(apiKey)

    // Ses seçenekleri (Popüler ElevenLabs sesleri)
    val voices = listOf(
        Voice("21m00Tcm4TlvDq8ikWAM", "Rachel (Female)"),
        Voice("AZnzlk1XvdvUeBnXmlld", "Domi (Female)"),
        Voice("EXAVITQu4vr4xnSDxMaL", "Bella (Female)"),
        Voice("ErXwobaYiN019PkySvjV", "Antoni (Male)"),
        Voice("MF3mGyEYCl7XYWbV9V6O", "Elli (Female)"),
        Voice("TxGEqnHWrfWFTfGW9XjX", "Josh (Male)")
    )

    private val _selectedVoice = MutableStateFlow(voices[0])
    val selectedVoice: StateFlow<Voice> = _selectedVoice

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _statusMessage = MutableStateFlow("")
    val statusMessage: StateFlow<String> = _statusMessage

    private val _recognizedText = MutableStateFlow("")
    val recognizedText: StateFlow<String> = _recognizedText

    private var mediaPlayer: MediaPlayer? = null
    private var mediaRecorder: MediaRecorder? = null
    private var audioFile: File? = null

    fun selectVoice(voice: Voice) {
        _selectedVoice.value = voice
    }

    // Text-to-Speech
    fun textToSpeech(text: String) {
        if (text.isBlank()) {
            _statusMessage.value = "Lütfen metin girin!"
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _statusMessage.value = "Ses oluşturuluyor..."

            val audioData = elevenLabsService.textToSpeech(text, _selectedVoice.value.id)

            if (audioData != null) {
                playAudio(audioData)
                _statusMessage.value = "Ses çalınıyor!"
            } else {
                _statusMessage.value = "Hata! API key'inizi kontrol edin."
            }

            _isLoading.value = false
        }
    }

    // Speech-to-Speech
    fun speechToSpeech() {
        val file = audioFile
        if (file == null || !file.exists()) {
            _statusMessage.value = "Önce ses kaydı yapın!"
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _statusMessage.value = "Ses dönüştürülüyor..."

            val audioData = elevenLabsService.speechToSpeech(file, _selectedVoice.value.id)

            if (audioData != null) {
                playAudio(audioData)
                _statusMessage.value = "Dönüştürülmüş ses çalınıyor!"
            } else {
                _statusMessage.value = "Hata! API key'inizi kontrol edin."
            }

            _isLoading.value = false
        }
    }

    // Ses kaydı başlat
    fun startRecording() {
        try {
            audioFile = File(getApplication<Application>().cacheDir, "recorded_audio.mp3")

            mediaRecorder = MediaRecorder().apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                setOutputFile(audioFile!!.absolutePath)
                prepare()
                start()
            }

            _statusMessage.value = "Kayıt başladı..."
        } catch (e: Exception) {
            _statusMessage.value = "Kayıt hatası: ${e.message}"
        }
    }

    // Ses kaydı durdur
    fun stopRecording() {
        try {
            mediaRecorder?.apply {
                stop()
                release()
            }
            mediaRecorder = null
            _statusMessage.value = "Kayıt tamamlandı!"
        } catch (e: Exception) {
            _statusMessage.value = "Durdurma hatası: ${e.message}"
        }
    }

    // Google Speech Recognition için recognized text set etme
    fun setRecognizedText(text: String) {
        _recognizedText.value = text
    }

    private fun playAudio(audioData: ByteArray) {
        try {
            val tempFile = File(getApplication<Application>().cacheDir, "temp_audio.mp3")
            FileOutputStream(tempFile).use { it.write(audioData) }

            mediaPlayer?.release()
            mediaPlayer = MediaPlayer().apply {
                setDataSource(tempFile.absolutePath)
                prepare()
                start()
            }
        } catch (e: Exception) {
            _statusMessage.value = "Çalma hatası: ${e.message}"
        }
    }

    override fun onCleared() {
        super.onCleared()
        mediaPlayer?.release()
        mediaRecorder?.release()
    }
}

data class Voice(val id: String, val name: String)