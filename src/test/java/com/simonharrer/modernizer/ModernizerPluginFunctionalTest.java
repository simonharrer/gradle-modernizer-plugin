package com.simonharrer.modernizer;

import java.io.IOException;
import java.nio.file.Paths;

import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.GradleRunner;
import org.junit.Test;

public class ModernizerPluginFunctionalTest {

    @Test
    public void testNoIssues() throws IOException {
        BuildResult result = GradleRunner.create()
                .withProjectDir(Paths.get("examples/noIssues").toFile())
                .withPluginClasspath()
                .withArguments("modernizer")
                .build();
    }

    @Test
    public void testOneIssue() throws IOException {
        BuildResult result = GradleRunner.create()
                .withProjectDir(Paths.get("examples/oneIssue").toFile())
                .withPluginClasspath()
                .withArguments("modernizer")
                .buildAndFail();
    }

}
