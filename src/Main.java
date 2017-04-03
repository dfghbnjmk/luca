import java.math.BigInteger;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;

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
        BigInteger nextPrime = ZERO;
        BigInteger p = ZERO;

        Helper.LucaGen lucaGen = new Helper.LucaGen(P, Q);

        //searching for the next candidate
        while (currentPrimeIndex < 20) {
            for (BigInteger r = ONE; r.compareTo(currentPrime.subtract(ONE)) <= 0; r = r.add(ONE)) {
                // nextPrime = 2 * r * currentPrime + 1
                nextPrime = BigInteger.valueOf(2).multiply(r).multiply(currentPrime).add(ONE);
                if (Helper.jacobiSymbol(BigInteger.valueOf(D), nextPrime) != -1) {
                    System.out.println(nextPrime + " is not primary: (1) jacobiSymbol != -1");
                    continue;
                }
                if (!nextPrime.gcd(BigInteger.valueOf(P * Q * D)).equals(ONE)) {
                    System.out.println(nextPrime + " is not primary: (2) gcd(n, PQD) != 1");
                    continue;
                }
                BigInteger luca_n_plus_1 = lucaGen.forIndex(nextPrime.add(ONE)); // Helper.luca(nextPrime.add(ONE), P, Q);
                if (!luca_n_plus_1.mod(nextPrime).equals(ZERO)) {
                    System.out.println(nextPrime + " is not primary: (3) luca(n + 1) % n != 0");
                    continue;
                }
                BigInteger luca_2r = Helper.luca(nextPrime.subtract(ONE).divide(currentPrime), P, Q);
                if (!luca_2r.gcd(nextPrime).equals(ONE)) {
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