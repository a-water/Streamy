package com.forfun.streamy.server;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by aaron on 12/26/15.
 */
public class SocketRocket extends AsyncTask<Void, Void, Void> {

    private final int socketPort = 8181;

    @Override
    protected Void doInBackground(Void... args) {

        ServerSocket serverSocket;

        try {
            serverSocket = new ServerSocket(socketPort);
            Socket socket = serverSocket.accept();

            PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

//            while(true) {
//                Socket socket = serverSocket.accept();
//                Log.d("TEXT", "IP: " + socket.getInetAddress());
//                try {
//                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
//                }

//            }

        } catch(IOException e) {
            e.printStackTrace();
        }

//
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {

    }

}
