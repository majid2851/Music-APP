package com.musicapk.presentation.splash

import androidx.lifecycle.viewModelScope
import com.musicapk.data.preferences.PreferencesManager
import com.musicapk.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager
    // Add use cases here if needed (e.g., CheckPermissionsUseCase, InitializeDatabaseUseCase)
) : BaseViewModel<SplashUiState, SplashUiEvent, SplashUiEffect>(
    initialState = SplashUiState()
) {
    
    init {
        // Auto-start initialization when ViewModel is created
        onEvent(SplashUiEvent.Initialize)
    }
    
    override fun onEvent(event: SplashUiEvent) {
        when (event) {
            is SplashUiEvent.Initialize -> initialize()
            is SplashUiEvent.CheckPermissions -> checkPermissions()
            is SplashUiEvent.RetryInitialization -> retryInitialization()
            is SplashUiEvent.OnGetStartedClick -> onGetStartedClick()
        }
    }
    
    private fun onGetStartedClick() {
        // Mark that the user has completed the splash screen
        preferencesManager.setFirstLaunchComplete()
    }
    
    private fun initialize() {
        viewModelScope.launch {
            setState { copy(isLoading = true, error = null) }
            
            try {
                // Simulate initialization work (replace with actual initialization)
                // Examples:
                // - Check if database is initialized
                // - Load app configuration
                // - Check for updates
                // - Verify permissions
                delay(2000) // Minimum splash display time
                
                // TODO: Add actual initialization logic here
                // val hasPermissions = checkPermissionsUseCase()
                // val isDbReady = initializeDatabaseUseCase()
                
                setState { 
                    copy(
                        isLoading = false,
                        isInitialized = true,
                        error = null
                    ) 
                }
                
                // Navigate to main screen after successful initialization
                sendEffect(SplashUiEffect.NavigateToMain)
                
            } catch (e: Exception) {
                setState { 
                    copy(
                        isLoading = false,
                        isInitialized = false,
                        error = e.message ?: "Initialization failed"
                    ) 
                }
                sendEffect(
                    SplashUiEffect.ShowError(
                        e.message ?: "Failed to initialize app"
                    )
                )
            }
        }
    }
    
    private fun checkPermissions() {
        viewModelScope.launch {
            try {
                // TODO: Implement permission checking logic
                // For now, assume permissions are needed
                sendEffect(SplashUiEffect.NavigateToPermissions)
            } catch (e: Exception) {
                sendEffect(
                    SplashUiEffect.ShowError("Failed to check permissions")
                )
            }
        }
    }
    
    private fun retryInitialization() {
        // Clear error and retry
        setState { copy(error = null) }
        initialize()
    }
}















