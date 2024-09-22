package com.example.simpleplugin.websocket;

import com.example.simpleplugin.service.CallService;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

public class WebSocketServerManager extends WebSocketServer {

    private static volatile WebSocketServerManager instance;

    public static WebSocketServerManager getInstance() {
        if (instance == null) {
            synchronized (WebSocketServerManager.class) {
                instance = new WebSocketServerManager();
                instance.start();
            }
        }
        return instance;
    }

    private WebSocketServerManager() {
        super(new InetSocketAddress(0));
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        conn.send("Welcome to the server!"); //This method sends a message to the new client
        broadcast("new connection: " + handshake
                .getResourceDescriptor()); //This method sends a message to all clients connected
        System.out.println(
                conn.getRemoteSocketAddress().getAddress().getHostAddress() + " entered the room!");
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        broadcast(conn + " has left the room!");
        System.out.println(conn + " has left the room!");
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        if (message.equals("OpenBaidu")) {
            new CallService().open();
        } else if (message.equals("request")) {
            new CallService().request();
        } else {
            System.out.println(conn + ": " + message);
        }
    }

    @Override
    public void onMessage(WebSocket conn, ByteBuffer message) {
        broadcast(message.array());
        System.out.println(conn + ": " + message);
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        ex.printStackTrace();
        if (conn != null) {
            // some errors like port binding failed may not be assignable to a specific websocket
        }
    }

    @Override
    public void onStart() {
        System.out.println("Server started!");
        setConnectionLostTimeout(0);
    }
}
