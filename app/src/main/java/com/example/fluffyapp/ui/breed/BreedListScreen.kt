package com.example.fluffyapp.ui.breed

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.fluffyapp.ui.common.Error
import com.example.fluffyapp.ui.common.Loading
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun CatBreedListScreen(paddingValues: PaddingValues, onItemClick: (String) -> Unit) {
    val viewModel: BreedViewModel = hiltViewModel()
    val context = LocalContext.current
    val data = viewModel.breeds.collectAsLazyPagingItems()
    val isRefreshing = data.loadState.refresh is LoadState.Loading

    LaunchedEffect(key1 = data.loadState) {
        if (data.loadState.refresh is LoadState.Error) {
            Toast.makeText(
                context,
                "No internet connection",
                Toast.LENGTH_LONG
            ).show()
        }
    }
    Scaffold(
        modifier = Modifier.padding(paddingValues),
        topBar = {
            TopBarSearch(viewModel)
        }
    ) { innerPadding ->
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
            onRefresh = { data.refresh() },
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                when {
                    data.loadState.refresh == LoadState.Loading -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                    data.loadState.refresh is LoadState.Error && data.itemSnapshotList.isEmpty() -> {
                        Error(onRetry = { data.retry() })
                    }
                    else -> {
                        LazyVerticalGrid(
                            columns = GridCells.Adaptive(150.dp),
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(
                                count = data.itemCount,
                                key = { index -> data.itemSnapshotList[index]?.breedId ?: index }
                            ) { i ->
                                val item = data[i]
                                if (item != null) {
                                    BreedItem(item, viewModel, onItemClick)
                                }
                            }
                            if (data.loadState.append is LoadState.Loading) {
                                item {
                                    Loading()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TopBarSearch(viewModel: BreedViewModel) {
    var searchQuery by rememberSaveable {
        mutableStateOf("")
    }
    LaunchedEffect(searchQuery) {
        viewModel.searchRepositories(searchQuery)
    }
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp, vertical = 20.dp)
    ) {
        TextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            placeholder = { Text(text = "Search by breed name") },
            singleLine = true,
            maxLines = 1,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            shape = RoundedCornerShape(22.dp)
        )
    }
}