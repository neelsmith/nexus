---
layout: page
title: Quick start
---




### Create and explore a `NexusData` set

Import the library, and use the `NexusSource` object to load a dataset from a URL or local file.


```scala mdoc:silent
import edu.holycross.shot.nexus._

val dataUrl = "https://raw.githubusercontent.com/neelsmith/nexus/master/jvm/src/test/resources/CaveTrechineCOI.nex"
val nexus = NexusSource.fromUrl(dataUrl)

// or NexusSource.fromFile("FILE_NAME")
```

See what blocks it contains:
```scala mdoc
nexus.blockNames
```

## Extract a named block

Block labels are case-insensitive in the NEXUS format.

```scala mdoc
val taxaOption = nexus.block("taxa")
```

The result is a Scala `Option`.

```scala mdoc:silent
val taxa = taxaOption match {
  case Some(block)  => block
  case None => throw new Exception("No block found for TAXA")
}
```

Of course if you are using a block name you've found in the list of block names, you can directly `get` it:


```scala mdoc
val assumed = nexus.block("assumptions").get
```

## What's in a block?

Blocks have a label and list of commands.

```scala mdoc
taxa.label

taxa.commands.size
```

Find the names of commands in a block:

```scala mdoc
taxa.commandNames
```


## What's in a command?

Commands have names and an argument string.

```scala mdoc
taxa.commands.head.commandName
taxa.commands.head.argsString
```
