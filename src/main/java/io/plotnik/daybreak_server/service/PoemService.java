package io.plotnik.daybreak_server.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import io.plotnik.daybreak_server.model.Poem;
import io.plotnik.daybreak_server.model.PoemCollection;
import jakarta.annotation.PostConstruct;

@Service
public class PoemService {

    private final Map<String, PoemCollection> poemsByFile = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        try {
            String userHome = System.getProperty("user.home");
            Path daybreakDir = Paths.get(userHome, "Documents", "daybreak");

            if (!Files.isDirectory(daybreakDir)) {
                System.out.println("WARN: Directory not found: " + daybreakDir);
                return;
            }
            try (Stream<Path> paths = Files.list(daybreakDir)) {
                paths.filter(path -> path.toString().endsWith(".md"))
                     .forEach(this::parseAndStorePoemFile);
            }
        } catch (IOException e) {
            // In a real app, use a proper logger
            System.err.println("Error reading poem files: " + e.getMessage());
        }
    }

    private void parseAndStorePoemFile(Path filePath) {
        try {
            List<String> lines = Files.readAllLines(filePath);
            if (lines.isEmpty()) return;

            String sectionTitle = "";
            List<Poem> poems = new ArrayList<>();
            String currentPoemTitle = null;
            StringBuilder currentPoemText = new StringBuilder();

            for (String line : lines) {
                if (line.startsWith("# ")) {
                    sectionTitle = line.substring(2).trim();
                } else if (line.startsWith("## ")) {
                    if (currentPoemTitle != null) {
                        poems.add(new Poem(currentPoemTitle, currentPoemText.toString().trim()));
                    }
                    currentPoemTitle = line.substring(3).trim();
                    currentPoemText.setLength(0); // Reset for the new poem
                } else if (line.startsWith("> ")) {
                    currentPoemText.append(line.substring(2)).append("\n");
                } else if (line.isBlank() && currentPoemText.length() > 0) {
                     currentPoemText.append("\n");
                }
            }
            // Add the last poem
            if (currentPoemTitle != null) {
                poems.add(new Poem(currentPoemTitle, currentPoemText.toString().trim()));
            }

            String fileName = filePath.getFileName().toString();
            if (fileName.endsWith(".md")) {
                fileName = fileName.substring(0, fileName.length() - 3);
            }
            poemsByFile.put(fileName, new PoemCollection(sectionTitle, poems));

        } catch (IOException e) {
            System.err.println("Error parsing file " + filePath + ": " + e.getMessage());
        }
    }

    public List<String> getAvailableFileNames() {
        return new ArrayList<>(poemsByFile.keySet());
    }

    public Optional<Integer> getPoemCount(String fileName) {
        return Optional.ofNullable(poemsByFile.get(fileName))
                       .map(collection -> collection.getPoems().size());
    }

    public Optional<Poem> getPoem(String fileName, int poemNumber) {
        return Optional.ofNullable(poemsByFile.get(fileName))
                       .filter(collection -> poemNumber > 0 && poemNumber <= collection.getPoems().size())
                       .map(collection -> collection.getPoems().get(poemNumber - 1));
    }
}
