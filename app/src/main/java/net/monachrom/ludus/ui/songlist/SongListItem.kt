package net.monachrom.ludus.ui.songlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.media3.common.MediaItem
import androidx.media3.session.MediaController
import net.monachrom.ludus.R
import net.monachrom.ludus.extensions.toSong
import net.monachrom.ludus.model.Song

@Composable
fun SongListItem(
    song: Song,
    onPlayPressed: () -> Unit
) {
    var nowPlaying by remember { mutableStateOf(false) }
    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .clickable { nowPlaying = !nowPlaying }
    ) {
        Row(
            Modifier
            .padding(12.dp)
        ) {
            Column(Modifier.weight(1f)) {
                Text(text = song.name, style = MaterialTheme.typography.h6)
                Text(text = "${song.album} | ${song.artist}", style = MaterialTheme.typography.caption)
            }
            IconButton(
                onClick = onPlayPressed,
            ) {
                Icon(
                    imageVector = if (nowPlaying) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                    contentDescription = if (nowPlaying) {
                        stringResource(R.string.pause)
                    } else {
                        stringResource(R.string.play)
                    }
                )
            }
        }
    }
}