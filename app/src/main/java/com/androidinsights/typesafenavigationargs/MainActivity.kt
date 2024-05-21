package com.androidinsights.typesafenavigationargs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavArgument
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import androidx.navigation.navArgument
import androidx.navigation.toRoute
import com.androidinsights.typesafenavigationargs.screens.CatScreen
import com.androidinsights.typesafenavigationargs.screens.CatsScreen
import com.androidinsights.typesafenavigationargs.ui.theme.TypeSafeNavigationArgsTheme
import kotlin.reflect.typeOf

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TypeSafeNavigationArgsTheme {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    TopAppBar(
                        title = {
                            Text(
                                text = stringResource(R.string.app_name)
                            )
                        }
                    )

                    Content(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun Content(
    modifier: Modifier
) {
    val navController = rememberNavController()

    NavHost(
        modifier = modifier,
        navController = navController,
        graph = rememberTypedNavGraph(navController)
    )
}

@Composable
private fun rememberTypedNavGraph(
    navController: NavController
): NavGraph {
    return remember(navController) {
        navController.createGraph(
            startDestination = Screen.CatsList,
        ) {
            composable<Screen.CatsList>{
                CatsScreen(
                    onCatClicked = { cat ->
                        navController.navigate(
                            Screen.CatDetails(
                                cat = cat,
                            )
                        )
                    }
                )
            }

            composable<Screen.CatDetailsWithPrimitives> { backStackEntry ->
                val route = backStackEntry.toRoute<Screen.CatDetailsWithPrimitives>()

                CatScreen(
                    cat = Cat(
                        iconRes = route.icon,
                        textRes = route.text,
                    )
                )
            }

            composable<Screen.CatDetails>(
                typeMap = mapOf(
                    typeOf<Cat>() to CatNavType
                ),
            ) { backStackEntry ->
                val route = backStackEntry.toRoute<Screen.CatDetails>()

                CatScreen(
                    cat = route.cat
                )
            }
        }
    }
}

@Composable
private fun rememberNavGraph(
    navController: NavController
): NavGraph {
    return remember(navController) {
        navController.createGraph(
            startDestination = "cats_list",
        ) {
            composable(
                route = "cats_list"
            ) {
                CatsScreen(
                    onCatClicked = { cat ->
                        navController.navigate(
                            route = "cat/${cat.iconRes}/${cat.textRes}",
                        )
                    }
                )
            }

            composable(
                route = "cat/{icon}/{text}",
                arguments = listOf(
                    navArgument("icon") { NavType.StringType },
                    navArgument("text") { NavType.StringType },
                ),
            ) { backStackEntry ->
                CatScreen(
                    cat = Cat(
                        iconRes = backStackEntry.arguments!!.getString("icon")!!.toInt(),
                        textRes = backStackEntry.arguments!!.getString("text")!!.toInt(),
                    )
                )
            }
        }
    }
}