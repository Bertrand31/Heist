# Heist

## Prerequisites

Make sure you have Java, Scala and SBT installed locally.

## How to run it through SBT

First, launch SBT from inside the Heist's repo by calling `sbt`.
Then from inside the SBT prompt, call `run bank.map`, where `bank.map` is a path to the bank map
file (a sample one is provided, at the root of the repo).

Sample output:
```
$> sbt
[info] welcome to sbt 1.4.7 (Oracle Corporation Java 11.0.9)
(………)
[info] started sbt server
sbt:Heist> run bank.map
[info] running heist.Heist bank.map
0.811
[success] Total time: 1 s, completed 14 févr. 2021 à 18:38:19
```

## How to generate a package

Another way of using Heist is to generate a package, that you can then run without using SBT.
You can generate it by typing `sbt universal:packageBin`.

Then, uncompress the resulting archive: `unzip ./target/universal/heist-1.zip`.

Finally, run it: `./heist-1/bin/heist bank.map`.
