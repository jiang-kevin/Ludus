package net.monachrom.ludus.data

import androidx.media3.common.MediaItem
import net.monachrom.ludus.extensions.toSong
import net.monachrom.ludus.media.MediaStoreMusicSource
import net.monachrom.ludus.model.Song
import javax.inject.Inject

class SongRepository @Inject constructor(
    private val musicSource: MediaStoreMusicSource
) {

    suspend fun load() {
        musicSource.load()
    }

    fun getSongs(): List<MediaItem> {
        return musicSource
            .iterator()
            .asSequence()
            .toList()
    }
}