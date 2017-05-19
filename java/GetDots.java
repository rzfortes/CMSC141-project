/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package getdots;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MIS
 */
public class GetDots {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try {
            FileReader fileReader = new FileReader("VRB.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            File fout = new File("VRB2.txt");
            FileOutputStream fos = new FileOutputStream(fout);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
            String line;
            int counter = 0;
            while((line = bufferedReader.readLine())!=null) {
                if(line.contains(".")) {
                    bw.write(line.substring(0, line.length()-1));
                    bw.newLine();
                    counter++;
                }
            }
            System.out.println("You've added " + counter + "words");
            bufferedReader.close();
            bw.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file corpus.txt");   
        }
        catch(IOException ex) {
            System.out.println("Error reading file corpus.txt");    
        }
    }
}
