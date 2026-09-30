import java.io.BufferedReader;
import java.io.IOException;

public class Util {
    public static int readInt(BufferedReader br, String prompt) throws IOException {
        while (true) {
            System.out.print(prompt);
            String line = br.readLine();
            if (line == null) { System.out.println(); System.exit(0); }
            try { return Integer.parseInt(line.trim()); }
            catch (NumberFormatException e) { System.out.println("Please enter a whole number."); }
        }
    }

    // Reads a line of space-separated integers (blank line -> empty array)
    public static int[] readInts(BufferedReader br, String prompt) throws IOException {
        System.out.print(prompt);
        String line = br.readLine();
        if (line == null || line.trim().isEmpty()) return new int[0];
        String[] parts = line.trim().split("\\s+");
        IntList list = new IntList();
        for (String p : parts) {
            try { list.add(Integer.parseInt(p)); } catch (NumberFormatException e) { }
        }
        return list.toArray();
    }
}
