import java.math.BigInteger;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;

/**
 * Created by USER on 19.03.2017.
 */
public class Helper {

    public static BigInteger bigIntSqRootFloor(BigInteger x)
            throws IllegalArgumentException {
        if (x.compareTo(ZERO) < 0) {
            throw new IllegalArgumentException("Negative argument.");
        }
        // square roots of 0 and 1 are trivial and
        // y == 0 will cause a divide-by-zero exception
        if (x.equals(ZERO) || x.equals(ONE)) {
            return x;
        }
        BigInteger two = BigInteger.valueOf(2);
        BigInteger y;
        // starting with y = x / 2 avoids magnitude issues with x squared
        for (y = x.divide(two);
             y.compareTo(x.divide(y)) > 0;
             y = ((x.divide(y)).add(y)).divide(two))
            ;
        return y;
    }

    static BigInteger[] decomposition(BigInteger n) {
        BigInteger[] res = new BigInteger[1000];
        for (int i = 1; i < res.length; i++) {
            res[i] = ZERO;
        }
        res[0] = ONE;
        int divisor = 2;
        int point = 0;
        while (n.compareTo(BigInteger.valueOf(2)) >= 0) {
            while (!n.mod(BigInteger.valueOf(divisor)).equals(ZERO) && bigIntSqRootFloor(n).compareTo(BigInteger.valueOf(divisor)) >= 0) {
                divisor++;
            }
            if (bigIntSqRootFloor(n).compareTo(BigInteger.valueOf(divisor)) >= 0) {
                res[point] = BigInteger.valueOf(divisor);
                point++;
                n = n.divide(BigInteger.valueOf(divisor));
                divisor = 2;
            } else {
                res[point] = n;
                break;
            }
        }
        return res;
    }

    static int jacobiSymbol(BigInteger D, BigInteger N) {
        if (D.mod(N).equals(ZERO)) {
            return 0;
        }
        int flag = 1;
        int res = 1;
        BigInteger[] decomposition = decomposition(D);
        if (D.equals(ONE)) {
            return 1;
        }
        for (int i = 0; i < decomposition.length && decomposition[i].compareTo(ZERO) > 0; i++) {
            if (decomposition[i].equals(ONE.negate())) {
                if (N.mod(BigInteger.valueOf(4)).equals(BigInteger.valueOf(3))) {
                    res *= -1;
                }
            } else if (decomposition[i].equals(BigInteger.valueOf(2))) {
                if (N.mod(BigInteger.valueOf(8)).equals(BigInteger.valueOf(3)) || N.mod(BigInteger.valueOf(8)).equals(BigInteger.valueOf(5))) {
                    res *= -1;
                }
            } else if (decomposition[i].compareTo(N) < 0) {
                if (N.mod(BigInteger.valueOf(4)).equals(BigInteger.valueOf(3)) && decomposition[i].mod(BigInteger.valueOf(4)).equals(BigInteger.valueOf(3))) {
                    flag = -1;
                }
                res *= flag * jacobiSymbol(N.mod(decomposition[i]), decomposition[i]);
            }
        }
        return res;
    }

    public static class LucaGen {
        private final int P;
        private final int Q;

        private BigInteger pn = ZERO;
        private BigInteger n = ONE;
        private BigInteger stateIndex = ONE;

        LucaGen(int P, int Q) {
            this.P = P;
            this.Q = Q;
        }

        BigInteger forIndex(BigInteger index) {
            long start = System.currentTimeMillis();
            if (index.equals(ZERO)) {
                return ZERO;
            }
            assert stateIndex.compareTo(index) <= 0;
            int toIncrement = index.subtract(stateIndex).intValueExact();
            for (int i = 0; i < toIncrement; i++) {
                increment();
            }
            System.out.printf("LucaGen.forIndex: %s computed in %s sec.\n", index, (System.currentTimeMillis() - start) / 1000.0);
            return n;
        }

        private void increment() {
            BigInteger next = nextLuca(pn, n, Q, P);
            pn = n;
            n = next;
            stateIndex = stateIndex.add(ONE);
        }

        private static BigInteger nextLuca(BigInteger pn, BigInteger n, int Q, int P) {
            assert pn.compareTo(n) < 0;
            return n.multiply(BigInteger.valueOf(P)).subtract(pn.multiply(BigInteger.valueOf(Q)));
        }
    }



    static int first = 0;
    static int last = 1;
    public static BigInteger lucaNumbers[] = {ZERO, ONE};

