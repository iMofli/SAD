import java.util.*;
import java.util.Observer;
import java.util.Observable;

public class Line extends Observable {
    boolean insert;
    int cursor;
    // int length;
    ArrayList<Integer> bufferline;

    public Line() {
        this.cursor=0;
        this.insert=true;
        this.bufferline=new ArrayList<>();
    }

    public void esquerra(){
        if(this.cursor>0){
            cursor--;
        }
        this.setChanged();
        this.notifyObservers(Constants.ESC);
    }

    public void dreta(){
        if(this.cursor<this.bufferline.size()){
            cursor++;
        }
        this.setChanged();
        this.notifyObservers(Constants.DRETA);

    }
    public void end(){
        this.cursor=this.bufferline.size();
        this.setChanged();
        this.notifyObservers(Constants.END);
    }
    public void home(){
        this.cursor = 0;
        this.setChanged();
        this.notifyObservers(Constants.HOME);
    }
    public void delete(){
        if(cursor<this.bufferline.size()){
            this.bufferline.remove(this.cursor); 
        }
        this.setChanged();
        this.notifyObservers(Constants.DEL);
    }
    public void backspace(){
        if(cursor>0){
            bufferline.remove(cursor-1);
            cursor--;
            
        }
        this.setChanged();
        this.notifyObservers(Constants.BACKSPACE);
    }
    public void insert(int element){
        if(insert){
            bufferline.add(cursor, element);  
        }else{
            if(cursor<bufferline.size()) bufferline.set(cursor, element);
            else bufferline.add(cursor,element);
        }
        cursor++;
        this.setChanged();
        this.notifyObservers(element);
    }
    public void ins(){
        this.insert = !insert;
        this.setChanged();
        this.notifyObservers(Constants.INS);
    }

    public String getLine(){
        int i = 0;
        String str = "";
        while(i<bufferline.size()){
            int b=bufferline.get(i);
            str= str +(char)b;
           i++;
        }
        return str;
    }
    
}