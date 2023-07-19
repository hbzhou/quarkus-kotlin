package com.itsz.vertx

import io.netty.handler.logging.ByteBufFormat
import io.vertx.core.net.NetServerOptions
import io.vertx.mutiny.core.Vertx
import io.vertx.mutiny.core.parsetools.RecordParser

private var numberOfConnections = 0
 fun main() {
    Vertx.vertx().createNetServer(NetServerOptions().setLogActivity(true).setActivityLogDataFormat(ByteBufFormat.HEX_DUMP))
        .connectHandler { socket ->
            numberOfConnections++
            println("we have a new Connection with ID: ${socket.writeHandlerID()}")
            RecordParser.newDelimited("\r\n", socket).handler {
                println(it)
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