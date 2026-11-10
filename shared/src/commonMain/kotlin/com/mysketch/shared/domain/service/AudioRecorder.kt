package com.mysketch.shared.domain.service

expect class AudioRecorder {
    fun startRecording(outputPath: String)
    fun stopRecording(): ByteArray?
    fun isRecording(): Boolean
}
