import java.math.BigInteger;

public class Poly {
    private BigInteger coeff;
    private BigInteger expo;
    
    public BigInteger getCoeff() {
        return coeff;
    }
    
    public BigInteger getExpo() {
        return expo;
    }
    //get methods
    
    public void setCoeff(BigInteger coeff) {
        this.coeff = coeff;
    }
    
    public void setExpo(BigInteger expo) {
        this.expo = expo;
    }
    //set methods
    
    public Poly() {
        coeff = null;
        expo = null;
    }
    
    public Poly(String polyReg) {
        if (polyReg.matches("[-\\+]?\\d+")) {
            coeff = new BigInteger("0");
            expo = new BigInteger("0");
        } else if (polyReg.matches("[\\+]?\\s*[x](\\s*\\^\\s*[-\\+]?\\d+)?")) {
            int pow = polyReg.indexOf("^") + 1;
            coeff = new BigInteger("1");
            if (pow == 0) {
                expo = new BigInteger("1");
                //eg. x
            } else {
                expo = new BigInteger(polyReg.substring(pow).trim());
            }
        } else if (polyReg.matches("[-]\\s*[x](\\s*\\^\\s*[-\\+]?\\d+)?")) {
            int pow = polyReg.indexOf("^") + 1;
            coeff = new BigInteger("-1");
            if (pow == 0) {
                expo = new BigInteger("1");
                //eg. -x
            } else {
                expo = new BigInteger(polyReg.substring(pow).trim());
            }
        } else {
            int pow = polyReg.indexOf("^") + 1;
            int coe = polyReg.indexOf("*");
            coeff = new BigInteger(polyReg.substring(0, coe).trim());
            if (pow == 0) {
                expo = new BigInteger("1");
                //eg. 4 * x
            } else {
                expo = new BigInteger(polyReg.substring(pow).trim());
            }
        }
    }
    //constructors
    
    public Poly diff(String op) {
        Poly r = new Poly();
        r.setCoeff(this.getCoeff().multiply(this.getExpo()));
        BigInteger zero = new BigInteger("0");
        if (this.getExpo().compareTo(zero) == 0) {
            r.setExpo(zero);
        } else {
            r.setExpo(this.getExpo().subtract(new BigInteger("1")));
        }
        //judge ops
        if ((op.matches("\\s*\\+\\s*\\+\\s*")) ||
                (op).matches("\\s*-\\s*-\\s*")) {
            return r;
        } else if ((op.matches("\\s*\\+\\s*-\\s*")) ||
                (op).matches("\\s*-\\s*\\+\\s*")) {
            r.setCoeff(r.getCoeff().negate());
            return r;
        } else if (op.matches("\\s*\\+\\s*")) {
            return r;
        } else if (op.matches("\\s*-\\s*")) {
            r.setCoeff(r.getCoeff().negate());
            return r;
        }
        return r;
    }
    //differentiate method
    
    @Override
    public String toString() {
        String term = new String();
        BigInteger zero = new BigInteger("0");
        BigInteger one = new BigInteger("1");
        if (coeff.compareTo(zero) == 0) {
            term = "+0";
        } else {
            if (coeff.compareTo(zero) > 0 && expo.compareTo(zero) == 0) {
                term = "+" + coeff;
            } else if (coeff.compareTo(zero) < 0 && expo.compareTo(zero) == 0) {
                term = coeff.toString();
            } else if (coeff.compareTo(zero) > 0) {
                if (coeff.compareTo(one) == 0) {
                    if (expo.compareTo(one) == 0) {
                        term = "+x";
                    } else {
                        term = "+x^" + expo;
                    }
                } else {
                    if (expo.compareTo(one) == 0) {
                        term = "+" + coeff + "*x";
                    } else {
                        term = "+" + coeff + "*x^" + expo;
                    }
                }
            } else if (coeff.compareTo(zero) < 0) {
                if (coeff.compareTo(one) == 0) {
                    if (expo.compareTo(one) == 0) {
                        term = "-x";
                    } else {
                        term = "-x^" + expo;
                    }
                } else {
                    if (expo.compareTo(one) == 0) {
                        term = coeff.toString() + "*x";
                    } else {
                        term = coeff.toString() + "*x^" + expo;
                    }
                }
            }
        }
        return term;
    }
}
