package lab2;

import java.util.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Mapper<K, V> {
	
	private List<KeyValuePair<K, V>> words;
	
	 private String regSpace = "\\s+";
	 private String regReplace = "[\\.\\,\\(\\)\\\"]";
	 private String regWord = "[a-zA-Z]+";
	    
	
	 public List<KeyValuePair<K, V>> map(String filePath) throws IOException {
		  List<String> lines = Files.readAllLines(Paths.get(filePath));
		  return map(lines);
	    }
	
	 public List<KeyValuePair<K, V>> map(List<String> lines) {
		 this.words  = new ArrayList<>();
		 for (String line : lines) {
				line = line.replaceAll(regReplace, "");
				line = line.replaceAll(regSpace, "-");
				
				List<String> wordList = Arrays.asList(line.split("-"));
		        wordList.stream()
		                .filter(item -> item.matches(regWord))
		                .forEach(item -> this.words.add(new KeyValuePair(item.toLowerCase(), 1)));
			}
			return this.words;
	    }
		
}
