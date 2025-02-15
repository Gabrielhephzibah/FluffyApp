package com.example.fluffyapp.ui.cat_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fluffyapp.domain.repository.CatRepository
import com.example.fluffyapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatListViewModel @Inject constructor(
    private val catRepository: CatRepository
) : ViewModel() {

    var state by mutableStateOf(CatListScreenState())

    init {
        getCatList()
    }

    private fun getCatList(){
      viewModelScope.launch {
          catRepository.getCat().collect { response ->
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