package com.example.fluffyapp.ui.favourite

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fluffyapp.domain.model.FavouriteBreed
import com.example.fluffyapp.domain.repository.BreedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteBreedViewModel @Inject constructor(
    private val repository: BreedRepository
) : ViewModel() {

    var state by mutableStateOf(FavouriteScreenState())

    init {
        getFavourites()
    }

    private fun getFavourites() {
        viewModelScope.launch {
            repository.getFavoriteBreeds().map { response ->
                val lifeSpan = calculateAverageLifespan(response)
                response to lifeSpan
            }.collect { (breeds, averageLifespan) ->
                state = state.copy(
                    favouriteBreed = breeds,
                    averageLifeSpan = averageLifespan.toString()
                )
            }
        }
    }

    private fun calculateAverageLifespan(data: List<FavouriteBreed>): Double {
        val lifespan = data.mapNotNull { favouriteBreed ->
            favouriteBreed.lifespan.split("-").firstOrNull()?.trim()?.toIntOrNull()
        }
        val averageLifespan = if (lifespan.isNotEmpty()) lifespan.average() else 0.0
        return String.format("%.2f", averageLifespan).toDouble()
    }

}