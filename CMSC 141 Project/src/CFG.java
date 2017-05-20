import java.util.*;
import java.lang.*;
import java.io.*;

public class CFG {	
	//datastruct for productions
	HashMap<String, ArrayList<String>> prod = new HashMap<String, ArrayList<String>>();	
	List<String> split_rhs;
	ArrayList<String> lhs_values;	
	char category;

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
		// System.out.println(" >>>>> POSSIBLE PRODUCTIONS ARE " + randomProd);
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
				if(split[1].equals("NN")) {
					if(split[2].charAt(0) == this.category) {
						System.out.println(split[0] + " is in category " + split[2]);
						addProd(split[0], split[1]);
					} else {
						if(this.category == 'N') {
							//if the category doesn't belong to any of the given
							addProd(split[0], split[1]);
						}
					}
				} else {
					addProd(split[0], split[1]);
				}
			}		
		} 
		catch (IOException e){
			e.printStackTrace();
		}
	}

	public static boolean contains(char c, char[] array) {
	    for (char x : array) {
	        if (x == c) {
	            return true;
	        }
	    }
	    return false;
	}

	public static void main(String[] args) {
		int flag = 0;
		char categories[] = {'A', 'B', 'F', 'G', 'H', 'I', 'K', 'L', 'M',
							 'O', 'S', 'T', 'W', 'X'};

		System.out.println("What category?");
		System.out.println("A - General & Abstract Terms");
		System.out.println("B - The Body & The Individual");
		System.out.println("F - Food & Farming");
		System.out.println("G - Govt. & The Public Domain");
		System.out.println("H - Architecture, Buildings, Houses & The Home");
		System.out.println("I - Money & Commerce");
		System.out.println("K - Entertainment, Sports & Games");
		System.out.println("L - Life & Living Things");
		System.out.println("M - Movement, Location, Travel & Transport");
		System.out.println("O - Substances, Materials, Objects");
		System.out.println("S - Social Actions, States & Processes");
		System.out.println("T - Time");
		System.out.println("W - The World & Our Environment");
		System.out.println("X - Psychological Actions, States & Processes");
		System.out.println("Other characters --- purely random");
		Scanner s = new Scanner(System.in);
		char category = s.next().charAt(0);

		if(!contains(category, categories)) {
			category = 'N';
			//N meaning no category, or any category
		}

		//new instance 
		CFG cfg1 = new CFG();
		cfg1.category = category;
		System.out.println("category is " + cfg1.category);
		cfg1.readRules();
		cfg1.readCorpus();

		// System.out.println(cfg1.generateRandom("S"));

		for(int i = 0; i < 1; i++) {
			System.out.println("");
			System.out.println(cfg1.generateRandom("S"));
		}
	}	

}