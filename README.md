# AISandbox-Client

Use your existing programming skills to train an AI in one of our environments.

The AI Sandbox client provides REST based interfaces that can be used with any modern programming language. This way you can leverage your existing coding expertise and don’t waste time on anything else. We provide hints and safe spaces for your baby AI to learn and grow.

Full documentation is available at [aisandbox.dev](https://www.aisandbox.dev).

## Quickstart

To run the very latest version of AI Sandbox, you can download (or clone) this archive and compile it yourself.

Notes:

1. AI Sandbox is a "desktop client" that connects to an AI that you provide. For an example of how to write such an AI, checkout our coding examples at [aisandbox.dev/learn/code.html](https://www.aisandbox.dev/learn/code.html).
2. Stable releases are available with OS specific installers from the [website](https://www.aisandbox.dev/).

### Prerequisites

You will need a Java Development Kit (version 11 or newer). These can be downloaded from [Oracle](https://www.oracle.com/technetwork/java/javase/downloads/index.html) or from [Adopt a JDK](https://adoptopenjdk.net/).
You will also need a recent version of [Maven](http://maven.apache.org/) to build the software.

### Running the Client

To compile and run the client, run the following command

```
mvn spring-boot:run
```

# Licence

This code is licenced under the GPLv3. For full details see the licence pages of [the website](https://aisandbox.dev/project/licence.html).

# Built With

AI Sandbox relies on several underlying technologies, these include:

* [Java JDK](https://openjdk.java.net/).
* [Spring Boot](https://spring.io/projects/spring-boot) and other Spring projects.
* [JavaFX](https://openjfx.io/).
* [Choco Solver](http://www.choco-solver.org/).

In addition, [Install4J](https://www.ej-technologies.com/products/install4j/overview.html) is used to provide platform specific installers.

# Authors

Lead Programmer : Graham Evans
