import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class AhoCorasick {
    static int MAXS = 500; // maximum number of states. Jumlahnya mirip dengan jummlah panjang kata pattern
    static int MAXC = 4; // jumlah alphabet
    static List<List<Integer>> out = new ArrayList<>();
    static int fail[] = new int[MAXS];
    static int go[][] = new int[MAXS][MAXC];
    static Map<Character, Integer> enumMap = Map.ofEntries(Map.entry('A', 0), Map.entry('G', 1), Map.entry('C', 2), Map.entry('T', 3));
    static Set<String> found = new HashSet<>();

    static int buildMachine(String arr[], int k){
        for(int i = 0; i < MAXS; i++){
            out.add(new ArrayList<>());
        }
        for(int i = 0; i < MAXS; i++){
            Arrays.fill(go[i], -1);
        }
        int states = 1;
        for(int i = 0; i < k; i++){
            String pattern = arr[i];
            int currState = 0;
            for(int j = 0; j < pattern.length(); j++){
                int ch = enumMap.get(pattern.charAt(j));
                if(go[currState][ch] == -1){
                    go[currState][ch] = states++;
                }
                currState = go[currState][ch];
            }
            out.get(currState).add(i); // tambahkan index word ke output
        }
        // untuk setiap character yang tidak punya edge dari root 
        // dikasih edge dari root ke root
        for(int ch = 0; ch < MAXC; ch++){
            if(go[0][ch] == -1){
                go[0][ch] = 0;
            }
        }
        Arrays.fill(fail, -1);

        Queue<Integer> queue = new LinkedList<>();

        for(int ch = 0; ch < MAXC; ch++){
            if(go[0][ch] != 0){
                fail[go[0][ch]] = 0;
                queue.add(go[0][ch]);
            }
        }
        while(!queue.isEmpty()){
            int state = queue.poll(); // state sebelum 
            for(int ch = 0; ch < MAXC; ch++){
                if(go[state][ch] != -1){ // terdefinisi child node dari state sebelum untuk ch
                    int failureState = fail[state]; // FAIL STATE DARI STATE sebelum
                    while(go[failureState][ch] == -1){ // fail state dari state sebelum tidak terdefinisi juga
                        failureState = fail[failureState]; // pergi ke fail statenya fail state dari state sebelum 
                        // cari sampe terdefinisi child node untuk ch
                    }
                    failureState = go[failureState][ch]; // fail state untuk state saat ini
                    fail[go[state][ch]] = failureState;
                    out.get(go[state][ch]).addAll(out.get(failureState)); // gabungkan output
                    queue.add(go[state][ch]); // masukkan ke dalam queue untuk diproses selanjutnya
                }
            }
        }
        return states;
    }
static int nextState(int currState, char currInput){
    int next = currState;
    int input = enumMap.get(currInput);
    while(go[next][input] == -1){
        next = fail[next];
    }
    next = go[next][input];
    return next;
}
static void search(String[] arrPattern, int k, String text){
    int numOfStates = buildMachine(arrPattern, k);
    int currState = 0;
    for(int i = 0; i < text.length(); i++){
        currState = nextState(currState, text.charAt(i));
        if(out.get(currState).isEmpty()){
            continue;
        }
        for(int index: out.get(currState)){
            System.out.println("Pattern " + arrPattern[index] + " found in text at index " + (i - arrPattern[index].length() + 1));
            found.add(arrPattern[index]);
        }
    }
    out.clear();

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
        // System.out.println(text);

        long startTime;
        long stopTime;

        startTime = System.nanoTime();
        search(arr, 5, text);
        stopTime = System.nanoTime();
        if(!AhoCorasick.found.isEmpty()){
            System.out.println("Person A diduga mempunyai penyakit: ");
            for(String pattern : AhoCorasick.found){
                System.out.println("    -" + disease.get(pattern));
            }
        }
        System.out.println("Waktu yang dibutuhkan: " + (stopTime-startTime)/1000 + " \u00B5s");
    }
}
