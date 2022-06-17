package com.sustav.mytest.view.speechweartest

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.sustav.mytest.view.speechweartest.databinding.ActivityMainBinding
import com.tamara.care.watch.speech.SpeechListener

class MainActivity : Activity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startForegroundSpeechListener()
    }

    private fun startForegroundSpeechListener() {
        if (!speechListenerServiceRunning()) {
            val speechIntent = Intent(this, SpeechListener::class.java)
            startForegroundService(speechIntent)
        }
    }

    private fun speechListenerServiceRunning(): Boolean {
        val systemService = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val runningServices = systemService.getRunningServices(Integer.MAX_VALUE)
        for (runningServiceInfo in runningServices) {
            if (runningServiceInfo.service.className == SpeechListener::class.java.name) {
                return true
            }
        }

        return false
    }
}