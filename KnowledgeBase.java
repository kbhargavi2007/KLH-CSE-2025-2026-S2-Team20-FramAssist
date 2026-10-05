// Parses the whole corpus into CropRecord objects (generic Key: value parser,
// so it works for any field name: Diseases, Pests, Soil, Fertilizer, ...)
public class KnowledgeBase {
    public CropRecord[] records = new CropRecord[16];
    public int count = 0;
    public String corpusLower = "";   // whole corpus, lowercase (used by benchmark)

    private static final String MARKER = "CROP INFORMATION";

    public KnowledgeBase(String corpusText) {
        corpusLower = corpusText.toLowerCase();
        int start = corpusText.indexOf(MARKER);
        while (start >= 0) {
            int next = corpusText.indexOf(MARKER, start + MARKER.length());
            String section = (next < 0) ? corpusText.substring(start) : corpusText.substring(start, next);
            CropRecord rec = parse(section);
            if (rec != null) add(rec);
            start = next;
        }
    }

    private void add(CropRecord r) {
        if (count == records.length) {
            CropRecord[] bigger = new CropRecord[count * 2];
            System.arraycopy(records, 0, bigger, 0, count);
            records = bigger;
        }
        records[count++] = r;
    }

    private static CropRecord parse(String section) {
        CropRecord rec = new CropRecord();
        String[] lines = section.split("\\r?\\n");
        for (String raw : lines) {
            String line = raw.trim();
            if (line.isEmpty() || line.startsWith("===") || line.startsWith("---")) continue;
            if (line.equalsIgnoreCase("CROP INFORMATION")) continue;
            int colon = line.indexOf(':');
            if (colon > 0 && colon <= 40) {
                rec.addField(line.substring(0, colon).trim(), line.substring(colon + 1).trim());
            } else {
                rec.appendToLastField(line);   // continuation of previous value
            }
        }
        if (rec.fieldCount == 0) return null;
        for (int i = 0; i < rec.fieldCount; i++) {
            String k = rec.keys[i].toLowerCase().replace("_", "").replace(" ", "");
            if (k.equals("cropname") || k.equals("name")) { rec.name = rec.values[i]; break; }
        }
        if (rec.name.isEmpty()) rec.name = rec.values[0];
        return rec;
    }

    public static boolean isNameKey(String key) {
        String k = key.toLowerCase().replace("_", "").replace(" ", "");
        return k.equals("cropname") || k.equals("name");
    }
}
