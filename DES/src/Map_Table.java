import java.io.*;
import java.util.*;

public class Map_Table {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int  M;
		int[] arr1, arr2;
		System.out.print("The size:  ");
		M = sc.nextInt();

		arr1 = new int[M];
		System.out.println("Enter the items: ");
		for (int i = 0; i < M; i++) {
			arr1[i] = sc.nextInt()-1 ;
		}

		System.out.println("Result: ");
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < M; i++)
			sb.append(arr1[i]).append(", ");
		System.out.println(sb.toString());

	}

}
