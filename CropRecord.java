// One "CROP INFORMATION" section: a list of Key: value fields
public class CropRecord {
    public String name = "";
    public String[] keys = new String[8];
    public String[] values = new String[8];
    public int fieldCount = 0;

    public void addField(String key, String value) {
        if (fieldCount == keys.length) {
            String[] k = new String[fieldCount * 2];
            String[] v = new String[fieldCount * 2];
            System.arraycopy(keys, 0, k, 0, fieldCount);
            System.arraycopy(values, 0, v, 0, fieldCount);
            keys = k;
            values = v;
        }
        keys[fieldCount] = key;
        values[fieldCount] = value;
        fieldCount++;
    }

    public void appendToLastField(String more) {
        if (fieldCount > 0) values[fieldCount - 1] = values[fieldCount - 1] + " " + more;
    }
}
