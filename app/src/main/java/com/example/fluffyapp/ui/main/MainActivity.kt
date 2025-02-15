package com.example.fluffyapp.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
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
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.scrim
                ) {
                   Scaffold(bottomBar = {
                       NavigationBar {
                        BottomNavigationItem.item.forEachIndexed{ index, item ->
                            NavigationBarItem(
                                selected = selectedItem == index,
                                onClick = {
                                          selectedItem = index
                                         navController.navigate(item.route)
                                },
                                label = {
                                        Text(text = item.title,
                                            fontSize = 12.sp
                                            )
                                },
                                alwaysShowLabel = false,
                                icon = {
                                    Icon(imageVector = if (selectedItem == index) item.iconSelected else item.iconNotSelected, contentDescription = item.title)
                                })

                        }
                       }

                   }) {paddingValues ->
                       AppNavigation(navController = navController, paddingValues )
                   }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FluffyAppTheme {
        Greeting("Android")
    }
}