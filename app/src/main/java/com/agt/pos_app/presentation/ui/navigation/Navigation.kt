package com.agt.pos_app.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.agt.pos_app.presentation.screen.DashBoardScreen
import com.agt.pos_app.presentation.screen.OrderScreen
import com.agt.pos_app.presentation.screen.demoTable

@Composable
fun PosNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.DashBoardScreen.route) {
        composable(Screen.DashBoardScreen.route) {
            DashBoardScreen(demoTable,navController)
        }

        composable(Screen.OrderScreen.route + "/{table_no}",
            arguments = listOf(
                navArgument("table_no") {
                    type = NavType.StringType
                    nullable = false
                }
            )
        ) { entry ->

            OrderScreen(tableNo = entry.arguments?.getString("table_no")!!)
        }

    }


}