import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class KMP {
    static Set<String> found = new HashSet<>();
    public static int kmpMatch(String pattern, String text) {
        int n = text.length();
        int m = pattern.length();
        int border[] = borderFunc(pattern);
        int j = 0;
        int i = 0;
        while (i < n) {
            if (pattern.charAt(j) == text.charAt(i)) {
                if (j == m - 1) {
                    System.out.println("Found " + pattern + " in text at index " + (i-m+1));
                    found.add(pattern);
                    j = border[j-1];
                    // return i - m + 1;
                }
                i++;
                j++;
            } else if (j > 0) {
                j = border[j - 1];
            } else {
                i++;
            }
            if (i == n) {
                break;
            }
        }
        if(found.isEmpty()){
            return -1;
        }else{
            return 1;
        }

    }

    public static int[] borderFunc(String pattern) {
        int border[] = new int[pattern.length()];
        border[0] = 0;
        int m = pattern.length();
        int j = 0;
        int i = 1;
        while (i < m) {
            if (pattern.charAt(j) == pattern.charAt(i)) {
                border[i] = j + 1;
                i++;
                j++;
            } else if (j > 0) {
                j = border[j - 1];
            } else {
                border[i] = 0;
                i++;
            }
        }
        return border;
    }

public static void main(String[] args) {
    Map<String, String> disease = Map.ofEntries(
        Map.entry("AGCTGCA", "Cystic Fibrosis"), 
        Map.entry("TGCAGTA", "Anemia"), 
        Map.entry("AACGTTC", "Huntington's Disease"), 
        Map.entry("CGTACGA", "Hemophilia"), 
        Map.entry("GATCAGC", "Tay-Sachs Disease"));
    String arr[] = { "AGCTGCA", "TGCAGTA", "AACGTTC", "CGTACGA", "GATCAGC" };
    String text = "CCTTTGTCTACGGGTAATGCTCAGCATTTCCGGATGCCGACACAGGGCGTCCTTAACTACTATCTCACAATACCGTCTCTTCTAGCGGATGCTCCGTGAGCAAGGAGTCCTATCGCGCTCTTCTCTAGTACCCTATGCGCTACGGATCCATAAGCCGTAATGCTACGCGAGAATTCAATGGATGTCTGCTAATTACTGGGATTTACTCGCGTAGTCTATGCTCACGTAATCGCGGTCCGAATTACTGCAATCTATCCAGCATTGAAGGGCAAAAGGTCTCGTTAACTTTCGGGTTGGAGATGAAATATCGTAACCGGCGTACATCTCTCACTTCGGAACAGTAACCCATGAAGTGGGGGGGGCATGCGCAACGTTCCGTGGGTGTCGACTCGCGAAAACAGTGCACAGGGGCATTGATTCTCAACTATAACAGGACAAACCTGGGCCTGTTGCACGCGCACGCAACAATCGTGGAGATGACCCCCTTCCGTTCGAAGCAAAAGGCACGAGGGTAGGAACAATATGCATCCCCTAGTGTTTGCGTAGGCTAAGTTCCTAGGAAGCTTCTGTAGATGTCGATTGCCATACGTCATCCTTGCAGACGTTGGACCAAGTTATACTCAATGAATATATTTTAACTGATGCAGATCAGCTAATCGTATGACGAATCCGACCTCTCCCTCCTAGACGAATCCCTATGTTAGGCTTCACAATGATTATTCTCTCGCATATCGGGCTCTTTTCCAGTTAATGTTACCCGTAATAGGTGCTTGGCCTATTTTGCTTGCCCGGGTATAATTCTACTTACGTCTCAGTGGGGAAGACGTGACACGCCGATGGTTAATATTAGACTGTGTGTATATGCAACTTAAAGGATTGCTTGCAGTACCACGAGCGGAAGGCATGGAGCTGAAGGACAAAGACGCTTTCAGCTCTAATCGTACAACTCTTGGCCATGTAACGGGTAGAACAACTCGACCAAGCCTTGAGTATCATCTAA";

    long startTime;
    long stopTime;

    startTime = System.nanoTime();
    for (String pattern : arr) {
        KMP.kmpMatch(pattern, text);
    }
    stopTime = System.nanoTime();
    if(!KMP.found.isEmpty()){
        System.out.println("Person A diduga mempunyai penyakit: ");
        for(String pattern : KMP.found){
            System.out.println("    -" + disease.get(pattern));
        }
    }
    System.out.println("Waktu yang dibutuhkan: " + (stopTime-startTime)/1000 + " \u00B5s");
}
}
