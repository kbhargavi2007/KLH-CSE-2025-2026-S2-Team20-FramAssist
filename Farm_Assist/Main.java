import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("========================================");
        System.out.println("              FARM ASSIST");
        System.out.println("========================================");

        System.out.print("Enter your search: ");

        String keyword = scanner.nextLine().trim();

        if (keyword.length() == 0) {

            System.out.println("Please enter a search term.");

            scanner.close();
            return;
        }

        /*
         * Name of the folder containing
         * the 20 Farm Assist corpus files.
         */
        String folderPath = "corpus";

        FileSearcher.searchCorpus(folderPath, keyword);

        scanner.close();
    }
}