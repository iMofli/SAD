import java.io.*;
import java.net.ServerSocket;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyServerSocket extends ServerSocket {
    Socket s;
    ServerSocket ss;
    MySocket ms;

    public MyServerSocket() throws IOException {
        this.ss = new ServerSocket();
    }
    public MyServerSocket(int port) throws IOException {
        this.ss = new ServerSocket(port);
    }
    


    public MySocket accept() {
        try {
            this.s = ss.accept();
            this.ms = new MySocket(s);
            return ms;

        } catch (IOException e) {
            // TODO Auto-generated catch block
            Logger.getLogger(MySocket.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }
    public void close(){
        try{
            this.ss.close();
        }catch (IOException ex){
            Logger.getLogger(MyServerSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
    
