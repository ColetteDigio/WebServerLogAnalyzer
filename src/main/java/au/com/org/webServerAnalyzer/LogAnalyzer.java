package au.com.org.webServerAnalyzer;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LogAnalyzer {
    public List<String> getTop3(Map<String, Integer> map, int n) {
        return map.entrySet().stream() // convert map entries to stream of map.entry object, each entry is <key, value> pair
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())) // compares values of the entries in descending order then in reverse order
                .limit(n)// limit the steam to the first n entries
                .map(entry -> entry.getKey() + " - " + entry.getValue() + " requests") // only interested at the keys
                .collect(Collectors.toList()); // returns keys into list
    }
}
