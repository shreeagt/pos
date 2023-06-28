package com.agt.pos_app.presentation.ui.navigation

sealed class Screen(val route: String) {

    object DashBoardScreen : Screen("/")
    object OrderScreen : Screen("/order_screen")

    fun withArgs(vararg args : String ):String{
        return buildString {
            append(route)
            args.forEach {arg ->
            append("/$arg")
            }
        }
    }


}
