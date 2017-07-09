package com.simonharrer.modernizer;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import org.gaul.modernizer_maven_plugin.ModernizerWrapper;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.Internal;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetContainer;
import org.gradle.api.tasks.TaskAction;

public class ModernizerTask extends DefaultTask {

    @TaskAction
    public void modernizer() throws Exception {
        final ModernizerPluginExtension extension = getExtension();
        if (extension.getJavaVersion() == null || extension.getJavaVersion().trim().isEmpty()) {
            extension.setJavaVersion(getJavaVersion());
        }

        final SourceSetContainer sourceSets = (SourceSetContainer) getProject().getProperties().get("sourceSets");
        executeForSourceSet(extension, sourceSets.getByName("main"));

        if(extension.isIncludeTestClasses()) {
            executeForSourceSet(extension, sourceSets.getByName("test"));
        }
    }

    private void executeForSourceSet(ModernizerPluginExtension extension, SourceSet mainSourceSet)
            throws Exception {
        final List<Path> classesDirs = mainSourceSet.getOutput().getClassesDirs().getFiles().
            stream().map(File::toPath).filter(Files::exists).collect(Collectors.toList());
        for (final Path classesDir : classesDirs) {
            final List<Path> sourceDirs = mainSourceSet.getJava().getSrcDirs().stream().map(File::toPath).collect(Collectors.toList());
            ModernizerWrapper.execute(extension, classesDir, sourceDirs, getLogger());
        }
    }

    @Internal
    private String getJavaVersion() {
        return getProject().getProperties().get("targetCompatibility").toString();
    }

    @Internal
    private ModernizerPluginExtension getExtension() {
        ModernizerPluginExtension extension = getProject().getExtensions().findByType(ModernizerPluginExtension.class);
        if (extension == null) {
            extension = new ModernizerPluginExtension();
        }
        return extension;
    }

}
