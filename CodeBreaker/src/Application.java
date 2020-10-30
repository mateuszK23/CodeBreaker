import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This is my Application class
 * author: Mateusz Koscielniak
 * version: 1.0
 */

public class Application {
    private String filename;
    private Scanner scan = new Scanner(System.in);
    private String text = "";
    private String loadedEncrypted = "";
    private String encrypted;
    private String nameOfCipher = "Caesar";
    Cipher cip = new Caesar();

    /**
     * runMenu() method is responsible for displaying menu and giving user opportunity to choose an option
     *
     * @throws IOException throws IOException when there's an error in loading key
     */
    public void runMenu() throws IOException {

        cipherDetails(cip);
        String response;
        do {
            cip.loadKey();
            System.out.println("Loaded plainText: " + cip.getText());
            System.out.println("Loaded cipherText: " + cip.getEncrypted());
            System.out.println("Current cipher: " + nameOfCipher);
            printMenu();
            System.out.println("What would you like to do:");
            response = scan.nextLine().toUpperCase();
            switch (response) {
                case "1":
                    loadPlainText();
                    break;
                case "2":
                    loadEncrypted();
                    break;
                case "3":
                    changeCipher();
                    break;
                case "4":
                    changeKey();
                    break;
                case "5":
                    encrypt();
                    break;
                case "6":
                    decrypt();
                    break;
                case "7":
                    displayKey();
                    break;
                case "8":
                    saveEncrypted();
                    break;
                case "9":
                    System.out.println("Decrypted text: " + cip.getDecrypted());
                    break;
                case "10":
                    saveDecrypted();
                    break;
                case "Q":
                    break;
                default:
                    System.out.println("Try again");
                    break;
            }
        } while (!(response.equals("Q")));
    }

    /**
     * displayKey() method displays the key based on chosen cipher
     */
    public void displayKey() {
        switch (nameOfCipher) {
            case "Caesar":
                System.out.println(cip.getShift());
                break;
            case "Keyed Caesar":
                System.out.println(cip.getKey());
                System.out.println(cip.getShift());
                break;
            case "Vigenere":
                System.out.println(cip.getKey());
                System.out.println(cip.getText());
                break;
        }
    }

    /**
     * changeCipher() allows user to change current cipher
     *
     * @throws FileNotFoundException  throws when there's an error in loading key
     * @throws InputMismatchException throws when user inputs wrong value
     */
    public void changeCipher() throws FileNotFoundException, InputMismatchException {
        try {
            System.out.println("Which cipher do u want to use ? ");
            System.out.println("A. Use Caesar Cipher");
            System.out.println("B. Use Keyed Caesar Cipher");
            System.out.println("C. Use Vigenere Cipher");
            String answer;
            answer = scan.nextLine().toUpperCase();
            switch (answer) {
                case "A":
                    nameOfCipher = "Caesar";
                    cip = new Caesar();
                    System.out.println("Cipher changed");
                    break;
                case "B":
                    nameOfCipher = "Keyed Caesar";
                    cip = new KeyedCaesar();
                    System.out.println("Cipher changed");
                    break;
                case "C":
                    nameOfCipher = "Vigenere";
                    cip = new Vigenere();
                    System.out.println("Cipher changed");
                    break;
                default:
                    System.out.println("Wrong value");
                    break;
            }
            cip.loadKey();
            cip.setText(text);
            cip.setEncrypted(loadedEncrypted);
        } catch (InputMismatchException e) {
            System.err.println("Wrong value");
        }
    }

    /**
     * printMenu() displays every available option in my program
     */
    public static void printMenu() {
        System.out.println("1. Input a file with plain text");
        System.out.println("2. Input a file with encrypted text");
        System.out.println("3. Change cipher");
        System.out.println("4. Edit key");
        System.out.println("5. Encrypt");
        System.out.println("6. Decrypt");
        System.out.println("7. Display key");
        System.out.println("8. Save encrypted text to a file");
        System.out.println("9. Display decrypted text");
        System.out.println("10. Save decrypted text to a file");
        System.out.println("Q - Quit");
    }

    /**
     * encrypt() encryption method
     */
    public void encrypt() {
        encrypted = cip.encrypt();
        System.out.println("Encrypted text: " + encrypted);
    }

    /**
     * decrypt() decryption method
     */
    public void decrypt() {
        String decrypted = cip.decrypt();
        cip.setDecrypted(decrypted);
        System.out.println("Decrypted text: " + decrypted);
    }

    /**
     * saveEncrypted() method that allows user to save cipher text to a file
     *
     * @throws FileNotFoundException needed for PrintWriter
     */
    public void saveEncrypted() throws FileNotFoundException, InputMismatchException {
        try {
            String file;
            System.out.println("Enter name of file: ");
            file = scan.nextLine();
            PrintWriter outfile = new PrintWriter(file);
            outfile.println(encrypted);
            outfile.close();
        } catch (FileNotFoundException e) {
            System.err.println("Wrong value");
        }
    }

