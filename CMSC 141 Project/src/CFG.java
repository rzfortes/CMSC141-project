import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class CFG {
	
	// new datatype for production
	HashMap<String, ArrayList<String>> prod = new HashMap<String, ArrayList<String>>();

	// constructor
	CFG() {
		// do nothing
	}
	
	public void add_prod(String rhs, String lhs) {
		// several productions are separated by '|'
		List<String> split_rhs = Arrays.asList(rhs.split("\\|"));
		
		// if lhs already exists as a key, get its values and append nalang the new set of rhs
		ArrayList<String> lhs_values = prod.get(lhs);
		if(prod.containsKey(lhs)) {
			for(int i = 0; i < split_rhs.size(); i++) {
				lhs_values.add(split_rhs.get(i));
			}
		} else { // put the new key and its values
			lhs_values = new ArrayList<>();
			for(int i = 0; i < split_rhs.size(); i++) {
				lhs_values.add(split_rhs.get(i));
			}
			prod.put(lhs, lhs_values);
		}
		
		// print to check entry set
//		 System.out.println(prod.entrySet());
			
	}
	
	public String gen_random(String symbol) {
		String sentence = "";
		
		Random rand = new Random();
		
		List<String> rand_prod = prod.get(symbol);
		
//		System.out.println(rand_prod.size());
		
		int r = rand.nextInt(rand_prod.size());

		String str = rand_prod.get(r);

		if(str.contains(" ")) {
			String split_process[] = str.split("\\s+");
			for(int i = 0; i < split_process.length; i++) {
				if(prod.keySet().contains(split_process[i])) {
					sentence += gen_random(split_process[i]);
				} else {
					sentence += split_process[i] + " ";
				}
			}
		} else {

			if(prod.keySet().contains(str)) {
					sentence += gen_random(str);
			} else {
					sentence += str + " ";
			}
		}
		
		return sentence;
	}
	
	public static void main(String[] args) {
		
		CFG cfg1 = new CFG();
		//		S -> NN + ang + NN
		//		S -> ADV + ang + NN
		//		S -> PP + ang + NN
		//		S -> VRB + SP + PP
		//		S -> VRB + SP + AP
		//		S -> VRB + C + SP
		//		S -> VRB + SP
		//		S -> VRB + O + SP
		//		S -> VRB + SP + DO + IO
		//		S -> VRB + OC + O + SP
		//		S -> VRB + SP + O + OC
		//		S -> VRB + SP + O + PP
		cfg1.add_prod("NN si NN", "S");
		cfg1.add_prod("Ang NN NN", "S");
		cfg1.add_prod("ADJ si NN", "S");
		cfg1.add_prod("ADV si NN", "S");
		cfg1.add_prod("ADJ ang NN", "S");
		cfg1.add_prod("VRB SP AP", "S");
		cfg1.add_prod("NN|NNP", "SP");
		cfg1.add_prod("og ADJ|ADV", "AP");
		
		// read corpus.txt
		try (BufferedReader br = new BufferedReader(new FileReader("corpus.txt"))){
			
			String currentLine = "";
			
			while((currentLine = br.readLine()) != null) {
//				System.out.println(currentLine);
				String split[] = currentLine.split(",");
				cfg1.add_prod(split[0], split[1]);
			}
			
		} catch (IOException e){
			e.printStackTrace();
		}

		
//		 System.out.println(cfg1.gen_random("S"));
		
		// generate 10 sentences
		for(int i = 0; i < 10; i++) {
			System.out.println(cfg1.gen_random("S"));
		}
	}

}
