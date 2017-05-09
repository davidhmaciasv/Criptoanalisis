import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.StringTokenizer;

public class Criptografia {
	public static void main(String[] args) throws IOException {
		PrintWriter out = new PrintWriter(System.out);
		BufferedReader tec = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("llave publica separada por espacio de forma (e n)");
		StringTokenizer st = new StringTokenizer(tec.readLine());
		System.out.println("texto");
		String line = tec.readLine().toLowerCase();
		int e = Integer.parseInt(st.nextToken());
		int n = Integer.parseInt(st.nextToken());
		int[] arr = factorizaciones(n);
		int p = arr[0];
		int q = arr[1];
		int fi = (p - 1) * (q - 1);
		int d = BigInteger.valueOf(e).modInverse(BigInteger.valueOf(fi)).intValue();

		cambio
		out.println("la llave privada es " + d + " " + n);
		out.print("el resultado es: ");
		int seg = 3;
		int pots = 27;
		// for (seg = 0; pots < n; seg++)
		// pots*=27;
		System.out.println("numero de segmentacion");
		seg = Integer.parseInt(tec.readLine().trim());
		System.out.println("c para cifrado d para desifrado");
		char cifrando = tec.readLine().trim().charAt(0);
		long suma = 0;
		for (int i = 0; i < line.length(); i++) {
			int pot = seg - 1 - (i % seg);
			suma += valorDeCaracter(line.charAt(i)) * Math.pow(27, pot);
			if ((i + 1) % seg == 0) {
				// para cifrar con e, para decifrar con d
				String bin = "";
				if (cifrando == 'c')
					bin = Long.toBinaryString(e);
				else if (cifrando == 'd')
					bin = Long.toBinaryString(d);
				long res = 1;
				long m = suma;
				for (int j = bin.length() - 1; j >= 0; j--) {
					if (bin.charAt(j) == '1')
						res = (res * m) % n;
					m = (m * m) % n;
				}
				m = res;
				StringBuffer sb = new StringBuffer();
				pots = 27;
				while (pots < m) {
					sb.append(valorDeCaracter((int) m % pots));
					m -= m % pots;
					pots *= 27;
				}
				pots /= 27;
				sb.append(valorDeCaracter((int) m / pots));
				out.print(sb.reverse());
				suma = 0;
			}
		}
		out.println();
		out.close();
	}

	static int valorDeCaracter(char c) {
		if (c <= 'n')
			return c - 'a';
		if (c == 'ñ')
			return 'n' + 1 - 'a';
		return c + 1 - 'a';
	}

	static char valorDeCaracter(int c) {
		c += 'a';
		if (c <= 'n')
			return (char) c;
		if (c == 'o')
			return 'ñ';
		return (char) (c - 1);
	}

	static int[] factorizaciones(int n) {
		int[] primos = primos(1000);
		for (int i = 0; i < primos.length; i++) {
			if (n % primos[i] == 0)
				return new int[] { primos[i], n / primos[i] };
		}
		return null;
	}

	static int[] primos(int M) {
		boolean b[] = new boolean[M];
		int i, j, k, c = 2;
		for (i = 2; (k = i * i) < M; i++)
			if (!b[i])
				for (j = k; j < M; j += i)
					if (!b[j]) {
						b[j] = true;
						c++;
					}
		int r[] = new int[M - c];
		for (i = 2, j = 0; i < M; i++)
			if (!b[i])
				r[j++] = i;
		return r;
	}

}
