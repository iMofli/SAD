import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MySocket extends Socket{
    Socket s;
    BufferedReader buff;
    PrintWriter out;
    
    public MySocket(String host, int port){//Creem el socket y el conectem al port que ens passaran
        try{
        this.s = new Socket(host,port);
        this.out = new PrintWriter(s.getOutputStream());
        this.buff = new BufferedReader(new InputStreamReader(s.getInputStream()));
        }catch(IOException e){
           Logger.getLogger(MySocket.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    public MySocket(Socket soc){
        try{
        this.s = soc;
        this.out = new PrintWriter(s.getOutputStream());
        this.buff = new BufferedReader(new InputStreamReader(s.getInputStream()));
        }catch(IOException e){
            Logger.getLogger(MySocket.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    public void MyConnect(SocketAddress endpoint){//conecta el socket con el servidor
        try{
            this.s.connect(endpoint);
        }catch(IOException ex){
             Logger.getLogger(MySocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public InputStream MygetInputStream(){//si el socket tiene un canal asociado, la fuente de entrada pasa a ser el canal
        try{
            return s.getInputStream();
        }catch(IOException ex){
            Logger.getLogger(MySocket.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public OutputStream MygetOutputStream(){//=MyGetInpustream pero con la fuente de salida
        try{
            return this.s.getOutputStream();
        }catch(IOException ex){
            Logger.getLogger(MySocket.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public void println(String s){
        out.println(s);
        out.flush();
    }
    public String readLine() throws Exception {
        return buff.readLine();
    }
    public void Myclose(){//cierra el socket y todo lo asociado a él(canal y Input-OutputStream)
        try{
            this.s.close();
        }catch(IOException ex){
            Logger.getLogger(MySocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}