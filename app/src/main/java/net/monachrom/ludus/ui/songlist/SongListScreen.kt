package net.monachrom.ludus.ui.songlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import net.monachrom.ludus.R
import net.monachrom.ludus.model.Song
import net.monachrom.ludus.ui.viewmodel.SongListViewModel


@Composable
fun SongListScreen(
    modifier: Modifier = Modifier,
    songListViewModel: SongListViewModel = viewModel()
    ) {
    LazyColumn(modifier = modifier) {
        items(items = songListViewModel.songs) { song ->
            SongListItem(song)
        }
    }
}