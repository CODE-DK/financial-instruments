package root.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import root.entity.Metric;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MetricTask implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(MetricTask.class);
    private final String path;
    private final Map<String, Metric> buffer = new HashMap<>();

    public MetricTask(String path) {
        this.path = path;
    }

    @Override
    public void run() {
        Optional<List<String>> read = readDataFromExternalSource();
        if (read.isPresent()) {
            for (String s : read.get()) {
                String[] parse = getInstrumentFromString(s);
                buffer.put(parse[0], new Metric(LocalDateTime.parse(parse[1]), Integer.valueOf(parse[2])));
            }
        }
    }

    private Optional<List<String>> readDataFromExternalSource() {
        try {
            return Optional.of(Files.readAllLines(Paths.get(path)));
        } catch (IOException e) {
            LOGGER.error("error during reading..");
        }
        return Optional.empty();
    }

    private String[] getInstrumentFromString(String instrument) {
        return instrument.split(",");
    }


}
