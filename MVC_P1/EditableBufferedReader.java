import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;





public class EditableBufferedReader extends BufferedReader{
    
    Line linia ;
    Console console;
    public EditableBufferedReader(Reader in) {
        super(in);
        this.linia = new Line();
        this.console = new Console(linia);
        this.linia.addObserver(this.console);
    }

    public void setRaw() throws IOException {
        try{    
            String[] cmd = {"/bin/sh", "-c", "stty raw -echo </dev/tty"};
            Runtime.getRuntime().exec(cmd);
        }catch (Exception e){} 
    }

    public void unsetRaw() throws IOException {
        try{
            String[] cmd = {"/bin/sh", "-c", "stty -raw echo </dev/tty"};
            Runtime.getRuntime().exec(cmd);
        }catch(Exception e){}    
    }
    /*
     * Cursor Right -> ESC [ C
     * Cursor Left -> ESC [ D      ^[[D
     * HOME -> ESC [ H
     * END -> ESC [ F
     * INSERT -> ESC [ 2 ~
     * SUPR -> ESC [ 3 ~
     *
     */
    public int read() throws IOException {
        int car = super.read();
        if(car == Constants.ESC){
            car = super.read();
            if(car== Constants.CLAUDATOR){
                car = super.read();
                switch(car) {
                    case 'F':
                        return Constants.END;
                    case 'H':
                        return Constants.HOME;
                    case 'D':
                        return Constants.ESQUERRA;
                    case 'C':
                        return Constants.DRETA;
                    case '2': 
                        car = super.read();
                        return Constants.INS;
                    case '3':
                        car = super.read();
                        return Constants.DEL;
                         
                    
                }
            }
        }
        else if(car == '^'){
            car=super.read();
            if(car == '?'){
                return Constants.BACKSPACE;
            }
        } 
        else{
            return car;
        }
        return -1; 
    }
    public String readLine() throws IOException{
        this.setRaw();
        
        int car = 0;
        while(car!=Constants.INTRO){
            car = this.read();
            switch(car){
                case Constants.END:
                    linia.end();
                    break;
                case Constants.ESQUERRA:
                    linia.esquerra();
                    break;
                case Constants.DRETA:
                    linia.dreta();
                    break;
                case Constants.INS:
                    linia.ins();
                    break;
                case Constants.DEL:
                    linia.delete();
                    break;
                case Constants.HOME:
                    linia.home();
                    break;
                case Constants.BACKSPACE:
                    linia.backspace();
                    break;
                case Constants.INTRO:
                    break;
                default: linia.insert(car);
            }
        }
        this.unsetRaw();
        return linia.getLine();
    }
}
   

