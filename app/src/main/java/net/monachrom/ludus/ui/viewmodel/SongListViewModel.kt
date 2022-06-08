package net.monachrom.ludus.ui.viewmodel

import android.content.ComponentName
import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import net.monachrom.ludus.data.SongRepository
import net.monachrom.ludus.media.MusicService
import net.monachrom.ludus.model.Song
import javax.inject.Inject

@HiltViewModel
class SongListViewModel @Inject constructor(
    private val songRepository: SongRepository,
    
): ViewModel() {
    init {
        viewModelScope.launch {
            loadSongs()
        }

    }

    lateinit var mediaController: MediaController

    private suspend fun loadSongs() {
        songRepository.load()
    }

    fun getAllMediaItems(): List<MediaItem> = songRepository.getSongs()
}