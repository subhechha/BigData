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
		this.shuffle();
		this.printReducer();
	 }
	 
	private void initialize(){
		this.mappers = new ArrayList<>(this.inputFiles.length);
	    this.reducers = new ArrayList<>(this.reducerCount);
	    
	    for (int i = 0; i < this.reducerCount; ++i) {
	    	List<KeyValuePair<String, List<Integer>>> tmp = new ArrayList<>();
            this.reducers.add(tmp);
        }
	}
	
	private void processMapper() throws IOException{
		for(String file : inputFiles){
			mappers.add(new Mapper().map(file));
		}
	}
	
	 private static Comparator<KeyValuePair<String, Integer>> Sort = (pair1, pair2) -> {
	     return pair1.getKey().compareToIgnoreCase(pair2.getKey());
	 };
	 
	 private static Function<List<Integer>, Integer> sumValue = (inputList) -> {
	        return inputList.stream().mapToInt(Integer::new).sum();
	  };
	  
	 private int getPartition(String key) {
	        return Math.abs(key.hashCode() % this.reducerCount);
	    }
		
	private void shuffle(){		
		for(int i = 0; i < this.mappers.size(); i++){
			List<KeyValuePair<String, Integer>> sortedList = this.mappers.get(i).stream().sorted(Sort).collect(Collectors.toList());
			
			for (KeyValuePair<String, Integer> pair : sortedList) {
				int pos = this.getPartition(pair.getKey());
				boolean contains = false;
				for(int ri=0; ri<this.reducers.get(pos).size(); ri++){
					if(this.reducers.get(pos).get(ri).getKey().equals(pair.getKey()))
					{
						List<Integer> vl =  this.reducers.get(pos).get(ri).getValue();
						vl.add(pair.getValue());
						this.reducers.get(pos).get(ri).setValue(vl);
						contains = true;
					}
				}
				
				if(!contains){
					List<Integer> vl = new ArrayList<>();
					vl.add(pair.getValue());
					this.reducers.get(pos).add(new KeyValuePair(pair.getKey(), vl));
				}
			}
		}
	}
	
	private void printReducer(){
		for(int i = 0; i < this.reducerCount; i++){
			List<KeyValuePair<String, Integer>> reduceOutput = new Reducer<String, Integer>().reduce(this.reducers.get(i), sumValue);
			System.out.println(reduceOutput);
		}
	}
	
	private void printMapper(){
		for(int i = 0; i < this.mappers.size(); i++){
			System.out.println(this.mappers.get(i).stream().sorted(Sort).collect(Collectors.toList()));
		}
	}
	
	
	
	  
}