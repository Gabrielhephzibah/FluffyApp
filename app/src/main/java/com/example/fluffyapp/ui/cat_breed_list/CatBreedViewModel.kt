package com.example.fluffyapp.ui.cat_breed_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fluffyapp.domain.repository.CatBreedRepository
import com.example.fluffyapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatBreedViewModel @Inject constructor(
    private val repository: CatBreedRepository
) : ViewModel() {

    var state by mutableStateOf(CatBreedScreenState())

    init {
        getCatList()
    }

    private fun getCatList(){
      viewModelScope.launch {
          repository.getCat().collect { response ->
              when(response){
                  is Resource.Error -> {
                      state = state.copy(error = response.error)
                  }
                  Resource.Loading -> state = state.copy(loading = true)
                  is Resource.Success -> {
                      state = state.copy(
                          data = response.data ?: emptyList()
                      )
                  }
              }

          }
      }

    }

}