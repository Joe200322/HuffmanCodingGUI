import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Files {
    public boolean createFile(String fileName){
        try{
            File newFile = new File(fileName);
            if(!newFile.createNewFile()){
                return false;
            }
        }catch(IOException error){
            return false;
        }
        return true;
    }
    public void writeFile(String input,String filePath,boolean append){
        File f = new File(filePath);
        if(!append){
            f.delete();
            try {
                f.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        String newFilePath = f.getAbsolutePath();
        String newFileName = f.getName();

        BufferedWriter newFile;
        try {
            newFile = new BufferedWriter(new FileWriter(newFilePath,true));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            newFile.write(input);
            newFile.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public ArrayList<String> readTextFile(String fileName){
        ArrayList<String> data = new ArrayList<>();
        try {
            File file = new File(fileName);
            Scanner reader = new Scanner(file);
            while(reader.hasNextLine()){
                String line = reader.nextLine();
                data.add(line);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return data;
    }
    public static String stringToBinary(String input) {
        byte[] bytes = input.getBytes();
        StringBuilder binaryStringBuilder = new StringBuilder();

        for (byte b : bytes) {
            int value = b;

            for (int i = 7; i >= 0; i--) {
                binaryStringBuilder.append((value & (1 << i)) == 0 ? '0' : '1');
            }
        }

        return binaryStringBuilder.toString();
    }
    public static void writeBinaryStringToFile(String binaryString, String filePath) {
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            // Convert the binary string to bytes
            byte[] byteArray = binaryStringToByteArray(binaryString);

            // Write the bytes to the file
            fos.write(byteArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static byte[] binaryStringToByteArray(String binaryString) {
        int length = binaryString.length();
        byte[] byteArray = new byte[length / 8];

        for (int i = 0; i < length; i += 8) {
            String byteString = binaryString.substring(i, Math.min(i + 8, length));
            byte b = (byte) Integer.parseInt(byteString, 2);
            byteArray[(i / 8)] = b;
        }

        return byteArray;
    }
    public static String readBinaryFile(String filePath) {
        StringBuilder binaryStringBuilder = new StringBuilder();

        try (FileInputStream fis = new FileInputStream(filePath)) {
            int data;

            while ((data = fis.read()) != -1) {
                // Convert each byte to a binary string and append to the StringBuilder
                binaryStringBuilder.append(String.format("%8s", Integer.toBinaryString(data & 0xFF)).replace(' ', '0'));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return binaryStringBuilder.toString();
    }
    public static String byteArrayToBinaryString(byte[] byteArray) {
        StringBuilder binaryStringBuilder = new StringBuilder();

        for (byte b : byteArray) {
            // Convert each byte to an 8-character binary string
            binaryStringBuilder.append(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));
        }

        return binaryStringBuilder.toString();
    }
    public static String binaryStringToString(String binaryString) {
        StringBuilder stringBuilder = new StringBuilder();

        // Split the binary string into chunks of 8 characters
        for (int i = 0; i < binaryString.length(); i += 8) {
            String binaryChunk = binaryString.substring(i, Math.min(i + 8, binaryString.length()));

            // Convert each binary chunk to its decimal equivalent and then to char
            int decimalValue = Integer.parseInt(binaryChunk, 2);
            char charValue = (char) decimalValue;

            stringBuilder.append(charValue);
        }

        return stringBuilder.toString();
    }
    public static ArrayList<String> retrieveText(String binaryString){
        String character = "";
        String hCode = "";
        String encodedText = "";
        int i = 0;
        ArrayList<String> Text = new ArrayList<>();
        while(true){
            character = binaryString.substring(0 , 8);
            int l = binaryString.length();

            binaryString = binaryString.substring(16,l);
            int startIndex = findPatternIndex(binaryString,"00011100");
            hCode = binaryString.substring(0,startIndex);
            l = binaryString.length();
            binaryString = binaryString.substring(startIndex+8);
            l = binaryString.length();
            //binaryString = binaryString.substring(0,l);
            Text.add(character+" "+hCode);
            character = "";
            hCode = "";
            if(binaryString.substring(0, 8).equals("00011110")){
                binaryString = binaryString.substring(8);
                break;
            }
        }
        Text.add(binaryString);
        return Text;

    }
    private static int findPatternIndex(String inputString, String pattern) {
        int inputLength = inputString.length();
        int patternLength = pattern.length();

        for (int i = 0; i <= inputLength - patternLength; i++) {
            // Check if the substring starting at index i matches the pattern
            if (inputString.substring(i, i + patternLength).equals(pattern)) {
                return i; // Return the start index if a match is found
            }
        }

        return -1; // Return -1 if the pattern is not found in the string
    }
}
