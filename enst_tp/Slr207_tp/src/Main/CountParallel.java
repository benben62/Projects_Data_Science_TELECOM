package Main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.TreeMap;

public class CountParallel extends Thread{
	String path = null;
	static int num = 0;
	int id = 0;
	int totalNumThread = 0;
	TreeMap<String, Integer> hm = null;
	
	public CountParallel(String path, int totalNumThread){
		id = num;
		num++;
		this.totalNumThread = totalNumThread;
		this.path = path;
		this.hm = new TreeMap<String, Integer>();
	}
	
	public void run() {
		try {
			readFile(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	public TreeMap<String, Integer> countDistinctWords(String s){
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
	
	public String filterCharacter(String s){
		String resultString = s.replaceAll("[^A-Za-z]", " ");
		return resultString;
	}
	
	public TreeMap<String, Integer> readFile(String path) throws FileNotFoundException, IOException
	{
		TreeMap<String, Integer> hm = new TreeMap<String, Integer>();
		try(BufferedReader br = new BufferedReader(new FileReader(path))) {
		    StringBuilder sb = new StringBuilder();
		    StringBuilder sb2 = new StringBuilder();
		    String line = null;
		    for (int i=0;i<=id;i++){
		    	line = br.readLine();
		    }
		    
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
		        hm = countDistinctWords(line);
			    for (int i=0;i<totalNumThread;i++){
			    	line = br.readLine();
			    }
			}	 
		    hm.remove("");
		    return hm;
		}
		
	}

	public TreeMap<String, Integer> getResult(){
		return hm;
	}
	

}
