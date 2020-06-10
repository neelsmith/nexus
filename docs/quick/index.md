---
layout: page
title: Quick start
---




### Create and `NexusData` set

Import the library, and use the `NexusSource` object to load a dataset from a URL or local file.


```scala:mdoc
import edu.holcyross.shot.nexus._

val dataUrl = "https://raw.githubusercontent.com/neelsmith/nexus/master/jvm/src/test/resources/CaveTrechineCOI.nex"
val nexus = NexusSource.fromURL(dataUrl)

// or NexusSource.fromFile("FILE_NAME")

```

See what blocks it contains:
```scala:mdoc
nexus.blockNames
```
