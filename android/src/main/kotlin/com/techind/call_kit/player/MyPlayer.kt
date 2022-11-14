package com.techind.call_kit.player

import android.app.Service
import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.text.TextUtils
import com.techind.call_kit.MyApplication

class MyPlayer(sound : String = "") {

    private var vibrator: Vibrator? = null
    private var audioManager: AudioManager? = null

    private var mediaPlayer: MediaPlayer? = null
    private var soundUri : String = ""

    init {
        soundUri = sound
    }

    fun playMySound(){
        playVibrator()
        playSound()
    }


    private fun playVibrator() {
        vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager = MyApplication.getInstance().getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            MyApplication.getInstance().getSystemService(Service.VIBRATOR_SERVICE) as Vibrator
        }
        audioManager = MyApplication.getInstance().getSystemService(Service.AUDIO_SERVICE) as AudioManager
        when (audioManager?.ringerMode) {
            AudioManager.RINGER_MODE_SILENT -> {
            }
            else -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator?.vibrate(VibrationEffect.createWaveform(longArrayOf(0L, 1000L, 1000L), 0))
                } else {
                    vibrator?.vibrate(longArrayOf(0L, 1000L, 1000L), 0)
                }
            }
        }
    }

    private fun playSound() {
        var uri = getRingtoneUri(soundUri)
        if (uri == null) {
            uri = RingtoneManager.getActualDefaultRingtoneUri(
                MyApplication.getInstance(),
                RingtoneManager.TYPE_RINGTONE
            )
        }
        try {
            mediaPlayer(uri!!)
        } catch (e: Exception) {
            try {
                uri = getRingtoneUri("ringtone_default")
                mediaPlayer(uri!!)
            } catch (e2: Exception) {
                e2.printStackTrace()
            }
        }
    }

    private fun mediaPlayer(uri: Uri) {
        mediaPlayer = MediaPlayer()
        val attribution = AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .setUsage(AudioAttributes.USAGE_NOTIFICATION_RINGTONE)
            .setLegacyStreamType(AudioManager.STREAM_RING)
            .build()
        mediaPlayer?.setAudioAttributes(attribution)
        mediaPlayer?.setDataSource(MyApplication.getInstance(), uri)
        mediaPlayer?.prepare()
        mediaPlayer?.isLooping = true
        mediaPlayer?.start()
    }

    private fun getRingtoneUri(fileName: String) = try {
        if (TextUtils.isEmpty(fileName)) {
            RingtoneManager.getActualDefaultRingtoneUri(
                MyApplication.getInstance(),
                RingtoneManager.TYPE_RINGTONE
            )
        }
        var packageName= MyApplication.getInstance().packageName
        val resId = MyApplication.getInstance().resources.getIdentifier(fileName, "raw",packageName)
        if (resId != 0) {
            Uri.parse("android.resource://${packageName}/$resId")
        } else {
            if (fileName.equals("system_ringtone_default", true)) {
                RingtoneManager.getActualDefaultRingtoneUri(
                    MyApplication.getInstance(),
                    RingtoneManager.TYPE_RINGTONE
                )
            } else {
                RingtoneManager.getActualDefaultRingtoneUri(
                    MyApplication.getInstance(),
                    RingtoneManager.TYPE_RINGTONE
                )
            }
        }
    } catch (e: Exception) {
        if (fileName.equals("system_ringtone_default", true)) {
            RingtoneManager.getActualDefaultRingtoneUri(
                MyApplication.getInstance(),
                RingtoneManager.TYPE_RINGTONE
            )
        } else {
            RingtoneManager.getActualDefaultRingtoneUri(
                MyApplication.getInstance(),
                RingtoneManager.TYPE_RINGTONE
            )
        }
    }

   public fun stopPlayer() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        vibrator?.cancel()
    }
}