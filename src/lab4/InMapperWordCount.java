package lab4;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class InMapperWordCount {
    private final String[] inputFiles;
    private final int reducerCount;
    private List<Mapper<String, Integer>> mappers;

    private List<Reducer<String, Integer>> reducers;

    public InMapperWordCount(String[] inputFiles, int reducerCount) {
        this.inputFiles = inputFiles;
        this.reducerCount = reducerCount;
    }

    public void start() throws IOException {
        this.initialize();
        this.printInputs();
        this.processMapper();
        this.printMapperInputOutput();
        this.processReducerInput();
        this.printReducerInput();
        this.processReducerOutput();
        this.printReducerOutput();
    }

    private void printReducerOutput() {
        for (int i = 0; i < this.reducerCount; ++i) {
            Reducer<String, Integer> reducer = this.reducers.get(i);
            System.out.println("Reducer " + i + " output");
            reducer.getResult().stream()
                    .sorted(pairComparator)
                    .forEach(pair -> System.out.println("< " + pair.getKey() + " , " + pair.getValue().toString() + " >"));
        }
    }

    private void processReducerOutput() {
        this.reducers.forEach(reducer -> reducer.reduce(sumFunction));
    }

    private Function<List<Integer>, Integer> sumFunction = (inputList) -> {
        return inputList.stream().mapToInt(Integer::new).sum();
    };

    private void printReducerInput() {
        for (int i = 0; i < this.reducerCount; ++i) {
            Reducer<String, Integer> reducer = this.reducers.get(i);
            System.out.println("Reducer " + i + " input");
            reducer.getInput().stream()
                    .sorted((item1, item2) -> item1.getKey().compareTo(item2.getKey()))
                    .forEach(pair -> System.out.println("< " + pair.getKey() + " , " + pair.getValue().toString() + " >"));
        }
    }

    private void printMapperInputOutput() {
        for (int i = 0; i < this.mappers.size(); i++) {
            System.out.println("Mapper " + i + " Input");
            System.out.println(this.mappers.get(i).getInput());
        }
        for (int i = 0; i < this.mappers.size(); i++) {
            System.out.println("Mapper " + i + " Output");
            this.mappers.get(i).getWords().forEach(pair -> System.out.println("< " + pair.getKey() + " , " + pair.getValue() + " >"));
        }
    }

    private void printInputs() {
        System.out.println("Number of Input-Splits: " + this.inputFiles.length);
        System.out.println("Number of Reducers: " + this.reducerCount);
    }

    private Comparator<KeyValuePair<String, Integer>> pairComparator = (pair1, pair2) -> {
        int compRes = pair1.getKey().compareToIgnoreCase(pair2.getKey());
        if (compRes == 0) {
            compRes = pair1.getValue().compareTo(pair2.getValue());
        }
        return compRes;
    };

    private void processReducerInput() {
        for (int i = 0; i < this.mappers.size(); i++) {
            for (Reducer reducer : this.reducers) {
                System.out.println("Pairs send from Mapper " + i + " Reducer " + this.reducers.indexOf(reducer));
                List<KeyValuePair<String, Integer>> pairs = this.mappers.get(i).getWords()
                        .stream()
                        .filter(pair -> this.reducers.indexOf(reducer) == getPartition(pair.getKey()))
                        .sorted(pairComparator)
                        .peek(pair -> System.out.println("< " + pair.getKey() + " , " + pair.getValue() + " >"))
                        .collect(Collectors.toList());
                reducer.addPairs(pairs);
            }
        }
        this.reducers.forEach(reducer -> {
            reducer.mergePair();
        });
    }

    private List<KeyValuePair<String, List<Integer>>> shuffleSort(List<KeyValuePair<String, Integer>> words) {
        List<KeyValuePair<String, List<Integer>>> result = new ArrayList<>();
        words.forEach(item -> {
        	KeyValuePair<String, List<Integer>> resItem = null;
            Optional<KeyValuePair<String, List<Integer>>> resItemOpt = result.stream().filter(stringListPair -> stringListPair.getKey().equals(item.getKey())).findFirst();
            if (resItemOpt.isPresent()) {
                resItem = resItemOpt.get();
            } else {
                List<Integer> values = new ArrayList<Integer>();
                resItem = new KeyValuePair<String, List<Integer>>(item.getKey(), values);
                result.add(resItem);
            }
            resItem.getValue().add(item.getValue());
        });
        return result;
    }

    private int getPartition(String key) {
        return Math.abs(key.hashCode() % this.reducerCount);
    }


    private void processMapper() throws IOException {
        for (String fileName : this.inputFiles) {
            URL url = Main.class.getResource(fileName);
            Mapper<String, Integer> mapper = new Mapper();
            mapper.map(url);//.stream().sorted(pairComparator).collect(Collectors.toList());
            this.mappers.add(mapper);
        }
    }

    private void initialize() {
        this.mappers = new ArrayList<>(this.inputFiles.length);
        this.reducers = new ArrayList<>(this.reducerCount);
        for (int i = 0; i < this.reducerCount; ++i) {
            Reducer<String, Integer> reducer = new Reducer<>();
            this.reducers.add(reducer);
        }
    }
}