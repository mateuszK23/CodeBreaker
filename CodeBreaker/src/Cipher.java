import java.io.FileNotFoundException;

/**
 * This is my Cipher class
 * author: Mateusz Koscielniak
 * version: 1.0
 */
public class Cipher {
    String text;
    String encrypted;
    String decrypted;
    int shift;
    String filename;
    final static int alphabetLength = 26;

    /**
     * empty constructor of super class
     */
    public Cipher() {
    }

    /**
     * @return null but is used by child classes
     */
    public String getKey() {
        return null;
    }

    /**
     * @return shift
     */
    public int getShift() {
        return shift;
    }

    /**
     * @return text
     */
    public String getText() {
        return text;
    }

    /**
     * @return encrypted
     */
    public String getEncrypted() {
        return encrypted;
    }

    /**
     * @return decrypted
     */
    public String getDecrypted() {
        return decrypted;
    }

    /**
     * sets decrypted
     *
     * @param decrypted
     */
    public void setDecrypted(String decrypted) {
        this.decrypted = decrypted;
    }

    /**
     * sets variable encrypted
     *
     * @param encrypted encrypted text
     */
    public void setEncrypted(String encrypted) {
        this.encrypted = encrypted;
    }

    /**
     * sets variable shift
     *
     * @param shift number of shifts
     */
    public void setShift(int shift) {
        this.shift = shift;
    }

    /**
     * prepares text to be used (deletes all spaces and makes it upper Case
     *
     * @param text plainText
     */
    public void setText(String text) {
        text = text.trim(); // removing white spaces from plainText
        text = text.replaceAll("\\s+", ""); // removing spaces from plainText
        text = text.toUpperCase();
        this.text = text;
    }

    /**
     * All of the methods below are empty, they are used by child classes
     */
    public String decrypt() {
        return null;
    }

    public String encrypt() {
        return null;
    }

    public void setKey(String key) {
    }

    public void loadKey() throws FileNotFoundException {
    }

    public void saveKey() throws FileNotFoundException {
    }

}
