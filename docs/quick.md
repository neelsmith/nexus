---
layout: page
title: Quick start
---




### Create a `NexusData` set

Import the library, and use the `NexusSource` object to load a dataset from a URL or local file.


```scala:mdoc
import edu.holcyross.shot.nexus._

val dataUrl = "https://raw.githubusercontent.com/neelsmith/nexus/master/jvm/src/test/resources/CaveTrechineCOI.nex"
val nexus = NexusSource.fromURL(dataUrl)

```
