package com.simonharrer.modernizer;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class ModernizerPlugin implements Plugin<Project> {

    public static final String MODERNIZER = "modernizer";

    @Override
    public void apply(Project project) {
        final ModernizerPluginExtension extension = project.getExtensions().create(MODERNIZER, ModernizerPluginExtension.class);
        final ModernizerTask modernizerTask = project.getTasks().create(MODERNIZER, ModernizerTask.class);
        modernizerTask.setGroup("Verification");
        modernizerTask.setDescription("Detects use of legacy APIs which modern Java versions supersede");

        // "modernizer" depends on "classes"
        modernizerTask.dependsOn("classes");

        if(extension.isIncludeTestClasses()) {
            // "modernizer" depends on "testClasses"
            modernizerTask.dependsOn("testClasses");
        }

        // "check" depends on "modernizer"
        project.getTasksByName("check",true).forEach(t -> t.dependsOn(MODERNIZER));
    }
}
