package Main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

// address of tp: https://docs.google.com/document/d/1Gp2O1Vwg4UPUOhEr4Sw8uDVieLi3E_-ySaGXbsDKrvo/edit
public class Main {
	
	public static Boolean containPronoms(String source){
		Set<String> uniqueKeySet = new HashSet<String>(Arrays.asList("il","elle", "ils", "elles", "je",
				"tu", "vous", "nous", "on", "les", "leur", "lui", "la", "le", "l", "des", "de", "ou", 
				"celui", "celle", "celui-ci", "celui-là", "celle-ci", "celle-là", "ceci", "cela", "ça",
				"ceux", "ceux-ci", "ceux-là", "celles-ci", "celles-là", "tous", "tout", "rien", "personne",
				"et", "qui", "que", "ne", "aux", "dont", "au", "en", "à", "du", "d", "s"));
		if (uniqueKeySet.contains(source))
			return true;
		else
			return false;
		
	}
	
	public static TreeMap<String, Integer> countDistinctWords(String s, Boolean filterPro){
		String[] splitted = s.split(" ");
		TreeMap<String, Integer> hm = new TreeMap<String, Integer>();
		int x;
		
		for (int i=0; i<splitted.length ; i++) {
			if (filterPro && containPronoms(splitted[i]))
				continue;
		    if (hm.containsKey(splitted[i])) {
		        x = ((Integer)hm.get(splitted[i])).intValue();
		        hm.put(splitted[i], new Integer(x+1));
		    }else{
			    hm.put(splitted[i], 1);  
		    }
		}
		return hm;
	}
	
	public static TreeMap<String, Integer> countDistinctWords(String s){
		return countDistinctWords(s, false);
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
	
	public static TreeMap<String, Integer> putFirstEntries(int max, Map<String, Integer> map) {
		  int count = 0;
		  TreeMap<String, Integer> firstN = new TreeMap<String, Integer>();
		  for (Entry<String, Integer> entry:map.entrySet()) {
		     if (count >= max) break;

		     firstN.put(entry.getKey(), entry.getValue());
		     count++;
		  }
		  //it is necessary to do the sorting again, beacause the rule of sorting of TreeMap is by keys
		  TreeMap<String, Integer> sorted = new TreeMap<String, Integer>();
		  sorted = sortByValues(firstN);
		  return sorted;
	}
	
	public static void writeFile(String filename, TreeMap<String, Integer> res, long startTime, long readTime, long endTime) throws IOException{
		File fout = new File(filename);
		FileOutputStream fos = new FileOutputStream(fout);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		for(Entry<String, Integer> entry : res.entrySet()){
			bw.write(entry.getKey() + " = " + entry.getValue() + "\n");
		}
		bw.write("-----Time-----\n");
		bw.write("Time of Reading: " + (readTime - startTime)/1000. + "s\n");
		bw.write("Time of sorting: " + (endTime - readTime)/1000. + "s\n");
		bw.write("The total Time : " + (endTime - startTime)/1000. + "s\n");
		bw.close();
	}
	

	public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {
		// TODO Auto-generated method stub
		int question = 17;
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
			case 8:
				test = readFile("forestier_mayotte.txt");
				String filtered3 = filterCharacter(test);
				TreeMap<String, Integer> sorted1 = sortByValues(countDistinctWords(filtered3, true));
				TreeMap<String, Integer> firstN1 = putFirstEntries(50, sorted1);
				System.out.println(firstN1);
				break;
			case 9:
				test = readFile("deontologie_police_nationale.txt");
				String filtered4 = filterCharacter(test);
				TreeMap<String, Integer> sorted2 = sortByValues(countDistinctWords(filtered4, true));
				TreeMap<String, Integer> firstN2 = putFirstEntries(50, sorted2);
				System.out.println(firstN2);
				break;
			case 10:
				test = readFile("domaine_public_fluvial.txt");
				String filtered5 = filterCharacter(test);
				TreeMap<String, Integer> sorted3 = sortByValues(countDistinctWords(filtered5, true));
				TreeMap<String, Integer> firstN3 = putFirstEntries(50, sorted3);
				System.out.println(firstN3);
				break;
			case 11:
			case 12:
				test = readFile("sante_publique.txt");
				long startTime = System.currentTimeMillis();
				String filtered6 = filterCharacter(test);
				long filterTime   = System.currentTimeMillis();
				TreeMap<String, Integer> count4 = countDistinctWords(filtered6, true);
				long countTime   = System.currentTimeMillis();
				TreeMap<String, Integer> sorted4 = sortByValues(count4);
				long sortTime   = System.currentTimeMillis();
				TreeMap<String, Integer> firstN4 = putFirstEntries(50, sorted4);
				long totalTime   = System.currentTimeMillis();
				System.out.println(firstN4);
				System.out.println(filterTime - startTime);
				System.out.println(countTime - startTime);
				System.out.println(sortTime - startTime);
				System.out.println(totalTime - startTime);
				break;
			case 16:
			case 17:
				startTime = System.currentTimeMillis();
				int numThread = 4;
				String fileP = "CC-MAIN-20170322212949-00140-ip-10-233-31-227.ec2.internal.warc.wet";
				CountParallel[] counters= new CountParallel[numThread];
				for (int i=0;i<numThread;i++){
					counters[i] =new CountParallel(fileP, numThread);
				}
				for (int i=0;i<numThread;i++){
					counters[i].start();
				}
				for (int i=0;i<numThread;i++){
					counters[i].join();
				}
				TreeMap<String, Integer> res = counters[0].getResult();
				long readTime = System.currentTimeMillis();
				for (int i=1;i<numThread;i++){
					TreeMap<String, Integer> tmp = counters[i].getResult();
					for (String k : tmp.keySet()) {
					    if (res.containsKey(k))
					    	res.put(k, res.get(k) + tmp.get(k));
					    else
					    	res.put(k, tmp.get(k));
					    	
					} 
				}
				long mergeTime = System.currentTimeMillis();
				TreeMap<String, Integer> sorted11 = putFirstEntries(50, sortByValues(res));
				long endTime = System.currentTimeMillis();
				System.out.println("Time of Reading: " + (readTime - startTime)/1000. + "s");
				System.out.println("Time of merging: " + (mergeTime - readTime)/1000. + "s");
				System.out.println("Time of sorting: " + (endTime - mergeTime)/1000. + "s");
				System.out.println("The total Time : " + (endTime - startTime)/1000. + "s");
				System.out.println(sorted11);
				writeFile("question13.txt", sorted11, startTime, readTime, endTime);
				break;

		}
	}

}
