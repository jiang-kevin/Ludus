package net.monachrom.ludus.media

import android.os.Bundle
import android.support.v4.media.MediaMetadataCompat
import androidx.media3.common.MediaItem


interface MusicSource : Iterable<MediaItem> {

    /**
     * Begins loading the data for this music source.
     */
    suspend fun load()

    /**
     * Method which will perform a given action after this [MusicSource] is ready to be used.
     *
     * @param performAction A lambda expression to be called with a boolean parameter when
     * the source is ready. `true` indicates the source was successfully prepared, `false`
     * indicates an error occurred.
     */
    fun whenReady(performAction: (Boolean) -> Unit): Boolean

    fun search(query: String, extras: Bundle): List<MediaItem>
}