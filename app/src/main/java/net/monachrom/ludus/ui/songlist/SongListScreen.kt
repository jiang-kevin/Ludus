package net.monachrom.ludus.ui.songlist

import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.session.MediaController
import net.monachrom.ludus.extensions.toSong
import net.monachrom.ludus.ui.viewmodel.SongListViewModel


@Composable
fun SongListScreen(
    modifier: Modifier = Modifier,
    songListViewModel: SongListViewModel = viewModel(),
    mediaController: MediaController?
    ) {
    LazyColumn(modifier = modifier) {
        items(items = songListViewModel.getAllMediaItems()) { mediaItem ->
            SongListItem(mediaItem.toSong(), onPlayPressed = {
                songListViewModel.mediaController.addMediaItem(mediaItem)
                songListViewModel.mediaController.prepare()
                songListViewModel.mediaController.play()
            })
        }
    }
}