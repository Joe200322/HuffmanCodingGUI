import org.w3c.dom.ls.LSOutput;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main extends JFrame{
    private JPanel MainPanel;
    private JLabel label;
    private JButton compButton;
    private JButton decompButton;
    public Main(){
        setContentPane(MainPanel);
        setTitle("Standard Huffman Coding");
        setSize(250,250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        compButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filePath = chosseFile();
                Files f = new Files();
                ArrayList<String> data = new ArrayList<>();
                data = f.readTextFile(filePath);
                Huffman h = new Huffman(data.get(0));
                //Files.writeBinaryToFile(h.compress(),"compressed.BIN");
                //f.writeFile(h.compress()+"\n","compressed.bin",false);
                Map<Character,String> hCodes = new HashMap<>();
                hCodes = h.getHCodes();
                ArrayList<String> s = new ArrayList<>();
                String comp = h.compress();
                hCodes.forEach((character, code) ->
                        s.add(Files.stringToBinary(character.toString())+"00011100"+Files.stringToBinary(code)+"00011100")
                        //s.add(Integer.toBinaryString(Integer.parseInt(character.toString()))+"00011100"+code+"00011100")
//                        Files.writeBinaryToFile(character.toString()+" "+code,"compressed.BIN")

                );
                s.add("00011110");
                s.add(comp);
                StringBuilder allText = new StringBuilder();
                for(String str : s){
                    allText.append(str);
                }
                System.out.println(allText);
                System.out.println("\n");
                Files.writeBinaryStringToFile(allText.toString(),"compressed.BIN");
                JOptionPane.showMessageDialog(null,"Compressed Successfully in Compressed.txt file");
            }
        });
        decompButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filePath = chosseFile();
                ArrayList<String> data = new ArrayList<>();
                Map<String,Character>hCodes = new HashMap<>();
                data = Files.retrieveText(Files.readBinaryFile(filePath));
                for(int i = 0 ; i < data.size()-1;i++){
                    String BinCode = data.get(i).substring(9);
                    String code = "";
                    for(int j = 0;j<BinCode.length();j+=8){
                        code += (char)Integer.parseInt(BinCode.substring(j, j+8),2);
                    }
                    hCodes.put(code,(char)Integer.parseInt(data.get(i).substring(0, 8),2));
                }
                String encodedText = data.get(data.size()-1);
                Huffman huffman = new Huffman("");
                String deCompressed = huffman.deCompress(encodedText,hCodes);
                Files f = new Files();
                f.writeFile(deCompressed,"Decompressed.txt",false);
                JOptionPane.showMessageDialog(null,"Decompressed Successfully in Deompressed.txt file");
            }
        });
    }
    public String chosseFile(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("."));
        int response = fileChooser.showOpenDialog(null);
        if(response == JFileChooser.APPROVE_OPTION){
            File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
            System.out.println(file);
            return fileChooser.getSelectedFile().getAbsolutePath();
        }
        return "";
    }
    public static void main(String[] args) {
        new Main();
    }

}
