import java.io.*;
import java.util.*;

public class RC4 {
	int keyLen = 0;
	int[] S = new int[256], T = new int[256];

	public RC4(int keyLen, int[] key) {
		this.keyLen = keyLen;
		for (int i = 0; i < 256; i++) {
			S[i] = i;
			T[i] = key[i % keyLen];
		}
		keySchedule();
	}

	private void keySchedule() {
		int j = 0;
		for (int i = 0; i < 256; i++) {
			j = (j + S[i] + T[i]) % 256;
			swap(S, i, j);
		}
	}

	private void swap(int[] arr, int i, int j) {
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}

	private int z_i = 0, z_j = 0;

	private int getZByte() {
		z_i = (z_i + 1) % 256;
		z_j = (z_j + S[z_i]) % 256;
		swap(S, z_i, z_j);
		return S[(S[z_i] + S[z_j]) % 256];

	}

	public StringBuilder sb_in = new StringBuilder().append(" input: ");
	public StringBuilder sb_z = new StringBuilder().append("     Z: ");
	public StringBuilder sb_out = new StringBuilder().append("output: ");

	public int getOutByte(int input) {
		int z, output;
		z = getZByte();
		output = input ^ z;

		appendSb(sb_in, input);
		appendSb(sb_z, z);
		appendSb(sb_out, output);

		return output;
	}

	private void appendSb(StringBuilder sb, int x) {
		sb.append(String.format("%3d ", x));
	}

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// get key and generate S[]
		System.out.print("Enter the key Length: ");
		int keyLen = Integer.parseInt(br.readLine());
		int[] key = new int[keyLen];
		System.out.print("Enter the key in a line: ");
		StringTokenizer st = new StringTokenizer(br.readLine());
		for (int i = 0; i < keyLen; i++)
			key[i] = Integer.parseInt(st.nextToken());
		RC4 rc4 = new RC4(keyLen, key);

		// encrypt/decrypt
		System.out.println("Enter the plain/cipher text continuously, one byte in a line. (q: quit): ");
		String line;
		int input, output;

		while ((line = br.readLine()) != null) {
			if (line.length() == 0) 	// skip empty input
				continue;
			if (line.charAt(0) == 'q') 	// q: quit program
				break;
			input = Integer.parseInt(line);
			output = rc4.getOutByte(input); // calc the byte stream
			System.out.printf("=> %d \n", output);
		}

		// show the result
		System.out.print("\n\n ::::: Result :::::\n");
		System.out.println(rc4.sb_in.toString());
		System.out.println(rc4.sb_z.toString());
		System.out.println(rc4.sb_out.toString());

	}

}
