package lab;

import java.io.IOException;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

public class Algorithm {
    private int[] keyFirst = null;
    private int[] keySecond = null;
    String str;
    int sizeRow;
    int sizeCol;

    public void setKeys(String keyF, String keyS) throws IOException {
        if (keyF == null || keyF.equals("")) {
            throw new IOException("Ключи пустые. Форма ключа *-*-*-*");
        }
        else {
            String[] keyFArray = keyF.split("-");
            if (keyFArray.length != sizeCol) {
                throw new IOException("Длина первого ключа не соответствует условию");
            }
            this.keyFirst = new int[keyFArray.length];
            try {
                for (int i = 0; i < keyFArray.length; i++) {
                    this.keyFirst[i] = Integer.parseInt(keyFArray[i]);
                }
            }
            catch (Exception e) {
                throw new IOException("Ключи должны содержать только цифры. Форма ключа 1-2-3-4-...");
            }
            if (!isValid(keyFirst)) {
                throw new IOException("Нарушена форма первого ключа. Форма ключа 1-2-3-4-...");
            }
        }

        if (keyS == null || keyS.equals("")) {
            throw new IOException("Нарушена форма ключа. Форма ключа 1-2-3-4-...");
        }
        else {
            String[] keySArray = keyS.split("-");
            if (keySArray.length != sizeRow) {
                throw new IOException("Длина второго ключа не соответствует условию");
            }
            this.keySecond = new int[keySArray.length];
            try {
                for (int i = 0; i < keySArray.length; i++){
                    this.keySecond[i] = Integer.parseInt(keySArray[i]);
                }
            }
            catch (Exception e) {
                throw new IOException("Ключи должны содержать только цифры. Форма ключа 1-2-3-4-...");
            }
            if (!isValid(keySecond)) {
                throw new IOException("Нарушена форма второго ключа. Форма ключа 1-2-3-4-...");
            }
        }
    }

    public Map<String, Integer> setStr(String s) throws IOException {
        if (s == null || s.equals("")) {
            throw new IOException("Строка пустая, введите строку");
        }
        str = s;
        sizeRow = (int) Math.sqrt(str.length());
        sizeCol = (int) Math.ceil((double) str.length()/sizeRow);
        Map<String, Integer> result = new HashMap<>();
        result.put("first", sizeCol);
        result.put("second", sizeRow);

        return result;
    }

    public String code() {
        char[][] codeArray = new char[sizeRow][sizeCol];
        int j = 0;
        int k = sizeCol;

        for (int i : keySecond) {
            codeArray[i - 1] = substr(str, j, k);
            j += sizeCol;
            k += sizeCol;
        }

        StringBuilder result = new StringBuilder();
        for (int i : keyFirst) {
            for (int l = 0; l < sizeRow; l++) {
                result.append(codeArray[l][i - 1]);
            }
        }

        return result.toString();
    }

    public String decode() {
        char[][] decodeArray = new char[sizeRow][sizeCol];
        int j = 0;

        for (int i : keyFirst) {
            for (int l = 0; l < sizeRow; l++) {
                decodeArray[l][i-1] = str.charAt(j);
                j++;
            }
        }

        StringBuilder res = new StringBuilder();

        for (int i : keySecond) {
            for (int l = 0; l < sizeCol; l++) {
                res.append(decodeArray[i-1][l]);
            }
        }

        return res.toString();
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
