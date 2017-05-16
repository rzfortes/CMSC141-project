/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package listofwords;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author MIS
 */
public class ListofWords {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        List tags = new ArrayList<String>();
        try {
            FileReader fileReader = new FileReader("partsofspeech.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = null;
            while((line = bufferedReader.readLine()) != null) {
                tags.add(line);
            }
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file corpus.txt");   
        }
        catch(IOException ex) {
            System.out.println("Error reading file corpus.txt");    
        }
        
        try {
            String line = null;
            String left = null;
            String right = null;
            String tocompare = null;
            for(int i = 0; i < tags.size(); i++) {
                tocompare = tags.get(i).toString();
                FileReader fileReader = new FileReader("corpus.txt");
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                File fout = new File(tocompare + ".txt");
                FileOutputStream fos = new FileOutputStream(fout);
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
                while((line = bufferedReader.readLine()) != null) {
                    left = line.substring(0, line.indexOf(','));
                    right = line.substring(line.indexOf(',')+1, line.length());
                    if(right.equals(tocompare)) {
                        bw.append(left);
                        bw.newLine();
                    }
                }
                bufferedReader.close();
                bw.close();
            }
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file corpus.txt");   
        }
        catch(IOException ex) {
            System.out.println("Error reading file corpus.txt");    
        }
    }
    
}
