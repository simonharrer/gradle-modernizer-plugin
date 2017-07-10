package org.gaul.modernizer_maven_plugin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.simonharrer.modernizer.ModernizerPluginExtension;
import org.gradle.api.logging.Logger;

/**
 * This class must be in this specific package to access the package private class Modernizer from the maven-modernizer-plugin
 */
public class ModernizerWrapper {

    public static final String MODERNIZER_VIOLATIONS_FILE = "modernizer.violationsFile";

    public static void execute(ModernizerPluginExtension extension,
            Path classesDir,
            List<Path> sourceDirs,
            Logger logger) throws Exception {

        Modernizer modernizer = getModernizer(extension);

        List<ViolationOccurrence> foundViolations;
        try (Stream<Path> walk = Files.walk(classesDir)) {
            foundViolations = walk
                    .filter(Files::isRegularFile)
                    .filter(p -> p.toString().endsWith(".class"))
                    .flatMap(p -> {
                                try (InputStream is = Files.newInputStream(p)) {
                                    return modernizer.check(is)
                                            .stream()
                                            .peek(v -> {
                                                final Path sourcePath = sourceFileName(p, classesDir, sourceDirs);
                                                final String message = String.format("%s:%d: %s",
                                                        sourcePath.toString(),
                                                        v.getLineNumber(),
                                                        v.getViolation().getComment());
                                                logger.warn(message);
                                            });
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                    )
                    .collect(Collectors.toList());
        }

        if (!foundViolations.isEmpty() && extension.isFailOnViolations()) {
            throw new Exception("Found " + foundViolations.size() + " violations");
        }
    }

    private static Modernizer getModernizer(ModernizerPluginExtension extension) throws Exception {
        return new Modernizer(
                extension.getJavaVersion(),
                getConfiguredViolations(extension.getViolationsFile()),
                getExclusions(extension.getExclusionsFile()),
                Collections.emptyList(),
                extension.getIgnorePackages()
        );
    }

    private static Set<String> getExclusions(String exclusionsFile) throws Exception {
        Set<String> exclusions = new HashSet<String>();
        if (exclusionsFile != null && !exclusionsFile.trim().isEmpty()) {
            Path path = Paths.get(exclusionsFile);
            if (Files.exists(path)) {
                try {
                    exclusions.addAll(Files.readAllLines(path));
                } catch (IOException ioe) {
                    throw new Exception("Error reading exclusion file: " + path, ioe);
                }
            } else {
                try (InputStream inputStream = ModernizerWrapper.class.getResourceAsStream(exclusionsFile)) {
                    if (inputStream == null) {
                        throw new Exception("Could not find exclusion file in classpath: " + exclusionsFile);
                    }
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            exclusions.add(line);
                        }
                    }
                } catch (IOException ioe) {
                    throw new Exception("Error reading exclusion file in classpath: " + exclusionsFile, ioe);
                }
            }
        }
        return exclusions;
    }

    private static Path sourceFileName(Path name, Path outputDir, List<Path> sourcesDirs) {
        if (name.startsWith(outputDir)) {
            for (Path sourceDir : sourcesDirs) {
                Path newname = sourceDir.resolve(name.toString().substring(outputDir.toString().length()));
                newname = newname.getParent().resolve(newname.getFileName().toString().replace(".class", ".java"));
                if (Files.exists(newname)) {
                    return newname;
                }
            }
        }
        return name;
    }

    private static Map<String, Violation> getConfiguredViolations(String violationsFile) throws Exception {
        Map<String, Violation> violations;
        if (violationsFile != null && !violationsFile.trim().isEmpty()) {
            final Path path = Paths.get(violationsFile);

            if (Files.exists(path)) {
                try (InputStream inputStream = Files.newInputStream(path)) {
                    violations = Modernizer.parseFromXml(inputStream);
                } catch (IOException e) {
                    throw new Exception("Could not read " + MODERNIZER_VIOLATIONS_FILE + " = " + violationsFile);
                }
            } else {
                try (InputStream inputStream = ModernizerWrapper.class.getResourceAsStream(violationsFile)) {
                    if (inputStream == null) {
                        throw new Exception("Could not find " + MODERNIZER_VIOLATIONS_FILE + " in classpath: " + violationsFile);
                    }
                    violations = Modernizer.parseFromXml(inputStream);
                } catch (IOException ioe) {
                    throw new Exception("Error reading " + MODERNIZER_VIOLATIONS_FILE + " in classpath: " + violationsFile, ioe);
                }
            }
        } else {
            try (InputStream inputStream = ModernizerWrapper.class.getResourceAsStream("/modernizer.xml")) {
                violations = Modernizer.parseFromXml(inputStream);
            } catch (IOException e) {
                throw new Exception("Could not read " + MODERNIZER_VIOLATIONS_FILE + " = " + "/modernizer.xml");
            }
        }
        return violations;
    }

}
