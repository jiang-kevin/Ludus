package net.monachrom.ludus.ui.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import net.monachrom.ludus.model.Song
import javax.inject.Inject

@HiltViewModel
class SongListViewModel @Inject constructor(): ViewModel() {
    var songs: List<Song> = emptyList()

    fun getAllSongs() {
        songs = listOf(
            Song("To Zanarkand", "Nobuo Uematsu", "Final Fantasy X", "Square Enix", Uri.EMPTY),
            Song("World Revolution", "Yasunori Mitsuda", "Chrono Trigger", "Square Enix", Uri.EMPTY),
            Song("Pure Vessel", "Christopher Larkin", "Hollow Knight", "Team Cherry", Uri.EMPTY),
        )
    }
}