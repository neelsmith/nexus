---
layout: page
title: The NexusMatrix
---


`CHARACTER` and `DATA` blocks can include a `Matrix` command, and while you could get the text contents of the command's label and data arguments using generic methods, the `NexusData` set also offers higher-order methods.



```scala mdoc:invisible
import edu.holycross.shot.nexus._

val dataUrl = "https://raw.githubusercontent.com/neelsmith/nexus/master/jvm/src/test/resources/CaveTrechineCOI.nex"
val nexus = NexusSource.fromUrl(dataUrl)
```


Using generic methods to extract the contents of a `Matrix`:

```scala mdoc:invisible
val commands = nexus.block("CHARACTERS").get.commands.filter(_.commandName.toLowerCase == "matrix")
```

```scala mdoc
commands.map(cmd => cmd.argsString)
```
