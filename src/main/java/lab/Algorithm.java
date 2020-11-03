package lab;

import java.io.IOException;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

public class Algorithm {
    private int[] columns = null;
    private int[] rows = null;
    String string;
    int rowSize;
    int colSize;

    public void setKeys(String keyF, String keyS) throws IOException {
        if (keyF == null || keyF.equals("")) {
            throw new IOException("Ключи пустые. Форма ключа *-*-*-*");
        }
        else {
            String[] keyFArray = keyF.split("-");
            if (keyFArray.length != colSize) {
                throw new IOException("Длина первого ключа не соответствует условию");
            }
            this.columns = new int[keyFArray.length];
            try {
                for (int i = 0; i < keyFArray.length; i++) {
                    this.columns[i] = Integer.parseInt(keyFArray[i]);
                }
            }
            catch (Exception e) {
                throw new IOException("Ключи должны содержать только цифры. Форма ключа 1-2-3-4-...");
            }
            if (!isValid(columns)) {
                throw new IOException("Нарушена форма первого ключа. Форма ключа 1-2-3-4-...");
            }
        }

        if (keyS == null || keyS.equals("")) {
            throw new IOException("Нарушена форма ключа. Форма ключа 1-2-3-4-...");
        }
        else {
            String[] keySArray = keyS.split("-");
            if (keySArray.length != rowSize) {
                throw new IOException("Длина второго ключа не соответствует условию");
            }
            this.rows = new int[keySArray.length];
            try {
                for (int i = 0; i < keySArray.length; i++){
                    this.rows[i] = Integer.parseInt(keySArray[i]);
                }
            }
            catch (Exception e) {
                throw new IOException("Ключи должны содержать только цифры. Форма ключа 1-2-3-4-...");
            }
            if (!isValid(rows)) {
                throw new IOException("Нарушена форма второго ключа. Форма ключа 1-2-3-4-...");
            }
        }
    }

    public Map<String, Integer> setStr(String s) throws IOException {
        if (s == null || s.equals("")) {
            throw new IOException("Строка пустая, введите строку");
        }
        string = s;
        rowSize = (int) Math.sqrt(string.length());
        colSize = (int) Math.ceil((double) string.length()/ rowSize);
        Map<String, Integer> result = new HashMap<>();
        result.put("first", colSize);
        result.put("second", rowSize);

        return result;
    }

    public String code() {
        char[][] code = new char[rowSize][colSize];
        int j = 0;
        int k = colSize;

        for (int i : rows) {
            code[i - 1] = substr(string, j, k);
            j += colSize;
            k += colSize;
        }

        StringBuilder result = new StringBuilder();
        for (int i : columns) {
            for (int l = 0; l < rowSize; l++) {
                result.append(code[l][i - 1]);
            }
        }

        return result.toString();
    }

    public String decode() {
        char[][] decode = new char[rowSize][colSize];
        int j = 0;

        for (int i : columns) {
            for (int l = 0; l < rowSize; l++) {
                decode[l][i-1] = string.charAt(j);
                j++;
            }
        }

        StringBuilder result = new StringBuilder();

        for (int i : rows) {
            for (int l = 0; l < colSize; l++) {
                result.append(decode[i-1][l]);
            }
        }

        return result.toString();
    }

    private Boolean isValid(int[] key) {
        int max = -1;
        int min = key.length;
        BitSet bitSet = new BitSet();
        for (int i : key) {
            if (max < i) {
                max = i;
            }
            if (min > i) {
                min = i;
            }
            if (bitSet.get(i)) {
                return false;
            }
            bitSet.set(i);
        }
        return min == 1 && max == key.length;
    }

    private char[] substr(String str, int beginIndex, int endIndex) {
        StringBuilder result = new StringBuilder();
        for (int i = beginIndex; i < endIndex; i++) {
            if (i >= str.length()) {
                result.append(" ");
            }
            else {
                result.append(str.charAt(i));
            }
        }
        return result.toString().toCharArray();
    }
}
