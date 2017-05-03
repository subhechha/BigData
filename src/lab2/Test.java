package lab2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Test {

	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			String filePath = "/Bigdata/BigDataLabs/src/lab2/testDataForW1D1.txt";
			List<KeyValuePair<String, Integer>> list = new Mapper().map(filePath);
			List<GroupByPair<String, Integer>> group = new ArrayList<>();
			group = new Reducer().reduceMethod(list);
			System.out.println("Reducer Input");
			System.out.println(group);
			
			List<KeyValuePair<String, Integer>> sum = new ArrayList<>();
			for (GroupByPair<String, Integer> keyValue : group) {
				sum.add(new KeyValuePair(keyValue.getKey(),keyValue.getValue().stream().reduce(0, (x,y) -> x+y)));
			}
			System.out.println("Reducer Output");
			System.out.println(sum);
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error "+e.getMessage());
		}
	}
}