    static BigInteger luca(BigInteger n, int Q, int P) {
        System.out.println("luca n: " + n);
        if (n.equals(ZERO) || n.equals(ONE)) {
            return n;
        }
        BigInteger temp;
        BigInteger res = ONE;
        if (n.compareTo(BigInteger.valueOf(last + 1)) > 0) {
            for (int i = last + 1; n.compareTo(BigInteger.valueOf(i)) > 0; i++) {
                temp = lucaNumbers[1].multiply(BigInteger.valueOf(P)).subtract(lucaNumbers[0].multiply(BigInteger.valueOf(Q)));
                lucaNumbers[0] = lucaNumbers[1];
                lucaNumbers[1] = temp;
                first++;
                last++;
            }
            res = lucaNumbers[1].multiply(BigInteger.valueOf(P)).subtract(lucaNumbers[0].multiply(BigInteger.valueOf(Q)));
            lucaNumbers[0] = lucaNumbers[1];
            lucaNumbers[1] = res;
            first++;
            last++;
        } else if (n.equals(BigInteger.valueOf(last + 1))) {
            res = lucaNumbers[1].multiply(BigInteger.valueOf(P)).subtract(lucaNumbers[0].multiply(BigInteger.valueOf(Q)));
            lucaNumbers[0] = lucaNumbers[1];
            lucaNumbers[1] = res;
            first++;
            last++;
        } else {
            lucaNumbers[0] = ZERO;
            lucaNumbers[1] = ONE;
            first = 0;
            last = 1;
            for (int i = 2; n.compareTo(BigInteger.valueOf(i)) >= 0; i++) {
                temp = lucaNumbers[1].multiply(BigInteger.valueOf(P)).subtract(lucaNumbers[0].multiply(BigInteger.valueOf(Q)));
                lucaNumbers[0] = lucaNumbers[1];
                lucaNumbers[1] = temp;
                first++;
                last++;
            }
            res = lucaNumbers[1].multiply(BigInteger.valueOf(P)).subtract(lucaNumbers[0].multiply(BigInteger.valueOf(Q)));
            lucaNumbers[0] = lucaNumbers[1];
            lucaNumbers[1] = res;
            first++;
            last++;
        }

        return res;
    }

    static int pow(int a, int n) {
        if (n==0) {
            return 1;
        }
        if (n%2==1){
            return a*pow(a,n-1);
        }
        int b=pow(a,n/2);
        return b*b;
    }

    static BigInteger[] multiplyMatrix(BigInteger[] A, BigInteger[] B){
        BigInteger[] res = new BigInteger[4];
        res[0]=A[0].multiply(B[0]).add(A[1].multiply(B[2]));
        res[1]=A[0].multiply(B[1]).add(A[1].multiply(B[3]));
        res[2]=A[2].multiply(B[0]).add(A[3].multiply(B[2]));
        res[3]=A[2].multiply(B[1]).add(A[3].multiply(B[3]));
        return res;
    }

    static BigInteger[] matrixPow(BigInteger[] A, BigInteger n) {
        //if (n.equals(BigInteger.ONE)){
        //    return A;
        //}
        BigInteger[] res = {BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE};
        while (n.compareTo(BigInteger.ZERO)>0) {
            if (n.mod(BigInteger.valueOf(2)).equals(BigInteger.ONE)) {
                res=multiplyMatrix(res,A);
            }
            A = multiplyMatrix(A,A);
            n=n.divide(BigInteger.valueOf(2));
        }
        return res;
    }

    static BigInteger luca(int P, int Q, BigInteger n) {
        BigInteger res;
        BigInteger[] A = {BigInteger.ZERO, BigInteger.valueOf(-Q), BigInteger.ONE, BigInteger.valueOf(P)};
        A = matrixPow(A,n.subtract(BigInteger.ONE));
        res=A[3];
        return res;
    }

    static BigInteger[] multiplyMatrixMod(BigInteger[] A, BigInteger[] B, BigInteger mod){
        BigInteger[] res = new BigInteger[4];
        res[0]=(A[0].multiply(B[0]).add(A[1].multiply(B[2]))).mod(mod);
        res[1]=(A[0].multiply(B[1]).add(A[1].multiply(B[3]))).mod(mod);
        res[2]=(A[2].multiply(B[0]).add(A[3].multiply(B[2]))).mod(mod);
        res[3]=(A[2].multiply(B[1]).add(A[3].multiply(B[3]))).mod(mod);
        return res;
    }

    static BigInteger[] matrixPowMod(BigInteger[] A, BigInteger n, BigInteger mod) {
        //if (n.equals(BigInteger.ONE)){
        //    return A;
        //}
        BigInteger[] res = {BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE};
        while (n.compareTo(BigInteger.ZERO)>0) {
            if (n.mod(BigInteger.valueOf(2)).equals(BigInteger.ONE)) {
                res=multiplyMatrixMod(res,A,mod);
            }
            A = multiplyMatrixMod(A,A,mod);
            n=n.divide(BigInteger.valueOf(2));
        }
        return res;
    }

    static BigInteger lucaMod(int P, int Q, BigInteger n) {
        BigInteger res;
        BigInteger[] A = {BigInteger.ZERO, BigInteger.valueOf(-Q), BigInteger.ONE, BigInteger.valueOf(P)};
        A = matrixPowMod(A,n.subtract(BigInteger.ONE),n.subtract(BigInteger.ONE));
        res=A[3];
        return res;
    }
}
