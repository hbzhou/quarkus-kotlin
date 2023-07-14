package com.itsz.vertx

import io.netty.handler.logging.ByteBufFormat
import io.vertx.core.net.NetServerOptions
import io.vertx.mutiny.core.Vertx

 private var numberOfConnections = 0
 fun main() {
    Vertx.vertx().createNetServer(NetServerOptions().setLogActivity(true).setActivityLogDataFormat(ByteBufFormat.HEX_DUMP))
        .connectHandler { socket ->
            numberOfConnections++
            socket.handler {
                println("${Thread.currentThread().name} --> receive data from client: $it")
                socket.writeAndForget("hello, from server...")
            }
            socket.closeHandler {
                numberOfConnections--
            }
        }
        .listen(3000)
        .subscribe()
        .with {
            println("${Thread.currentThread().name} --> netserver started at port ${it.actualPort()}")
        }

    Vertx.vertx().setPeriodic(5000) {
        println("${Thread.currentThread().name} --> we now have $numberOfConnections connections")
    }
}