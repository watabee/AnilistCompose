package com.github.watabee.anilist.ui

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.watabee.anilist.Screen
import com.github.watabee.anilist.core.designsystem.theme.AnilistTheme
import com.github.watabee.anilist.feature.animetop.AnimeTopScreen

@Composable
fun AnilistApp() {
    AnilistTheme {
        Scaffold {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = Screen.AnimeTop.route) {
                composable(route = Screen.AnimeTop.route) {
                    AnimeTopScreen()
                }
            }
        }
    }
}
