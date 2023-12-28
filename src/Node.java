public class Node implements Comparable<Node>{
    private final int freq;
    private Node leftNode;
    private Node rightNode;

    public Node(Node lNode,Node rNode){
        this.leftNode = lNode;
        this.rightNode = rNode;
        this.freq = leftNode.getFreq() + rightNode.getFreq();
    }
    public Node(int f){
        this.leftNode = null;
        this.rightNode = null;
        this.freq = f;
    }
    public int getFreq(){return freq;}
    public Node getLeftNode(){return leftNode;}
    public Node getRightNode(){return rightNode;}
    @Override
    public int compareTo(Node node){
        return Integer.compare(freq,node.getFreq());
    }
}
