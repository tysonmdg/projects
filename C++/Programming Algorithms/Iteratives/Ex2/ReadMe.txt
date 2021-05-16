This code search for a sequence that has at least l>1 consequtive equal values that are equal or bigger than all values at its right.

You have to enter an integer number that indicates the size of the vector, and the size of the sequence that you are looking for. 
Then, you must indicate the vector values.

For each case, it returns a vector: the first value returned is the size of the largest sequence found, the second one 
is the number of sequences that satisfy the restriction, and the next ones its the value of the sequence from right to left.

Example input:
10 3
3 8 8 8 5 5 6 6 6 1
3 3
4 4 4
11 2
9 9 9 2 1 2 2 4 4 1 2
6 3
4 4 8 6 6 2

Example output:
3 2 8 3
3 1 2
3 2 8 2
0 0