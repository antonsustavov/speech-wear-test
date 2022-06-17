package com.tamara.care.watch.speech

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import androidx.lifecycle.LifecycleService
import java.util.*

class SpeechListener : LifecycleService(), RecognitionListener {
    private lateinit var speechRecognizer: SpeechRecognizer
    private var isActivated: Boolean = false
    private val activationKeyword: String = "help"
    private val foregroundId: Int = 1000001

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i(">>>>>> START", ">>>>>>>>>>>>>>>>>>>>> START")
        Log.i(">>>>>> START", SpeechRecognizer.isRecognitionAvailable(this).toString())

//        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(
//            this,
//            ComponentName.unflattenFromString("com.google.android.googlequicksearchbox/com.google.android.voicesearch.serviceapi.GoogleRecognitionService")
//        )
//        speechRecognizer = SpeechRecognizer.createOnDeviceSpeechRecognizer(this)
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
        speechRecognizer.setRecognitionListener(this)

        val voice = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        voice.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        voice.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        voice.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, true)
        voice.component = ComponentName.unflattenFromString("com.google.android.googlequicksearchbox/com.google.android.voicesearch.serviceapi.GoogleRecognitionService")
//        voice.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 10)
//        startActivityForResult(voice, 1111)

        speechRecognizer.startListening(voice)

        startForeground(this)

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onCreate() {
        super.onCreate()
//        startForeground(this)
//        val notificationBuilder = NotificationCompat.Builder(this, "channel id")
//        val notification = notificationBuilder.setOngoing(true)
//            .setSmallIcon(R.mipmap.ic_launcher)
//            .setPriority(PRIORITY_MIN)
//            .setCategory(Notification.CATEGORY_SERVICE)
//            .build()
//        startForeground(foregroundId, notification)
    }

    private fun startForeground(service: Service) {
//        val notificationBuilder = NotificationCompat.Builder(this, "channel id")
//        val notification = notificationBuilder.setOngoing(true)
//            .setSmallIcon(R.mipmap.ic_launcher)
//            .setPriority(PRIORITY_MIN)
//            .setCategory(Notification.CATEGORY_SERVICE)
//            .build()
//        startForeground(foregroundId, notification)

//        val notification: Notification = Notification.Builder(service).notification
//        service.startForeground(foregroundId, notification)

//        val notificationBuilder: NotificationCompat.Builder = NotificationCompat.Builder(this, "channelID")
//            .setSmallIcon(R.drawable.ic_launcher_foreground)
//            .setContentTitle("Notification")
//            .setContentText("Hello! This is a notification.")
//            .setAutoCancel(true)
//
//        val notificationManager: NotificationManager =
//            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        val notificationId = 1
//        createChannel(notificationManager)
//        notificationManager.notify(notificationId, notificationBuilder.build())
    }

    private fun createChannel(notificationManager: NotificationManager) {
        if (Build.VERSION.SDK_INT < 26) {
            return
        }
        val channel = NotificationChannel(
            "channelID",
            "name",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        channel.setDescription("Hello! This is a notification.")
        notificationManager.createNotificationChannel(channel)
    }

    override fun onReadyForSpeech(params: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun onBeginningOfSpeech() {
        TODO("Not yet implemented")
    }

    override fun onRmsChanged(rmsdB: Float) {
        TODO("Not yet implemented")
    }

    override fun onBufferReceived(buffer: ByteArray?) {
        TODO("Not yet implemented")
    }

    override fun onEndOfSpeech() {
        TODO("Not yet implemented")
    }

    override fun onError(error: Int) {
        TODO("Not yet implemented")
    }

    override fun onResults(results: Bundle?) {
        val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
//        val scores = results?.getFloatArray(SpeechRecognizer.CONFIDENCE_SCORES)
        Log.i(">>>>>>>>> SPOKEN TEXT ", matches!![0])
//        if (matches != null) {
//            if (isActivated) {
//                isActivated = false
////                stopRecognition()
//                Log.i("Matches", matches.toString())
//            } else {
//                matches.firstOrNull { it.contains(other = activationKeyword, ignoreCase = true) }
//                    ?.let {
//                        isActivated = true
//                    }
//                Log.i("Matches", matches.toString())
////                startRecognition()
//            }
//        }
    }

    override fun onPartialResults(partialResults: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun onEvent(eventType: Int, params: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun onDestroy() {
//        speechRecognizer.stopListening()
//        speechRecognizer.destroy()
        super.onDestroy()
        speechRecognizer.destroy()
    }
}