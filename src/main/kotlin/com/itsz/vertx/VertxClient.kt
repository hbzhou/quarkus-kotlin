package com.itsz.vertx

import io.vertx.mutiny.core.Vertx

class VertxClient {
    private val vertx = Vertx.vertx()
    fun start () {
        vertx.createNetClient()
            .connect(3000, "localhost")
            .subscribe()
            .with { socket ->
                socket.handler {
                    println("${Thread.currentThread().name} -->receiving message from server $it")
                    socket.write("hello server")
                }
                socket.closeHandler {
                    println("${Thread.currentThread().name} --> connection ended ...")
                }
            }
    }

}

fun main() {
    VertxClient().start()
}