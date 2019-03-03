import java.math.BigInteger;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PolyTester {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String exp = in.nextLine().trim();
        if (!exp.matches("[-\\+]?\\s*" +
                "(?:((([-\\+]?\\d+\\s*\\*\\s*)|([-\\+]?\\s*))" +
                "[x](\\s*\\^\\s*[-\\+]?\\d+)?)|" +
                "([-\\+]?\\d+))" +
                "(?:\\s*[-\\+]\\s*" +
                "(((([-\\+]?\\d+\\s*\\*\\s*)|([-\\+]?\\s*))" +
                "[x](\\s*\\^\\s*[-\\+]?\\d+)?)|" +
                "([-\\+]?\\d+)))*+")) {
            //Attention: use Possessive mode to avoid backtracking
            System.out.println("WRONG FORMAT!");
            return;
        } //judge wrong format!
        in.close();
        if (exp.charAt(0) != '+' && exp.charAt(0) != '-') {
            exp = "+".concat(exp);
        }
        BigInteger zero = new BigInteger("0");
        Pattern pat = Pattern.compile("(((\\d+\\s*\\*\\s*)|(\\s*))" +
                "[x](\\s*\\^\\s*[-\\+]?\\d+)?)|(\\d+)");
        //terms without signs
        String[] ops = exp.split(pat.toString());
        //for (String op : ops) {
        //System.out.println(op);
        //}
        Matcher match = pat.matcher(exp);
        LinkedList<Poly> polys = new LinkedList<>();
        int k = 0;
        while (match.find()) {
            Poly dif = new Poly(match.group()).diff(ops[k]);
            polys.add(dif);
            k++;
        }
        for (int i = 0; i < polys.size() - 1; i++) {
            Poly a = polys.get(i);
            for (int j = i + 1; j < polys.size(); j++) {
                Poly b = polys.get(j);
                if (a.getExpo().compareTo(b.getExpo()) == 0) {
                    a.setCoeff(a.getCoeff().add(b.getCoeff()));
                    polys.remove(b);
                    j--;
                }
            }
        }
        //merge same exponent terms
        StringBuilder result = new StringBuilder("");
        for (Poly p : polys) {
            if (polys.size() > 1 && p.toString() == "+0") {
                continue;
            } else {
                result.append(p.toString());
            }
        }
        if (result.charAt(0) == '+') {
            System.out.println(result.deleteCharAt(0).toString());
        } else {
            System.out.println(result.toString());
        }
    }
}
