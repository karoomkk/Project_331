package projectAK;

public class Comparison {
    // Main method to measure and compare execution times of single-threaded and multi-threaded approaches
    public static void main(String[] args) {
        long singleThreadStartTime, singleThreadEndTime, multiThreadStartTime, multiThreadEndTime;
        int totalThreads = 4;  // Modify as necessary

        // Measure execution time for single-threaded approach
        singleThreadStartTime = System.nanoTime();
        SingleThread.main(args);
        singleThreadEndTime = System.nanoTime();
        long singleThreadElapsedTime = (singleThreadEndTime - singleThreadStartTime) / 1_000_000_000L; // Convert to seconds
        System.out.println("Execution time for single-thread: " + singleThreadElapsedTime + " seconds");

        // Measure execution time for multi-threaded approach
        multiThreadStartTime = System.nanoTime();
        MultiThread.main(args);
        multiThreadEndTime = System.nanoTime();
        long multiThreadElapsedTime = (multiThreadEndTime - multiThreadStartTime) / 1_000_000_000L; // Convert to seconds
        System.out.println("Execution time for multi-thread with " + totalThreads + " threads: " + multiThreadElapsedTime + " seconds");
    }
}
