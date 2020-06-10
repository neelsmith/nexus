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


## The hard way

Using generic methods to extract the contents of a `Matrix`:

- get the "CHARACTERS" block
- select the "Matrix" command
- get the command's arguments

Too much work, and you're still left with a string to parse out labels and data!

```scala mdoc:silent
val commands = nexus.block("CHARACTERS").get.commands.filter(_.commandName.toLowerCase == "matrix")
```

```scala mdoc
commands.map(cmd => cmd.argsString).head
```


## The easy way

The better choice is the `matrix` method:

```scala mdoc:silent
nexus.matrix
```

This creates a `NexusMatrix` object, which has a Vector of `NexusCharacters`, called `rows`.

```scala mdoc
nexus.matrix.rows.size
```

The `NexusMatrix` also gives us access to the labels and data for each row.  Let's sample a few:

```scala mdoc
nexus.matrix.labels.take(10)
nexus.matrix.data.take(10)
```

If you want to combine labels and data in a text string with a separating String of your choice, you can use the `delimited` method

```scala mdoc
nexus.matrix.delimited("#")
```
