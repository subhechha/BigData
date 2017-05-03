package lab2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Reducer{
	
	Reducer(){
		
	}
	
	public List<GroupByPair<String, Integer>> reduceMethod(List<KeyValuePair<String, Integer>> list){
		List<GroupByPair<String, Integer>> group = new ArrayList<>();
		Collections.sort(list, Reducer.sort);
		List<Integer> values = new ArrayList<>();
		String prev = list.get(0).getKey();
		
		for (KeyValuePair<String, Integer> keyValue : list) {
			if (prev.equals(keyValue.getKey())) {
				prev = keyValue.getKey();
				values.add(keyValue.getValue());
			} else {
				group.add(new GroupByPair(prev, values));
				values = new ArrayList<>();
				prev = keyValue.getKey();
				values.add(keyValue.getValue());
			}
		}
		group.add(new GroupByPair(prev, values));
		return group;
	}
	
	public static Comparator<KeyValuePair<String, Integer>> sort = (o1, o2) -> {
		return o1.getKey().compareToIgnoreCase(o2.getKey());
	};
	
	public List<GroupByPair<String, Integer>> reducerOutput(List<KeyValuePair<String, Integer>> reducerInput){
		List<KeyValuePair<String, Integer>> sum = new ArrayList<>();
		return null;
	}

}
