---
title: "Text formatting"
layout: page
---

The `NexusData` and `NexusBlock` classes override the default `toString` methods with a summary of component elements.

```scala mdoc:invisible
import edu.holycross.shot.nexus._
val dataUrl = "https://raw.githubusercontent.com/neelsmith/nexus/master/jvm/src/test/resources/CaveTrechineCOI.nex"
```

```scala mdoc:silent
val nexus = NexusSource.fromUrl(dataUrl)
val codons = nexus.block("codons").get
val assumed = nexus.block("assumptions").get
```
```scala mdoc
nexus.toString
codons.toString
assumed.toString
```

The `NexusData`, `NexusBlock` and `NexusCommand` classes also have `nexusString` methods that format their contents according to the NEXUS file format specification.

```scala mdoc
assumed.nexusString
```

Using `nexusString` on a `NexusData` object should produce a NEXUS string that is semantically equivalent to the source String used to create it.
