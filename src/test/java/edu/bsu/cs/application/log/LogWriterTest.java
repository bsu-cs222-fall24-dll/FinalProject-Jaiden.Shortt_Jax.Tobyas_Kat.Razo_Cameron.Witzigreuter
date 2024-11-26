package edu.bsu.cs.application.log;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LogWriterTest {
    private static LogWriter testLogWriter;
    private static final String DIRECTORY_ROOT = "src/test/resources/edu.bsu.cs/application/log/";

    private String getLogContents(String filePath) throws IOException {
        return IOUtils.toString(new FileInputStream(filePath), StandardCharsets.UTF_8);
    }

    private String formatPath(String fileName) {
        return String.format("%s%s", DIRECTORY_ROOT, fileName);
    }

    private boolean fileExists(String path) {
        return Files.exists(Paths.get(path));
    }
    private void deleteFile(String path) throws IOException {
        Files.delete(Paths.get(path));
    }


    @Test
    public void test_logWriterFileExists() throws IOException {
        String filePath = formatPath("testlog1_exists.log");

        if (fileExists(filePath))
            deleteFile(filePath);

        testLogWriter = new LogWriter(filePath);

        Assertions.assertTrue(fileExists(filePath));
        testLogWriter.close();
    }

    @Test
    public void test_writeAndFlush() throws IOException {
        String filePath = formatPath("testlog2_write.log");
        testLogWriter = new LogWriter(filePath);

        testLogWriter.write("Hello world");
        testLogWriter.flush();

        String expectedFileOutput = "Hello world";
        String actualFileOutput = getLogContents(filePath);

        Assertions.assertEquals(expectedFileOutput, actualFileOutput);
        testLogWriter.close();
    }

    @Test
    public void test_writeTwice() throws IOException {
        String filePath = formatPath("testlog3_writeTwice.log");
        testLogWriter = new LogWriter(filePath);

        testLogWriter.write("Write One");
        testLogWriter.write("Write Two");
        testLogWriter.flush();

        String expectedFileOutput = "Write OneWrite Two";
        String actualFileOutput = getLogContents(filePath);

        Assertions.assertEquals(expectedFileOutput, actualFileOutput);
        testLogWriter.close();
    }

    @Test
    public void test_writelnTwice() throws IOException {
        String filePath = formatPath("testlog4_writelnTwice.log");
        testLogWriter = new LogWriter(filePath);

        testLogWriter.writeln("Write One");
        testLogWriter.writeln("Write Two");
        testLogWriter.flush();

        String expectedFileOutput = String.format("Write One%nWrite Two%n");
        String actualFileOutput = getLogContents(filePath);

        Assertions.assertEquals(expectedFileOutput, actualFileOutput);
        testLogWriter.close();
    }


}
