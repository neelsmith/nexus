---
layout: page
title: Quick start
---




### Create and `NexusData` set

Import the library, and use the `NexusSource` object to load a dataset from a URL or local file.


```scala
import edu.holycross.shot.nexus._

val dataUrl = "https://raw.githubusercontent.com/neelsmith/nexus/master/jvm/src/test/resources/CaveTrechineCOI.nex"
val nexus = NexusSource.fromUrl(dataUrl)

// or NexusSource.fromFile("FILE_NAME")

```

See what blocks it contains:
```scala
nexus.blockNames
// res0: Vector[String] = Vector(
//   "TAXA",
//   "CHARACTERS",
//   "ASSUMPTIONS",
//   "CODONS",
//   "NOTES",
//   "LABELS",
//   "SETS",
//   "MESQUITECHARMODELS",
//   "MESQUITE"
// )
```
