package au.com.org.webServerAnalyzer;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LogAnalyzer {

    public Map<String, Integer> countOccurrences(List<String> items) {
        Map<String, Integer> count = new HashMap<>();
        for (String item : items) {
            count.put(item, count.getOrDefault(item, 0) + 1);
        }
        return count;
    }

    public List<String> getTop3(Map<String, Integer> map, int n) {

        List<String> top3Entries = map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(n)
                .map(entry -> entry.getKey() + " - " + entry.getValue() + " requests")
                .collect(Collectors.toList());

        return top3Entries;
    }
}

