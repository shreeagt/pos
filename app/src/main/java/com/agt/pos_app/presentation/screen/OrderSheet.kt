package com.agt.pos_app.presentation.screen

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.agt.pos_app.R
import com.agt.pos_app.data.module.Item
import com.agt.pos_app.data.module.Order
import com.agt.pos_app.data.module.OrderStatus
import com.agt.pos_app.util.inPosTheme


@Composable
fun OrderSheet(
    itemList: List<Item> = emptyList(),
) {

    val orderList = remember {
        mutableStateListOf<Order>()
    }

    Box(modifier = Modifier) {
        LazyColumn {


            items(itemList) { item ->

                CartItem(
                    item = item,
                    orderList,
                    onMinusClick = { index ->
                        if (orderList[index].qnt == 1) {
                            orderList.removeAt(index)
                        } else {
                            val order = orderList[index]
                            order.qnt -= 1
                            orderList.removeAt(index)
                            orderList.add(order)
                        }
                    },
                    onPlusClick = { index ->
                        val order = orderList[index]
                        order.qnt += 1
                        orderList.removeAt(index)
                        orderList.add(order)
                    },
                    onAddOrder = {
                        Order(
                            System.currentTimeMillis(),
                            OrderStatus.INITIATE,
                            item,
                            1
                        ).apply {
                            orderList.add(this)
                        }

                    }
                )
            }
        }

        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Green
                )

            ) {
                Text(text = "Initiate Order")
            }
        }

    }

}


@Composable
fun CartItem(
    item: Item,
    orders: List<Order>,
    onAddOrder: () -> Unit,
    onPlusClick: (Int) -> Unit,
    onMinusClick: (Int) -> Unit
) {

    val index = orders.indexOfFirst { it.orderItem == item }
    val isAlreadyOrdered by remember(index) {
        mutableStateOf(index != -1)
    }
    val qnt = if (isAlreadyOrdered) orders[index].qnt else 0


    Log.d("Order", "CartItem: $isAlreadyOrdered : $index : $item ")
    orders.forEach {
        Log.d("Order", "CartItem: $it")
    }

    OutlinedCard(modifier = Modifier.padding(10.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Text(text = item.name)

            if (isAlreadyOrdered) {
                Box(
                    modifier = Modifier
                ) {
                    Row(
                        modifier = Modifier.border(
                            2.dp, Color.Yellow.copy(green = 0.5f),
                            RoundedCornerShape(10.dp)
                        ), verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = {
                            onMinusClick(index)
                        }) {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = R.drawable.round_horizontal_rule),
                                contentDescription = ""
                            )
                        }

                        Text(text = qnt.toString())

                        IconButton(onClick = {
                            onPlusClick(index)
                        }) {
                            Icon(imageVector = Icons.Filled.Add, contentDescription = "")
                        }
                    }
                }
            } else {
                Button(
                    onClick = onAddOrder,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Yellow.copy(green = 0.5f)
                    )
                ) {
                    Text(text = "Add")
                }
            }


        }
    }


}


@Preview
@Composable
fun OrderSheet_Preview() {
    inPosTheme {
        val myOrder = remember {
            demoOrder
        }
        OrderSheet(demoItem)
    }
}

val demoOrder = mutableListOf(
    Order(
        1,
        OrderStatus.INITIATE,
        Item(2, "Biryani"),
        1
    ),
    Order(
        2,
        OrderStatus.INITIATE,
        Item(3, "Ruti"),
        1
    ),
    Order(
        3,
        OrderStatus.INITIATE,
        Item(4, "Chicken"),
        1
    )
)

val demoItem = listOf(
    Item(5, "Ice Cream"),
    Item(4, "Chicken"),
    Item(3, "Ruti"),
    Item(2, "Biriyani"),
)