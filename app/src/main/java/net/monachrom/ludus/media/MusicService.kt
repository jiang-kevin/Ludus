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
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector
import com.google.android.exoplayer2.ext.mediasession.TimelineQueueNavigator
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

@AndroidEntryPoint
class MusicService : MediaBrowserServiceCompat() {

    @Inject lateinit var mediaSource: MediaStoreMusicSource

    private lateinit var mediaSession: MediaSessionCompat
    private lateinit var mediaSessionConnector: MediaSessionConnector

    private val serviceJob = SupervisorJob()
    private val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob);

    private val player: ExoPlayer by lazy {
        ExoPlayer.Builder(this).build().apply {
            setHandleAudioBecomingNoisy(true)
        }
    }

    override fun onCreate() {
        super.onCreate()



        // Create a MediaSessionCompat
        mediaSession = MediaSessionCompat(this, Constants.MEDIA_SESSION_TAG).apply {

            // Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player
            val stateBuilder = PlaybackStateCompat.Builder()
                .setActions(PlaybackStateCompat.ACTION_PLAY or PlaybackStateCompat.ACTION_PLAY_PAUSE)
            setPlaybackState(stateBuilder.build())

            // MySessionCallback() has methods that handle callbacks from a media controller
            // setCallback(MySessionCallback())

            // Set the session's token so that client activities can communicate with it
            setSessionToken(sessionToken)
        }

        mediaSessionConnector = MediaSessionConnector(mediaSession)

        serviceScope.launch {
            mediaSource.load()
        }
    }

    override fun onGetRoot(
        clientPackageName: String,
        clientUid: Int,
        rootHints: Bundle?
    ): BrowserRoot {
        // may need to return different content hierarchies for android auto later
        return BrowserRoot(LUDUS_BROWSABLE_ROOT, null)
    }

    override fun onLoadChildren(
        parentId: String,
        result: Result<List<MediaBrowserCompat.MediaItem>>
    ) {
        // Assume that the music catalog is already loaded
        val mediaItems = emptyList<MediaBrowserCompat.MediaItem>()

        // Check if this is the root menu:
        if (LUDUS_BROWSABLE_ROOT == parentId) {
            // Build the MediaItem objects for the top level,
            // and put them in the mediaItems list...
            val resultsSent = mediaSource.whenReady { isInitialized ->
                if (isInitialized) {
                }
            }
        } else {
            // Examine the passed parentMediaId to see which submenu we're at,
            // and put the children of that menu in the mediaItems list...
        }
        result.sendResult(mediaItems)
    }
}