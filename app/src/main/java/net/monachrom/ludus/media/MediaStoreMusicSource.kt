package net.monachrom.ludus.media

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.media.MediaMetadataCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import net.monachrom.ludus.model.Song
import javax.inject.Inject

class MediaStoreMusicSource @Inject constructor(
    @ApplicationContext private val context: Context
): MusicSource{

    private var music: List<MediaMetadataCompat> = emptyList()

    override suspend fun load() {

        val test: MediaMetadataCompat

        val tableUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI

        val projection = arrayOf(
            MediaStore.Audio.AudioColumns.TITLE,
            MediaStore.Audio.AudioColumns.ARTIST,
            MediaStore.Audio.AudioColumns.ALBUM,
            MediaStore.MediaColumns._ID)

        val selectionClause: String? = null
        val selectionArgs: Array<String> = emptyArray()
        val orderBy = MediaStore.Audio.AudioColumns.TITLE

        val c: Cursor? = context.contentResolver.query(
            tableUri,
            projection,
            selectionClause,
            selectionArgs,
            orderBy)

        if (c != null) {
            while (c.moveToNext()) {
                val title = c.getString(0)
                val artist = c.getString(1)
                val album = c.getString(2)
                val uriStr = c.getString(3)
                val songUri = Uri.withAppendedPath(tableUri, uriStr)

                val newSong = Song(title, artist, album, artist, songUri)
            }
            c.close()
        }
    }

    override fun whenReady(performAction: (Boolean) -> Unit): Boolean {
        TODO("Not yet implemented")
    }

    override fun search(query: String, extras: Bundle): List<MediaMetadataCompat> {
        TODO("Not yet implemented")
    }

    override fun iterator(): Iterator<MediaMetadataCompat> = music.iterator()
}