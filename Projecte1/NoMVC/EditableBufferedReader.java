import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;



public class EditableBufferedReader extends BufferedReader {

    private static final int NUM_ESQUERRA = 68;
    private static final int NUM_DRETA = 67;
    private static final int NUM_HOME = 72;
    private static final int NUM_END = 70;

    private static final int ESQUERRA = 256;
    private static final int DRETA = 257;
    private static final int HOME = 258;
    private static final int END = 259;
    private static final int DEL = 260;
    private static final int INS = 261;

    private static final int ONADA =126;
    private static final int CLAUDATOR = 91;
    
    private static final int ESC = 27;
    private static final int BACKSPACE = 8;
    private static final int INTRO = 13;


    public EditableBufferedReader(Reader in) {
        super(in);
        
    }

    public void setRaw() throws IOException {
        try{    
            String[] cmd = {"/bin/sh", "-c", "stty raw -echo </dev/tty"};
            Runtime.getRuntime().exec(cmd).waitFor();
        }catch (Exception e){

        }
        
    }

    public void unsetRaw() throws IOException {
        try{
            String[] cmd = {"/bin/sh", "-c", "stty -raw echo </dev/tty"};
            Runtime.getRuntime().exec(cmd).waitFor();
        }catch(Exception e){

        }
        
    }
    /*
     * DRETA -> ESC [ C
     * ESQUERRA -> ESC [ D      ^[[D
     * HOME -> ESC [ H
     * END -> ESC [ F
     * INSERT -> ESC [ 2 ~
     * DELETE -> ESC [ 3 ~
     *
     */
    public int read() throws IOException {
        int car = super.read();
        if(car == ESC){
            car = super.read();
            if(car== CLAUDATOR){
                car = super.read();
                switch(car) {
                    case NUM_END:
                        return END;
                    case NUM_HOME:
                        return HOME;
                    case NUM_ESQUERRA:
                        return ESQUERRA;
                    case NUM_DRETA:
                        return DRETA;
                    case 50: 
                        car = super.read();
                        if(car==ONADA){
                           return INS;
                        }else break;
                    case 51:
                        car = super.read();
                        if(car == ONADA){
                            return DEL;
                        }else break;
                }
            }
        }
        else{
            return car;
        }
        return -1; 
    }
    public String readLine() throws IOException{
        this.setRaw();
        Line linia = new Line();
        int car = 0;
        while(car!=INTRO){
            car = this.read();
            switch(car){
                case END:
                    linia.end();
                    break;
                case ESQUERRA:
                    linia.esquerra();
                    break;
                case DRETA:
                    linia.dreta();
                    break;
                case INS:
                    linia.ins();
                    break;
                case DEL:
                    linia.delete();
                    break;
                case HOME:
                    linia.home();
                    break;
                case BACKSPACE:
                    linia.backspace();
                    break;
                case INTRO:
                    break;
                default: linia.insert(car);
            }
        }
        this.unsetRaw();
        return linia.getLine();
    }
}
   

