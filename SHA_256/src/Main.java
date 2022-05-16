
import java.io.*; 
public class Main {

	public static void main(String[] args) throws IOException { 
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		System.out.print("The input: ");
		String input = br.readLine();
		SHA_256 sha = new SHA_256();
		String hash = sha.getHash(input);
		System.out.println("hash: "+hash);
	}

}
