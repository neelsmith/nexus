---
title: "Text formatting"
layout: page
---

The `NexusData` and `NexusBlock` classes override the default `toString` methods with a summary of component elements.


```scala
val nexus = NexusSource.fromUrl(dataUrl)
val codons = nexus.block("codons").get
val assumed = nexus.block("assumptions").get
```
```scala
nexus.toString
// res0: String = "Nexus data set [TAXA, CHARACTERS, ASSUMPTIONS, CODONS, NOTES, LABELS, SETS, MESQUITECHARMODELS, MESQUITE]"
codons.toString
// res1: String = "CODONS [commands: CODONPOSSET, CODESET]"
assumed.toString
// res2: String = "ASSUMPTIONS [commands: TYPESET]"
```

The `NexusData`, `NexusBlock` and `NexusCommand` classes also have `nexusString` methods that format their contents according to the NEXUS file format specification.

```scala
assumed.nexusString
// res3: String = """BEGIN ASSUMPTIONS;
// 	TYPESET * UNTITLED   =  unord:  1- 848;
// 	TYPESET TNTITLED   =  unord:  1- 848;
// ;"""
```

Using `nexusString` on a `NexusData` object should produce a NEXUS string that is semantically equivalent to the source String used to create it.
