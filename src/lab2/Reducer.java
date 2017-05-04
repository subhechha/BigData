package lab2;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;


public class Reducer<T, S> {
    private List<KeyValuePair<T, S>> result;

    public List<KeyValuePair<T, S>> reduce(List<KeyValuePair<T, List<S>>> input, Function<List<S>, S> reducer) {
        if (input == null && reducer == null) {
            throw new IllegalArgumentException(Reducer.class.getSimpleName());
        }
        result = new ArrayList<>();
        input.stream().forEach(item -> {
            S value = reducer.apply(item.getValue());
            result.add(new KeyValuePair<T, S>(item.getKey(), value));
        });
        return result;
    }
}