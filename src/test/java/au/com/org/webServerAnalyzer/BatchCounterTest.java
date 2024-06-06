package au.com.org.webServerAnalyzer;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BatchCounterTest {

    private BatchCounter batchCounter;

    @Before
    public void setUp() {
        batchCounter = new BatchCounter();
    }

    @Test
    public void testInitialCount() {
        assertEquals(0, batchCounter.getCount());
    }

    @Test
    public void testIncrement() {
        batchCounter.increment();
        assertEquals(1, batchCounter.getCount());

        batchCounter.increment();
        assertEquals(2, batchCounter.getCount());
    }

    @Test
    public void testMultipleIncrements() {
        for (int i = 0; i < 10; i++) {
            batchCounter.increment();
        }
        assertEquals(10, batchCounter.getCount());
    }
}
