package go.deyu.stopwatch.domain

import android.content.Context
import android.media.RingtoneManager
import javax.inject.Inject

class PlaySoundUseCase @Inject constructor(private val context: Context) {
    fun playNotificationSound() {
        val notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val ringtone = RingtoneManager.getRingtone(context, notification)
        ringtone.play()
    }
}