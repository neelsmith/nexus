---
layout: page
title: Quick start
---




### Create and explore a `NexusData` set

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

## Extract a named block

```scala
val taxaOption = nexus.block("taxa")
```

The result is a Scala `Option`.

```scala
val taxa = taxaOption match {
  case Some(block)  => block
  case None => throw new Exception("No block found for TAXA")
}
```


## What's in a block?

Blocks have a label and list of commands.

```scala
taxa.label
// res1: String = "TAXA"

taxa.commands.size
// res2: Int = 3
```

Find the names of commands in a block:

```scala
taxa.commandNames
// res3: Vector[String] = Vector("TITLE", "DIMENSIONS", "TAXLABELS")
```


## What's in a command?

Commands have names and an argument string.

```scala
taxa.commands.head.commandName
// res4: String = "TITLE"
taxa.commands.head.argsString
// res5: String = "Taxa"
```
