package src.Utils;

import java.util.concurrent.atomic.AtomicInteger;

public class IdGenerator {
    private static final AtomicInteger counter = new AtomicInteger(1);

    public static String generateID(String prefix) {
        return prefix + counter.getAndIncrement();
    }

    public static void resetCounter(int startValues){
        counter.set(startValues);
    }
}
