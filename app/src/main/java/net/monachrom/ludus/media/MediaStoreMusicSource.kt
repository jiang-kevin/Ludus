package net.monachrom.ludus.media

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.media.MediaMetadataCompat
import android.util.Log
import android.util.Log.DEBUG
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import dagger.hilt.android.qualifiers.ApplicationContext
import net.monachrom.ludus.model.Song
import javax.inject.Inject

class MediaStoreMusicSource @Inject constructor(
    @ApplicationContext private val context: Context
): MusicSource{

    private var music: MutableList<MediaItem> = mutableListOf()

    override suspend fun load() {

        val tableUri = MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)

        val projection = arrayOf(
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media._ID)

        val selectionClause: String? = null
        val selectionArgs: Array<String> = emptyArray()
        val orderBy = MediaStore.Audio.Media.TITLE

        val c: Cursor? = context.contentResolver.query(
            tableUri,
            projection,
            selectionClause,
            selectionArgs,
            orderBy)

        if (c != null) {
            while (c.moveToNext()) {
                val uriStr = c.getString(3)
                val mediaUri = Uri.withAppendedPath(tableUri, uriStr)

                val mediaMetadata =
                    MediaMetadata.Builder()
                        .setTitle(c.getString(0))
                        .setArtist(c.getString(1))
                        .setAlbumTitle(c.getString(2))
                        .setMediaUri(mediaUri)
                        .build()


                val mediaItem =
                    MediaItem.Builder()
                        .setMediaId(c.getString(3))
                        .setMediaMetadata(mediaMetadata)
                        .setUri(mediaUri)
                        .build()

                music.add(mediaItem)
            }
            c.close()
        }
    }

    override fun whenReady(performAction: (Boolean) -> Unit): Boolean {
        TODO("Not yet implemented")
    }

    override fun search(query: String, extras: Bundle): List<MediaItem> {
        TODO("Not yet implemented")
    }

    override fun iterator(): Iterator<MediaItem> = music.toList().iterator()
}