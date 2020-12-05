import java.io.IOException;
import java.util.Observer;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Console implements Observer{
    private Line line;
    
    public Console(Line line){
        this.line=line;
    }

    @Override
    public void update(Observable o, Object arg){
        try {
            Runtime.getRuntime().exec("clear");
        } catch (IOException ex) {
            Logger.getLogger(Console.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
