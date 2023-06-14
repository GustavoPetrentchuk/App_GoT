package com.example.projeto3.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.projeto3.R
import com.example.projeto3.data.Character
import coil.compose.rememberImagePainter
import com.example.projeto3.Destination


@Composable
fun CharactersView(
    charactersViewModel: CharactersViewModel = viewModel(),
    navController: NavController
) {
    val uiState by charactersViewModel.uiState.collectAsState()

    when (uiState) {
        is CharactersUiState.Loading -> LoadingScreen()
        is CharactersUiState.Success -> {
            val characters = (uiState as CharactersUiState.Success).characters
            CharactersList(characters = characters, navigateToCharacterDetails = { characterId ->
                navController.navigate(Destination.CharacterDetails.createRoute(characterId))
            })
        }
        is CharactersUiState.Error -> ErrorScreen()
    }
}

@Composable
fun CharactersList(
    characters: List<Character>,
    navigateToCharacterDetails: (Int) -> Unit
) {
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray),
        columns = GridCells.Fixed(2)
    ) {
        items(characters) { character ->
            CharacterEntry(character = character) {
                navigateToCharacterDetails(character.id)
            }
        }
    }
}

@Composable
fun CharacterEntry(
    character: Character,
    onItemClick: () -> Unit
) {
    val density = LocalDensity.current.density
    val width = remember { mutableStateOf(0F) }
    val height = remember { mutableStateOf(0F) }
    Card(
        modifier = Modifier
            .padding(6.dp)
            .height(200.dp)
            .width(200.dp)
            .clickable { onItemClick() },
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Box {
            Image(
                painter = rememberImagePainter(data = character.imageUrl),
                contentDescription = character.fullName,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RectangleShape)
                    .onGloballyPositioned {
                        width.value = it.size.width / density
                        height.value = it.size.height / density
                    }
            )
            Box(
                modifier = Modifier
                    .size(
                        width = width.value.dp,
                        height = height.value.dp
                    )
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black),
                            startY = 200F,
                            endY = 475F
                        )
                    )
            )
            Text(
                modifier = Modifier.align(Alignment.BottomCenter),
                text = character.fullName,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            )
        }
    }
}

@Composable
fun ErrorScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Erro")
    }
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.got_placeholder),
            contentDescription = null
        )
    }
}