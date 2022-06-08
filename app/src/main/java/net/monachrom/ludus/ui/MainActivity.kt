package net.monachrom.ludus.ui

import android.content.ComponentName
import android.media.AudioManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors
import dagger.hilt.android.AndroidEntryPoint
import net.monachrom.ludus.media.MusicService
import net.monachrom.ludus.ui.songlist.SongListScreen
import net.monachrom.ludus.ui.theme.LudusTheme
import net.monachrom.ludus.ui.viewmodel.SongListViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val songListViewModel: SongListViewModel by viewModels()
    private lateinit var controllerFuture: ListenableFuture<MediaController>
    private val controller: MediaController?
        get() = if (controllerFuture.isDone) controllerFuture.get() else null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeController()

        setContent {
            LudusApp(controller)
        }
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
        volumeControlStream = AudioManager.STREAM_MUSIC
    }

    override fun onStop() {
        super.onStop()
    }

    private fun initializeController() {
        controllerFuture =
            MediaController.Builder(
                this,
                SessionToken(this, ComponentName(this, MusicService::class.java))
            ).buildAsync()
        controllerFuture.addListener({ setController() }, MoreExecutors.directExecutor())
    }

    private fun setController() {
        val controller = this.controller ?: return
        controller.playWhenReady = true
        songListViewModel.mediaController = controller
    }

}

@Composable
fun LudusApp(mediaController: MediaController?) {

    LudusTheme() {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "Ludus")
                    }
                )
            }
        ) {
            SongListScreen(mediaController = mediaController)
        }
    }

}
