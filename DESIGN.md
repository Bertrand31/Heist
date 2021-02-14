# Heist

## The problem

You are a burglar, robbing a bank. In order to get to the vault, you must go through a room with a
number of detectors, whose efficacy in detecting the presence of a moving object is a function of
the distance of that object with the detector. Your goal is to find, with the highest precision
possible, the path across the room with the lowest probability of detection.

You will write a program that, given the description of the room including your starting position
and the location of the vault, produces a single floating point number as output. This floating
point number represents the lowest probability of detection achievable given the number and location
of the detectors, rounded to the 3th decimal digit.

## The solution

First, we simplify the problem by making a continuous space discrete: we generate a grid, making the
bank's room somewhat of a chessboard.
On this grid, the burglar will travel from intersection to intersection.

```
___________•__________  <- this • represents the vault
|__|__|__|__|__|__|__|
|__|__|__|__|__|__|_+|  <- each + represents a detector
|__|__|__|__|__|__|__|
|__|__|__|__|__|__|__|
|__|__|__|__|__|__|__|
|__|+_|__|__|__|__|__|  <- each + represents a detector
|__|__|__|__|__|__|__|
|  |  |  |  |  |  |  |
‾‾‾‾‾‾‾‾‾‾‾•‾‾‾‾‾‾‾‾‾   <- this • represents the entrance
```

We then compute a "heatmap" of detectors over that grid, mapping each intersection to how likely it
is that any detector would ring the alarm if the burglar were to stand on that intersection.

```
___________•__________
|_0|.1|.1|.1|.3|.5|.6|
|_0|.1|.2|.2|.4|.6|_1|
|.4|.4|.3|.4|.5|.6|.6|   The values here are just meant to illustrate the approach.
|.5|.5|.4|.4|.4|.4|.4|
|.5|.6|.5|.4|.2|.2|.2|
|.6|_1|.6|.4|.3|.2|.2|
|.6|.6|.4|.4|.3|.2|.1|
|.5|.4|.3|.2|.1|.1|.1|
‾‾‾‾‾‾‾‾‾‾‾•‾‾‾‾‾‾‾‾‾
```

Then, we pick the path our burglar will follow: each step he will take will be forward, and will be
on the intersection with the lowest probability of being detected.

```
___________•__________
|_•|__|__|__|__|__|__|
|_•|__|__|__|__|__|_+|   Across this board, the • are representing each of the burglar's steps
|__|__|_•|__|__|__|__|
|__|__|__|_•|__|__|__|
|__|__|__|__|_•|__|__|
|__|+ |__|__|__|_•|__|
|__|__|__|__|__|__|_•|
|  |  |  |  |  |  | •|
‾‾‾‾‾‾‾‾‾‾‾•‾‾‾‾‾‾‾‾‾
```

We then aggregate all the probabilities of being detected from the starting point, the vault, and
all the intersections on our path.

The runtime complexity of this solution is `w² × d`, where `w` is the width of the room, and `d` is
the number of detectors.

## Tradeoffs

• the grid's granularity will highly influence the outcome: the more intersections the burglar will
step on, the higher the probability of him being detected. This comes from the initial
simplification of deliberatly using a discrete space.

• the simplification I chose (using a grid, and traveling horizontally as much as needed while
walking up), will effectively result (sometimes) in a longer path from the entrance to the vault
than necessary, resulting in longer exposure to the detectors, although that isn't reflected by the
current algorithm.

• for simplicity's sake, right now the map parsing function brings the whole file in memory before
starting to look at it. Should the map become too large for that, we'd be able to easily change the
parsing code so that it loads the file line by line. But then we might run into other memory issues,
in other parts of the app.

• the input .map file is not validated. Should the need arise, it'd be easy to add to the MapUtils
singleton.
