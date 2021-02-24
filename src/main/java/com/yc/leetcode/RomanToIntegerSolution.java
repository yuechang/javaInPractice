/* Copyright © 2020 Yuech and/or its affiliates. All rights reserved. */
package com.yc.leetcode;

/**
 * 罗马数字转整数
 * roman-to-integer
 * https://leetcode-cn.com/problems/roman-to-integer/
 * I             1
 * V             5
 * X             10
 * L             50
 * C             100
 * D             500
 * M             1000
 *
 *
 * @author Yue Chang
 * @version 1.0
 * @date 2021-02-18 9:12
 */
public class RomanToIntegerSolution {

    public static void main(String[] args) {

        RomanToIntegerSolution instance = new RomanToIntegerSolution();
        int intValue = instance.romanToInt("III");
        System.out.println(intValue);

        intValue = instance.romanToInt("IV");
        System.out.println(intValue);

        intValue = instance.romanToInt("IX");
        System.out.println(intValue);

        intValue = instance.romanToInt("LVIII");
        System.out.println(intValue);

        intValue = instance.romanToInt("MCMXCIV");
        System.out.println(intValue);

    }

    /**
     * 1
     */
    public static final String I = "I";

    /**
     * 2
     */
    public static final String II = "II";

    /**
     * 3
     */
    public static final String III = "III";

    /**
     * 4
     */
    public static final String IV = "IV";

    /**
     * 5
     */
    public static final String V = "V";

    /**
     * 6
     */
    public static final String VI = "VI";

    /**
     * 7
     */
    public static final String VII = "VII";

    /**
     * 8
     */
    public static final String VIII = "VIII";

    /**
     * 9
     */
    public static final String IX = "IX";


    /**
     * 10
     */
    public static final String X = "X";

    /**
     * 20
     */
    public static final String XX = "XX";

    /**
     * 30
     */
    public static final String XXX = "XXX";

    /**
     * 40
     */
    public static final String XL = "XL";

    /**
     * 50
     */
    public static final String L = "L";

    /**
     * 60
     */
    public static final String LX = "LX";

    /**
     * 70
     */
    public static final String LXX = "LXX";

    /**
     * 80
     */
    public static final String LXXX = "LXXX";

    /**
     * 90
     */
    public static final String XC = "XC";


    /**
     * 100
     */
    public static final String C = "C";

    /**
     * 200
     */
    public static final String CC = "CC";

    /**
     * 300
     */
    public static final String CCC = "CCC";

    /**
     * 400
     */
    public static final String CD = "CD";

    /**
     * 500
     */
    public static final String D = "D";

    /**
     * 600
     */
    public static final String DC = "DC";

    /**
     * 700
     */
    public static final String DCC = "DCC";

    /**
     * 800
     */
    public static final String DCCC = "DCCC";

    /**
     * 900
     */
    public static final String CM = "CM";


    /**
     * 1000
     */
    public static final String M = "M";

    /**
     * 2000
     */
    public static final String MM = "MM";

    /**
     * 3000
     */
    public static final String MMM = "MMM";

    public int romanToInt(String str) {
        int one = 0;
        int length = 0;
        if (str.contains(IX)) {
            one = 9;
            length = IX.length();
        } else if (str.contains(VIII)) {
            one = 8;
            length = VIII.length();
        } else if (str.contains(VII)) {
            one = 7;
            length = VII.length();
        } else if (str.contains(VI)) {
            one = 6;
            length = VI.length();
        } else if (str.contains(IV)) {
            one = 4;
            length = IV.length();
        } else if (str.contains(V)) {
            one = 5;
            length = V.length();
        } else if (str.contains(III)) {
            one = 3;
            length = III.length();
        } else if (str.contains(II)) {
            one = 2;
            length = II.length();
        } else if (str.contains(I)) {
            one = 1;
            length = I.length();
        }
        str = str.substring(0, str.length() - length);
        if ("".equals(str)) {
            return one;
        }

        int ten = 0;
        length = 0;
        if (str.contains(XC)) {
            ten = 90;
            length = XC.length();
        } else if (str.contains(LXXX)) {
            ten = 80;
            length = LXXX.length();
        } else if (str.contains(LXX)) {
            ten = 70;
            length = LXX.length();
        } else if (str.contains(LX)) {
            ten = 60;
            length = LX.length();
        } else if (str.contains(XL)) {
            ten = 40;
            length = XL.length();
        } else if (str.contains(L)) {
            ten = 50;
            length = L.length();
        } else if (str.contains(XXX)) {
            ten = 30;
            length = XXX.length();
        } else if (str.contains(XX)) {
            ten = 20;
            length = XX.length();
        } else if (str.contains(X)) {
            ten = 10;
            length = X.length();
        }
        str = str.substring(0, str.length() - length);
        if ("".equals(str)) {
            return ten + one;
        }

        int hundred = 0;
        length = 0;
        if (str.contains(CM)) {
            hundred = 900;
            length = CM.length();
        } else if (str.contains(DCCC)) {
            hundred = 800;
            length = DCCC.length();
        } else if (str.contains(DCC)) {
            hundred = 700;
            length = DCC.length();
        } else if (str.contains(DC)) {
            hundred = 600;
            length = DC.length();
        } else if (str.contains(CD)) {
            hundred = 400;
            length = CD.length();
        } else if (str.contains(D)) {
            hundred = 500;
            length = D.length();
        } else if (str.contains(CCC)) {
            hundred = 300;
            length = CCC.length();
        } else if (str.contains(CC)) {
            hundred = 200;
            length = CC.length();
        } else if (str.contains(C)) {
            hundred = 100;
            length = C.length();
        }
        str = str.substring(0, str.length() - length);
        if ("".equals(str)) {
            return hundred + ten + one;
        }

        int thousand = 0;
        if (str.contains(MMM)) {
            thousand = 3000;
        } else if (str.contains(MM)) {
            thousand = 2000;
        } else if (str.contains(M)) {
            thousand = 1000;
        }
        return thousand + hundred + ten + one;
    }
}

