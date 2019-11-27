package fun.heing.cal.util;
import java.math.BigDecimal;
import java.util.Stack;
import java.util.ArrayList;
public class Util {
    public boolean validInput(String s) {
        try {
            cal(s);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public  String cal (String s2) {

        char[] s = s2.toCharArray();
        Stack<Character> sigStack = new Stack<Character>();
        ArrayList<MyN> expList = new ArrayList<MyN>();
        for (int i=0; i<s.length; ++i) {
            char c = s[i];
            System.out.print(s[i]);
            int t = getType(c);
            if (t==2) {
                int level1 = getLevel(c);
                while (!sigStack.empty()) {
                    char topC = sigStack.peek();
                    if (topC == '(') {
                        break;
                    }
                    int level2 = getLevel(topC);
                    if (level2 < level1) {
                        break;
                    }

                    // +-*/
                    expList.add(new MyN(sigStack.pop()));
                }
                sigStack.push(c);
                continue;
            }
            if (t==3) {
                if (c == ')') {
                    char cTop = sigStack.pop();
                    while (cTop != '(') {
                        expList.add(new MyN(cTop));
                        cTop = sigStack.pop();
                    }
                }
                if (c == '(') {
                    sigStack.push(c);
                }
                continue;
            }
            String ns = "";
            for (int k=i; k<s.length; ++k) {
                char c2 = s[k];
                int t2 = getType(c2);
                if (t2 != 1 || c2 == '=') {
                    i = k-1;
                    expList.add(new MyN(ns));

                    if (c2 == '=') {
                        i += 1;
                    }

                    break;
                } else {
                    ns += c2;
                }
            }
        }

        while (!sigStack.empty()) {
            expList.add(new MyN(sigStack.pop()));
        }
        /*
        System.out.println("ns foreach");
        sigStack.forEach((n)->{
            System.out.print("|" + n + "|");
        });
        System.out.println(":end:");
        */
        System.out.println("expList forEach:");
        expList.forEach((n)->{
            System.out.print("|" + n + "|");
        });


        Stack<MyN> mStack = new Stack<MyN>();
        for (int i=0; i<expList.size(); ++i) {
            MyN c = expList.get(i);
            if (c.type == 1) {
                mStack.push(c);
            }
            if (c.type == 2) {
                MyN n1, n2;
                MyN r = new MyN();
                n1 = mStack.pop();
                n2 = mStack.pop();
                switch (c.c) {
                    case '+':
                        r = new MyN(n1.b.add(n2.b)); break;
                    case '-':
                        r = new MyN(n2.b.subtract(n1.b)); break;
                    case '*':
                        r = new MyN(n1.b.multiply(n2.b)); break;
                    case '/':
                        r= new MyN(n2.b.divide(n1.b)); break;
                }
                mStack.push(r);
            }

        }
        MyN resultN = mStack.pop();
        return resultN.b.toString();
    }
    public int getType(char c) {
        if (c=='+' || c== '-' || c=='*' || c=='/') {
            return 2;
        }
        if (c=='(' || c==')') {
            return 3;
        }
        return 1;
    }
    private int getLevel(char c) {
        if (c=='+' || c=='-') {
            return 1;
        }
        if (c=='*' || c=='/') {
            return 2;
        }
        return -1;
    }
    private class MyN {
        // 1->bigdiciaml, 2->+-*/, 3->()
        public int type;
        public BigDecimal b;
        public char c;
        public MyN() {

        }
        public MyN(BigDecimal _b) {
            b = _b;
            type = 1;
        }
        public MyN(char _c) {
            c = _c;
            type = getType(_c);
        }
        public MyN(String s) {
            b = new BigDecimal(s);
            type = 1;
        }

        @Override
        public String toString() {
            if (type==1) {
                return b.toString();
            } else {
                return c+"";
            }
        }
    }
}
