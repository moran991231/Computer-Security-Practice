import java.util.Arrays;

public class SHA_256 {

	private final int BLOCK_LEN = 512, BLOCK_LEN_BYTE = BLOCK_LEN / 8, BLOCK_LEN_INT = BLOCK_LEN / 32;
	private byte[] block = new byte[BLOCK_LEN_BYTE]; // input String: char -> byte(ascii) , will besliced into fixed length
	private int[] msg = new int[BLOCK_LEN_INT]; // byte[]block -> int[]msg
	private int[] W = new int[64]; // will be generated by int[]msg
	private final int[] IV = { 0x6a09e667, 0xbb67ae85, 0x3c6ef372, 0xa54ff53a, 0x510e527f, 0x9b05688c, 0x1f83d9ab,
			0x5be0cd19 }; // [8], Initial valye --> H
	private int[] H = new int[8]; // H will be the result of SHA, i.e. the hash value
	private int[] reg = new int[8]; // register for a,b,c, ...,h
	private static final int a = 0, b = 1, c = 2, d = 3, e = 4, f = 5, g = 6, h = 7; // the indice of reg[a,b,...h]
	private final int[] K = { 0x428a2f98, 0x71374491, 0xb5c0fbcf, 0xe9b5dba5, 0x3956c25b, 0x59f111f1, 0x923f82a4,
			0xab1c5ed5, 0xd807aa98, 0x12835b01, 0x243185be, 0x550c7dc3, 0x72be5d74, 0x80deb1fe, 0x9bdc06a7, 0xc19bf174,
			0xe49b69c1, 0xefbe4786, 0x0fc19dc6, 0x240ca1cc, 0x2de92c6f, 0x4a7484aa, 0x5cb0a9dc, 0x76f988da, 0x983e5152,
			0xa831c66d, 0xb00327c8, 0xbf597fc7, 0xc6e00bf3, 0xd5a79147, 0x06ca6351, 0x14292967, 0x27b70a85, 0x2e1b2138,
			0x4d2c6dfc, 0x53380d13, 0x650a7354, 0x766a0abb, 0x81c2c92e, 0x92722c85, 0xa2bfe8a1, 0xa81a664b, 0xc24b8b70,
			0xc76c51a3, 0xd192e819, 0xd6990624, 0xf40e3585, 0x106aa070, 0x19a4c116, 0x1e376c08, 0x2748774c, 0x34b0bcb5,
			0x391c0cb3, 0x4ed8aa4a, 0x5b9cca4f, 0x682e6ff3, 0x748f82ee, 0x78a5636f, 0x84c87814, 0x8cc70208, 0x90befffa,
			0xa4506ceb, 0xbef9a3f7, 0xc67178f2 }; // [64], constants

	private void init() {
		H = Arrays.copyOf(IV, 8); // H <- IV
	}
	public String getHash(String str) {
		init();
		int len = str.length();
		int len_bit = len * 8;
		int k = 0; // the length of zero padding  byte
		for (k = 0; (len + k + 9) % BLOCK_LEN_BYTE != 0; k += 1)
			;

		for (int i = BLOCK_LEN_BYTE; i < len; i += BLOCK_LEN_BYTE) { // process more than 2 blocks
			copyStrBlock(str, i - BLOCK_LEN_BYTE, BLOCK_LEN_BYTE);
			process();
		}
		
		// process remained string with zero-padding and length
		int rem = len % BLOCK_LEN_BYTE;
		if ((rem + 9 + k) == BLOCK_LEN_BYTE) { // only one process
			copyStrBlock(str, len - rem, rem);
			block[rem] = (byte) 0x80;
			fill0inBlock(rem + 1, BLOCK_LEN_BYTE - 8);
			fillMsgLen(len_bit);
			process();
			
		} else { // process needed twice
			copyStrBlock(str, len - rem, rem);
			block[rem] = (byte) 0x80;
			fill0inBlock(rem + 1, BLOCK_LEN_BYTE);
			process();
			
			fill0inBlock(0, BLOCK_LEN_BYTE - 8);
			fillMsgLen(len_bit);
			process();

		}

		return intArr2Str(H);
	}

