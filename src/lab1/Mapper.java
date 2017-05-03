package lab1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Mapper {

	public List<KeyValuePair<String, Integer>> map() throws IOException {
		List<KeyValuePair<String, Integer>> list = new ArrayList<>();
		List<String> lines = Files.readAllLines(Paths.get("/Bigdata/BigDataLabs/src/lab1/testDataForW1D1.txt"));

		for (String line : lines) {
			String[] words = line.split(" ");
			for (String string : words) {
				if(string.matches("[a-zA-Z]+")){
					list.add(new KeyValuePair<String, Integer>(string.toLowerCase(), 1));
				}
			}
		}
		return list;
	}
	
	public static Comparator<KeyValuePair<String, Integer>> sort = (o1, o2)->{
		return o1.getKey().compareToIgnoreCase(o2.getKey());
	};

}
