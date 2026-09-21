import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws Exception {

        BufferedReader br =
                new BufferedReader(new InputStreamReader(System.in));

        // Read all corpus files
        File folder = new File("corpus");

        File[] files = folder.listFiles();

        StringBuilder corpus = new StringBuilder();

        if (files != null) {

            for (File file : files) {

                if (file.isFile()) {

                    corpus.append(
                            CorpusReader.readFile(file)
                    );
                }
            }
        }

        String corpusText = corpus.toString();

        while (true) {

            System.out.println("\n========== FARM ASSIST ==========");
            System.out.println("1. Exact Search");
            System.out.println("2. Fuzzy Search");
            System.out.println("3. Water Distribution");
            System.out.println("4. Exit");

            System.out.print("Enter your choice: ");

            int choice =
                    Integer.parseInt(br.readLine().trim());

            switch (choice) {

                case 1:

                    System.out.print("Enter search word: ");

                    String exactQuery =
                            br.readLine().trim();

                    FileSearcher.exactSearch(
                            corpusText,
                            exactQuery
                    );

                    break;


         case 2:

    System.out.print("Enter search word: ");

    String fuzzyQuery = br.readLine().trim();

    FileSearcher.fuzzySearch(
            corpusText,
            fuzzyQuery
    );

    break;


                case 3:

                    MaxFlow.runWaterDistribution(br);

                    break;


                case 4:

                    System.out.println("Exiting Farm Assist...");
                    return;


                default:

                    System.out.println(
                            "Invalid choice."
                    );
            }
        }
    }
}