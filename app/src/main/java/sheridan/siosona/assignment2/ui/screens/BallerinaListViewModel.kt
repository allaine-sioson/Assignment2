package sheridan.siosona.assignment2.ui.screens

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import sheridan.siosona.assignment2.ProfileDataApplication
import sheridan.siosona.assignment2.data.ProfileDataRepository
import sheridan.siosona.assignment2.model.Profile
import kotlin.time.Duration.Companion.milliseconds


sealed interface ProfileListUiState {
    object Loading: ProfileListUiState
    data class Success(val profiles: List<Profile>): ProfileListUiState
    object Error: ProfileListUiState
}

class ProfileListViewModel(
    private val repository: ProfileDataRepository
) : ViewModel() {
    private val _uiState: MutableState<ProfileListUiState> =
        mutableStateOf(ProfileListUiState.Loading)
    val uiState: State<ProfileListUiState> = _uiState

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            // fake 2s delay
            delay(2000.milliseconds)

            _uiState.value = try {
                ProfileListUiState.Success(repository.getAllProfiles())
            } catch (e: Exception) {
                Log.e("ProfileListViewModel", "loadData: ${e.message}")
                ProfileListUiState.Error
            }
        }
    }

    fun reloadData(){
        _uiState.value = ProfileListUiState.Loading
        loadData()
    }

    /**
     * Factory for [MarsViewModel] that takes [MarsPhotosRepository] as a dependency
     */
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as ProfileDataApplication)
                val profileDataRepository = application.container.profileDataRepository
                ProfileListViewModel(repository = profileDataRepository)
            }
        }
    }
}