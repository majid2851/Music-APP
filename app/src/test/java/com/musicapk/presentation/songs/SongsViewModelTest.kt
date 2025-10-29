package com.musicapk.presentation.songs

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.musicapk.domain.model.Song
import com.musicapk.domain.usecase.GetAllSongsUseCase
import com.musicapk.domain.usecase.PlaySongUseCase
import com.musicapk.domain.usecase.SearchSongsUseCase
import com.musicapk.domain.usecase.ToggleFavoriteUseCase
import com.musicapk.domain.util.Result
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Unit tests for SongsViewModel
 * 
 * This demonstrates:
 * - Testing ViewModels in MVI pattern
 * - Mocking use cases with MockK
 * - Testing state updates
 * - Testing side effects with Turbine
 * - Coroutines testing with TestDispatcher
 */
@ExperimentalCoroutinesApi
class SongsViewModelTest {
    
    // Rule to make LiveData work synchronously in tests
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    
    // Test dispatcher for coroutines
    private val testDispatcher = StandardTestDispatcher()
    
    // Mock dependencies
    private lateinit var getAllSongsUseCase: GetAllSongsUseCase
    private lateinit var searchSongsUseCase: SearchSongsUseCase
    private lateinit var toggleFavoriteUseCase: ToggleFavoriteUseCase
    private lateinit var playSongUseCase: PlaySongUseCase
    
    // System under test
    private lateinit var viewModel: SongsViewModel
    
    // Test data
    private val testSong = Song(
        id = "1",
        title = "Test Song",
        artist = "Test Artist",
        album = "Test Album",
        duration = 180000L,
        artworkUri = null,
        audioUri = "test://audio",
        dateAdded = System.currentTimeMillis(),
        isFavorite = false
    )
    
    private val testSongs = listOf(
        testSong,
        testSong.copy(id = "2", title = "Another Song")
    )
    
    @Before
    fun setup() {
        // Set the main dispatcher for testing
        Dispatchers.setMain(testDispatcher)
        
        // Initialize mocks
        getAllSongsUseCase = mockk()
        searchSongsUseCase = mockk()
        toggleFavoriteUseCase = mockk()
        playSongUseCase = mockk(relaxed = true)
        
        // Default mock behavior
        coEvery { getAllSongsUseCase() } returns flowOf(Result.Success(testSongs))
    }
    
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
    
    private fun createViewModel(): SongsViewModel {
        return SongsViewModel(
            getAllSongsUseCase,
            searchSongsUseCase,
            toggleFavoriteUseCase,
            playSongUseCase
        )
    }
    
    @Test
    fun `initial state should be empty`() {
        // When
        viewModel = createViewModel()
        
        // Then
        val state = viewModel.uiState.value
        assertThat(state.songs).isEmpty()
        assertThat(state.isLoading).isFalse()
        assertThat(state.error).isNull()
    }
    
    @Test
    fun `loadSongs should update state with songs on success`() = runTest {
        // Given
        coEvery { getAllSongsUseCase() } returns flowOf(Result.Success(testSongs))
        
        // When
        viewModel = createViewModel()
        advanceUntilIdle()
        
        // Then
        val state = viewModel.uiState.value
        assertThat(state.songs).isEqualTo(testSongs)
        assertThat(state.isLoading).isFalse()
        assertThat(state.error).isNull()
        
        coVerify { getAllSongsUseCase() }
    }
    
    @Test
    fun `loadSongs should show loading state`() = runTest {
        // Given
        coEvery { getAllSongsUseCase() } returns flowOf(Result.Loading)
        
        // When
        viewModel = createViewModel()
        advanceUntilIdle()
        
        // Then
        val state = viewModel.uiState.value
        assertThat(state.isLoading).isTrue()
    }
    
    @Test
    fun `loadSongs should update state with error on failure`() = runTest {
        // Given
        val errorMessage = "Network error"
        coEvery { getAllSongsUseCase() } returns flowOf(
            Result.Error(Exception(errorMessage))
        )
        
        viewModel = createViewModel()
        
        // When
        advanceUntilIdle()
        
        // Then
        val state = viewModel.uiState.value
        assertThat(state.error).isEqualTo(errorMessage)
        assertThat(state.isLoading).isFalse()
    }
    
