package com.example.fluffyapp.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.fluffyapp.ui.navigation.AppNavigation
import com.example.fluffyapp.ui.navigation.BottomNavigationItem
import com.example.fluffyapp.ui.theme.FluffyAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FluffyAppTheme {
                var selectedItem by rememberSaveable {
                    mutableStateOf(0)
                }
                val navController = rememberNavController()

                val bottomNavigationItems = listOf(
                    BottomNavigationItem.BreedList,
                    BottomNavigationItem.BreedFavourite
                )

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.scrim
                ) {

                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination?.route
                    val bottomBarDestination =
                        bottomNavigationItems.any { it.route == currentDestination }

                    Scaffold(
                        bottomBar = {
                            if (bottomBarDestination) {
                                NavigationBar {
                                    bottomNavigationItems.forEachIndexed { index, item ->
                                        NavigationBarItem(
                                            selected = selectedItem == index,
                                            onClick = {
                                                selectedItem = index
                                                navController.navigate(item.route)
                                            },
                                            label = {
                                                Text(
                                                    text = item.title,
                                                    fontSize = 12.sp
                                                )
                                            },
                                            alwaysShowLabel = false,
                                            icon = {
                                                Icon(
                                                    imageVector = if (selectedItem == index) item.iconSelected else item.iconNotSelected,
                                                    contentDescription = item.title
                                                )
                                            },
                                            colors = NavigationBarItemDefaults.colors(
                                                selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                                                selectedTextColor = MaterialTheme.colorScheme.onPrimary
                                            )
                                        )
                                    }
                                }
                            }
                        }) { paddingValues ->
                        AppNavigation(navController = navController, paddingValues)
                    }
                }
            }
        }
    }
}