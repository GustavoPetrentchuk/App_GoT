package com.example.projeto3

import CharacterDetailsView
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.projeto3.ui.theme.Projeto3Theme
import com.example.projeto3.ui.views.CharactersUiState
import com.example.projeto3.ui.views.CharactersView
import com.example.projeto3.ui.views.CharactersViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            Projeto3Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //CharactersView()
                    val charactersViewModel: CharactersViewModel = viewModel()
                    AppNavigation(navController = navController, charactersViewModel = charactersViewModel)
                }
            }
        }
    }
}

sealed class Destination(val route: String) {
    object Characters : Destination("characters")
    object CharacterDetails : Destination("character/{elementId}") {
        fun createRoute(elementId: Int): String = "character/$elementId"
    }
}

@Composable
fun AppNavigation(navController: NavHostController, charactersViewModel: CharactersViewModel) {
    val uiState by charactersViewModel.uiState.collectAsState()

    NavHost(navController = navController, startDestination = Destination.Characters.route) {
        composable(Destination.Characters.route) {
            CharactersView(charactersViewModel = charactersViewModel, navController = navController)
        }
        composable(
            route = Destination.CharacterDetails.route,
            arguments = listOf(navArgument("elementId") { type = NavType.IntType })
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            val characterId = arguments.getInt("elementId")
            val characters = (uiState as? CharactersUiState.Success)?.characters
            val character = characters?.find { it.id == characterId }
            character?.let {
                CharacterDetailsView(character = it)
            }
        }
    }
}





