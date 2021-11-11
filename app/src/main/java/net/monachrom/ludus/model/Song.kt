package net.monachrom.ludus.model

import android.net.Uri

data class Song(
    val name: String,
    val artist: String,
    val album: String,
    val albumArtist: String,
    val uri: Uri
)
