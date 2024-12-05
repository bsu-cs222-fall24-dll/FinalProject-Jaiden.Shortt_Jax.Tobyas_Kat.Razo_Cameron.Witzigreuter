package edu.bsu.cs.application.splitstopwatch;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class SplitsListIO {
    public static File saveToFile(List<Split> splits) throws IOException {
        String filename = figureOutFilename(splits);
        String filepath = "src/main/resources/edu/bsu/cs/application/splitstopwatch/savedSplits/" + filename + ".txt";

        try (FileWriter fileWriter = new FileWriter(filepath)) {
            for (Split split : splits)
                fileWriter.write(String.format("%s%n", split.toString()));
        }

        return new File(filepath);
    }

    private static String figureOutFilename(List<Split> splits) {
        String toReturn = tryForFilenameFromSplitNames(splits);
        if (toReturn == null) {
            LocalDateTime now = LocalDateTime.now();
            return String.format("%s-%s-%s_%s-%s-%s",
                    now.getMonthValue(), now.getDayOfMonth(), now.getYear(),
                    now.getHour(), now.getMinute(), now.getSecond()
            );
        }

        return toReturn;
    }
    private static String tryForFilenameFromSplitNames(List<Split> splits) {
        for (Split split : splits)
            if (!split.getSplitName().isEmpty())
                return split.getSplitName();

        return null;
    }
}