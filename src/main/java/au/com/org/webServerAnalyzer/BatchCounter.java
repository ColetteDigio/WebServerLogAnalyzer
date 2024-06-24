package au.com.org.webServerAnalyzer;

public class BatchCounter {
    private int count = 0;

    public void increment() {
        count++;
    }

    public int getCount() {
        return count;
    }
}