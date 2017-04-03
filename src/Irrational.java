import java.math.BigInteger;
import java.util.Iterator;

/**
 * Created by USER on 14.03.2017.
 */
public class Irrational {
    int integer;
    int additional;
    int root;

    BigInteger bInteger;
    BigInteger bAdditional;
    BigInteger bRoot;

    Irrational(int additional, int integer, int root){
        this.additional=additional;
        this.integer=integer;
        this.root=root;
    }

    Irrational(BigInteger additional, BigInteger integer, BigInteger root){
        this.bAdditional=additional;
        this.bInteger=integer;
        this.bRoot=root;
    }

    /*static public Irrational squareRoot(int D) {
        Irrational result = new Irrational(0,1,1);
        int[] mass = Helper.decomposition(D);
        int i=0;
        while (mass[i]!=0 && i<mass.length-1){
            if (mass[i]==mass[i+1]){
                result.integer*=mass[i];
                i+=2;
            }
            else {
                result.root*=mass[i];
                i+=1;
            }
            if (i==mass.length-1){
                result.root*=mass[i];
            }
        }
        return result;
    }

    static Irrational equationRoot(int P, int Q, int flag){
        int D=P*P-4*Q;
        Irrational dSqareRoot=squareRoot(D);
        Irrational alpha=new Irrational(P/2, dSqareRoot.integer*flag/2, dSqareRoot.root);
        return alpha;
    }*/

    static Irrational newtonFormula(Irrational alpha, int power){
        Irrational res = new Irrational(0,0, alpha.root);
        for (int k=0;k<=power;k++){
            if (k%2==0){
                res.additional+=Helper.cnk(power,k)*Helper.pow(alpha.additional,power-k)*Helper.pow(alpha.integer,k)*Helper.pow(alpha.root,k/2);
            }
            else {
                res.integer+=Helper.cnk(power,k)*Helper.pow(alpha.additional,power-k)*Helper.pow(alpha.integer,k)*Helper.pow(alpha.root,(k-1)/2);
            }
        }
        return res;
    }

    static Irrational dif(Irrational a, Irrational b) {
        if (a.root==b.root){
            Irrational res = new Irrational(a.additional-b.additional, a.integer-b.integer, a.root);
            return res;
        }
        else return new Irrational(0,0,0);
    }

    static int reduction(Irrational a, Irrational b){
        if(a.integer>b.integer){
            if(a.integer%b.integer==0){
                if (a.additional/(a.integer/b.integer)==b.additional){
                    if (a.additional==0){
                        return (a.integer/b.integer);
                    }
                    else {
                        return a.additional/(a.integer/b.integer);
                    }
                }
                else return 0;
            }
            else return 0;
        }
        else return 0;
    }

    static int lucaSequenceMember(Irrational alpha, Irrational beta, int n){
        int res=0;
        Irrational newAlpha=newtonFormula(alpha,n);
        Irrational newBeta=newtonFormula(beta,n);
        Irrational numerator=dif(newAlpha,newBeta);
        Irrational denumerator=dif(alpha,beta);
        res=reduction(numerator,denumerator);
        return res;
    }
}
