import java.io.File;

public class FileSearcher {

    public static void searchCorpus(String folderPath, String keyword) {

        File folder = new File(folderPath);

        if (!folder.exists() || !folder.isDirectory()) {

            System.out.println("Corpus folder not found.");
            return;
        }

        File[] files = folder.listFiles();

        if (files == null || files.length == 0) {

            System.out.println("No corpus files found.");
            return;
        }

        int filesChecked = 0;
        int cropsFound = 0;

        System.out.println();
        System.out.println("Searching 100-crop corpus...");
        System.out.println("Search Algorithm: Z-Algorithm");
        System.out.println("----------------------------------------");

        for (File file : files) {

            // Only process .txt files
            if (!file.isFile() ||
                !file.getName().toLowerCase().endsWith(".txt")) {

                continue;
            }

            filesChecked++;

            // Read the complete crop file
            String completeText = CorpusReader.readFile(file);

            if (completeText.length() == 0) {
                continue;
            }

            /*
             * Each file represents one crop.
             *
             * Therefore, the Z-Algorithm searches
             * the complete crop document.
             */
            if (ZAlgorithm.search(completeText, keyword)) {

                System.out.println();
                System.out.println("========================================");
                System.out.println("MATCH FOUND");
                System.out.println("CROP FILE: " + file.getName());
                System.out.println("========================================");

                System.out.println(completeText);

                cropsFound++;
            }
        }

        System.out.println("----------------------------------------");
        System.out.println("Crop Files Checked : " + filesChecked);
        System.out.println("Matching Crops     : " + cropsFound);

        if (cropsFound == 0) {

            System.out.println();
            System.out.println(
                    "No information found for: " + keyword);
        }
    }
}