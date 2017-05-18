import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class CFG {
	
	// new datatype for production
	HashMap<String, List<String>> prod = new HashMap<String, List<String>>();

	// constructor
	CFG() {
		// do nothing
	}
	
	public void add_prod(String lhs, String rhs) {
		// several productions are separated by '|'
		List<String> split_rhs = Arrays.asList(rhs.split("\\|"));
		
		// split 
		for(int i = 0; i < split_rhs.size(); i++) {
			prod.put(lhs, split_rhs);
		}
		
		// print to check
		// System.out.println(prod.entrySet());
			
	}
	
	public String gen_random(String symbol) {
		String sentence = "";
		
		Random rand = new Random();
		
		List<String> rand_prod = prod.get(symbol);
		
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
		
		cfg1.add_prod("S", "NP VP");
		cfg1.add_prod("NP", "Det N|Det N|I|he|she|Joe");
		cfg1.add_prod("VP", "V NP|VP");
		cfg1.add_prod("Det", "a|the|my|his");
		cfg1.add_prod("N", "elephant|cat|jeans|suit");
		cfg1.add_prod("V", "kicked|followed|shot");
		
		// System.out.println(cfg1.gen_random("S"));
		
		// generate 10 sentences
		for(int i = 0; i < 10; i++) {
			System.out.println(cfg1.gen_random("S"));
		}
	}

}
