package com.settlr.app.ui.components.core

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.People
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.ShowChart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.settlr.app.ui.navigation.Routes

@Composable
fun BottomNavBar(
    currentRoute: String?,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(modifier = modifier) {
        NavigationBarItem(
            selected = currentRoute == Routes.People,
            onClick = { onNavigate(Routes.People) },
            icon = { Icon(Icons.Rounded.People, contentDescription = "People") },
            label = { Text("People") }
        )
        NavigationBarItem(
            selected = currentRoute == Routes.Activity,
            onClick = { onNavigate(Routes.Activity) },
            icon = { Icon(Icons.Rounded.ShowChart, contentDescription = "Activity") },
            label = { Text("Activity") }
        )
        NavigationBarItem(
            selected = currentRoute == Routes.You,
            onClick = { onNavigate(Routes.You) },
            icon = { Icon(Icons.Rounded.Person, contentDescription = "You") },
            label = { Text("You") }
        )
    }
}
