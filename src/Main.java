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
            System.out.println("Error! D<=0!");
        }
        //defining array of primary numbers
        //int[] primary = new int[15];
        //primary[0]=2;
        //primary[1]=3;
        BigInteger currentPrime = BigInteger.valueOf(3);
        int currentPrimeIndex = 2;
        BigInteger nextPrime = BigInteger.ZERO;
        BigInteger p = BigInteger.ZERO;
        int count = 0;
        //Irrational alpha=Irrational.equationRoot(P,Q,1);
        //Irrational beta=Irrational.equationRoot(P,Q,-1);
        //searching for the next candidate
        while (currentPrimeIndex < 20) {
            for (BigInteger r = BigInteger.ONE; r.compareTo(currentPrime.subtract(BigInteger.ONE)) <= 0; r = r.add(BigInteger.ONE)) {
                count++;
                nextPrime = BigInteger.valueOf(2).multiply(r).multiply(currentPrime).add(BigInteger.ONE);
                //simplifying of nextPrime+1...
                p = currentPrime;
                if (Helper.jacobiSymbol(BigInteger.valueOf(D), nextPrime) == -1) {
                    if (Helper.gcd(nextPrime, BigInteger.valueOf(P * Q * D)).equals(BigInteger.ONE)) {
                        if (Helper.luca(nextPrime.add(BigInteger.ONE), P, Q).mod(nextPrime).equals(BigInteger.ZERO)) {
                            if (Helper.gcd(Helper.luca((nextPrime.subtract(BigInteger.ONE)).divide(p), P, Q), nextPrime).equals(BigInteger.ONE)) {
                                currentPrime = nextPrime;
                                currentPrimeIndex++;
                                System.out.println(nextPrime);
                                break;
                            } else {
                                System.out.println(nextPrime + " is not primary 4");
                            }
                        } else {
                            System.out.println(nextPrime + " is not primary 3");
                        }
                    } else {
                        System.out.println(nextPrime + " is not primary 2");
                    }
                } else {
                    System.out.println(nextPrime + " is not primary 1");
                }
            }
        }
    }
}