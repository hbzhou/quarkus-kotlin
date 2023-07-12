package com.itsz.vertx

import io.vertx.mutiny.core.Vertx

class VertxClient {
    private val vertx = Vertx.vertx()
    fun start () {
        vertx.createNetClient()
            .connect(3000, "localhost")
            .subscribe()
            .with { socket ->
                socket.write("hello, server!!").replaceWithVoid()
                socket.handler {
                    socket.write(it)
                }
            }
    }

}

fun main() {
    VertxClient().start()
}