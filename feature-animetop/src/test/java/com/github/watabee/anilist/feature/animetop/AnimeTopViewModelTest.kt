package com.github.watabee.anilist.feature.animetop

import com.github.watabee.anilist.core.data.repository.AnimeRepository
import com.github.watabee.anilist.core.model.AnimeTop
import com.github.watabee.anilist.core.testing.util.MainDispatcherRule
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import java.io.IOException

class AnimeTopViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule(testDispatcher = StandardTestDispatcher())

    @Test
    fun test_whenInitialized_thenUiStateIsLoading() {
        val viewModel = AnimeTopViewModel(animeRepository = mockk())
        Truth.assertThat(viewModel.animeTopUiState).isInstanceOf(AnimeTopUiState.Loading::class.java)
    }

    @Test
    fun test_whenGetAnimeTopSuccess_thenUiStateIsSuccess() = runTest {
        val animeRepository: AnimeRepository = mockk {
            coEvery { getAnimeTop() } returns AnimeTop(emptyList(), emptyList(), emptyList(), emptyList(), emptyList())
        }
        val viewModel = AnimeTopViewModel(animeRepository = animeRepository)
        viewModel.getAnimeTop()

        Truth.assertThat(viewModel.animeTopUiState).isInstanceOf(AnimeTopUiState.Loading::class.java)
        advanceUntilIdle()
        Truth.assertThat(viewModel.animeTopUiState).isInstanceOf(AnimeTopUiState.Success::class.java)
    }

    @Test
    fun test_whenGetAnimeTopError_thenUiStateIsError() = runTest {
        val animeRepository: AnimeRepository = mockk {
            coEvery { getAnimeTop() } throws IOException()
        }
        val viewModel = AnimeTopViewModel(animeRepository = animeRepository)
        viewModel.getAnimeTop()

        Truth.assertThat(viewModel.animeTopUiState).isInstanceOf(AnimeTopUiState.Loading::class.java)
        advanceUntilIdle()
        Truth.assertThat(viewModel.animeTopUiState).isInstanceOf(AnimeTopUiState.Error::class.java)
    }
}
