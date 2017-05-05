package lab4;


import java.io.IOException;

class Main {

    public static void main(String[] args) {
        String[] files = {"testinput1.txt", "testinput2.txt", "testinput3.txt"};
        InMapperWordCount wordCount = new InMapperWordCount(files, 4);
        try {
            wordCount.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}