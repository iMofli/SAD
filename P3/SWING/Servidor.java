/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p3;

/**
 *
 * @author jordiperez
 */
import java.io.*;
import java.util.*;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import javax.swing.JPasswordField;

import java.util.logging.Level;
import java.util.concurrent.ExecutorService;

public class Servidor {
    public static ConcurrentHashMap<String, MySocket> mapa = new ConcurrentHashMap<>();
    //private MySocket socket;
    private static final int port = 12345;
    
    

    // public
    public static void main(String[] args) throws IOException {
        System.out.println("Servidor en marxa...");
        // ExecutorService pool = Executors.newFixedThreadPool(500);
        try (MyServerSocket reciever = new MyServerSocket(port)) {
            do {

                new ChatServidor(reciever.accept()).start();
            } while (true);
        }
    }

    public static class ChatServidor extends Thread {
        public PrintWriter out;
        public BufferedReader buff;
        private MySocket socket;
        private Client_GUI client;
        
        String nickname;

        public ChatServidor(MySocket s) {
            
            this.socket = s;
            out = new PrintWriter(s.MygetOutputStream(), true);
            buff = new BufferedReader(new InputStreamReader(s.MygetInputStream()));
        }

        @Override
        public void run() {
            do {
                nickname = null;
                this.out.println("Introdueix el teu nick:");
                
                try {
                    nickname = buff.readLine();
                    while (nickname == null || nickname.equals("")) {
                        nickname = buff.readLine();
                    }
                } catch (IOException ex) {
                    System.out.println(ex);
                }
                if (!mapa.containsKey(nickname)) {

                    System.out.println("MAPAJAT");
                    
                    socket.println("Benvingut al xat general.");
                    for (MySocket s : mapa.values()) {
                        s.println(nickname+" s'ha afegit");
                    }
                    mapa.put(nickname, socket);
                    String users = "users";
                    Enumeration<String> e = mapa.keys();
                    while(e.hasMoreElements()){
                        String name = e.nextElement();
                        users += "/" + name;
                    }
                    for (MySocket s : mapa.values()) {
                        s.println(users);
                    }
                    break;
              
                   
                } else {
                    this.out.println("Aquest nickname ja esta agafat");
                }

            } while (true);
            String line;

            do {

                try {
                    line = buff.readLine();
                    line = buff.readLine();

                    for (MySocket s : mapa.values()) { // BROADCAST
                        if (!(mapa.get(nickname) == s) && !line.equals("/exit")) { // NOSALTRES MATEIXOS NO
                            s.println("["+nickname+"]" + ": " + line);

                        }
                        if ((mapa.get(nickname) == s)) { // NOSALTRES MATEIXOS PERQUE AL SERVIDOR NOMES ES VEGI UN COP
                            System.out.println("["+nickname+"]" + ": " + line);

                        }

                    }

                    if (line.equals("/exit")) {
                        mapa.remove(nickname);
                        for (MySocket s : mapa.values()) {
                            s.println(nickname+ " ha marxat");
                            
                        }
                        break;
                    }

                    // while ( line==null || line==""){
                    // line = buff.readLine();
                    // }
                   
                } catch (IOException ex) {
                    System.out.println(ex);
                }

            } while (true);
            try {
                socket.close();
                out.close();
                buff.close();
            } catch (Exception e) {
                Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, e);
            }
        }

    }

}
