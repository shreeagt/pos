package com.agt.pos_app.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.agt.pos_app.data.module.Order
import com.agt.pos_app.data.module.Table
import com.agt.pos_app.data.module.TableStatus
import com.agt.pos_app.presentation.ui.theme.PosShapes
import com.agt.pos_app.util.inPosTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderScreen(table: Table, orders: List<Order> = emptyList()) {

    Box(modifier = Modifier) {
        Column {
            TopAppBar(title = {
                Text("Table No : ${table.tableNo}")
            },
                navigationIcon = {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "back button")
                }
            )

            if (orders.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

                    Column {
                        Text(
                            text = "No Order found",
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 32.sp
                            )
                        )
                    }


                }
            }


        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { /*TODO*/ },
                    shape = PosShapes.medium,
                ) {
                    Text(text = "Mark as Checkout")
                }

                Button(
                    onClick = { /*TODO*/ },
                    shape = PosShapes.medium,
                ) {
                    Text(text = "Add Order")
                }

            }
        }

    }


}


@Preview
@Composable
fun OrderScreen_preview() {

    inPosTheme {
        OrderScreen(Table(2, TableStatus.BOOKED))
    }


}