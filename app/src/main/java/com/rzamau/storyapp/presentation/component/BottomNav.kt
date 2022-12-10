package com.rzamau.storyapp.presentation.component

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.rzamau.storyapp.presentation.navigation.Screen

@Composable
fun BottomNav(
    navController: NavController,
    items: List<Screen>
) {
    val context = LocalContext.current
    BottomNavigation(modifier = Modifier.height(66.dp)) {
        val currentRoute = currentRoute(navController = navController)
        items.forEach { screen ->
            BottomNavigationItem(
                icon = {
                    screen.icon?.let {
                        Icon(
                            modifier = Modifier.size(34.dp),
                            imageVector = it,
                            contentDescription = ""
                        )
                    }
                },
                label = {
                    Text(
                        text = context.getString(screen.label),
                    )
                },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(
                            navController.graph.findStartDestination().id
                        ) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }

                },
            )

        }

    }
}


@Composable
private fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}