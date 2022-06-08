package net.monachrom.ludus.extensions

import android.net.Uri
import android.os.Bundle
import android.support.v4.media.MediaMetadataCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import net.monachrom.ludus.model.Song

fun MediaItem.toSong(): Song {
    return Song(
        mediaMetadata.title.toString(),
        mediaMetadata.artist.toString(),
        mediaMetadata.albumTitle.toString(),
        mediaMetadata.albumArtist.toString(),
        Uri.EMPTY
    )
}