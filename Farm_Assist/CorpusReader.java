import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CorpusReader {

    /*
     * Reads one complete crop corpus file.
     */
    public static String readFile(File file) {

        StringBuilder content = new StringBuilder();

        try {

            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {

                content.append(scanner.nextLine());
                content.append("\n");
            }

            scanner.close();

        } catch (FileNotFoundException e) {

            System.out.println(
                    "Unable to read file: " + file.getName());

            return "";
        }

        return content.toString();
    }
}