// CO6 - Reservoir sampling: uniform random k items from a stream of unknown length.
// Used for "Crops of the day". Own random generator (java.util.Random is not allowed).
public class ReservoirSampler {
    private static long seed = System.nanoTime();

    private static int nextInt(int bound) {
        seed = seed * 6364136223846793005L + 1442695040888963407L;
        return (int) ((seed >>> 33) % bound);
    }

    public static String[] sample(String[] stream, int total, int k) {
        if (k > total) k = total;
        String[] reservoir = new String[k];
        for (int i = 0; i < total; i++) {
            if (i < k) {
                reservoir[i] = stream[i];
            } else {
                int j = nextInt(i + 1);          // 0..i
                if (j < k) reservoir[j] = stream[i];
            }
        }
        return reservoir;
    }
}
