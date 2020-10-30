import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * This is my KeyedCaesar cipher class
 * author: Mateusz Koscielniak
 * version: 1.0
 */

public class KeyedCaesar extends Cipher {
    private String key;

    /**
     * Empty constructor that assigns name of the file to variable filename
     */
    public KeyedCaesar() {
        super();
        filename = "KeyedKey.txt";
    }

    /**
     * Sets shift, restraining it to be between 0 and 26 (number of letters in alphabet)
     *
     * @param shift number of shifts
     */
    public void setShift(int shift) {
        if (shift > 26) {
            shift = shift % 26;
        } else if (shift < 0) {
            shift = (shift % 26) + 26;
        }
        this.shift = shift;
    }

    /**
     * prepares keyWord to be used ( deletes every spaces and makes it upper Case )
     *
     * @param key keyWord
     */
    public void setKey(String key) {
        super.setKey(key);
        key = key.trim(); // removing white spaces from plainText
        key = key.replaceAll("\\s+", ""); // removing spaces from plainText
        key = key.toUpperCase();
        this.key = key;
    }

    /**
     * @return key
     */
    public String getKey() {
        super.getKey();
        return key;
    }


    /**
     * Based on given keyWord creates and array with new alphabet
     *
     * @return result char array with new alphabet inside
     */
    public char[] newAlphabet() {
        String newAlphabet;
        char[] result;
        char[] table = createTable();
        String keyWord = removeDuplicates(key);
        String alphabetWithoutKey = "";

        for (int i = 0; i < keyWord.length(); i++) {
            for (int j = 0; j < alphabetLength; j++) {
                if (table[j] == keyWord.charAt(i)) {
                    table[j] = '0';
                }
            }
        }
        for (int a = 0; a < alphabetLength; a++) {
            alphabetWithoutKey += table[a];
        }
        newAlphabet = keyWord + alphabetWithoutKey;
        newAlphabet = newAlphabet.replaceAll("0", "");
        result = newAlphabet.toCharArray();
        return result;
    }

    /**
     * Creates normal english alphabet table (A - Z) using ASCII
     *
     * @return table
     */
    public char[] createTable() {
        char[] table = new char[alphabetLength];
        for (int i = 0; i < alphabetLength; i++) {
            table[i] = (char) ('A' + (char) i);
        }
        return table;
    }

    /**
     * removes duplicate characters from keyWord
     *
     * @param word keyWord
     * @return keyWord without duplicate characters
     */
    public static String removeDuplicates(String word) {
        StringBuilder result = new StringBuilder();
        String character;
        int wordLn = word.length();
        for (int i = 0; i < wordLn; i++) {
            character = word.substring(i, i + 1);
            if (result.indexOf(character) < 0) {
                result.append(character);
            }
        }
        return result.toString();
    }

    /**
     * encryption method
     *
     * @return encrypted text
     */
    public String encrypt() {
        int shift = this.getShift();
        super.encrypt();
        char[] table = newAlphabet();
        String result = "";
        int temp;
        int textLn = text.length();
        for (int i = 0; i < textLn; i++) {
            temp = (int) text.charAt(i) - 65 + shift;
            if (temp >= 26) temp = 0;
            result += table[temp];
        }
        return result;
    }

    /**
     * decryption method
     *
     * @return decrypted text
     */
    public String decrypt() {
        int shift = this.getShift();
        super.decrypt();
        String result = "";
        int temp;
        char[] table = newAlphabet();
        int textLn = encrypted.length();
        for (int i = 0; i < textLn; i++) {
            for (int j = 0; j < alphabetLength; j++) {
                temp = j - shift;
                if (temp >= 26) temp = temp % 26;
                if (temp < 0) temp = (temp % 26) + 26;
                if (table[j] == encrypted.charAt(i)) {
                    result += (char) (temp + 65);
                }
            }
        }
        return result;
    }

    /**
     * loads key from text file
     *
     * @throws FileNotFoundException throws when file is not found
     */
    public void loadKey() throws FileNotFoundException {
        super.loadKey();
        FileReader fr = new FileReader(filename);
        Scanner infile = new Scanner(fr);
        infile.useDelimiter(":|\r?\n|\r");
        setKey(infile.nextLine());
        setShift(infile.nextInt());
        infile.close();

    }

    /**
     * saves key to text file
     *
     * @throws FileNotFoundException throws when file is not found
     */
    public void saveKey() throws FileNotFoundException {
        super.saveKey();
        PrintWriter outfile = new PrintWriter(filename);
        outfile.println(key);
        outfile.print(shift);
        outfile.close();
    }
}
