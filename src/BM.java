import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BM{
    static Set<String> found = new HashSet<>();
    public static Map<Character, Integer> BuildLast(String pattern){
        Map<Character, Integer> last = new HashMap<Character, Integer>();
        for (int i = 0; i < pattern.length(); i++)
        {
            last.put(pattern.charAt(i), i);
        }
        return last;
    }
    public static int BMmatch(String pattern, String text){
        Map<Character, Integer> last = BuildLast(pattern);
        int n = text.length();
        int m = pattern.length();
        if (m > n){
            return - 1;
        }
        int i = m - 1;
        int j = m - 1;
        do{
            if(pattern.charAt(j) == text.charAt(i)){
                if(j == 0){
                    System.out.println("Found " + pattern + " in text at index " + (i));
                    found.add(pattern);
                    int lo = last.getOrDefault(text.charAt(i), -1);
                    i = i + m - Math.min(j, lo + 1);
                    j = m - 1;
                    // return i;
                }else{
                    j--; i--;
                }
            }else{
                int lo = last.getOrDefault(text.charAt(i), -1);
                i = i + m - Math.min(j, lo + 1);
                j = m - 1;
            }
        } while(i <= n - 1);
        if(found.isEmpty()){
            return -1;
        }else{
            return 1;
        }
    }
    public static void main(String[] args) {
        Map<String, String> disease = Map.ofEntries(
            Map.entry("AGCTGCA", "Cystic Fibrosis"), 
            Map.entry("TGCAGTA", "Anemia"), 
            Map.entry("AACGTTC", "Huntington's Disease"), 
            Map.entry("CGTACGA", "Hemophilia"), 
            Map.entry("GATCAGC", "Tay-Sachs Disease"));
        String arr[] = { "AGCTGCA", "TGCAGTA", "AACGTTC", "CGTACGA", "GATCAGC" };
        String text ="CCTTTGTCTACGGGTAATGCTCAGCATTTCCGGATGCCGACACAGGGCGTCCTTAACTACTATCTCACAATACCGTCTCTTCTAGCGGATGCTCCGTGAGCAAGGAGTCCTATCGCGCTCTTCTCTAGTACCCTATGCGCTACGGATCCATAAGCCGTAATGCTACGCGAGAATTCAATGGATGTCTGCTAATTACTGGGATTTACTCGCGTAGTCTATGCTCACGTAATCGCGGTCCGAATTACTGCAATCTATCCAGCATTGAAGGGCAAAAGGTCTCGTTAACTTTCGGGTTGGAGATGAAATATCGTAACCGGCGTACATCTCTCACTTCGGAACAGTAACCCATGAAGTGGGGGGGGCATGCGCAACGTTCCGTGGGTGTCGACTCGCGAAAACAGTGCACAGGGGCATTGATTCTCAACTATAACAGGACAAACCTGGGCCTGTTGCACGCGCACGCAACAATCGTGGAGATGACCCCCTTCCGTTCGAAGCAAAAGGCACGAGGGTAGGAACAATATGCATCCCCTAGTGTTTGCGTAGGCTAAGTTCCTAGGAAGCTTCTGTAGATGTCGATTGCCATACGTCATCCTTGCAGACGTTGGACCAAGTTATACTCAATGAATATATTTTAACTGATGCAGATCAGCTAATCGTATGACGAATCCGACCTCTCCCTCCTAGACGAATCCCTATGTTAGGCTTCACAATGATTATTCTCTCGCATATCGGGCTCTTTTCCAGTTAATGTTACCCGTAATAGGTGCTTGGCCTATTTTGCTTGCCCGGGTATAATTCTACTTACGTCTCAGTGGGGAAGACGTGACACGCCGATGGTTAATATTAGACTGTGTGTATATGCAACTTAAAGGATTGCTTGCAGTACCACGAGCGGAAGGCATGGAGCTGAAGGACAAAGACGCTTTCAGCTCTAATCGTACAACTCTTGGCCATGTAACGGGTAGAACAACTCGACCAAGCCTTGAGTATCATCTAA";
        // System.out.println(text);

        long startTime;
        long stopTime;

        startTime = System.nanoTime();
        for (String pattern : arr) {
            BMmatch(pattern, text);
        }
        stopTime = System.nanoTime();
        if(!BM.found.isEmpty()){
            System.out.println("Person A diduga mempunyai penyakit: ");
            for(String pattern : BM.found){
                System.out.println("    -" + disease.get(pattern));
            }
        }
        System.out.println("Waktu yang dibutuhkan: " + (stopTime-startTime)/1000 + " \u00B5s");
    }
}