# Kd-Tree

## 1. Intro

This is the detail of the assignment 5 Kd-Tree.
Kd-Tree is a BST which aims at solving the geometric problem.

This assignment asks us to implement the 2d version, that is the **2d-Tree**

**Notice:**  
To use the auto-complete I wrap the src into a package name `algs4_assignment`;  
But to submit the assignment, you need to put the src into the `default` package.

**DO REMEMBER IT!!**

## 2. Solutions

The difficult and important parts of this assignment are **Insert and Search**, **Range**, **Nearest**.

But we need to define our node at first.

### 2.0 Node design

You might think the node is very simple and straightforward.
Just use the point as the node, that is done.

It's OK, but we want to have more convenience when we are doing the two specific 2d-Tree
function, called **range** and **nearest**.

To achieve that, we add the rectangle which corresponding to the node.

> This rectangle mean the **smallest** rectangle which can contain the point.
The rectangle corresponding to the root is **the whole unit space**.
And the rectangle corresponding to the `root.lb` is the left side of the split
unit space.

So, the node looks like this:

```java
private class Node {
    Point2D p;
    RectHV rectHV;
    Node lb;
    Node rt;
}
```

### 2.1 Insert and Search

The key to this problem is figure out
**how the 2d-tree being constructed, and how it works**.

The 2d-Tree has the following feature:

1. In EVEN_LEVEL, split the space at the point as **vertical**.
2. In ODD_LEVEL, split the space at the point as **horizontal**.
3. Insert at EVEN_LEVEL, x-coordinate as the key.

    > If less, go to the left.
    Else, go to the right

4. Insert at ODD_LEVEL, y-coordinate as the key.

    > If less, go down.
    Else, go up.

Ok, that is how we define the 2d-Tree.
Following the rules to construct the tree.
When searching, **check the popper key** to determine which subtree to go.

### Range

The first application of 2d-Tree is to find the points inside the specific rectangle.

Since we have add a rectangle to a node, to solve it will be much more easier.

Just check if the node's rectangle intersects with the given one.
**If there is no intersects, it is no need to check its subtree**.
And just check if the node's point inside the rectangle, and keep going
deep into the subtree.

And it's done.

### Nearest

That is the most difficult part.
This problem is to find the nearest point to the given position.

At the very beginning, I am very confused why I
just cannot get 100%.

And thanks to @borgnix, with his help, I eventually achieve 100%.

So, the basic theory is that:

We iterate the node, and continuous to find the nearest.
Of cause, we need to skip some useless point to improve the performance.

First thing is, if the distance to the node's rectangle even **farther** than the
current champion. It is **NO** need to check the node **and its subtrees**.

That is mentioned at the assignment specification. And it's very easy to understand.

It is because the point is inside the node's rectangle.

So, if we using the current minimum distance
as the  **radius** and the given position as the **center** to draw a circle.

![hehe](http://ww3.sinaimg.cn/large/0060lm7Tgw1f2ay1hafupj307z07l74f.jpg)

It's **impossible** to find the point **inside** the rectangle also inside the circle.
Like the point `(8, 1)` shown above.

Obviously, the champion will only appears **inside the circle**.

But it's **NOT** enough, if you only do this optimization, you will stick at 95% -99%, and will not get 100%.

The other important thing is, the champion will more likely to appears at the **near side**.

Therefore, it is reasonable and necessary to **check the near side first**.

And to reduce the calculations of distance, it is wise to **cache** the distance
at the very beginning.

So, the instructions are as following:

1. Initialize the champion as root.
2. Update the champion when reach a new node.
3. Check if the subtrees are empty
    1. If both null, return the champion
    2. If either null, check the rectangle distance to determine whether to go deep into
4. If both are NOT null, figure out which side is near side.

    > This section will be a little tricky, to reduce the distance calculations,
    I recommend to use `contain()` method instead.
    If one of the subtree node's rectangle contain the **given point**, and it is the near side.

5. Go deep into the near side first.
6. Check the far side rectangle distance with the **new champion**.
7. If it is still **possible** to have champion at the far side, check it.





