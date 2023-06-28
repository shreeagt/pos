package com.agt.pos_app.presentation.screen

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.agt.pos_app.data.module.Order
import com.agt.pos_app.domain.generateAndPrintInvoice
import com.agt.pos_app.presentation.ui.theme.PosShapes
import com.agt.pos_app.util.inPosTheme
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun OrderScreen(tableNo: String) {

    val context = LocalContext.current

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )
    val coroutineScope = rememberCoroutineScope()
    var orderList = remember {
        mutableStateListOf<Order>()
    }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            Box(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .padding(10.dp)
                        .size(width = 50.dp, height = 5.dp)
                        .align(Alignment.Center)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.DarkGray)
                )
            }
            OrderSheet(demoItem) {
                generateAndPrintInvoice(context,tableNo ,it,)
                orderList.addAll(it)
                coroutineScope.launch {
                    sheetState.hide()
                }
            }
        },
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column {
                TopAppBar(title = {
                    Text(
                        "Table No : $tableNo",
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                    )
                },
                    navigationIcon = {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "back button"
                        )
                    }
                )
                if (orderList.isEmpty()) {
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
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Name", modifier = Modifier.weight(1f))
                    Text(text = "Qnt", modifier = Modifier.weight(1f))
                    Text(text = "Price", modifier = Modifier.weight(1f))
                    Text(text = "Status", modifier = Modifier.weight(1f))
                }
                LazyColumn {
                    items(orderList) {

                        OrderItem(order = it)
                        Spacer(modifier = Modifier.height(5.dp))
                        Divider(modifier = Modifier.padding(horizontal = 20.dp))

                    }
                    item {
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 14.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Total :")
                            Text(text = "₹${getTotal(orderList)}")
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
                        onClick = {
                            coroutineScope.launch {
                                sheetState.show()
                            }
                        },
                        shape = PosShapes.medium,
                    ) {
                        Text(text = "Add Order")
                    }

                }
            }

        }

    }


}

fun getTotal(orders: List<Order>): Float {
    var total = 0F
    orders.forEach {
        total += it.orderItem.price * it.qnt
    }
    return total
}

@Composable
fun OrderItem(order: Order) {
    Box {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = order.orderItem.name,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "${order.qnt}", style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "₹${order.orderItem.price * order.qnt}",
                modifier = Modifier.weight(1f)
            )
            Text(
                text = order.orderStatus.name,
                modifier = Modifier.weight(1f)
            )
        }
    }

}


@Preview
@Composable
fun OrderScreen_preview() {

    inPosTheme {
        OrderScreen("2")
    }


}