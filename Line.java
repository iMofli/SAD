import java.util.*;

public class Line {
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
        //this.setChanged();
    }

    public void dreta(){
        if(this.cursor<this.bufferline.size()){
            cursor++;
        }
    }
    public void end(){
        this.cursor=this.bufferline.size();
    }
    public void home(){
        this.cursor = 0;
    }
    public void delete(){
        if(cursor<this.bufferline.size()){
            this.bufferline.remove(this.cursor); 
        }
    }
    public void backspace(){
        if(cursor>0){
            bufferline.remove(cursor-1);
            
        }
    }
    public void insert(int element){
        if(insert){
            bufferline.add(cursor, element);  
        }else{
            if(this.bufferline.size()>=cursor){
                bufferline.set(cursor+1, element);
            }
        }
        cursor++;
    }
    public void ins(){
        this.insert = !insert;
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