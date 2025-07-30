package hu.elte.inf.pet.project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.tika.Tika;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;

public class FileHandler {
    private static final Logger logger = LoggerFactory.getLogger(FileHandler.class);
    
    private final Path path;
    private final Tika tika;
    
    public FileHandler(String filePath) throws IOException {
        logger.debug("Initializing FileHandler with path: {}", filePath);
        
        // Initialize Tika with custom configuration
        Tika tikaInstance;
        try {
            TikaConfig config = new TikaConfig(getClass().getClassLoader().getResourceAsStream("tika-config.xml"));
            tikaInstance = new Tika(config);
            logger.debug("Tika initialized with custom configuration");
        } catch (TikaException | IOException | SAXException e) {
            logger.warn("Failed to load custom Tika configuration, using default: {}", e.getMessage());
            tikaInstance = new Tika();
        }
        this.tika = tikaInstance;
        
        this.path = Paths.get(filePath);
        if (!path.isAbsolute()) {
            logger.error("File path must be absolute: {}", filePath);
            throw new IllegalArgumentException("File path must be absolute: " + filePath);
        }
        if (!Files.exists(path)) {
            logger.error("File does not exist: {}", filePath);
            throw new IllegalArgumentException("File does not exist: " + filePath);
        }
        if (!Files.isDirectory(path)) {
            logger.error("Path is not a directory: {}", filePath);
            throw new IllegalArgumentException("Path is not a directory: " + filePath);
        }
        
        logger.info("FileHandler initialized successfully for directory: {}", path);
    }

    public void processFiles() throws IOException {
        logger.info("Starting file processing for directory: {}", path);
        
        List<Path> files = listAllFiles(path);
        logger.info("Found {} files to process", files.size());
        
        Map<FileCategory, List<Path>> fileCategories = categorizeFiles(files);
        logger.info("Files categorized into {} categories", fileCategories.size());
        
        for (Map.Entry<FileCategory, List<Path>> entry : fileCategories.entrySet()) {
            FileCategory category = entry.getKey();
            List<Path> categorizedFiles = entry.getValue();
            
            if (category != null) {
                logger.info("Category: {} ({} files)", category, categorizedFiles.size());
                logger.debug("Files in category {}:", category);
                for (Path file : categorizedFiles) {
                    logger.debug(" - {}", file.getFileName());
                }
            } else {
                logger.warn("Found {} files that could not be categorized", categorizedFiles.size());
            }
        }
        
        logger.info("File processing completed");
    }

    /**
     * Categorizes files based on their MIME type.
     *
     * @param files the list of file paths to categorize
     * @return a map where keys are FileCategory and values are lists of file paths
     */
    private Map<FileCategory, List<Path>> categorizeFiles(List<Path> files) {
        logger.debug("Categorizing {} files", files.size());
        
        return files.stream()
        .collect(Collectors.groupingBy(file -> {
            try {
                String mimeType = tika.detect(file);
                FileCategory category = FileCategory.fromMimeType(mimeType);
                return category;
            } catch (IOException e) {
                logger.warn("Failed to detect MIME type for file {}: {}", 
                          file.getFileName(), e.getMessage());
                return null;
            }
        }));
    }

    /**
     * Lists all files in the directory and its subdirectories.
     *
     * @param path the directory to list files from
     * @return a list of file paths
     * @throws IOException if an I/O error occurs
     */
    private List<Path> listAllFiles(Path start) throws IOException {
        List<Path> result = new java.util.ArrayList<>();
        walkSafe(start, result);
        return result;
    }

    private void walkSafe(Path dir, List<Path> result) {
        try (var stream = Files.list(dir)) {
            for (Path entry : (Iterable<Path>) stream::iterator) {
                try {
                    if (Files.isDirectory(entry)) {
                        logger.trace("Entering directory: {}", entry);
                        walkSafe(entry, result);
                    } else if (Files.isRegularFile(entry)) {
                        result.add(entry);
                        logger.trace("Found file: {}", entry);
                    }
                } catch (Exception e) {
                    logger.debug("Skipping entry {} due to exception: {}", entry, e.getMessage());
                }
            }
        } catch (Exception e) {
            logger.debug("Skipping directory {} due to exception: {}", dir, e.getMessage());
        }
    }
}
