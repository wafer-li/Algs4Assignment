# Intro
This is the detail of the assignment 1 Percolation

**Notice:**  
To use the auto-complete I wrap the src into a package name `algs4_assignment`;  
But to submit the assignment, you need to put the src into the `default` package.

**DO REMEMBER IT!!**

# Solution

## Basic (Remain with the backwash bug)

The percolation problem is actually the dynamic link problem of the virtual top site and the virtual bottom site.  
So, as a dynamic link problem, we can use the UF algorithm to solve it.

That is:

1. Construct the UF object which contains the whole map.
2. The top line of the sites connect to the virtual top.
3. When a site is opened, it will auto connect to the open site which is around it.

    > That's we need to check whether its around sites are opened.  
    If opened, we connect to it. If not, we do nothing.
    
4. If the virtual top is connected to the virtual bottom, the map is percolate.


**Conclusion:**

This algorithm is okay, but it could only be used to solve the percolation problem.  
When you check whether the site is Full, it is not correct totally.  
Because using the tow virtual will cause the backwash problem.

> The site is Full: 
>> If a site has a **direct** path connect to the top, we call it full.

> Backwash: 
>> The most simple idea to check whether the site is full is to check whether it's connected to the top.  
But if the map is percolated, the site which connect to the bottom and has **NO DIRECT PATH** connect to the top is also
connect to the top. It's just like the water pouring into the bottom, and it surge up to the site.  
That is called **Backwash**.


## Improve 1: Using the two UF object to solve the backwash problem (Easier)

To solve the backwash problem, the simplest way is using one more UF object which has no **VirtualBottom**.  
The second UF object is just like the pipe, The site is full needs to match two conditions:

1. The site is connect to the top(So that we can place our pipe in it)
2. The site has pipe(In the second UF, it is connected to the bottom)

When the map is percolated, because the second UF has no virtual bottom, the water will not flow back.

So, when we open a site, we need to open the **path**, that is the primary UF object's work.  
And we need to lay our **pipe**, that is the secondary UF object's work.

Remember that, when the site is at the last row, and it has no open sites around it, we do NOT need to lay our pipe.  
In the other situations, the secondary UF object do the same job of the primary UF object.

**Conclusion:**

This way is much more easier, but it will cost more memory because of the duplicated UF object.


## Improve 2: Using an byte array to replace with the secondary UF object.

To reduce our memory usage, we can use a byte array to replace the secondary UF object

As we know, the UF only use the component's identifier, that's the root of the tree.

So we can use this point, we construct a byte array, use it as the `id[]` inside the UF object.
And the UF contains **NO virtualBottom site**

When a component identifier is connected to the bottom, the component is connected to the bottom.  
When the around side component is connect to the bottom or the current component itself is connected to the bottom, it will
**transfer** this property to itself and the around side.

And when the virtualTop's component is connected to the bottom, the map is percolated.

Because no virtualBottom site in the UF, it will not cause the backwash

And we reduce the memory usage by cutting off a UF, although we add a extra `byte[]`, through it we cut off 2 `int[]`,
so it is worth doing that.

The instructions are as below:

1. As row 1, connect it to the virtual top
2. As row N, mark its **identifier** as `SITE_CONNECT_TO_BOTTOM`
3. As the normal row, check whether the **identifier** of itself or the identifiers of the around side are marked as `SITE_CONNECT_TO_BOTTOM`

    > If true, mark the identifier of both itself and the around side as `SITE_CONNECT_TO_BOTTOM`
    
4. `percolate()`

    > Check if the identifier of the component is marked as `SITE_CONNECT_TO_BOTTOM`
    
5. `isFull()`

    > Check if the site is connected to the virtual top
    

### Small improvement of Improvement 2

Because byte can represent -127 ~ 128, so we can only use one array to represent the 3 statuses of the site.

So, we just use one byte array.

**Notice:**

To using the UF's `find()` method to locate the identifier of the site, we can only use the **One Dimension** array.  
Or, we must implement a 2 dimension UF object to achieve this.
