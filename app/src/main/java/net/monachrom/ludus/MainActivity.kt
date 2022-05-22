package net.monachrom.ludus

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.monachrom.ludus.model.Song
import net.monachrom.ludus.ui.songlist.SongListBody
import net.monachrom.ludus.ui.theme.LudusTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LudusApp()
        }
    }
}

@Composable
fun LudusApp() {
    val songs = listOf<Song>(
        Song("To Zanarkand", "Nobuo Uematsu", "Final Fantasy X", "Square Enix", Uri.EMPTY),
        Song("World Revolution", "Yasunori Mitsuda", "Chrono Trigger", "Square Enix", Uri.EMPTY),
        Song("Pure Vessel", "Christopher Larkin", "Hollow Knight", "Team Cherry", Uri.EMPTY),
    )
//    Column(modifier = Modifier.padding(vertical = 4.dp)) {
//        for (song in songs) {
//            Song(song)
//        }
//    }

    LudusTheme() {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "Ludus")
                    }
                )
            }
        ) { innerPadding ->
            SongListBody(songs = List(1000) { songs[1] }, Modifier.padding(innerPadding) )
        }
    }

}

@Composable
private fun Greeting(name: String) {
    val expanded = remember { mutableStateOf(false) }
    val extraPadding = if (expanded.value) 48.dp else 0.dp
    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(modifier = Modifier.padding(24.dp)) {
            Column(modifier = Modifier
                .weight(1f)
                .padding(bottom = extraPadding)
            ) {
                Text(text = "Hello, ")
                Text(text = name)
            }
            OutlinedButton(
                onClick = { expanded.value = !expanded.value }
            ) {
                Text(if (expanded.value) "Show less" else "Show more")
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview() {
    LudusTheme {
        LudusApp()
    }
}
