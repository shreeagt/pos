package com.agt.pos_app.data.module

data class Order(
    val orderId :Long,
    val orderStatus: OrderStatus,
    val orderItem : Item,
    var qnt : Int
)