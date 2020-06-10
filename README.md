# `nexus`

A Scala library for working data in NEXUS format.


## What it is

`nexus` is a cross-platform library for parsing data in nexus format.  Binaries for Scala 2.11 and 2.12 on both JVM and ScalaJS platforms are available from bintray, or you can build from source and test using `sbt`.

The parsing is based largely on descriptions of the Nexus file format here:

- <http://wiki.christophchamp.com/index.php?title=NEXUS_file_format>
- <http://hydrodictyon.eeb.uconn.edu/eebedia/index.php/Phylogenetics:_NEXUS_Format>


## Current version: 1.0.0

Status: early development.  

Implements basic syntax of Nexus file format, parsing source into blocks containing commands.  [Release notes](releases.md)


## License

[GPL 3.0](https://opensource.org/licenses/gpl-3.0.html  )


## Documentation

In progress at [http://neelsmith.github.io/nexus/].

Build locally in `sbt`:  `docs mdoc` (Results are written to the `mdocs` directory.)
