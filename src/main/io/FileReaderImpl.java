package main.io;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileReaderImpl implements FileReader {
    @Override
    public String getInformation(String path) {
        String information = "";
        try {
            information = Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8).get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return information;
    }
}