	private String intArr2Str(int[] arr) {
		System.out.println(arr.length);
		StringBuilder sb = new StringBuilder();
		for (int x : arr)
			sb.append(String.format("%08x ", x));

		return sb.toString();
	}

	public void process() { // process the one block
		int T1, T2;
		bytes2intArr(block, msg); // int[]msg <- byte[] block
//		System.out.println("Msg: " + intArr2Str(msg));
		makeW(msg); // derive W[] from msg[]
		
		// reg <- H
		for (int i = 0; i < 8; i++)
			reg[i] = H[i];
		
		// 64 steps repeated to process one block!
		for (int j = 0; j < 64; j++) {
			T1 = reg[h] + largeSigma1(reg[e]) + Ch(reg[e], reg[f], reg[g]) + K[j] + W[j];
			T2 = largeSigma0(reg[a]) + maj(reg[a], reg[b], reg[c]);
			reg[h] = reg[g];
			reg[g] = reg[f];
			reg[f] = reg[e];
			reg[e] = reg[d] + T1;
			reg[d] = reg[c];
			reg[c] = reg[b];
			reg[b] = reg[a];
			reg[a] = T1 + T2;
//			System.out.printf("reg %2d: %s \n",j, intArr2Str(reg));
			
		}
		
		// the new H <- the old H + reg
		for (int i = 0; i < 8; i++)
			H[i] += reg[i];
	}

	private void bytes2intArr(byte[] src, int[] dst) {
		for (int i = 0; i < BLOCK_LEN_INT; i++) {
			dst[i] = ((src[i * 4] & 0xFF) << 24) | ((src[i * 4 + 1] & 0xFF) << 16) | ((src[i * 4 + 2] & 0xFF) << 8)
					| (src[i * 4 + 3] & 0xFF);
		}
	}

	private void makeW(int[] msg) {
		for (int j = 0; j < 16; j++)
			W[j] = msg[j];
		for (int j = 16; j < 64; j++)
			W[j] = smallSigma1(W[j - 2]) + W[j - 7] + smallSigma0(W[j - 15]) + W[j - 16];

	}

	private int Ch(int x, int y, int z) {
		return (x & y) ^ ((~x) & z);
	}

	private int maj(int x, int y, int z) {
		return (x & y) ^ (x & z) ^ (y & z);
	}

	private int largeSigma0(int x) {
		int x1 = Integer.rotateRight(x, 2);
		int x2 = Integer.rotateRight(x, 13);
		int x3 = Integer.rotateRight(x, 22);

		return x1 ^ x2 ^ x3;
	}

	private int largeSigma1(int x) {
		int x1 = Integer.rotateRight(x, 6);
		int x2 = Integer.rotateRight(x, 11);
		int x3 = Integer.rotateRight(x, 25);

		return x1 ^ x2 ^ x3;
	}

	private int smallSigma0(int x) {
		int x1 = Integer.rotateRight(x, 7);
		int x2 = Integer.rotateRight(x, 18);
		int x3 = x>>>3;

		return x1 ^ x2 ^ x3;
	}

	private int smallSigma1(int x) {
		int x1 = Integer.rotateRight(x, 17);
		int x2 = Integer.rotateRight(x, 19);
		int x3 = x>>>10;

		return x1 ^ x2 ^ x3;
	}

	public void copyStrBlock(String str, int si, int len) {
		for (int i = 0; i < len; i++) {
			block[i] = (byte) (str.charAt(si + i) & 0x7f);
		}
	}

	private void fill0inBlock(int si, int ei) {
		for (int i = si; i < ei; i++)
			block[i] = 0;
	}

	private void fillMsgLen(long msgLen_bit) {
		final long mask = 0xFF;
		for (int i = 0; i < 8; i++) {
			block[BLOCK_LEN_BYTE - 8 + i] = (byte) ((msgLen_bit >> (56 - 8 * i)) & mask);
		}
	}
}