package com.example.whiskeyreviewer.nav

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
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
import com.example.whiskeyreviewer.viewModel.WriteReviewViewModel

@Composable
fun MainNavGraph(
    navController: NavHostController,
    writeReviewViewModel: WriteReviewViewModel
) {




    NavHost(
        navController = navController,
        startDestination = "homeRoute"
    ) {
        homeRoute(navController,writeReviewViewModel)
    }
}



private fun NavGraphBuilder.homeRoute(
    navController: NavHostController,
    writeReviewViewModel: WriteReviewViewModel,

) {
    navigation(
        route = "homeRoute",
        startDestination = MainRoute.HOME
    ) {
        composable(MainRoute.HOME) {
            HomeView(writeReviewViewModel,navController)
        }
        composable(MainRoute.WHISKY_DETAIL) {
            WhiskeyDetailView(writeReviewViewModel,navController)
        }
        composable(MainRoute.INSERT_REVIEW) {
            InsertReviewView(writeReviewViewModel,navController)
        }
        composable(MainRoute.WHISKEY_SEARCH) {
            WhiskeySearchView(writeReviewViewModel,navController)
        }
        composable(MainRoute.REVIEW_DETAIL) {
            ReviewDetailView(navController,writeReviewViewModel)
        }
    }
}


