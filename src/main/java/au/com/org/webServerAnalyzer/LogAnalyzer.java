package au.com.org.webServerAnalyzer;

import org.apache.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

public class LogAnalyzer {

    private final Logger logger = Logger.getLogger(LogAnalyzer.class);

    public int getUniqueCount(List<String> logs) {
        Set<String> uniqueIps = new HashSet<>();

        for (String log : logs) {
            uniqueIps.add(log.split(" ")[0]);
        }
        return uniqueIps.size();
    }
    public List<String> getTop3(Map<String, Integer> map, int n) {

        List<String> top3Entries = map.entrySet().stream() // convert map entries to stream of map.entry object, each entry is <key, value> pair
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())) // compares values of the entries in descending order then in reverse order
                .limit(n)// limit the steam to the first n entries
                .map(entry -> entry.getKey() + " - " + entry.getValue() + " requests") // only interested at the keys
                .collect(Collectors.toList()); // returns keys into list

        return top3Entries;
    }
}
