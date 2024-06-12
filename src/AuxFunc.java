import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class AuxFunc {
    public static String generateRandomDNA(int length) {
        Random random = new Random();
        StringBuilder dna = new StringBuilder();
        String nucleotides = "ACGT";
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(nucleotides.length());
            char randomNucleotide = nucleotides.charAt(randomIndex);
            dna.append(randomNucleotide);
        }
        return dna.toString();

    }

public static String[] generateRandomPattern(int count, int length) {
    String[] res = new String[count];
    Set<String> patterns = new HashSet<>();
    Random random = new Random();
    StringBuilder pattern = new StringBuilder();
    String nucleotides = "ACGT";
    while (patterns.size() < count) {
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(nucleotides.length());
            char randomNucleotide = nucleotides.charAt(randomIndex);
            pattern.append(randomNucleotide);
        }
        patterns.add(pattern.toString());
        pattern.setLength(0);
    }
    int i = 0;
    for (String pat : patterns) {
        res[i++] = pat;
    }
    return res;

}

    public static void main(String[] args) {
        Map<String, String> disease = Map.ofEntries(Map.entry("AGCTGCA", "Cystic Fibrosis"),
                Map.entry("TGCAGTA", "Anemia"), Map.entry("AACGTTC", "Huntington's Disease"),
                Map.entry("CGTACGA", "Hemophilia"), Map.entry("GATCAGC", "Tay-Sachs Disease"));
        String arr[] = { "AGCTGCA", "TGCAGTA", "AACGTTC", "CGTACGA", "GATCAGC" };
        // String arr[] = generateRandomPattern(32, 5);
        String text = generateRandomDNA(32000);
        int k = arr.length;
        for (String string : arr) {
          System.out.println(string);
        }
            
        long startTime;
        long stopTime;
        startTime = System.nanoTime();
        System.out.println();
        for (String pattern : arr) {
            KMP.kmpMatch(pattern, text);
        }
        for (String pattern : arr) {
            BM.BMmatch(pattern, text);
        }
        AhoCorasick.search(arr, k, text);
        stopTime = System.nanoTime();

        System.out.println("BATAS");
        System.out.println();
        System.out.println();
        startTime = System.nanoTime();
        for (String pattern : arr) {
            KMP.kmpMatch(pattern, text);
        }
        stopTime = System.nanoTime();
        if (!KMP.found.isEmpty()) {
            System.out.println("Person A diduga mempunyai penyakit: ");
            for (String pattern : KMP.found) {
                System.out.println(" -" + disease.get(pattern));
            }
        }
        System.out.println("Waktu yang dibutuhkan (algoritma KMP): " + (stopTime - startTime) / 1000 + " \u00B5s\n\n");

        startTime = System.nanoTime();
        for (String pattern : arr) {
            BM.BMmatch(pattern, text);
        }
        stopTime = System.nanoTime();
        if (!BM.found.isEmpty()) {
            System.out.println("Person A diduga mempunyai penyakit: ");
            for (String pattern : BM.found) {
                System.out.println(" -" + disease.get(pattern));
            }
        }
        System.out.println("Waktu yang dibutuhkan (algoritma BM): " + (stopTime - startTime) / 1000 + " \u00B5s\n\n");

        startTime = System.nanoTime();
        AhoCorasick.search(arr, k, text);
        stopTime = System.nanoTime();
        if (!AhoCorasick.found.isEmpty()) {
            System.out.println("Person A diduga mempunyai penyakit: ");
            for (String pattern : AhoCorasick.found) {
                System.out.println(" -" + disease.get(pattern));
            }
        }
        System.out.println("Waktu yang dibutuhkan (algoritma Aho-Corasick): " + (stopTime - startTime) / 1000 + " \u00B5s");
        

    }
}
