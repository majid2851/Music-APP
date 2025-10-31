package com.musicapk.data.local.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.musicapk.data.local.MusicDatabase
import com.musicapk.data.local.entity.SongEntity
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumentation tests for SongDao
 * 
 * This demonstrates:
 * - Testing Room DAOs
 * - In-memory database testing
 * - Flow testing in instrumentation tests
 */
@RunWith(AndroidJUnit4::class)
class SongDaoTest {
    
    private lateinit var database: MusicDatabase
    private lateinit var songDao: SongDao
    
    private val testSong = SongEntity(
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
    
    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context,
            MusicDatabase::class.java
        ).build()
        
        songDao = database.songDao()
    }
    
    @After
    fun tearDown() {
        database.close()
    }
    
    @Test
    fun insertSong_andRetrieveById() = runTest {
        // Given
        songDao.insertSong(testSong)
        
        // When
        val retrieved = songDao.getSongById(testSong.id)
        
        // Then
        assertThat(retrieved).isEqualTo(testSong)
    }
    
    @Test
    fun insertMultipleSongs_retrieveAll() = runTest {
        // Given
        val songs = listOf(
            testSong,
            testSong.copy(id = "2", title = "Song 2"),
            testSong.copy(id = "3", title = "Song 3")
        )
        songDao.insertSongs(songs)
        
        // When/Then
        songDao.getAllSongs().test {
            val retrieved = awaitItem()
            assertThat(retrieved).hasSize(3)
            assertThat(retrieved).containsExactlyElementsIn(songs)
        }
    }
    
    @Test
    fun searchSongs_byTitle() = runTest {
        // Given
        val songs = listOf(
            testSong,
            testSong.copy(id = "2", title = "Another Song"),
            testSong.copy(id = "3", title = "Different Track")
        )
        songDao.insertSongs(songs)
        
        // When/Then
        songDao.searchSongs("Song").test {
            val results = awaitItem()
            assertThat(results).hasSize(2)
            assertThat(results.map { it.title }).containsExactly("Test Song", "Another Song")
        }
    }
    
    @Test
    fun toggleFavorite_updatesFavoriteStatus() = runTest {
        // Given
        songDao.insertSong(testSong)
        assertThat(songDao.getSongById(testSong.id)?.isFavorite).isFalse()
        
        // When
        songDao.toggleFavorite(testSong.id)
        
        // Then
        assertThat(songDao.getSongById(testSong.id)?.isFavorite).isTrue()
        
        // Toggle again
        songDao.toggleFavorite(testSong.id)
        assertThat(songDao.getSongById(testSong.id)?.isFavorite).isFalse()
    }
    
    @Test
    fun getFavoriteSongs_returnsOnlyFavorites() = runTest {
        // Given
        val songs = listOf(
            testSong.copy(id = "1", isFavorite = true),
            testSong.copy(id = "2", isFavorite = false),
            testSong.copy(id = "3", isFavorite = true)
        )
        songDao.insertSongs(songs)
        
        // When/Then
        songDao.getFavoriteSongs().test {
            val favorites = awaitItem()
            assertThat(favorites).hasSize(2)
            assertThat(favorites.all { it.isFavorite }).isTrue()
        }
    }
    
    @Test
    fun updateSong_updatesData() = runTest {
        // Given
        songDao.insertSong(testSong)
        
        // When
        val updatedSong = testSong.copy(title = "Updated Title")
        songDao.updateSong(updatedSong)
        
        // Then
        val retrieved = songDao.getSongById(testSong.id)
        assertThat(retrieved?.title).isEqualTo("Updated Title")
    }
    
    @Test
    fun deleteSong_removesSong() = runTest {
        // Given
        songDao.insertSong(testSong)
        assertThat(songDao.getSongById(testSong.id)).isNotNull()
        
        // When
        songDao.deleteSong(testSong)
        
        // Then
        assertThat(songDao.getSongById(testSong.id)).isNull()
    }
}

























