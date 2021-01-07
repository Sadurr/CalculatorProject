package sample;

import com.sun.media.sound.SF2InstrumentRegion;

import java.math.BigInteger;

public class DataModel {

    private BigInteger zero = BigInteger.valueOf(Integer.valueOf(0));
    public DataModel(){}

    public BigInteger add(BigInteger first, BigInteger second) {
        return first.add(second);
    }

    public BigInteger subtract(BigInteger firstOperand, BigInteger secondOperand) {
        return firstOperand.subtract(secondOperand);
    }

    public BigInteger multiply(BigInteger firstOperand, BigInteger secondOperand){
        return firstOperand.multiply(secondOperand);
    }

    public BigInteger divide(BigInteger firstOperand, BigInteger secondOpernad) {
        return firstOperand.divide(secondOpernad);
    }

    public BigInteger moduloDivide(BigInteger firstOperand, BigInteger secondOpernad) {
         if(secondOpernad.compareTo(zero) <= 0 ) {
             throw new IllegalArgumentException();
         }
         return firstOperand.mod(secondOpernad);
    }

    public BigInteger power(BigInteger firstOperand, BigInteger secondOpernad) {
        if(secondOpernad.compareTo(makeBigInt(40)) > 0) {
            throw new IllegalArgumentException();
        }
        try {
            int secVal = secondOpernad.intValue();
            return firstOperand.pow(secVal);
        }
        catch(Exception e) {
            throw new IllegalArgumentException();
        }
    }

    private BigInteger makeBigInt(int number) {
        return BigInteger.valueOf(Integer.valueOf(number));
    }

    public BigInteger factorial(BigInteger firstOperand) {
        if(firstOperand.compareTo(makeBigInt(30)) > 0 ||
        firstOperand.compareTo(makeBigInt(0)) <= 0){
          throw new IllegalArgumentException();
        }
        if (firstOperand.compareTo(new BigInteger("1")) == 0) {
            return new BigInteger("1");
        }
        return firstOperand.multiply(factorial(firstOperand.subtract(BigInteger.valueOf(Integer.valueOf(1)))));
    }

    public BigInteger newton(BigInteger firstOpernad, BigInteger secondOperand) {
        return factorial(firstOpernad).divide(factorial(secondOperand)).multiply(factorial(firstOpernad.subtract(secondOperand)));
    }

}