    /**
     * saveDecrypted() method that allows user to save decrypted text to a file
     */
    public void saveDecrypted() throws FileNotFoundException, InputMismatchException {
        try {
            String file;
            System.out.println("Enter name of file: ");
            file = scan.nextLine();
            PrintWriter outfile = new PrintWriter(file);
            outfile.println(cip.getDecrypted());
            outfile.close();
        } catch (FileNotFoundException e) {
            System.err.println("Wrong value");
        }
    }

    /**
     * changeKey() method allowing user to change key based on chosen cipher
     *
     * @throws FileNotFoundException needed for saveKey, throws when there's an error while saving the key
     */
    public void changeKey() throws FileNotFoundException {
        switch (nameOfCipher) {
            case "Caesar":
                caesarDetails(cip);
                break;
            case "Vigenere":
                vigenereDetails(cip);
                break;
            case "Keyed Caesar":
                keyedDetails(cip);
                break;
        }
        cip.saveKey();
    }

    /**
     * CaesarDetails() method that lets user input all necessary info about key for Caesar Cipher
     *
     * @param cipher Object that inputted data is being assigned to
     * @throws InputMismatchException throws when user inputs wrong value
     */
    public void caesarDetails(Cipher cipher) throws InputMismatchException {
        try {
            System.out.println("Enter number of shifts: ");
            int answer = scan.nextInt();
            cipher.setShift(answer);
            System.out.println(answer);

        } catch (InputMismatchException e) {
            System.err.println("Wrong value");
        }
        scan.nextLine();
    }

    /**
     * VigenereDetails() method that lets user input all necessary info about key for Vigenere Cipher
     *
     * @param cipher Object that inputted data is being assigned to
     * @throws InputMismatchException throws when user enters bad input
     */
    public void vigenereDetails(Cipher cipher) throws InputMismatchException {
        try {
            System.out.println("Enter your keyWord: ");
            String key;
            key = scan.nextLine();
            cipher.setKey(key);
        } catch (InputMismatchException e) {
            System.err.println("Wrong value");
        }
    }

    /**
     * KeyedDetails() method that lets user input all neccesary info about key for KeyedCaesar Cipher
     *
     * @param cipher Object that inputted data is being assigned to
     * @throws InputMismatchException throws when user enters bad Input
     */
    public void keyedDetails(Cipher cipher) throws InputMismatchException {
        try {
            System.out.println("Enter your keyWord: ");
            String key = scan.nextLine();

            System.out.println("Enter number of shifts: ");
            int shift = scan.nextInt();

            cipher.setShift(shift);
            cipher.setKey(key);
        } catch (InputMismatchException e) {
            System.err.println("Wrong value");
        }
        scan.nextLine();
    }

    /**
     * CipherDetails() method that assigns text and cipher text to chosen cipher
     *
     * @param cipher Object that texts are being assigned to
     */
    public void cipherDetails(Cipher cipher) {
        cipher.setText(text);
        cipher.setEncrypted(loadedEncrypted);
    }

    /**
     * loadPlainText() method that loads plainText from a file
     */
    public void loadPlainText() {
        try {
            System.out.println("Enter the filename");
            filename = scan.nextLine();
            String text1;
            FileReader fr = new FileReader(filename);
            Scanner infile = new Scanner(fr);
            infile.useDelimiter(":|\r?\n|\r");
            StringBuilder sb = new StringBuilder();
            while (infile.hasNext()) {
                text1 = infile.next();
                sb.append(text1);
            }
            this.text = sb.toString();
            infile.close();
            cip.setText(text);
            System.out.println(text);
        } catch (FileNotFoundException e) {
            System.err.println("File not found");
        }
    }

    /**
     * loadEncrypted() method that loads cipher text from a file
     */
    public void loadEncrypted() {
        try {
            System.out.println("Enter the filename");
            filename = scan.nextLine();
            String text1;
            FileReader fr = new FileReader(filename);
            Scanner infile = new Scanner(fr);
            infile.useDelimiter(":|\r?\n|\r");
            StringBuilder sb = new StringBuilder();
            while (infile.hasNext()) {
                text1 = infile.next();
                sb.append(text1);
            }
            this.loadedEncrypted = sb.toString();
            infile.close();
            cip.setEncrypted(loadedEncrypted);
            System.out.println(loadedEncrypted);

        } catch (FileNotFoundException e) {
            System.err.println("File not found");
        }
    }

    /**
     * main() main method, program starts here
     */

    public static void main(String[] args) throws IOException {
        System.out.println("**********HELLO***********");
        Application app = new Application();
        app.runMenu();
        System.out.println("***********GOODBYE**********");
    }


}
