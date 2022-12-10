package com.rzamau.storyapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.HiltViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rzamau.storyapp.presentation.component.BottomNav
import com.rzamau.storyapp.presentation.login.LoginScreen
import com.rzamau.storyapp.presentation.login.LoginViewModel
import com.rzamau.storyapp.presentation.maps.MapsScreen
import com.rzamau.storyapp.presentation.maps.MapsViewModel
import com.rzamau.storyapp.presentation.register.RegisterScreen
import com.rzamau.storyapp.presentation.register.RegisterViewModel
import com.rzamau.storyapp.presentation.story_detail.StoryDetailScreen
import com.rzamau.storyapp.presentation.story_detail.StoryDetailViewModel
import com.rzamau.storyapp.presentation.story_list.StoryListScreen
import com.rzamau.storyapp.presentation.story_list.StoryListViewModel
import com.rzamau.storyapp.presentation.story_upload.StoryUploadScreen
import com.rzamau.storyapp.presentation.story_upload.StoryUploadViewModel
import com.rzamau.storyapp.presentation.welcome.WelcomeScreen
import com.rzamau.storyapp.presentation.welcome.WelcomeViewModel

@Composable
fun Navigation() {
    val navController = rememberNavController()

    val bottomNavigationItems = listOf(
        Screen.StoryList,
        Screen.Maps,
    )

    val bottomBar: @Composable () -> Unit = {
        BottomNav(navController = navController, items = bottomNavigationItems)
    }

    NavHost(navController = navController, startDestination = Screen.Welcome.route) {

        composable(route = Screen.Welcome.route) { navBackStackEntry ->
            val factory = HiltViewModelFactory(LocalContext.current, navBackStackEntry)
            val viewModel: WelcomeViewModel = viewModel(key = "WelcomeViewModel", factory = factory)
            WelcomeScreen(
                state = viewModel.state.value,
                onIsLogin = {
                    navController.navigate(Screen.StoryList.route) {
                        launchSingleTop = true
                        popUpTo(Screen.Welcome.route) {
                            inclusive = true
                        }
                    }
                },
                onClickLoginBtn = {
                    navController.navigate(Screen.Login.route)
                }, onClickRegisterBtn = {
                    navController.navigate(Screen.Register.route)
                }

            )
        }

        composable(route = Screen.Login.route) { navBackStackEntry ->
            val factory = HiltViewModelFactory(LocalContext.current, navBackStackEntry)
            val viewModel: LoginViewModel = viewModel(key = "LoginViewModel", factory = factory)
            LoginScreen(
                state = viewModel.state.value,
                onTriggerEvent = viewModel::onTriggerEvent,
                onLoginSuccessfully = {
                    navController.navigate(Screen.StoryList.route) {
                        launchSingleTop = true
                        popUpTo(Screen.Login.route) {
                            inclusive = true
                        }
                    }
                }, onBack = {
                    navController.popBackStack()
                }
            )

        }

        composable(route = Screen.Register.route) { navBackStackEntry ->
            val factory = HiltViewModelFactory(LocalContext.current, navBackStackEntry)
            val viewModel: RegisterViewModel =
                viewModel(key = "RegisterViewModel", factory = factory)
            RegisterScreen(
                state = viewModel.state.value,
                onTriggerEvent = viewModel::onTriggerEvent,
                onRegisterSuccessfully = {
                    navController.navigate(Screen.Login.route) {
                        launchSingleTop = true
                        popUpTo(Screen.Register.route) {
                            inclusive = true
                        }
                    }
                },
                onBack = {
                    navController.popBackStack()
                }
            )

        }


        composable(
            route = Screen.StoryList.route + "?forceRefresh={forceRefresh}",
            arguments = listOf(navArgument("forceRefresh") {
                defaultValue = false
                type = NavType.BoolType
            })
        ) { navBackStackEntry ->
            val factory = HiltViewModelFactory(LocalContext.current, navBackStackEntry)
            val viewModel: StoryListViewModel =
                viewModel(key = "StoryListViewModel", factory = factory)

            StoryListScreen(
                state = viewModel.state.value,
                isForceRefresh = navBackStackEntry.arguments?.getBoolean("forceRefresh"),
                stories = viewModel.stories,
                onClickStoryListItem = { story ->
                    navController.navigate("${Screen.StoryDetail.route}/${story.id}")
                },
                onClickUploadStoryBtn = {
                    navController.navigate(Screen.StoryUpload.route)
                },
                onTriggerEvent = viewModel::onTriggerEvent,
                onLoginSuccessfully = {
                    navController.navigate(Screen.Welcome.route) {
                        launchSingleTop = true
                        popUpTo(Screen.StoryList.route) {
                            inclusive = true
                        }
                    }
                },
                bottomBar = bottomBar,
            )

        }
        composable(route = Screen.Maps.route) { navBackStackEntry ->
            val factory = HiltViewModelFactory(LocalContext.current, navBackStackEntry)
            val viewModel: MapsViewModel =
                viewModel(key = "MapsViewModel", factory = factory)

            MapsScreen(
                state = viewModel.state.value,
                bottomBar = bottomBar,
                onTriggerEvent = viewModel::onTriggerEvent,
            )

        }

        composable(
            route = Screen.StoryDetail.route + "/{storyId}",
            arguments = listOf(navArgument("storyId") {
                type = NavType.StringType
            })
        ) { navBackStackEntry ->
            val factory = HiltViewModelFactory(LocalContext.current, navBackStackEntry)
            val viewModel: StoryDetailViewModel =
                viewModel(key = "StoryDetailViewModel", factory = factory)
            StoryDetailScreen(
                state = viewModel.state.value,
            )

        }

        composable(route = Screen.StoryUpload.route) { navBackStackEntry ->
            val factory = HiltViewModelFactory(LocalContext.current, navBackStackEntry)
            val viewModel: StoryUploadViewModel =
                viewModel(key = "StoryUploadViewModel", factory = factory)
            StoryUploadScreen(
                onTriggerEvent = viewModel::onTriggerEvent,
                state = viewModel.state.value,
                onBack = {
                    navController.popBackStack()
                },
                navigateUp = {
                    navController.navigate("${Screen.StoryList.route}?forceRefresh=${true}") {
                        launchSingleTop = true
                        popUpTo(Screen.StoryUpload.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }


    }
}



