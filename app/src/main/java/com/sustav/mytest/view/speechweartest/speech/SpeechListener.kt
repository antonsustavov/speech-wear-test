package com.tamara.care.watch.speech

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.IBinder
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleService
import java.util.*

class SpeechListener : Service(), RecognitionListener {
    private lateinit var speechRecognizer: SpeechRecognizer
    private var isActivated: Boolean = false
    private val activationKeyword: String = "help"
    private val foregroundId: Int = 1000001

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//        if (ContextCompat.checkSelfPermission(this, "android.permission.RECORD_AUDIO") != PackageManager.PERMISSION_GRANTED) {
//            requestPermissions(requiredA, arrayOf(Manifest.permission.RECORD_AUDIO), 9379995)
//        }

        Log.i(">>>>>> START", ">>>>>>>>>>>>>>>>>>>>> START")
        Log.i(">>>>>> START", SpeechRecognizer.isRecognitionAvailable(this).toString())

//        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(
//            this,
//            ComponentName.unflattenFromString("com.google.android.googlequicksearchbox/com.google.android.voicesearch.serviceapi.GoogleRecognitionService")
//        )
//        speechRecognizer = SpeechRecognizer.createOnDeviceSpeechRecognizer(this)
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
        speechRecognizer.setRecognitionListener(this)

        startListening()

//        val voice = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
//        voice.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
//        voice.putExtra(
//            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
//            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
//        )
//        voice.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, true)
//        voice.component = ComponentName.unflattenFromString("com.google.android.googlequicksearchbox/com.google.android.voicesearch.serviceapi.GoogleRecognitionService")
//        voice.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 10)
//        startActivityForResult(voice, 1111)

//        speechRecognizer.startListening(voice)

        startForeground(this)

        return super.onStartCommand(intent, flags, startId)
    }

    private fun startListening() {
        val voice = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        voice.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        voice.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        speechRecognizer.startListening(voice)
        object : CountDownTimer(10, 10) {
            override fun onTick(millisUntilFinished: Long) {}

            override fun onFinish() {
                speechRecognizer.cancel()
            }
        }.start()
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
        Log.i(">>>> READY FOR SPEECH ", params.toString())
    }

    override fun onBeginningOfSpeech() {
        Log.i(">>>>>> ON BEGINNING ", "fsdfsdfdsf")
    }

    override fun onRmsChanged(rmsdB: Float) {
        Log.i(">>>>> RMSDB", rmsdB.toString())
    }

    override fun onBufferReceived(buffer: ByteArray?) {
        Log.i(">>>>> ON BUFFER", buffer.toString())
    }

    override fun onEndOfSpeech() {
        Log.i(">>>>> ON END SPEECH", "END")
    }

    override fun onError(error: Int) {
        Log.i(">>>>>>>>>> ERROR", error.toString())
        speechRecognizer.cancel()
        startListening()
    }

    override fun onResults(results: Bundle?) {
        val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
        if (matches != null) {
           Log.i(">>>>>>>>> SPOKEN TEXT ", matches!![0])
        } else {
            Log.i(">>>>>>>>> SPOKEN TEXT ", "SPEECH Result null")
        }
        startListening()
    }

    override fun onPartialResults(partialResults: Bundle?) {
        Log.i(">>>>> ON PARTITONAL", partialResults.toString())
    }

    override fun onEvent(eventType: Int, params: Bundle?) {
        Log.i(">>>>> ON EVENT", eventType.toString() + params.toString())
    }

    override fun onDestroy() {
//        speechRecognizer.stopListening()
//        speechRecognizer.destroy()
        super.onDestroy()
        speechRecognizer.destroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.i(">>>>>>>>> BIND TEXT ", intent.toString())
        return null;
    }
}