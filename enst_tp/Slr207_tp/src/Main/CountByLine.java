package Main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Comparator;
import java.util.TreeMap;
import java.util.Map.Entry;

public class CountByLine {
	
	public static TreeMap<String, Integer> countDistinctWords(TreeMap<String, Integer> hm, String s){
		String[] splitted = s.split(" ");
		int x = 0;	
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
		String resultString = s.replaceAll("[^A-Za-z]", " ");
		return resultString;
	}
	
	public static TreeMap<String, Integer> readFile(String path) throws FileNotFoundException, IOException
	{
		TreeMap<String, Integer> hm = new TreeMap<String, Integer>();
		try(BufferedReader br = new BufferedReader(new FileReader(path))) {
		    StringBuilder sb = new StringBuilder();
		    StringBuilder sb2 = new StringBuilder();  
		    String line = br.readLine();
		    
		    while (line != null) {
		        sb.append(line);
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
		        line = sb2.toString();
		        sb.setLength(0);
		        sb2.setLength(0);
		        line = filterCharacter(line);
		        hm = countDistinctWords(hm, line);
		        line = br.readLine();
		    }	 
		    hm.remove("");
		    return hm;
		}
		
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

	public static void main(String[] args) throws FileNotFoundException, IOException {
		long startTime = System.currentTimeMillis();
		TreeMap<String, Integer> test = readFile("CC-MAIN-20170322212949-00140-ip-10-233-31-227.ec2.internal.warc.wet");
		long readTime = System.currentTimeMillis();
		TreeMap<String, Integer> sorted = Main.putFirstEntries(50, Main.sortByValues(test));
		long endTime = System.currentTimeMillis();
		System.out.println("Time of Reading: " + (readTime - startTime)/1000. + "s");
		System.out.println("Time of sorting: " + (endTime - readTime)/1000. + "s");
		System.out.println("The total Time : " + (endTime - startTime)/1000. + "s");
		System.out.println(sorted);
		writeFile("question13.txt", sorted, startTime, readTime, endTime);
	}
}
