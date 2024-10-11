package cl.bootcamp.mobistore.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import cl.bootcamp.mobistore.viewModels.PhoneViewModel
import cl.bootcamp.mobistore.views.DetailsView
import cl.bootcamp.mobistore.views.HomeView

@Composable
fun NavManager(
    phoneVM: PhoneViewModel
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "Home"
    ) {
        
        composable("Home") 
        { 
            HomeView(
                navController = navController,
                phoneVM = phoneVM
            )
        }
        
        composable("Details/{id}", arguments = listOf(
            navArgument("id") { type = NavType.IntType }
        ))
        {
            val id = it.arguments?.getInt("id") ?: 0

            DetailsView(
                navController = navController,
                phoneVM = phoneVM,
                id = id
            )
        }
        
    }
}