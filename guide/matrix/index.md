---
layout: page
title: The NexusMatrix
---


`CHARACTER` and `DATA` blocks can include a `Matrix` command, and while you could get the text contents of the command's label and data arguments using generic methods, the `NexusData` set also offers a `matrix` method that creates a higher-order `NexusMatrix` object.



```scala mdoc:invisible
import edu.holycross.shot.nexus._
val dataUrl = "https://raw.githubusercontent.com/neelsmith/nexus/master/jvm/src/test/resources/CaveTrechineCOI.nex"
```


```scala mdoc:silent
val nexus = NexusSource.fromUrl(dataUrl)
nexus.matrix
```

The `NexusMatrix` object has a Vector of `NexusCharacters`, named `rows`.

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
