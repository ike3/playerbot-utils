package org.mangosbot.sound;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MusicDataSource {
    private static final Logger LOG = LoggerFactory.getLogger(MusicDataSource.class);

    private Socket socket;

    private String host;
    private int port;

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void preDestroy() {
        if (socket == null) {
            return;
        }

        close();
    }

    private void close() {
        try {
            socket.close();
        } catch (IOException e) {
            LOG.warn("Error closing socket", e);
        }
        socket = null;
    }

    public String get(int account) {
        return query("state,"+ account);
    }

    private synchronized String query(String message) {
        try {
            return queryInternal(message);
        } catch (SocketException e) {
            close();
            return null;
        } catch (IOException e) {
            LOG.error("I/O error", e);
            return null;
        }
    }
    private String queryInternal(String message) throws IOException {
        Socket socket = reconnect();

        if (socket == null) {
            return null;
        }

        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        out.print(message);
        out.print("\n");
        out.flush();
        String line = in.readLine();
        return line;
    }

    private Socket reconnect() {
        try {
            return reconnectInternal();
        } catch (UnknownHostException e) {
            LOG.error("Unknown host " + host, e);
        } catch (IOException e) {
            LOG.error("I/O error" + e.getMessage());
        }
        return null;
    }

    private Socket reconnectInternal() throws UnknownHostException, IOException {
        if (socket == null || socket.isClosed()) {
            socket = new Socket(host, port);
            socket.setSoTimeout(1000);
        }
        return socket;
    }

}
