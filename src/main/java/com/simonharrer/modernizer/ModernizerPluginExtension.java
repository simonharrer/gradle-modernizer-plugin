package com.simonharrer.modernizer;

import java.util.HashSet;
import java.util.Set;

public class ModernizerPluginExtension {

    private String exclusionsFile = "";
    private String violationsFile = "";
    private boolean includeTestClasses = true;
    private boolean failOnViolations = true;
    private Set<String> exclusions = new HashSet<>();
    private Set<String> exclusionPatterns = new HashSet<>();
    private Set<String> ignorePackages = new HashSet<>();
    private String javaVersion = "";

    public String getJavaVersion() {
        return javaVersion;
    }

    public void setJavaVersion(String javaVersion) {
        this.javaVersion = javaVersion;
    }

    public String getExclusionsFile() {
        return exclusionsFile;
    }

    public void setExclusionsFile(String exclusionsFile) {
        this.exclusionsFile = exclusionsFile;
    }

    public String getViolationsFile() {
        return violationsFile;
    }

    public void setViolationsFile(String violationsFile) {
        this.violationsFile = violationsFile;
    }

    public boolean isIncludeTestClasses() {
        return includeTestClasses;
    }

    public void setIncludeTestClasses(boolean includeTestClasses) {
        this.includeTestClasses = includeTestClasses;
    }

    public boolean isFailOnViolations() {
        return failOnViolations;
    }

    public void setFailOnViolations(boolean failOnViolations) {
        this.failOnViolations = failOnViolations;
    }

    public Set<String> getExclusions() {
        return exclusions;
    }

    public void setExclusions(Set<String> exclusions) {
        this.exclusions = exclusions;
    }

    public Set<String> getExclusionPatterns() {
        return exclusionPatterns;
    }

    public void setExclusionPatterns(Set<String> exclusionPatterns) {
        this.exclusionPatterns = exclusionPatterns;
    }

    public Set<String> getIgnorePackages() {
        return ignorePackages;
    }

    public void setIgnorePackages(Set<String> ignorePackages) {
        this.ignorePackages = ignorePackages;
    }
}
