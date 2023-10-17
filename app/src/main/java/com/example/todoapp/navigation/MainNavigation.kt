package com.example.todoapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todoapp.MainViewModal
import com.example.todoapp.screens.EditorScreen
import com.example.todoapp.screens.MainScreen

@Composable
fun MainNavigation(noteViewModal:MainViewModal) {
    val navHostController = rememberNavController()
    NavHost(navController = navHostController, startDestination =Screens.MainScreen.route ){

        composable(Screens.MainScreen.route){
            MainScreen(navHostController,noteViewModal)
        }
        composable(Screens.EditorScreen.route){
            EditorScreen(navHostController,noteViewModal)
        }

    }
}

sealed class Screens(val route :String){
    object MainScreen : Screens("main_screen")
    object EditorScreen : Screens("editor_screen")
}