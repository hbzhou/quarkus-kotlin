package com.itsz.vertx

import io.vertx.mutiny.core.Vertx

class VertxServer {
    private val vertx: Vertx = Vertx.vertx()
    private var numberOfConnections = 0

    fun start(){
        vertx.createNetServer()
            .connectHandler { socket ->
                numberOfConnections ++
                socket.handler {
                    socket.write(it)
                }
                socket.closeHandler {
                    numberOfConnections --
                }
            }
            .listen(3000)
            .subscribe()
            .with {
                println("net server started at ${it.actualPort()}")
            }

        vertx.setPeriodic(5000) {
            println("we now have $numberOfConnections connections")
        }

        vertx.createHttpServer()
            .requestHandler {request ->
                request.response().end("we no have $numberOfConnections connection")
            }
            .listen(8080)
            .subscribe()
            .with {
                println("http server started at ${it.actualPort()}")
            }

    }
}

fun main() {
    VertxServer().start()
}