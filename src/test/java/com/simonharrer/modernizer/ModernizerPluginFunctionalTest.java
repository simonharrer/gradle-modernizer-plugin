package com.simonharrer.modernizer;

import java.nio.file.Paths;
import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.GradleRunner;
import org.junit.Test;

public class ModernizerPluginFunctionalTest {

  @Test
  public void testNoIssues() {
    GradleRunner.create()
        .withProjectDir(Paths.get("examples/noIssues").toFile())
        .withPluginClasspath()
        .withArguments("modernizer")
        .build();
  }

  @Test
  public void testOneIssueInTestIgnored() {
    GradleRunner.create()
        .withProjectDir(Paths.get("examples/oneIssueInTestIgnored").toFile())
        .withPluginClasspath()
        .withArguments("modernizer")
        .build();
  }

  @Test
  public void testOneIssueInTest() {
    GradleRunner.create()
        .withProjectDir(Paths.get("examples/oneIssueInTest").toFile())
        .withPluginClasspath()
        .withArguments("modernizer")
        .buildAndFail();
  }

  @Test
  public void testOneIssue() {
        GradleRunner.create()
            .withProjectDir(Paths.get("examples/oneIssue").toFile())
            .withPluginClasspath()
            .withArguments("modernizer")
            .buildAndFail();
  }
}
