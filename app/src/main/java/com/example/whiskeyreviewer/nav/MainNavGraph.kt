package com.example.whiskeyreviewer.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.whiskeyreviewer.data.MainRoute
import com.example.whiskeyreviewer.view.HomeView
import com.example.whiskeyreviewer.view.InsertReviewView
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
        composable(MainRoute.INSERT_REVIEW) {
            InsertReviewView(writeReviewViewModel,navController,mainViewModel)
        }
        composable(MainRoute.WHISKEY_SEARCH) {
            WhiskeySearchView(writeReviewViewModel,navController,mainViewModel)
        }
        composable(MainRoute.REVIEW_DETAIL) {
            ReviewDetailView(navController,writeReviewViewModel,mainViewModel)
        }
    }
}


