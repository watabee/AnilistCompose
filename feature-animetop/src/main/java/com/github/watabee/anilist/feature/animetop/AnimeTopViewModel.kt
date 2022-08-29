package com.github.watabee.anilist.feature.animetop

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.watabee.anilist.core.data.repository.AnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AnimeTopViewModel @Inject constructor(private val animeRepository: AnimeRepository) : ViewModel() {

    var animeTopUiState: AnimeTopUiState by mutableStateOf(AnimeTopUiState.Loading)
        private set

    fun getAnimeTop() {
        if (animeTopUiState is AnimeTopUiState.Success) {
            return
        }
        viewModelScope.launch {
            animeTopUiState = AnimeTopUiState.Loading
            animeTopUiState = try {
                val animeTop = animeRepository.getAnimeTop()
                AnimeTopUiState.Success(animeTop)
            } catch (e: Throwable) {
                Timber.e(e)
                AnimeTopUiState.Error
            }
        }
    }
}
