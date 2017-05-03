package Main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class Main {
	public static TreeMap<String, Integer> countDistinctWords(String s){
		String[] splitted = s.split(" ");
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

	public static String filterCharacter(String s){
		String resultString = s.replaceAll("[^\\p{L}\\p{Nd}]+", " ");
		return resultString;
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
		    StringBuffer sb2 = new StringBuffer();  
		    if(sb!=null){  
		        for(int i=0;i<sb.length();i++){  
		            char c = sb.charAt(i);  
		            if(Character.isUpperCase(c)){  
		                sb2.append(Character.toLowerCase(c));  
		            }else{  
		                sb2.append(c);   
		            }  
		        }  
		    }  
		    return sb2.toString();
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
	
	public static TreeMap<String, Integer> putFirstEntries(int max, TreeMap<String, Integer> source) {
		  int count = 0;
		  TreeMap<String, Integer> firstN = new TreeMap<String, Integer>();
		  for (Entry<String, Integer> entry:source.entrySet()) {
		     if (count >= max) break;

		     firstN.put(entry.getKey(), entry.getValue());
		     count++;
		  }
		  //it is necessary to do the sorting again, beacause the rule of sorting of TreeMap is by keys
		  TreeMap<String, Integer> sorted = new TreeMap<String, Integer>();
		  sorted = sortByValues(firstN);
		  return sorted;
	}
	public static void main(String[] args) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		int question = 6;
		String test = null;
		switch(question){
			case 1:
				test = readFile("test.txt");
				System.out.println(countDistinctWords(test));
				break;
			case 3: //question 2 and 3 together, because Treemap is sorted by keys automatically
				test = readFile("test.txt");
				System.out.println(sortByValues(countDistinctWords(test)));
				break;
			case 4:
				test = readFile("forestier_mayotte.txt");
				System.out.println(test);
				System.out.println(sortByValues(countDistinctWords(test)));
				break;
			case 5:
				test = readFile("forestier_mayotte.txt");
				String filtered = filterCharacter(test);
				System.out.println(filtered);
				System.out.println(sortByValues(countDistinctWords(filtered)));
				break;
			case 6:
				test = readFile("forestier_mayotte.txt");
				String filtered2 = filterCharacter(test);
				TreeMap<String, Integer> sorted = sortByValues(countDistinctWords(filtered2));
				TreeMap<String, Integer> firstN = putFirstEntries(50, sorted);
				System.out.println(firstN);
				break;
			case 7:
				
		}
	}

}
