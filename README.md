# gradle-modernizer-plugin [![Build Status](https://travis-ci.org/simonharrer/gradle-modernizer-plugin.svg?branch=master)](https://travis-ci.org/simonharrer/gradle-modernizer-plugin)

This gradle plugin is a thin wrapper around the [modernizer-maven-plugin](https://github.com/andrewgaul/modernizer-maven-plugin).

The version of this gradle plugin uses the format "${modernizer-maven-plugin.version}-${internalVersion}.
For instance, "1.6.0-1" refers to the first version using modernizer-maven-plugin 1.6.0.

## Usage

Add to your gradle script

```groovy
plugins {
   id "com.simonharrer.modernizer" version "1.6.0-1"
}
```

and use it from the command line

```bash
$ gradle modernizer
```

## Configuration

The default configuration that is automatically applied:

```groovy
modernizer {
   includeTestClasses = true
   failOnViolations = true

   exclusionsFile = ""
   violationsFile = "/modernizer.xml"

   javaVersion = project.targetCompatibility

   ignorePackages = []

   exclusions = []
   exclusionPatterns = []
}
```

Feel free to overwrite it if necessary.

## Release



## License

[MIT license](https://tldrlegal.com/license/mit-license). See the [LICENSE.md](LICENSE.md) for the full MIT license.

