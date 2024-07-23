package au.com.org.webServerAnalyzer;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LogAnalyzerTest {

    private LogAnalyzer logAnalyzer;

    @Before
    public void setUp() {
        logAnalyzer = new LogAnalyzer();
    }

    @Test
    public void test_CountOccurrences() {
        List<String> items = List.of("a", "b", "a", "c", "b", "a");
        Map<String, Integer> occurrences = logAnalyzer.countOccurrences(items);

        assertEquals(3, occurrences.get("a").intValue());
        assertEquals(2, occurrences.get("b").intValue());
        assertEquals(1, occurrences.get("c").intValue());
    }

    @Test
    public void test_CountOccurrences_With_EmptyList() {
        List<String> items = List.of();
        Map<String, Integer> occurrences = logAnalyzer.countOccurrences(items);

        assertTrue(occurrences.isEmpty());
    }

    @Test
    public void test_GetTop3() {
        Map<String, Integer> occurrences = Map.of(
                "a", 5,
                "b", 3,
                "c", 8,
                "d", 2
        );

        List<String> top3 = logAnalyzer.getTop3(occurrences, 3);

        assertEquals(3, top3.size());
        assertEquals("c - 8 requests", top3.get(0));
        assertEquals("a - 5 requests", top3.get(1));
        assertEquals("b - 3 requests", top3.get(2));
    }

    @Test
    public void test_GetTop3_With_LessThan_N_Entries() {
        Map<String, Integer> occurrences = Map.of(
                "a", 5,
                "b", 3
        );

        List<String> top3 = logAnalyzer.getTop3(occurrences, 3);

        assertEquals(2, top3.size());
        assertEquals("a - 5 requests", top3.get(0));
        assertEquals("b - 3 requests", top3.get(1));
    }
    @Test
    public void test_GetTop3_With_EmptyMap() {
        Map<String, Integer> occurrences = Map.of();

        List<String> top3 = logAnalyzer.getTop3(occurrences, 3);

        assertTrue(top3.isEmpty());
    }
}


