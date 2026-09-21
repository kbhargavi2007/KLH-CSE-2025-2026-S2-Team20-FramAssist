public class ZAlgorithm {

    // Calculate the Z array
    public static int[] calculateZ(String text) {

        int n = text.length();

        int[] z = new int[n];

        int left = 0;
        int right = 0;

        for (int i = 1; i < n; i++) {

            if (i <= right) {
                z[i] = Math.min(
                        right - i + 1,
                        z[i - left]
                );
            }

            while (i + z[i] < n &&
                   text.charAt(z[i]) ==
                   text.charAt(i + z[i])) {

                z[i]++;
            }

            if (i + z[i] - 1 > right) {

                left = i;
                right = i + z[i] - 1;
            }
        }

        return z;
    }

    // Search for a pattern using Z Algorithm
    public static void search(String text, String pattern) {

        String combined = pattern + "$" + text;

        int[] z = calculateZ(combined);

        int count = 0;

        for (int i = 0; i < z.length; i++) {

            if (z[i] == pattern.length()) {

                int position =
                        i - pattern.length() - 1;

                System.out.println(
                        "Match found at position: "
                        + position
                );

                count++;
            }
        }

        System.out.println("Total matches: " + count);
    }
}