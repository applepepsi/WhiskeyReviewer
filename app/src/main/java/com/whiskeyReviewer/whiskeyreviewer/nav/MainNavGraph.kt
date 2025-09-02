package com.whiskeyReviewer.whiskeyreviewer.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.whiskeyReviewer.whiskeyreviewer.data.MainRoute
import com.whiskeyReviewer.whiskeyreviewer.view.HomeView
import com.whiskeyReviewer.whiskeyreviewer.view.InsertReviewView
import com.whiskeyReviewer.whiskeyreviewer.view.OtherUserReviewDetailView
import com.whiskeyReviewer.whiskeyreviewer.view.PrivacyPolicyView
import com.whiskeyReviewer.whiskeyreviewer.view.ReviewDetailView
import com.whiskeyReviewer.whiskeyreviewer.view.SettingView
import com.whiskeyReviewer.whiskeyreviewer.view.TermsOfServiceView
import com.whiskeyReviewer.whiskeyreviewer.view.WhiskeyDetailView
import com.whiskeyReviewer.whiskeyreviewer.view.WhiskeySearchView
import com.whiskeyReviewer.whiskeyreviewer.viewModel.MainViewModel
import com.whiskeyReviewer.whiskeyreviewer.viewModel.WriteReviewViewModel

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
        composable(MainRoute.SETTING_VIEW) {
            SettingView(mainViewModel,navController)
        }

        composable(MainRoute.TERMS_OF_SERVICE) {
            TermsOfServiceView(mainViewModel,navController)
        }

        composable(MainRoute.PRIVACY_POLICY) {
            PrivacyPolicyView(mainViewModel,navController)
        }
    }
}


