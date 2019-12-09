package root.entity;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Metric {
    private final Map<LocalDateTime, Integer> buffer = new HashMap<>();

    public Metric() {
    }

    public Metric(LocalDateTime date, Integer value) {
        this.add(date, value);
    }

    public void add(LocalDateTime key, Integer value) {
        if (buffer.containsKey(key)) {
            buffer.put(key, buffer.get(key) + value);
        }
        buffer.put(key, value);
    }

    public void addAll(Map<LocalDateTime, Integer> metrics) {
        for (Map.Entry<LocalDateTime, Integer> entry : metrics.entrySet()) {
            add(entry.getKey(), entry.getValue());
        }
    }

    public Metric getMetric(){
        return this;
    }
}
