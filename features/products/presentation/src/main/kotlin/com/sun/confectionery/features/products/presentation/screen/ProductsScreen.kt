package com.sun.confectionery.features.products.presentation.screen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sun.confectionery.core.ApiConfig
import com.sun.confectionery.features.products.presentation.ProductsEvent
import com.sun.confectionery.features.products.presentation.ProductsIntent
import com.sun.confectionery.features.products.presentation.ProductsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProductsScreen(
    viewModel: ProductsViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    LaunchedEffect(Unit) { viewModel.handleIntent(ProductsIntent.Load) }
    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is ProductsEvent.OpenProduct -> {}
            }
        }
    }

    Box(Modifier.fillMaxSize()) {
        if (state.isLoading) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
        LazyColumn(contentPadding = WindowInsets.safeDrawing.asPaddingValues()) {
            items(state.items) { item ->
                val imageUrl = "http://" + ApiConfig.BASE_URL + ":8080" + item.imageUrl
                Log.d("ProductsScreen", "imageUrl:$imageUrl ")
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { viewModel.handleIntent(ProductsIntent.Select(item.id)) }
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(imageUrl)
                            .crossfade(true)
                            .listener(
                                onStart = { Log.d("Coil", "Start loading: $imageUrl") },
                                onSuccess = { _, _ -> Log.d("Coil", "Success: $imageUrl") },
                                onError = { _, result ->
                                    Log.e("Coil", "Error: $imageUrl", result.throwable)
                                }
                            )
                            .build(),
                        contentDescription = null,
                        modifier = Modifier.size(80.dp)
                    )
                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text(item.name, style = MaterialTheme.typography.titleMedium)
                        Text(item.formattedPrice, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
}
