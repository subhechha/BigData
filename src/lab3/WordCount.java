
package lab3;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import lab2.Mapper;
import lab2.KeyValuePair;
import lab2.Reducer;

public class WordCount {
	 private final String[] inputFiles;
	 private final int reducerCount;
	 
	 
	 private List<List<KeyValuePair<String, Integer>>> mappers = null;
	 private List<List<KeyValuePair<String, List<Integer>>>> reducers = null;
	    
	 public WordCount(String[] inputFiles, int reducerCount) {
	        this.inputFiles = inputFiles;
	        this.reducerCount = reducerCount;
	    }
	 
	 public void start() throws IOException{
		this.initialize();
		this.processMapper();
		this.printMapper();
	 }
	 
	private void initialize(){
		this.mappers = new ArrayList<>(this.inputFiles.length);
	    this.reducers = new ArrayList<>(this.reducerCount);
	}
	
	private void processMapper() throws IOException{
		for(String file : inputFiles){
			mappers.add(new Mapper().map(file));
		}
	}
	
	 private static Comparator<KeyValuePair<String, Integer>> pairSort = (pair1, pair2) -> {
	     return pair1.getKey().compareToIgnoreCase(pair2.getKey());
	 };
	 
	 
	  private static Function<List<Integer>, Integer> sumValues = (inputList) -> {
	        return inputList.stream().mapToInt(Integer::new).sum();
	  };
	  
	  private int getPartition(String key) {
	        return Math.abs(key.hashCode() % this.reducerCount);
	    }
		
	  
	private void shuffle(){
		for(int i = 0; i < this.mappers.size(); i++){;
			List<KeyValuePair<String, Integer>> sortedList = this.mappers.get(i).stream().sorted(pairSort).collect(Collectors.toList());
			
			for (KeyValuePair<String, Integer> pair : sortedList) {
				
			}
		}
	}
	
	private void processReducer(){
		for(int i = 0; i < this.reducerCount; i++){
			List<KeyValuePair<String, Integer>> reduceOutput = new Reducer<String, Integer>().reduce(this.reducers.get(0), sumValues);
			System.out.println(reduceOutput);
		}
	}
	
	private void printMapper(){
		for(int i = 0; i < this.mappers.size(); i++){
			System.out.println(this.mappers.get(i).stream().sorted(pairSort).collect(Collectors.toList()));
		}
	}
	
	
	

}