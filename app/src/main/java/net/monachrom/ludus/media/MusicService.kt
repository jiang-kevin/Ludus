package net.monachrom.ludus.media

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.ResultReceiver
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.media.MediaBrowserServiceCompat
import androidx.media3.common.AudioAttributes
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.LibraryResult
import androidx.media3.session.MediaLibraryService
import androidx.media3.session.MediaSession
import com.google.common.util.concurrent.Futures
import com.google.common.util.concurrent.ListenableFuture
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import net.monachrom.ludus.extensions.album
import net.monachrom.ludus.extensions.toMediaItem
import net.monachrom.ludus.extensions.trackNumber
import net.monachrom.ludus.util.Constants
import javax.inject.Inject

private const val LUDUS_BROWSABLE_ROOT = "/"

class MusicService : MediaLibraryService() {

    private lateinit var player: ExoPlayer
    private lateinit var mediaLibrarySession: MediaLibrarySession
    private val librarySessionCallback = LudusMediaLibrarySessionCallback()

    private inner class LudusMediaLibrarySessionCallback : MediaLibrarySession.MediaLibrarySessionCallback {
        override fun onGetLibraryRoot(
            session: MediaLibrarySession,
            browser: MediaSession.ControllerInfo,
            params: LibraryParams?
        ): ListenableFuture<LibraryResult<MediaItem>> {
            return Futures.immediateFuture(LibraryResult.ofItem(MediaItemTree.getRootItem(), params))
        }
    }

    override fun onCreate() {
        super.onCreate()
        initializeSessionAndPlayer()
    }

    override fun onDestroy() {
        player.release()
        mediaLibrarySession.release()
        super.onDestroy()
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaLibrarySession {
        return mediaLibrarySession
    }

    private fun initializeSessionAndPlayer() {
        player =
            ExoPlayer.Builder(this)
                .setAudioAttributes(AudioAttributes.DEFAULT, true)
                .build()

        mediaLibrarySession =
            MediaLibrarySession.Builder(this, player, librarySessionCallback)
                .setMediaItemFiller(LudusMediaItemFiller())
                .build()

    }
}

class LudusMediaItemFiller : MediaSession.MediaItemFiller {
    override fun fillInLocalConfiguration(
        session: MediaSession,
        controller: MediaSession.ControllerInfo,
        mediaItem: MediaItem
    ): MediaItem {
        return MediaItem.Builder()
            .setUri(mediaItem.mediaMetadata.mediaUri)
            .setMediaMetadata(mediaItem.mediaMetadata)
            .build()
    }
}