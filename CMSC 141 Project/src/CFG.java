import java.util.*;
import java.lang.*;
import java.io.*;

public class CFG {	
	//datastruct for productions
	HashMap<String, ArrayList<String>> prod = new HashMap<String, ArrayList<String>>();	
	List<String> split_rhs;
	ArrayList<String> lhs_values;	

	//datastruct for storing rules
	ArrayList <ArrayList <String>> angProd = new ArrayList <ArrayList <String>> ();
	ArrayList <String> list = new ArrayList <String> (); 

	//datastruct for generating random
	List<String> randomProd;
	

	public void addProd(String rhs, String lhs) {
		// System.out.println("RHS "+rhs);
		// System.out.println("LHS "+lhs);
		// several productions are separated by '|'
		split_rhs = Arrays.asList(rhs.split("\\|"));
		
		// if lhs already exists as a key, get its values and append nalang the new set of rhs
		lhs_values = prod.get(lhs);
		if(prod.containsKey(lhs)) {
			for(int i = 0; i < split_rhs.size(); i++) {
				lhs_values.add(split_rhs.get(i));
			}
		} 
		else { // put the new key and its values
			lhs_values = new ArrayList<>();
			for(int i = 0; i < split_rhs.size(); i++) {
				lhs_values.add(split_rhs.get(i));
			}
			prod.put(lhs, lhs_values);
		}
		
		// print to check entry set
		// System.out.println(prod.entrySet());	
		// System.out.println(prod);		
	}
	public String generateRandom(String symbol) {
		String sentence = "";
		
		Random rand = new Random();
		
		randomProd = prod.get(symbol);

		// System.out.println(symbol);
		// System.out.println(prod);		
		// System.out.println(randomProd.size());
		
		int r = rand.nextInt(randomProd.size());

		String str = randomProd.get(r);

		if(str.contains(" ")) {
			String split_process[] = str.split("\\s+");
			for(int i = 0; i < split_process.length; i++) {
				if(prod.keySet().contains(split_process[i])) {
					sentence += generateRandom(split_process[i]);
				} else {
					sentence += split_process[i] + " ";
				}
			}
		} else {

			if(prod.keySet().contains(str)) {
					sentence += generateRandom(str);
			} else {
					sentence += str + " ";
			}
		}
		
		return sentence;
	}

	//read and tokenize the rules
	public void readRules() {
		String rules = "rules.txt";
        BufferedReader input = null;   
        String line;
        int flag = 0;
        int limit = 1;       
        try {
            input = new BufferedReader(new FileReader(rules));
            line = input.readLine();  
            while (line != null) { 
                if(!(line.isEmpty())) {
                    StringTokenizer stk = new StringTokenizer(line, ",");
                    while(stk.hasMoreTokens()) {
                        String token=stk.nextToken();                                              
                        if(flag < limit) {                      	
                        	list.add(token);
                        }
                        else {
                        	list.add(token);                        	
                        	angProd.add(list);
                        	list = new ArrayList <String> ();   
                        	flag = -1;
                        }
                    	flag++;                                                                  	
                    }         
                }              
                line = input.readLine(); 
            }  
        }
        catch (Exception e) {
            System.err.println(e);  
            return;  
        } 
        for(int i = 0; i < angProd.size(); i++) {
        	ArrayList <String> temp = angProd.get(i);
        	String value = temp.get(1);
        	value = value.replace(" ","");
        	String key = temp.get(0);
        	addProd(key,value);
        }        
	}

	public void readCorpus() {
		// read corpus.txt
		String corpus = "corpus.txt";
		try (BufferedReader br = new BufferedReader(new FileReader(corpus))){
			String currentLine = "";
			while((currentLine = br.readLine()) != null) {
				// System.out.println(currentLine);
				String split[] = currentLine.split(",");
				addProd(split[0], split[1]);
			}		
		} 
		catch (IOException e){
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		int flag = 0;

		//new instance 
		CFG cfg1 = new CFG();
		cfg1.readRules();
		cfg1.readCorpus();

		// System.out.println(cfg1.generateRandom("S"));
		for(int i = 0; i < 11; i++) {
			System.out.println(cfg1.generateRandom("S"));
		}
	}	

}