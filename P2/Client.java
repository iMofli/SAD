import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
public class Client {

    public static final int port = 12345;
    public MySocket s;
    public static final String Server_host="LocalHost";
    public String name;
    public static void main(String args[])throws IOException{
        //Scanner reb
        MySocket socket= new MySocket(Server_host,port);
        PrintWriter out=new PrintWriter(socket.MygetOutputStream(),true);
        BufferedReader buff= new BufferedReader(new InputStreamReader(socket.MygetInputStream()));
        BufferedReader b_client= new BufferedReader(new InputStreamReader(System.in));
        
    
        Thread input = new Thread (new Runnable(){
            public void run(){
                try{
                    String linia;
                    while((linia=b_client.readLine())!=null){
                        out.println(linia+"\n");
                        
                      
                    }
                }catch(IOException e){
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, e);
                }   
            } 
        });

        Thread output= new Thread(new Runnable() {
            public void run(){
                try{
                    String linia;
                    while((linia=buff.readLine())!= null){
                       System.out.println(linia);
                       //System.out.println("hola");
                    }
                }catch(IOException e){
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, e);
                }

            }    
        });
        input.start();
        output.start();
        System.out.println("Client esta connectat");

        
    }
    
}
