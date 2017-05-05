package lab4;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;


public class Reducer<T, S> {

    private List<KeyValuePair<T, S>> result;

    private List<KeyValuePair<T, S>> pairs;

    private List<KeyValuePair<T, List<S>>> input;

    public List<KeyValuePair<T, List<S>>> getInput() {
        return input;
    }

    public List<KeyValuePair<T, S>> getResult() {
        return result;
    }

    public void reduce(Function<List<S>, S> reducer) {
        if (this.input == null && reducer == null) {
            throw new IllegalArgumentException(Reducer.class.getSimpleName() + "\"reduce\" parameter is not defined properly.");
        }
        this.result = new ArrayList<>();
        this.input.stream().forEach(item -> {
            S value = reducer.apply(item.getValue());
            this.result.add(new KeyValuePair<>(item.getKey(), value));
        });
    }

    public void addPairs(List<KeyValuePair<T, S>> pairs) {
        if (pairs == null) {
            throw new IllegalArgumentException(Reducer.class.getSimpleName() + "\"reduce\" parameter is not defined properly.");
        }
        if (this.pairs == null) this.pairs = new ArrayList<>();
        this.pairs.addAll(pairs);
    }

    public void mergePair() {
        this.input = new ArrayList<>();
        this.pairs.forEach(item -> {
        	KeyValuePair<T, List<S>> resItem = null;
            Optional<KeyValuePair<T, List<S>>> resItemOpt = this.input.stream()
                    .filter(stringListPair -> stringListPair.getKey().equals(item.getKey())).findFirst();
            if (resItemOpt.isPresent()) {
                resItem = resItemOpt.get();
            } else {
                List<S> values = new ArrayList<>();
                resItem = new KeyValuePair<>(item.getKey(), values);
                this.input.add(resItem);
            }
            resItem.getValue().add(item.getValue());
        });
    }
}