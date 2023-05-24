package com.agt.pos_app.util

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.agt.pos_app.presentation.ui.theme.POS_AppTheme

@Composable
fun inPosTheme(content: @Composable () -> Unit) {

    POS_AppTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            content()
        }
    }


}