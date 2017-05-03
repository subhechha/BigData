package lab1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Test {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			
			List<KeyValuePair<String, Integer>> list  = new Mapper().map();
			List<GroupByPair<String, List<Integer>>> group = new ArrayList<>();
			List<Integer> values = new ArrayList<>();
			String prev = list.get(0).getKey();
			Collections.sort(list, Mapper.sort);
			for (KeyValuePair<String, Integer> keyValuepair : list) {
				if (prev.equals(keyValuepair.getKey())) {
					prev = keyValuepair.getKey();
					values.add(keyValuepair.getValue());
				} else {
					group.add(new GroupByPair(prev, values));
					values = new ArrayList<>();
					prev = keyValuepair.getKey();
					values.add(keyValuepair.getValue());
				}
			}
			
			
			System.out.println(group);
			
			group.add(new GroupByPair(prev, values));
			List<KeyValuePair<String, Integer>> sum = new ArrayList<>();
			
//			for (GroupByPair<String, Integer> keyValuepair : group) {
//				sum.add(new KeyValuePair(keyValuepair.getKey(),keyValuepair.getValue().stream().reduce(0, (x,y) -> x+y)));
//			}
			
			
			System.out.println("Sum of grouping value.......");
			System.out.println(sum);
		
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
	}
}
