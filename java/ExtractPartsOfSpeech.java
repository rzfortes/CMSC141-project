/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package extractpartsofspeech;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.AbstractSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author MIS
 */
public class ExtractPartsOfSpeech {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Set listofparts = new HashSet<String>();
         try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader("corpus.txt");
            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            // Reads per line from the file
            
            File fout = new File("partsofspeech.txt");
            FileOutputStream fos = new FileOutputStream(fout);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
            
            String line = null;
            String partofspeech = "";
            while((line = bufferedReader.readLine()) != null) {
                partofspeech = line.substring(line.indexOf(',')+1, line.length());
                System.out.println("checking.." + partofspeech);
                listofparts.add(partofspeech);
            }
            String item = "";
            for(Iterator<String> i = listofparts.iterator(); i.hasNext();) {
                item = i.next();
                bw.append(item);
                bw.newLine();
            }
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