    @Test
    fun `loadSongs should send error effect on failure`() = runTest {
        // Given
        val errorMessage = "Network error"
        coEvery { getAllSongsUseCase() } returns flowOf(
            Result.Error(Exception(errorMessage))
        )
        
        viewModel = createViewModel()
        
        // When/Then
        viewModel.effect.test {
            advanceUntilIdle()
            
            val effect = awaitItem()
            assertThat(effect).isInstanceOf(SongsUiEffect.ShowError::class.java)
            assertThat((effect as SongsUiEffect.ShowError).message).isEqualTo(errorMessage)
        }
    }
    
    @Test
    fun `searchSongs should update search query in state`() = runTest {
        // Given
        val query = "test"
        coEvery { searchSongsUseCase(query) } returns flowOf(Result.Success(testSongs))
        
        viewModel = createViewModel()
        advanceUntilIdle()
        
        // When
        viewModel.onEvent(SongsUiEvent.SearchSongs(query))
        advanceUntilIdle()
        
        // Then
        assertThat(viewModel.uiState.value.searchQuery).isEqualTo(query)
        coVerify { searchSongsUseCase(query) }
    }
    
    @Test
    fun `searchSongs with empty query should load all songs`() = runTest {
        // Given
        viewModel = createViewModel()
        advanceUntilIdle()
        
        // When
        viewModel.onEvent(SongsUiEvent.SearchSongs(""))
        advanceUntilIdle()
        
        // Then
        assertThat(viewModel.uiState.value.searchQuery).isEmpty()
        // Should call getAllSongsUseCase again
        coVerify(exactly = 2) { getAllSongsUseCase() }
    }
    
    @Test
    fun `selectSong should update selected song in state`() = runTest {
        // Given
        viewModel = createViewModel()
        advanceUntilIdle()
        
        // When
        viewModel.onEvent(SongsUiEvent.SelectSong(testSong))
        
        // Then
        assertThat(viewModel.uiState.value.selectedSong).isEqualTo(testSong)
    }
    
    @Test
    fun `playSong should call playSongUseCase and send navigation effect`() = runTest {
        // Given
        viewModel = createViewModel()
        advanceUntilIdle()
        
        // When/Then
        viewModel.effect.test {
            viewModel.onEvent(SongsUiEvent.PlaySong(testSong))
            advanceUntilIdle()
            
            val effect = awaitItem()
            assertThat(effect).isInstanceOf(SongsUiEffect.NavigateToPlayer::class.java)
            assertThat((effect as SongsUiEffect.NavigateToPlayer).songId).isEqualTo(testSong.id)
            
            coVerify { playSongUseCase(testSong) }
        }
    }
    
    @Test
    fun `toggleFavorite should call toggleFavoriteUseCase on success`() = runTest {
        // Given
        coEvery { toggleFavoriteUseCase(testSong.id) } returns Result.Success(Unit)
        
        viewModel = createViewModel()
        advanceUntilIdle()
        
        // When
        viewModel.onEvent(SongsUiEvent.ToggleFavorite(testSong.id))
        advanceUntilIdle()
        
        // Then
        coVerify { toggleFavoriteUseCase(testSong.id) }
    }
    
    @Test
    fun `toggleFavorite should show snackbar effect on success`() = runTest {
        // Given
        coEvery { toggleFavoriteUseCase(testSong.id) } returns Result.Success(Unit)
        
        viewModel = createViewModel()
        advanceUntilIdle()
        
        // When/Then
        viewModel.effect.test {
            viewModel.onEvent(SongsUiEvent.ToggleFavorite(testSong.id))
            advanceUntilIdle()
            
            val effect = awaitItem()
            assertThat(effect).isInstanceOf(SongsUiEffect.ShowSnackbar::class.java)
        }
    }
    
    @Test
    fun `clearError should clear error from state`() = runTest {
        // Given
        coEvery { getAllSongsUseCase() } returns flowOf(
            Result.Error(Exception("Error"))
        )
        
        viewModel = createViewModel()
        advanceUntilIdle()
        
        assertThat(viewModel.uiState.value.error).isNotNull()
        
        // When
        viewModel.onEvent(SongsUiEvent.ClearError)
        
        // Then
        assertThat(viewModel.uiState.value.error).isNull()
    }
}


