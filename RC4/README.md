# RC4

The test result is like below:  

* key: 991231

* plain text: 10 9 8 7 6 5 4 3 2 1 0

* cipher text: 133 16 61 37 25 37 238 189 48 213 93

```
<Encryption>
Enter the key Length: 6
Enter the key in a line: 9 9 1 2 3 1
Enter the plain/cipher text continuously, one byte in a line. (q: quit): 
10
=> 133 
9
=> 16 
8
=> 61 
7
=> 37 
6
=> 25 
5
=> 37 
4
=> 238 
3
=> 189 
2
=> 48 
1
=> 213 
0
=> 93 
q


 ::::: Result :::::
 input:  10   9   8   7   6   5   4   3   2   1   0 
     Z: 143  25  53  34  31  32 234 190  50 212  93 
output: 133  16  61  37  25  37 238 189  48 213  93 
```



```
<Decryption>
Enter the key Length: 6
Enter the key in a line: 9 9 1 2 3 1
Enter the plain/cipher text continuously, one byte in a line. (q: quit): 
133
=> 10 
16
=> 9 
61
=> 8 
37
=> 7 
25
=> 6 
37
=> 5 
238
=> 4 
189
=> 3 
48
=> 2 
213
=> 1 
93
=> 0 
q


 ::::: Result :::::
 input: 133  16  61  37  25  37 238 189  48 213  93 
     Z: 143  25  53  34  31  32 234 190  50 212  93 
output:  10   9   8   7   6   5   4   3   2   1   0 

```

