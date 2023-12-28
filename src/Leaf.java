public class Leaf extends Node{
    private final char Char;
    public Leaf (char c,int f){
        super(f);
        this.Char = c;

    }
    public char getChar(){return Char;}
}
