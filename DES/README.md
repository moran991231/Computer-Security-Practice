# DES
Data Encryption Standard practice (2022-Spring, Computer Security, Univ. of Seoul)  

See the full description of DES here: [https://page.math.tu-berlin.de/~kant/teaching/hess/krypto-ws2006/des.htm](https://page.math.tu-berlin.de/~kant/teaching/hess/krypto-ws2006/des.htm)  

Java 

## Encryption

> Plain Text: 0123456789ABCDEF  
>
> Key:  133457799BBCDFF1  
>
> Result: 85E813540F0AB405



Enter the mode, plain text and key, then the program generate the subkeys automatically and will show the cipher text

```
Enter the mode (0:Encryption/ else:Decryption): 0
Enter the plain/cipher text in hexadecimal: 
0123456789ABCDEF
Enter the key text in hexadecimal: 
133457799BBCDFF1
:::: Plain -> Cipher ::::
round    left        right          subkey    
 1     F0AAF0AA     EF4A6544     1B02EFFC7072 
 2     EF4A6544     CC017709     79AED9DBC9E5 
 3     CC017709     A25C0BF4     55FC8A42CF99 
 4     A25C0BF4     77220045     72ADD6DB351D 
 5     77220045     8A4FA637     7CEC07EB53A8 
 6     8A4FA637     E967CD69     63A53E507B2F 
 7     E967CD69     064ABA10     EC84B7F618BC 
 8     064ABA10     D5694B90     F78A3AC13BFB 
 9     D5694B90     247CC67A     E0DBEBEDE781 
10     247CC67A     B7D5D7B2     B1F347BA464F 
11     B7D5D7B2     C5783C78     215FD3DED386 
12     C5783C78     75BD1858     7571F59467E9 
13     75BD1858     18C3155A     97C5D1FABA41 
14     18C3155A     C28C960D     5F43B7F2E73A 
15     C28C960D     43423234     BF918D3D3F0A 
16     43423234     0A4CD995     CB3D8B0E17F5 
--------------------------------------------------
Cipher text: 85E813540F0AB405  1000010111101000000100110101010000001111000010101011010000000101
--------------------------------------------------

```



## Decryption

How to decrypt the text is simillar!  

> Cipher Text: 85E813540F0AB405 
>
> Key:  133457799BBCDFF1  
>
> Result:  0123456789ABCDEF  

Enter the mode, cipher text and key.

```
Enter the mode (0:Encryption/ else:Decryption): 1
Enter the plain/cipher text in hexadecimal: 
85E813540F0AB405
Enter the key text in hexadecimal: 
133457799BBCDFF1
:::: Cipher -> Plain ::::
round    left        right          subkey    
16     43423234     C28C960D     CB3D8B0E17F5 
15     C28C960D     18C3155A     BF918D3D3F0A 
14     18C3155A     75BD1858     5F43B7F2E73A 
13     75BD1858     C5783C78     97C5D1FABA41 
12     C5783C78     B7D5D7B2     7571F59467E9 
11     B7D5D7B2     247CC67A     215FD3DED386 
10     247CC67A     D5694B90     B1F347BA464F 
 9     D5694B90     064ABA10     E0DBEBEDE781 
 8     064ABA10     E967CD69     F78A3AC13BFB 
 7     E967CD69     8A4FA637     EC84B7F618BC 
 6     8A4FA637     77220045     63A53E507B2F 
 5     77220045     A25C0BF4     7CEC07EB53A8 
 4     A25C0BF4     CC017709     72ADD6DB351D 
 3     CC017709     EF4A6544     55FC8A42CF99 
 2     EF4A6544     F0AAF0AA     79AED9DBC9E5 
 1     F0AAF0AA     CC00CCFF     1B02EFFC7072 
--------------------------------------------------
Plain text: 0123456789ABCDEF  0000000100100011010001010110011110001001101010111100110111101111
--------------------------------------------------
```

