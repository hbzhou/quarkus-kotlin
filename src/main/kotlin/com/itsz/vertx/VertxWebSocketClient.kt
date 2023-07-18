package com.itsz.vertx

import io.vertx.mutiny.core.Vertx

fun main() {
    Vertx.vertx()
        .createHttpClient()
        .webSocket(8080, "localhost", "/")
        .subscribe()
        .with { websocket ->
            websocket.writeTextMessageAndForget("hello, from websocket client ....")
            websocket.textMessageHandler {
                println(it)
            }
        }
}