import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * This is my Vigenere Cipher class
 * author: Mateusz Koscielniak
 * version: 1.0
 */

public class Vigenere extends Cipher {
    private String key;
    private char[][] table = createTable();

    /**
     * empty constructor assigning name of the file to variable filename
     */
    public Vigenere() {
        super();
        filename = "VigKey.txt";
    }

    /**
     * @return key
     */
    public String getKey() {
        super.getKey();
        return key;
    }

    /**
     * sets key
     *
     * @param key keyWord
     */
    public void setKey(String key) {
        super.setKey(key);
        key = key.trim(); // removing white spaces
        key = key.replaceAll("\\s+", ""); // removing spaces from key
        key = key.toUpperCase();
        this.key = key;
    }

    /**
     * encryption method
     *
     * @return encrypted text
     */
    public String encrypt() {
        super.encrypt();
        String plainText = text;
        String keyWord = key;
        int keyChar;
        int textChar;
        String encrypted = "";

        for (int i = 0; i < plainText.length(); i++) {
            textChar = plainText.charAt(i) - 65;
            if (i > keyWord.length() - 1) {
                keyChar = keyWord.charAt(i % keyWord.length()) - 65;
            } else {
                keyChar = keyWord.charAt(i) - 65;
            }
            encrypted += table[textChar][keyChar];
        }
        return encrypted;
    }

    /**
     * decryption method
     *
     * @return decrypted text
     */
    public String decrypt() {
        super.decrypt();
        String decrypted = "";
        int keyChar;
        String keyWord = key;
        String mssg = encrypted;
        for (int j = 0; j < mssg.length(); j++) {
            for (int i = 0; i < 26; i++) {
                if (j > keyWord.length() - 1) {
                    keyChar = keyWord.charAt(j % keyWord.length()) - 65;
                } else {
                    keyChar = keyWord.charAt(j) - 65;
                }
                if (table[keyChar][i] == mssg.charAt(j)) {
                    decrypted += (char) (i + 65);
                }
            }
        }
        return decrypted;
    }

    /**
     * creates vigenere cipher table based on ASCII
     *
     * @return vigenere table
     */
    char[][] createTable() {
        char[][] table = new char[alphabetLength][alphabetLength];
        int a = 0;
        for (int i = 0; i < alphabetLength; i++) {

            for (int j = 0; j < alphabetLength; j++) {
                if (a > 26) {
                    a = a % 26;
                }
                char nextChar = (char) ('A' + (char) a);
                if (nextChar > 90) nextChar = 'A';
                table[i][j] = nextChar;
                a++;
            }
            a = a % 26 + 1;
        }
        return table;
    }

    /**
     * loads key from a text file
     *
     * @throws FileNotFoundException throws when file is not found
     */

    public void loadKey() throws FileNotFoundException {
        super.loadKey();
        FileReader fr = new FileReader(filename);
        Scanner infile = new Scanner(fr);
        infile.useDelimiter(":|\r?\n|\r");
        setKey(infile.nextLine());
        infile.close();
    }

    /**
     * saves key to a text file
     *
     * @throws FileNotFoundException throws when file is not found
     */
    public void saveKey() throws FileNotFoundException {
        super.saveKey();
        PrintWriter outfile = new PrintWriter(filename);
        outfile.println(key);
        outfile.close();
    }

}