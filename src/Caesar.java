import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * This is my Caesar cipher class
 * author: Mateusz Koscielniak
 * version: 1.0
 */

public class Caesar extends Cipher {
    /**
     * empty constructor that assigns name of the file to variable filename
     */
    public Caesar() {
        super();
        filename = "defaults/CaesarKey.txt";
    }

    /**
     * encryption method
     *
     * @return encrypted text
     */
    public String encrypt() {
        super.encrypt();
        String plainText = text;
        int shift = this.getShift();
        String cipherText = "";
        //making sure the shift number is in range 0-26
        if (shift > 26) {
            shift = shift % 26;
        } else if (shift < 0) {
            shift = (shift % 26) + 26;
        }
        int length = plainText.length();
        for (int i = 0; i < length; i++) {
            char plainC = plainText.charAt(i);
            if (Character.isLetter(plainC)) {
                char shiftedC = (char) (plainC + shift);
                if (shiftedC > 90) {
                    cipherText += (char) (plainC - (26 - shift));
                } else {
                    cipherText += shiftedC;
                }
            } else {
                cipherText += plainC;
            }
        }
        return cipherText;
    }

    /**
     * decryption method
     *
     * @return decrypted text
     */

    public String decrypt() {
        super.decrypt();
        String plainText = encrypted;
        int shift = this.getShift();
        plainText = plainText.trim(); // removing white spaces from plainText
        plainText = plainText.replaceAll("\\s+", ""); //removing spaces from plainText
        plainText = plainText.toUpperCase();
        String cipherText = "";
        //making sure the shift number is in range 0-26
        if (shift > 26) {
            shift = shift % 26;
        } else if (shift < 0) {
            shift = (shift % 26) + 26;
        }
        int length = plainText.length();
        for (int i = 0; i < length; i++) {
            char plainC = plainText.charAt(i);
            if (Character.isLetter(plainC)) {
                char shiftedC = (char) (plainC - shift);
                if (shiftedC < 65) {
                    cipherText += (char) (plainC + (26 - shift));
                } else {
                    cipherText += shiftedC;
                }
            } else {
                cipherText += plainC;
            }
        }
        return cipherText;
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
        setShift(infile.nextInt());
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
        outfile.println(shift);
        outfile.close();
    }
}
