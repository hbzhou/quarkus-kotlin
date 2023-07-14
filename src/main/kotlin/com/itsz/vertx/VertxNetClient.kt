package com.itsz.vertx

import io.netty.handler.logging.ByteBufFormat
import io.vertx.core.net.NetClientOptions
import io.vertx.mutiny.core.Vertx

fun main() {
    Vertx.vertx().createNetClient(NetClientOptions().setReconnectInterval(2000).setReconnectAttempts(1000).setLogActivity(true).setActivityLogDataFormat(ByteBufFormat.SIMPLE)).connect(3000, "localhost")
        .subscribe()
        .with { socket ->
            socket.writeAndForget("hello, from client ....")
            socket.handler {
                println("${Thread.currentThread().name} -->receiving message from server $it")
                socket.writeAndForget(it)
            }
            socket.closeHandler {
                println("socket closed.....")
            }
        }
}