
package lab2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

class Test {
   
    public static void main(String[] args) {
    	
    	String filePath = "C:/Bigdata/labs/src/lab2/testDataForW1D1.txt";
    	List<KeyValuePair<String, Integer>> word = null;
        Mapper<String, Integer> mapper = new Mapper();
        try {
        	
        	word = mapper.map(filePath).stream().sorted(pairComparator).collect(Collectors.toList());
            System.out.println("Mapper Output");
            word.forEach(item -> System.out.println("< " + item.getKey() + " , " + item.getValue() + " >"));
            List<KeyValuePair<String, List<Integer>>> reducerInput = shuffleSort(word);
            System.out.println("/Reducer Input");
            reducerInput.forEach(item -> System.out.println("< " + item.getKey() + " , " + item.getValue() + " >"));
            Reducer<String, Integer> reducer = new Reducer<>();
            List<KeyValuePair<String, Integer>> reduceOutput = reducer.reduce(reducerInput, sumValues);
            System.out.println("Reducer Output");
            reduceOutput.forEach(item -> System.out.println("< " + item.getKey() + " , " + item.getValue() + " >"));
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<KeyValuePair<String, List<Integer>>> shuffleSort(List<KeyValuePair<String, Integer>> words) {
        List<KeyValuePair<String, List<Integer>>> result = new ArrayList<>();
        List<Integer> values = new ArrayList<>();
		String prev = words.get(0).getKey();

		for (KeyValuePair<String, Integer> keyValue : words) {
			if (prev.equals(keyValue.getKey())) {
				prev = keyValue.getKey();
				values.add(keyValue.getValue());
			} else {
				result.add(new KeyValuePair(prev,values));
				values = new ArrayList<>();
				prev = keyValue.getKey();
				values.add(keyValue.getValue());
			}
		}
		result.add(new KeyValuePair(prev,values));
        return result;
    }
    
    private static Comparator<KeyValuePair<String, Integer>> pairComparator = (pair1, pair2) -> {
        return pair1.getKey().compareToIgnoreCase(pair2.getKey());
      };

      private static Function<List<Integer>, Integer> sumValues = (inputList) -> {
          return inputList.stream().mapToInt(Integer::new).sum();
      };
}