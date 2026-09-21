public class FileSearcher {

    // =====================================================
    // EXACT SEARCH
    // =====================================================
    public static void exactSearch(String text, String pattern) {

        System.out.println("\n========== EXACT SEARCH ==========");

        String[] sections = text.split("(?=CROP INFORMATION)");

        boolean found = false;

        for (String section : sections) {

            String cropName = getCropName(section);

            if (cropName.equalsIgnoreCase(pattern)) {

                // Use Z Algorithm for exact matching
                ZAlgorithm.search(cropName.toLowerCase(),
                                  pattern.toLowerCase());

                System.out.println("\n========== CROP DETAILS ==========");
                System.out.println(section.trim());
                System.out.println("==================================");

                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Crop not found.");
        }
    }


    // =====================================================
    // FUZZY SEARCH
    // =====================================================
    public static void fuzzySearch(String text, String pattern) {

        System.out.println("\n========== FUZZY SEARCH ==========");

        String[] sections = text.split("(?=CROP INFORMATION)");

        String bestCrop = "";
        String bestSection = "";
        int bestDistance = Integer.MAX_VALUE;

        // Check every crop automatically
        for (String section : sections) {

            String cropName = getCropName(section);

            if (cropName.isEmpty()) {
                continue;
            }

            int distance = EditDistance.calculate(
                    pattern.toLowerCase(),
                    cropName.toLowerCase()
            );

            if (distance < bestDistance) {

                bestDistance = distance;
                bestCrop = cropName;
                bestSection = section;
            }
        }

        // Automatically accept a close match
        if (!bestCrop.isEmpty() && bestDistance <= 2) {

            System.out.println("\nDid you mean: " + bestCrop + "?");

            System.out.println("\n========== CROP DETAILS ==========");
            System.out.println(bestSection.trim());
            System.out.println("==================================");

        } else {

            System.out.println("No related crop found.");
        }
    }


    // =====================================================
    // GET CROP NAME
    // =====================================================
    private static String getCropName(String section) {

        String[] lines = section.split("\\r?\\n");

        for (String line : lines) {

            line = line.trim();

            if (line.toLowerCase().startsWith("crop_name:")) {

                return line.substring("Crop_Name:".length()).trim();
            }
        }

        return "";
    }
}