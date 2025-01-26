package com.example.whiskeyreviewer.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.whiskeyreviewer.component.camea.CameraComponent
import com.example.whiskeyreviewer.data.AddImageTag
import com.example.whiskeyreviewer.data.MainRoute
import com.example.whiskeyreviewer.view.HomeView
import com.example.whiskeyreviewer.view.InsertReviewView
import com.example.whiskeyreviewer.view.OtherUserReviewDetailView
import com.example.whiskeyreviewer.view.ReviewDetailView
import com.example.whiskeyreviewer.view.WhiskeyDetailView
import com.example.whiskeyreviewer.view.WhiskeySearchView
import com.example.whiskeyreviewer.viewModel.MainViewModel
import com.example.whiskeyreviewer.viewModel.WriteReviewViewModel

@Composable
fun MainNavGraph(
    navController: NavHostController,
    writeReviewViewModel: WriteReviewViewModel,
    mainViewModel: MainViewModel
) {




    NavHost(
        navController = navController,
        startDestination = "homeRoute"
    ) {
        homeRoute(navController,writeReviewViewModel,mainViewModel)
    }
}



private fun NavGraphBuilder.homeRoute(
    navController: NavHostController,
    writeReviewViewModel: WriteReviewViewModel,
    mainViewModel: MainViewModel,

    ) {
    navigation(
        route = "homeRoute",
        startDestination = MainRoute.HOME
    ) {
        composable(MainRoute.HOME) {
            HomeView(writeReviewViewModel,navController,mainViewModel)
        }
        composable(MainRoute.WHISKY_DETAIL) {
            WhiskeyDetailView(writeReviewViewModel,navController,mainViewModel)
        }
        composable("${MainRoute.INSERT_REVIEW}/{tag}") {backStackEntry ->
            InsertReviewView(
                writeReviewViewModel,
                navController,
                mainViewModel,
                tag=backStackEntry.arguments?.getString("tag") ?: "",
            )
        }
        composable(MainRoute.WHISKEY_SEARCH) {
            WhiskeySearchView(writeReviewViewModel,navController,mainViewModel)
        }
        composable(MainRoute.REVIEW_DETAIL) {
            ReviewDetailView(navController,writeReviewViewModel,mainViewModel)
        }
        composable(MainRoute.OTHER_USER_REVIEW_DETAIL) {
            OtherUserReviewDetailView(writeReviewViewModel,navController,mainViewModel)
        }
        composable(route= MainRoute.CAMERA,) {
            CameraComponent(mainViewModel,navController)
        }
    }
}


