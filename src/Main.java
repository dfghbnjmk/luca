import java.math.BigInteger;

/**
 * Created by USER on 08.03.2017.
 */
public class Main {
    public static void main(String[] args) {
        //defining Luca sequence
        int P = 6;
        int Q = 6;
        int D = P * P - 4 * Q;
        if (D <= 0) {
            System.out.println("Error! D <= 0!");
        }
        BigInteger currentPrime = BigInteger.valueOf(3);
        int currentPrimeIndex = 2;
        BigInteger nextPrime = BigInteger.ZERO;
        BigInteger p = BigInteger.ZERO;

        //searching for the next candidate
        while (currentPrimeIndex < 20) {
            for (BigInteger r = BigInteger.ONE; r.compareTo(currentPrime.subtract(BigInteger.ONE)) <= 0; r = r.add(BigInteger.ONE)) {
                // nextPrime = 2 * r * currentPrime + 1
                nextPrime = BigInteger.valueOf(2).multiply(r).multiply(currentPrime).add(BigInteger.ONE);
                if (Helper.jacobiSymbol(BigInteger.valueOf(D), nextPrime) != -1) {
                    System.out.println(nextPrime + " is not primary: (1) jacobiSymbol != -1");
                    continue;
                }
                if (!Helper.gcd(nextPrime, BigInteger.valueOf(P * Q * D)).equals(BigInteger.ONE)) {
                    System.out.println(nextPrime + " is not primary: (2) gcd(n, PQD) != 1");
                    continue;
                }
                if (!Helper.luca(nextPrime.add(BigInteger.ONE), P, Q).mod(nextPrime).equals(BigInteger.ZERO)) {
                    System.out.println(nextPrime + " is not primary: (3) luca(n + 1) % n != 0");
                    continue;
                }
                BigInteger luca_2r = Helper.luca(nextPrime.subtract(BigInteger.ONE).divide(currentPrime), P, Q);
                if (!Helper.gcd(luca_2r, nextPrime).equals(BigInteger.ONE)) {
                    System.out.println(nextPrime + " is not primary: (4) gcd(luca(2r), n) != 1");
                    continue;
                }
                currentPrime = nextPrime;
                currentPrimeIndex++;
                System.out.println(nextPrime);
                break;
            }
        }
    }
}