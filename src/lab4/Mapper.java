package lab4;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Mapper<T, S> {

    private List<KeyValuePair<T, S>> words;
    private String spaceReg = "\\s+";
    private String replaceReg = "[\\,\\(\\)\\\"\\']";
    private String wordReg = "[a-zA-Z]+";
    private String input;

    public List<KeyValuePair<T, S>> map(URL fileUrl) throws IOException {
        File file = new File(fileUrl.getPath());

        Path path = Paths.get(file.toURI());//this.fileName);
        Charset charset = StandardCharsets.UTF_8;

        this.input = new String(Files.readAllBytes(path), charset);
        return map();
    }

    public List<KeyValuePair<T, S>> map() {
        String content = this.input;
        this.words = new ArrayList<>();
        content = content.replaceAll(replaceReg, "");
        content = content.replaceAll(spaceReg, "-");
        List<String> wordList = Arrays.asList(content.split("-"));
        wordList.stream()
                .filter(item -> item.matches(wordReg))
                .forEach(item -> this.words.add(new KeyValuePair(item.toLowerCase(), 1)));
        return this.words;
    }

    public List<KeyValuePair<T, S>> getWords() {
        return words;
    }

    public String getInput() {
        return input;
    }
}