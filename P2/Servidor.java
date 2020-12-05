import java.io.*;
import java.util.*;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import javax.swing.JPasswordField;

import java.util.logging.Level;
import java.util.concurrent.ExecutorService;

public class Servidor{
    public static ConcurrentHashMap<String,MySocket> mapa = new ConcurrentHashMap<>();
    private MySocket socket;
    private static final int port = 12345;
    //public
    public static void main(String[] args) throws IOException {
        System.out.println("Servidor en marxa...");
        //ExecutorService pool = Executors.newFixedThreadPool(500);
        try(MyServerSocket reciever=new MyServerSocket(port)){
            do{
                
                new ChatServidor(reciever.accept()).start();
            }while(true);
        }
    }
    public static class ChatServidor extends Thread{
        public PrintWriter out;
        public BufferedReader  buff;
        private MySocket socket;
        
        String nickname;
        public ChatServidor(MySocket s){
            this.socket=s;
            out = new PrintWriter(s.MygetOutputStream(),true);
            buff= new BufferedReader(new InputStreamReader(s.MygetInputStream()));
        }
        public void run(){
            do{
                nickname=null;
                this.out.println("Introdueix el teu nick:");
                try{
                    nickname = buff.readLine();
                    while(nickname==null){
                        int random =new Random().nextInt(1000);
                        nickname= "Guest"+random;
                    }
                }catch(IOException ex){
                    System.out.println(ex);
                }
                if(!mapa.containsKey(nickname)){
                    
                    System.out.println("MAPAJAT");
                    for(MySocket s: mapa.values()){
                        s.println(nickname + " s'ha afegit");
                    }
                    mapa.put(nickname, socket);
                    break;
                }
                else{
                    this.out.println("Aquest nickname ja esta agafat");
                }
        
            }while(true);
            String line;
            int numero =0;
            do{
                
                try{
                    line = buff.readLine();
                    line = buff.readLine();
                    
                
                    //if(this.buff.ready() && numero ==0){
                    //    line = buff.readLine();
                    //}
                        for(MySocket s:mapa.values()){ //BROADCAST
                            if(!(mapa.get(nickname) == s)){ //NOSALTRES MATEIXOS NO
                                s.println(nickname+": "+line);
                                
                            }
                            if((mapa.get(nickname) == s)){ //NOSALTRES MATEIXOS PERQUE AL SERVIDOR NOMES ES VEGI UN COP
                                System.out.println(nickname + ": "+line);
                                
                            }
                            
                        }
        
                        if(line.equals("Log out")){
                            mapa.remove(nickname);
                            for(MySocket s: mapa.values()){
                                s.println(nickname + "kilo"+"  ha marxat");
                                
                            }
                            break;  
                        }
                    
                    
                    //while ( line==null || line==""){
                    //    line = buff.readLine();
                    //}
                    numero = 1;
                }catch(IOException ex){
                    System.out.println(ex);
                } 
                
                
                

            }while(true);
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




