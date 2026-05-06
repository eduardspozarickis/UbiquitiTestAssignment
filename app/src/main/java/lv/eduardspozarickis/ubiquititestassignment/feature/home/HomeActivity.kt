package lv.eduardspozarickis.ubiquititestassignment.feature.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import lv.eduardspozarickis.ubiquititestassignment.feature.details.ProductDetailsScreen
import lv.eduardspozarickis.ubiquititestassignment.ui.theme.UbiquityTestAssignmentTheme
import lv.eduardspozarickis.ubiquititestassignment.utils.NavigationMap
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : ComponentActivity() {

    private val viewModel: HomeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            UbiquityTestAssignmentTheme {
                val navController = rememberNavController()
                val uiState by viewModel.uiState.collectAsState()

                NavHost(
                    navController = navController,
                    startDestination = NavigationMap.Home
                ) {
                    composable<NavigationMap.Home> {
                        HomeScreen(
                            selectedTab = uiState.selectedCategory,
                            onTabClicked = { viewModel.onCategoryClicked(it) },
                            products = uiState.displayProducts,
                            isLoading = uiState.isLoading,
                            error = uiState.error,
                            onRefresh = { viewModel.fetchData() },
                            onItemClick = { product ->
                                navController.navigate(
                                    NavigationMap.ProductDetails(
                                        id = product.id,
                                        deviceType = product.deviceType,
                                        name = product.name,
                                        line = product.line,
                                        imageId = product.imageId
                                    )
                                )
                            }
                        )
                    }

                    composable<NavigationMap.ProductDetails> { backStackEntry ->
                        val productDetails: NavigationMap.ProductDetails = backStackEntry.toRoute()

                        ProductDetailsScreen(
                            productDetails = productDetails,
                            onBackClick = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}