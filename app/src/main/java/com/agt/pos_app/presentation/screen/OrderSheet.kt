package com.agt.pos_app.presentation.screen

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.modifier.modifierLocalConsumer
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
    onOrderSubmit: (List<Order>) -> Unit
) {

    val orderList = remember {
        mutableStateListOf<Order>()
    }

    val categorys = remember {
        itemList.map { it.category }.toSet().toList()
    }

    Box(modifier = Modifier) {
        LazyColumn(modifier = Modifier.padding(bottom = 50.dp, top = 10.dp)) {
            items(categorys) { category ->

                CategoryCard(
                    category = category,
                    items = itemList,
                    orders = orderList,
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
                    onAddOrder = { item ->
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
                onClick = {
                    onOrderSubmit(orderList)
                    orderList.clear()
                },
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
    onAddOrder: (Item) -> Unit,
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

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 10.dp, vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Text(text = item.name)
        Text(text = "₹${item.price}")

        if (isAlreadyOrdered) {
            Box(
                modifier = Modifier
            ) {
                Row(
                    modifier = Modifier, verticalAlignment = Alignment.CenterVertically
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
                onClick = { onAddOrder(item) },
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

@Composable
fun CategoryCard(
    category: String,
    items: List<Item>,
    orders: List<Order>,
    onAddOrder: (Item) -> Unit,
    onPlusClick: (Int) -> Unit,
    onMinusClick: (Int) -> Unit
) {
    val inThisCategory = remember {
        items.filter { it.category == category }
    }

    var isExpend by remember {
        mutableStateOf(false)
    }

    val nowIcon =
        Card(modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
            ) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .clickable { isExpend = !isExpend }) {
                    Icon(
                        imageVector = if (isExpend) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowRight,
                        contentDescription = ""
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(text = category)
                }

                AnimatedVisibility(visible = isExpend) {
                    Column {
                        inThisCategory.forEach { item ->
                            CartItem(
                                item = item,
                                orders = orders,
                                onAddOrder = onAddOrder,
                                onPlusClick = onPlusClick,
                                onMinusClick = onMinusClick
                            )

                        }
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
        OrderSheet(demoItem) {}
    }
}

val demoOrder = mutableListOf(
    Order(
        1,
        OrderStatus.INITIATE,
        Item(2, "Biryani", "CAT3", 100f),
        1
    ),
    Order(
        2,
        OrderStatus.INITIATE,
        Item(3, "Ruti", "CAT2", 100f),
        1
    ),
    Order(
        3,
        OrderStatus.INITIATE,
        Item(4, "Chicken", "CAT1", 100f),
        1
    )
)

val demoItem = listOf(
    Item(5, "Ice Cream", "CAT1", 100f),
    Item(4, "Chicken", "CAT2", 100f),
    Item(3, "Ruti", "CAT3", 100f),
    Item(6, "Mutton Raan", "CAT3", 100f),
    Item(7, "Mutton Gulati Kabab", "CAT3", 100f),
    Item(2, "Biriyani", "CAT4", 100f),
)