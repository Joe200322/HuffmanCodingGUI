import java.util.*;

import static java.util.Objects.requireNonNull;

public class Huffman {
    private Node root;
    private final String text;
    private Map<Character,Integer> freqs; // to store the frequency of each character
    private final Map<Character,String> hCodes; // to store the code of each character after applying huffman algorithm
    public Huffman(String t){
        this.text =t;
        getFreqs();
        hCodes = new HashMap<>();
    }
    private void getFreqs(){ // to calculate the frequency of each character
        freqs = new HashMap<>();
        for(int i = 0;i< text.length();i++){
            char c = text.charAt(i);
            freqs.put(c,freqs.getOrDefault(c,0)+1);
        }
    }
    public String compress(){
        Queue<Node> q = new PriorityQueue<>();
        freqs.forEach((c , f) -> q.add(new Leaf(c,f)));
        while(q.size() > 1){
            q.add(new Node(q.poll(),requireNonNull(q.poll())));
        }

        makeHuffmanCode(root = q.poll(),"");
        return getCompressed();
    }/*
    public void buildTree(Map<Character,Integer> freq){
        Queue<Node> q = new PriorityQueue<>();
        freq.forEach((c , f) -> q.add(new Leaf(c,f)));
        while(q.size() > 1){
            q.add(new Node(q.poll(),requireNonNull(q.poll())));
        }
        root = q.poll();
    }
    */

    private void makeHuffmanCode(Node node,String code){
        if(node instanceof Leaf l){ // if it is leaf that means it is one of the characters
            hCodes.put(l.getChar(),code);
            return;
        }
        makeHuffmanCode(node.getLeftNode(),code.concat("0"));
        makeHuffmanCode(node.getRightNode(),code.concat("1"));
    }
    //001100101010001
    public String deCompress(String encodedText,Map<String,Character> codes){
        StringBuilder deCompressed = new StringBuilder();
        String s ="";
        for(int i = 0;i<encodedText.length();i++){
            s += encodedText.charAt(i);
            if(codes.containsKey(s)){
                deCompressed.append(codes.get(s));
                s="";
            }
        }
        return deCompressed.toString();
    }
    private String getCompressed(){
        StringBuilder s = new StringBuilder();
        for(char character : text.toCharArray()){
            s.append(hCodes.get(character));
        }
        return s.toString();
    }
    public void printCodes() {
        hCodes.forEach((character, code) ->
                System.out.println(character + ": " + code)
        );
    }
    public int getSize(){return hCodes.size();}
    public Map<Character,String> getHCodes(){return hCodes;}
}
