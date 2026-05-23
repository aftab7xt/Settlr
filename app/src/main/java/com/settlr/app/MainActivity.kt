package com.settlr.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.People
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.ShowChart
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.settlr.app.ui.navigation.Routes
import com.settlr.app.ui.navigation.SettlrNavGraph
import com.settlr.app.ui.theme.SettlrTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        setContent {
            SettlrTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        NavigationBar {
                            NavigationBarItem(
                                selected = currentRoute == Routes.People,
                                onClick = {
                                    navController.navigate(Routes.People) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                icon = { Icon(Icons.Rounded.People, contentDescription = "People") },
                                label = { Text("People") }
                            )
                            NavigationBarItem(
                                selected = currentRoute == Routes.Activity,
                                onClick = {
                                    navController.navigate(Routes.Activity) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                icon = { Icon(Icons.Rounded.ShowChart, contentDescription = "Activity") },
                                label = { Text("Activity") }
                            )
                            NavigationBarItem(
                                selected = currentRoute == Routes.You,
                                onClick = {
                                    navController.navigate(Routes.You) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                icon = { Icon(Icons.Rounded.Person, contentDescription = "You") },
                                label = { Text("You") }
                            )
                        }
                    },
                    floatingActionButton = {
                        val showFab = currentRoute != Routes.QuickAdd && currentRoute?.startsWith("contact_detail") != true
                        if (showFab) {
                            FloatingActionButton(
                                onClick = { navController.navigate(Routes.QuickAdd) },
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                            ) {
                                Icon(Icons.Rounded.Add, contentDescription = "Add Entry")
                            }
                        }
                    }
                ) { innerPadding ->
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        SettlrNavGraph(
                            navController = navController,
                            paddingValues = innerPadding
                        )
                    }
                }
            }
        }
    }
}
