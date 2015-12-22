package org.mangosbot.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.annotation.PreDestroy;

import org.mangosbot.service.api.LiveDataService;
import org.mangosbot.service.api.dto.LiveData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LiveDataServiceImpl implements LiveDataService {
    private static final Logger LOG = LoggerFactory.getLogger(LiveDataServiceImpl.class);

    private Socket socket;

    private String host;
    private int port;

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @PreDestroy
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

    @Override
    public LiveData get(long guid) {
        LiveData data = new LiveData();
        data.setState(query("state,"+ guid));
        data.setPosition(query("position,"+ guid));
        data.setTpos(query("tpos,"+ guid));
        data.setTarget(query("target,"+ guid));
        data.setHp(query("hp,"+ guid));
        data.setAction(query("action,"+ guid));
        return data;
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
            LOG.error("I/O error", e);
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
