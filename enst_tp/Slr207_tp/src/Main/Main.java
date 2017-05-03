package Main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.TreeMap;

public class Main {
	public static TreeMap<String, Integer> countDistinctWords(String s){
		String[] splitted = s.split("\\W+");
		TreeMap<String, Integer> hm = new TreeMap<String, Integer>();
		int x;
		
		for (int i=0; i<splitted.length ; i++) {
		   if (hm.containsKey(splitted[i])) {
		       x = ((Integer)hm.get(splitted[i])).intValue();
		       hm.put(splitted[i], new Integer(x+1));
		   }else{
			   hm.put(splitted[i], 1);  
		   }
		}
		return hm;
	}
	public static String readFile(String path) throws FileNotFoundException, IOException
	{
		try(BufferedReader br = new BufferedReader(new FileReader(path))) {
		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();

		    while (line != null) {
		        sb.append(line);
		    	sb.append(" ");
		        line = br.readLine();
		    }
		    String everything = sb.toString();
		    return everything;
		}
	}
	public static <K, V extends Comparable<V>> TreeMap<K, V> sortByValues(final TreeMap<K, V> map) {
	    Comparator<K> valueComparator =  new Comparator<K>() {
	        public int compare(K k1, K k2) {
	            int compare = map.get(k2).compareTo(map.get(k1));
	            if (compare == 0) return 1;
	            else return compare;
	        }
	    };
	    TreeMap<K, V> sortedByValues = new TreeMap<K, V>(valueComparator);
	    sortedByValues.putAll(map);
	    return sortedByValues;
	}
	public static void main(String[] args) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
//		String test = readFile("/Users/Nicolas/Development/eclipse/workspace/Slr207_tp/src/test.txt");
		String test = readFile("/Users/Nicolas/Development/eclipse/workspace/Slr207_tp/src/test.txt");
		System.out.println(test);
		System.out.println(countDistinctWords(test));
	}

}
