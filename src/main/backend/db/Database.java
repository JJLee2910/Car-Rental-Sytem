package main.backend.db;

import main.backend.db.repository.Model;
import main.backend.db.repository.Repository;
import main.backend.utils.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Database {
    private final String baseDirectory;

    public Database(String baseDirectory) {
        this.baseDirectory = baseDirectory;
        init();
    }

    private void init() {
        FileUtils.createDirectoryIfNotExists(baseDirectory);
    }

    /*
    * registering a folder path in a repository
    */
    public void register(Repository<?, ?> repository) {
        try {
            FileUtils.createFileIfNotExists(baseDirectory, repository.getFileName());
        } catch (Exception e) {
            System.out.printf("Unable to create file %s in database%n", repository.getFileName());
            throw new IllegalStateException();
        }
    }

    public long getTotalCount(Repository<?, ?> repository) {
        Path path = getPath(repository);
        long lines = 0;
        try {
            lines = Files.lines(path).count();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }
    /*get file path in the repository
    */
    private Path getPath(Repository<?, ?> repository) {
        return FileUtils.joinPath(baseDirectory, repository.getFileName());
    }

    private String getPathString(Repository<?, ?> repository) {
        return FileUtils.joinPathString(baseDirectory, repository.getFileName());
    }

    public void appendAtEnd(Repository<?, ?> repository, String string) {
        try {
            Files.write(Paths.get(getPathString(repository)), string.getBytes(), StandardOpenOption.APPEND);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public <K, T extends Model<K>> void rewriteFile(Repository<K, T> repository) {
        try {
            byte[] data = repository.getLines().map(repository::recordToString).collect(Collectors.joining()).getBytes();
            Files.write(Paths.get(getPathString(repository)), data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Stream<String> getLines(Repository<?, ?> repository) {
        try {
            return Files.lines(getPath(repository));
        } catch (IOException e) {
            return Stream.empty();
        }
    }
}
