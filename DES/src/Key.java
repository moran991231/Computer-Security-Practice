
public class Key {
	// the map: 64->56
	private final byte[] map64_56 = { 56, 48, 40, 32, 24, 16, 8, 0, 57, 49, 41, 33, 25, 17, 9, 1, 58, 50, 42, 34, 26,
			18, 10, 2, 59, 51, 43, 35, 62, 54, 46, 38, 30, 22, 14, 6, 61, 53, 45, 37, 29, 21, 13, 5, 60, 52, 44, 36, 28,
			20, 12, 4, 27, 19, 11, 3 };
	private final int[] shift_amount_at_round = { 1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1 };
	// the map: 56->48
	private final byte[] map56_48 = { 13, 16, 10, 23, 0, 4, 2, 27, 14, 5, 20, 9, 22, 18, 11, 3, 25, 7, 15, 6, 26, 19,
			12, 1, 40, 51, 30, 36, 46, 54, 29, 39, 50, 44, 32, 47, 43, 48, 38, 55, 33, 52, 45, 41, 49, 35, 28, 31 };
	public byte[] key;
	public long[] subkeys = new long[16];

	public Key(byte[] key_in) {
		key = key_in;
		genSubkey();
	}

	private void genSubkey() {
		byte[] temp = new byte[56];
		byte[] subkey = new byte[48];
		DES.permutate(key, temp, map64_56);

		for (int round = 0; round < 16; round++) {
			temp = key_shift(temp, shift_amount_at_round[round]);
			DES.permutate(temp, subkey, map56_48);
			subkeys[round] = DES.bitStream2hex(subkey);
		}
	}

	private byte[] key_shift(byte[] arr56, int amount) {
		byte[] ret = new byte[56];
		int p;
		for (int i = 0; i < 28; i++) {
			p = (i + amount + 28) % 28;
			ret[i] = arr56[p];
			ret[i + 28] = arr56[28 + p];
		}

		return ret;
	}

}