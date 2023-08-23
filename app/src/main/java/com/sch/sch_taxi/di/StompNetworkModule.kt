package com.sch.sch_taxi.di

import com.sch.data.api.ApiClient.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import ua.naiksoftware.stomp.Stomp

// https://gwynn.tistory.com/89
class StompNetworkModule {
    private val client = OkHttpClient()
    private val url = "ws" + BASE_URL.substring(4) + "stomp/chat"
    private val request = Request.Builder().url(url).build()

    val stompClient =  Stomp.over(Stomp.ConnectionProvider.OKHTTP, url)

//    private val listener = object : WebSocketListener() {
//        override fun onOpen(webSocket: WebSocket, response: Response) {
//            subscribeTopic("/topic/example") // Subscribe to a topic after connection
//        }
//
//        override fun onMessage(webSocket: WebSocket, text: String) {
//            // Handle incoming text messages
//            println("Received: $text")
//        }
//
//        override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
//            // Handle incoming binary messages
//        }
//
//        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
//            // Handle connection failure
//        }
//    }
//
//    private val webSocket = client.newWebSocket(request, listener)
//
//    fun subscribeTopic(topic: String) {
//        val subscribeFrame = "SUBSCRIBE\n" +
//                "id:sub-0\n" +
//                "destination:$topic\n\n"
//
//        webSocket.send(subscribeFrame)
//    }
//
//    fun sendMessage(destination: String, message: String) {
//        val sendFrame = "SEND\n" +
//                "destination:$destination\n\n" +
//                "$message\n"
//
//        webSocket.send(sendFrame)
//    }
//
//    fun disconnect() {
//        webSocket.close(1000, "Disconnecting")
//    }
}