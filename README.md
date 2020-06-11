# `nexus`

A Scala library for working with data in [NEXUS format](https://en.wikipedia.org/wiki/Nexus_file).


## What it is

`nexus` is a cross-platform library for parsing data in the [Nexus file format](https://en.wikipedia.org/wiki/Nexus_file).  Binaries for Scala 2.11 and 2.12 on both the JVM and ScalaJS platforms are [available from bintray](https://bintray.com/neelsmith/maven/nexus), or you can build from source and test using `sbt`.

The parsing is based largely on descriptions of the Nexus file format linked from the [wikipedia article](https://en.wikipedia.org/wiki/Nexus_file), including these web pages:

- <http://wiki.christophchamp.com/index.php?title=NEXUS_file_format>
- <http://hydrodictyon.eeb.uconn.edu/eebedia/index.php/Phylogenetics:_NEXUS_Format>


## Current version: 1.2.0

Status: early development.  

Implements basic syntax of Nexus file format, parsing source into blocks containing commands.  [Release notes](releases.md)


## License

[GPL 3.0](https://opensource.org/licenses/gpl-3.0.html  )


## Documentation

- [http://neelsmith.github.io/nexus/](http://neelsmith.github.io/nexus/).

Build the docs locally in `sbt`:  `docs mdoc` (Results are written to the `docs` directory.)
