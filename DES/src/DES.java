import java.util.*;

public class DES {
	public static void main(String[] args) {
		
		// Get Input Data: mode, plain/cipher text, key
		int mode = 0; // 0:Encrpytion, else: Decryption
		String text, keyText;
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter the mode (0:Encryption/ else:Decryption): ");
		mode = sc.nextInt();
		sc.nextLine();
		System.out.println("Enter the plain/cipher text in hexadecimal: ");
		text = sc.nextLine();
		System.out.println("Enter the key text in hexadecimal: ");
		keyText = sc.nextLine();
		sc.close();

		// make a Key object and a Cryptography object
		Key key = new Key(hex2bitStream(keyText));
		Cryptography cr = new Cryptography(hex2bitStream(text));

		// do encrypt/decrypt
		encrypt_decrypt(cr, key, mode);
	}

	public static void encrypt_decrypt(Cryptography cr, Key key, int mode) {
		final String format = "%2d     %08X     %08X     %012X \n"; // printf format: round, left, right, subkey

		// Calculate the sixteen rounds
		if (mode == 0) {
			System.out.println(":::: Plain -> Cipher ::::");
			System.out.println("round    left        right          subkey    ");

			for (int i = 0; i < 16; i++) { // subkey1 ~ subkey16
				cr.round(key.subkeys[i]);
				System.out.printf(format, i + 1, bitStream2hex(cr.left), bitStream2hex(cr.right), key.subkeys[i]);
			}
		} else {
			System.out.println(":::: Cipher -> Plain ::::");
			System.out.println("round    left        right          subkey    ");
			for (int i = 15; 0 <= i; i--) { // subkey16 ~ subkey1
				cr.round(key.subkeys[i]);
				System.out.printf(format, i + 1, bitStream2hex(cr.left), bitStream2hex(cr.right), key.subkeys[i]);
			}
		}

		// Postprocess after sixteen rounds
		cr.calcOutput();

		// Print result
		System.out.println("--------------------------------------------------");
		System.out.printf("%s text: %016X  %s\n", (mode == 0 ? "Cipher" : "Plain"), bitStream2hex(cr.output),
				bitStream2str(cr.output));
		System.out.println("--------------------------------------------------");

	}

	static byte[] hex2bitStream(long val) {
		byte[] bits = new byte[64];
		for (int i = 0; i < 64; i++) {
			bits[63 - i] = (byte) ((val >> i) & 1);
		}
		return bits;
	}

	static byte[] hex2bitStream(String s) {
		long val = Long.parseUnsignedLong(s, 16);
		return hex2bitStream(val);
	}

	static long bitStream2hex(byte[] bits) {
		long ret = 0L;
		int len = bits.length;
		for (int i = 0; i < len; i++) {
			ret |= ((long) bits[len - 1 - i]) << i;
		}
		return ret;
	}

	static String bitStream2str(byte[] bits) {
		StringBuilder sb = new StringBuilder();
		for (byte bin : bits)
			sb.append(bin);
		return sb.toString();
	}

	static void permutate(byte[] src, byte[] dst, byte[] map) {
		// shuffle the elements of array in order of map
		// dst can be compressed or expanded or maintained by mapping
		for (int i = 0; i < dst.length; i++)
			dst[i] = src[map[i]];
	}

}

