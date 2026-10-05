import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringBuilder corpus = new StringBuilder();
        File[] files = new File("corpus").listFiles();
        if (files != null) {
            for (File f : files) if (f.isFile()) corpus.append(CorpusReader.readFile(f));
        }
        KnowledgeBase kb = new KnowledgeBase(corpus.toString());
        System.out.println("Loaded " + kb.count + " crops from the corpus folder.");
        if (kb.count == 0) System.out.println("WARNING: no crops found. Put your .txt files in a folder named 'corpus'.");

        while (true) {
            System.out.println("\n========== FARM ASSIST ==========");
            System.out.println("1. Search (crop / disease / pest / anything)");
            System.out.println("2. List all crops");
            System.out.println("3. Compare string-search algorithms (Naive/KMP/Z/Rabin-Karp)");
            System.out.println("4. Water Distribution (Max Flow)");
            System.out.println("5. Crop -> Field Assignment (Bipartite Matching)");
            System.out.println("6. Field Visit Route (Bitmask DP / TSP)");
            System.out.println("7. Field Inspection Planner (Vertex Cover Approximation)");
            System.out.println("8. Crops of the Day (Reservoir Sampling)");
            System.out.println("9. Exit");

            int choice = Util.readInt(br, "Enter your choice: ");
            switch (choice) {
                case 1:
                    System.out.print("Enter crop / disease / keyword: ");
                    FileSearcher.search(kb, br.readLine(), br);
                    break;
                case 2:
                    for (int i = 0; i < kb.count; i++) System.out.println((i + 1) + ". " + kb.records[i].name);
                    break;
                case 3:
                    System.out.print("Enter pattern to benchmark: ");
                    Benchmark.run(kb, br.readLine().trim());
                    break;
                case 4:
                    MaxFlow.runWaterDistribution(br);
                    break;
                case 5:
                    CropFieldMatcher.run(br, kb);
                    break;
                case 6:
                    RoutePlanner.run(br);
                    break;
                case 7:
                    FieldInspector.run(br);
                    break;
                case 8:
                    int k = Util.readInt(br, "How many crops? ");
                    String[] names = new String[kb.count];
                    for (int i = 0; i < kb.count; i++) names[i] = kb.records[i].name;
                    String[] pick = ReservoirSampler.sample(names, kb.count, k);
                    for (String s : pick) System.out.println(" * " + s);
                    break;
                case 9:
                    System.out.println("Exiting Farm Assist...");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
