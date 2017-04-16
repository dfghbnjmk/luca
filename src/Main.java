import java.math.BigInteger;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;

/**
 * Created by USER on 08.03.2017.
 */
public class Main {
    public static void main(String[] args) {
        /*BigInteger[] A={BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE};
        BigInteger[] B={BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE};
        //BigInteger[] C=Helper.multiplyMatrix(A,B);
        BigInteger[] C = Helper.matrixPow(A,BigInteger.valueOf(4));
        System.out.println(C[0]);
        System.out.println(C[1]);
        System.out.println(C[2]);
        System.out.println(C[3]);*/

        //defining Luca sequence
        int P = 6;
        int Q = 6;
        int D = P * P - 4 * Q;
        if (D <= 0) {
            System.out.println("Error! D <= 0!");
        }
        BigInteger currentPrime = BigInteger.valueOf(3);
        int currentPrimeIndex = 2;
        BigInteger p = ZERO;

        Helper.LucaGen lucaGen = new Helper.LucaGen(P, Q);

        //searching for the next candidate
        /*while (currentPrimeIndex < 20) {
            for (BigInteger r = ONE; r.compareTo(currentPrime.subtract(ONE)) <= 0; r = r.add(ONE)) {
                // nextPrime = 2 * r * currentPrime + 1
                BigInteger trial = BigInteger.valueOf(2).multiply(r).multiply(currentPrime).add(ONE);
                if (Helper.jacobiSymbol(BigInteger.valueOf(D), trial) != -1) {
                    System.out.println(trial + " is not primary: (1) jacobiSymbol != -1");
                    continue;
                }
                if (!trial.gcd(BigInteger.valueOf(P * Q * D)).equals(ONE)) {
                    System.out.println(trial + " is not primary: (2) gcd(n, PQD) != 1");
                    continue;
                }
                BigInteger luca_n_plus_1 = lucaGen.forIndex(trial.add(ONE));
                if (!luca_n_plus_1.mod(trial).equals(ZERO)) {
                    System.out.println(trial + " is not primary: (3) luca(n + 1) % n != 0");
                    continue;
                }
                BigInteger luca_2r = Helper.luca(trial.subtract(ONE).divide(currentPrime), P, Q);
                if (!luca_2r.gcd(trial).equals(ONE)) {
                    System.out.println(trial + " is not primary: (4) gcd(luca(2r), n) != 1");
                    continue;
                }
                currentPrime = trial;
                currentPrimeIndex++;
                System.out.println(trial);
                break;
            }
        }*/
        while (currentPrimeIndex < 20) {
            for (BigInteger r = ONE; r.compareTo(currentPrime.subtract(ONE)) <= 0; r = r.add(ONE)) {
                // nextPrime = 2 * r * currentPrime + 1
                BigInteger trial = BigInteger.valueOf(2).multiply(r).multiply(currentPrime).add(ONE);
                if (Helper.jacobiSymbol(BigInteger.valueOf(D), trial) != -1) {
                    System.out.println(trial + " is not primary: (1) jacobiSymbol != -1");
                    continue;
                }
                if (!trial.gcd(BigInteger.valueOf(P * Q * D)).equals(ONE)) {
                    System.out.println(trial + " is not primary: (2) gcd(n, PQD) != 1");
                    continue;
                }
                BigInteger luca_n_plus_1 = Helper.luca(P,Q,trial.add(BigInteger.ONE));
                if (!luca_n_plus_1.mod(trial).equals(ZERO)) {
                    System.out.println(trial + " is not primary: (3) luca(n + 1) % n != 0");
                    continue;
                }
                BigInteger luca_2r = Helper.luca(P,Q,trial.subtract(ONE).divide(currentPrime));
                if (!luca_2r.gcd(trial).equals(ONE)) {
                    System.out.println(trial + " is not primary: (4) gcd(luca(2r), n) != 1");
                    continue;
                }
                currentPrime = trial;
                currentPrimeIndex++;
                System.out.println(trial);
                break;
            }
        }
    }
}