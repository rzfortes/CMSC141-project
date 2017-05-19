/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package createcorpus;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 *
 * @author MIS
 */
public class CreateCorpus {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        // TODO code application logic here
        String terminals[] = {"NN", "ADJ", "VRB", "PREP", "FOR", "FROM", 
                              "NNP", "PZ", "NNI", "LK"};
        
        File fout = new File("corpus.txt");
        FileOutputStream fos = new FileOutputStream(fout);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        
        for(int i = 0; i < terminals.length; i++) {
            FileReader fileReader = new FileReader(terminals[i] + ".txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            
            while((line=bufferedReader.readLine())!= null) {
                bw.append(line + ',' + terminals[i]);
                bw.newLine();
            }

            bufferedReader.close();
        }
        
        bw.close();
    }
    
}
