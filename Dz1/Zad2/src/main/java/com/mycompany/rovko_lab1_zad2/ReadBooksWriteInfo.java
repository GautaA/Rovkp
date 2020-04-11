package com.mycompany.rovko_lab1_zad2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class ReadBooksWriteInfo {
    
    public static final int INDEX_KNJIGA = 0;
    public static final int INDEX_REDAKA = 1;
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        long startTime = System.currentTimeMillis();
        
        FileWriter fWriter = new FileWriter("gutenberg_books.txt");
        
        List<Integer> booksAndLines = countBooksAndLines(new File("C:\\Users\\anton\\Desktop\\Fer\\gutenberg"), fWriter);
        
        fWriter.close();
        
        System.out.println("Broj knjiga: " + booksAndLines.get(INDEX_KNJIGA));
        System.out.println("Broj redaka: " + booksAndLines.get(INDEX_REDAKA));
       
        System.out.println("Program se izvodio " + (System.currentTimeMillis() - startTime) + " ms");
    }
    
    public static ArrayList<Integer> countBooksAndLines(File file, FileWriter fWriter) throws FileNotFoundException, IOException {
        ArrayList <Integer> count = new ArrayList<>(); 
        for (File currentFile : file.listFiles()) {
            if (currentFile.isDirectory()) {
                ArrayList<Integer> tempCount = countBooksAndLines(currentFile,fWriter);
                if (count.isEmpty()) {
                    count.add(INDEX_KNJIGA, tempCount.get(INDEX_KNJIGA));
                    count.add(INDEX_REDAKA, tempCount.get(INDEX_REDAKA));
                } else {
                    count.set(INDEX_KNJIGA, tempCount.get(INDEX_KNJIGA) + count.get(INDEX_KNJIGA));
                    count.set(INDEX_REDAKA, tempCount.get(INDEX_REDAKA) + count.get(INDEX_REDAKA));
                }
            } else {
                
                BufferedReader reader = new BufferedReader(new FileReader(currentFile));
                int lines = 0;
                String currentLine;
                while ((currentLine = reader.readLine()) != null) {
                    fWriter.write(currentLine + "\n");
                    lines++;
                }
                reader.close();
                
                if (count.isEmpty()) {
                    count.add(INDEX_KNJIGA, 1);
                    count.add(INDEX_REDAKA, lines);
                } else {
                    count.set(INDEX_KNJIGA, count.get(INDEX_KNJIGA) + 1);
                    count.set(INDEX_REDAKA, count.get(INDEX_REDAKA) + lines);
                }
                
            }
        }
        return count;
    }
}
