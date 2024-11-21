package edu.bsu.cs.application.log;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class LogWriter {
    private final PrintWriter logWriter;

    public LogWriter(String relativeFilePath) throws IOException {
        logWriter = new PrintWriter(new BufferedWriter(new FileWriter(relativeFilePath)));
    }

    public <T> void write(T obj) {
        logWriter.print(obj);
    }
    public <T> void writeln(T obj) {
        logWriter.println(obj);
    }

    public void flush() {
        logWriter.flush();
    }

    public void close() {
        logWriter.close();
    }
}
