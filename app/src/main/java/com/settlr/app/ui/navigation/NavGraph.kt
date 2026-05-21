package com.settlr.app.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.settlr.app.ui.screens.activity.ActivityScreen
import com.settlr.app.ui.screens.contact_detail.ContactDetailScreen
import com.settlr.app.ui.screens.people.PeopleScreen
import com.settlr.app.ui.screens.quickadd.QuickAddSheet
import com.settlr.app.ui.screens.you.YouScreen

@Composable
fun SettlrNavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Routes.People,
        modifier = modifier.padding(paddingValues)
    ) {
        composable(Routes.People) {
            PeopleScreen(
                onPersonClick = { personId -> 
                    navController.navigate(Routes.createContactDetailRoute(personId)) 
                }
            )
        }
        composable(Routes.Activity) {
            ActivityScreen()
        }
        composable(Routes.You) {
            YouScreen()
        }
        composable(
            route = Routes.ContactDetail,
            arguments = listOf(navArgument("personId") { type = NavType.StringType })
        ) { backStackEntry ->
            val personId = checkNotNull(backStackEntry.arguments?.getString("personId"))
            ContactDetailScreen(
                personId = personId,
                onBack = { navController.popBackStack() }
            )
        }
        composable(Routes.QuickAdd) {
            QuickAddSheet(
                onDismiss = { navController.popBackStack() }
            )
        }
    }
}
