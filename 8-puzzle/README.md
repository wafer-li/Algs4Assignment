# 8 Puzzle

## Intro
This is the detail of the assignment 4 8 Puzzle.

8 Puzzle is the the problem of  sliding puzzle game.  
The goal is rearrange the numbers within the 3-by-3 grid,  
which contains one piece of blank, and use the **minimum** moves.

**Notice:**  
To use the auto-complete I wrap the src into a package name `algs4_assignment`;  
But to submit the assignment, you need to put the src into the `default` package.

**DO REMEMBER IT!!**

## Solutions

### Main idea

To solve this problem, we use **A\* algorithm**,  
which is the best-first search algorithm mainly implemented using the **priority queue**.

The main idea of this algorithm is:

1. Start with the initial node, insert it into the priority queue
2. Remove the max/min priority node.
3. Insert all its neighbors.

Repeat 2 and 3, until the removed node match the goal.

That is how we find our goal.  
As you can see, the priority is the key, so lets talk about how to define the priority.

### Priority

There is two priority function according the specification.

1. The Hamming Priority

    > The number of blocks in wrong position + The number of moves so far to get to the node
    
    > **It's mentioned that the hamming priority will be slower**  
    **DO NOT SUBMIT THE HAMMING FUNCTION VERSION**  
    **DO REMEMBER!!!!**

2. The Manhattan Priority

    > The sum of the manhattan distance between the blocks and goal  
    > \+ The number of the moves so far to get to the node
    
    > Manhattan distance: The horizontal distance + The vertical distance
    
    
According to the specification, it asks us to submit the manhattan version.  
There is definitely some causes to make the hamming function so slow.

### Search Node

So, it is time to design our node.

Of cause, the search node need to have the board state

Because both priority functions mention the **the number of moves**,  
we need to have `moves` abbr.

And since we need to print the sequences of rearranging the grid, it is necessary to 
**tracking back** of the goal node.

Therefore, we need a `prev` node reference.

At last, due to the costly priority calculating, we could have a priority cache to store the priority value.

And to make the Priority Queue function, the search node need to be comparable.

Therefore, here comes our search node:

```java
private class SearchNode implements Comparable<SearchNode> {
    // Abbr
    private Board board;
    private int moves;
    private SearchNode prev;
    private int cachedPriority = -1;
    
    @Override
    public int compareTo(SearchNode y) {
    
    }

}
```

### Generate the Sequences

Remember, the A\* Algorithm is used to **find the goal node**. It cannot help us to generate the sequences.

The way we generate it is using the `prev` node reference, or *link* to tracking back to the initial node.

That is the Game Tree, which described in the specification.

Because we need to print the reverse order of tracking back. It's the best to use **Stack** to store the node and
eventually print them out.

> The order we need to print out is **initial -> goal**  
The order we tracking back is **goal -> initial**

