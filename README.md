# gradle-modernizer-plugin

This gradle plugin is a thin wrapper around the [modernizer-maven-plugin](https://github.com/andrewgaul/modernizer-maven-plugin).

Its version corresponds to that of the modernizer-maven-plugin.

## Usage

Add to your gradle script

```groovy
plugins {
   id "com.simonharrer.modernizer" version "1.4.0"
}
```

and use it from the command line

```bash
$ gradle modernizer
```

## Configuration

The default configuration that is automatically applied:

```
modernizer {
   includeTestClasses = true
   failOnViolations = true
   
   exclusionsFile = ""
   violationsFile = "/modernizer.xml"
   
   javaVersion = project.targetCompatibility
   
   ignorePackages = []
}
```

Feel free to overwrite it if necessary.

## License

[MIT license](https://tldrlegal.com/license/mit-license). See the [LICENSE.md](LICENSE.md) for the full MIT license.

