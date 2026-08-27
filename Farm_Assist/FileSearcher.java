import java.io.File;

public class FileSearcher {

    public static void searchCorpus(String folderPath, String keyword) {

        File folder = new File(folderPath);

        if (!folder.exists() || !folder.isDirectory()) {

            System.out.println("Corpus folder not found.");
            return;
        }

        File[] files = folder.listFiles();

        if (files == null) {

            System.out.println("No files found.");
            return;
        }

        int filesChecked = 0;
        int recordsFound = 0;

        System.out.println("\nSearching corpus files...");
        System.out.println("Search Algorithm: Z-Algorithm");
        System.out.println("----------------------------------------");

        for (File file : files) {

            if (!file.isFile()) {
                continue;
            }

            filesChecked++;

            String completeText = CorpusReader.readFile(file);

            if (completeText.length() == 0) {
                continue;
            }

            /*
             * Split the file into individual records.
             *
             * Your corpus records are separated by blank lines.
             */
            String[] records = completeText.split("\\n\\s*\\n");

            boolean fileHeaderPrinted = false;

            for (String record : records) {

                record = record.trim();

                if (record.length() == 0) {
                    continue;
                }

                /*
                 * Z-Algorithm searches the keyword
                 * inside the current record.
                 */
                if (ZAlgorithm.search(record, keyword)) {

                    if (!fileHeaderPrinted) {

                        System.out.println("\n========================================");
                        System.out.println(
                                "FILE: " + file.getName());
                        System.out.println("========================================");

                        fileHeaderPrinted = true;
                    }

                    System.out.println(record);
                    System.out.println();

                    recordsFound++;
                }
            }
        }

        System.out.println("----------------------------------------");
        System.out.println("Files Checked : " + filesChecked);
        System.out.println("Records Found : " + recordsFound);

        if (recordsFound == 0) {

            System.out.println(
                    "No information found for: " + keyword);
        }
    }
}