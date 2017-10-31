import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("res/MultiplexedSamples.txt"));

        int lines = 100000;
        int count = 0;
        int unmultiplexedSequences = 0;
        String line = bufferedReader.readLine();

        HashMap<String, Integer> sequenceAmount = new HashMap<>();
        HashMap<String, HashMap<Integer, Integer>> lengthsMap = new HashMap<>();
        while (line != null) {
            int stringLength = 50;
            while (line.charAt(stringLength) == 'A') {
                stringLength -= 1;
            }
            String sequenceWithAdapter = line.substring(0, stringLength + 1);
            if (sequenceWithAdapter.contains("TCGTATGCCGTCTTCTGCTTG")) {
                String sequenceWithBarcode = sequenceWithAdapter.substring(0, sequenceWithAdapter.length() - 21);

                int leftT = 5;
//                while (sequenceWithBarcode.charAt(sequenceWithBarcode.length() - leftT) != 'T') {
//                    leftT += 1;
//                }

                String barcode = sequenceWithBarcode.substring(sequenceWithBarcode.length() - leftT);
//                System.out.println("barcode: " + barcode);
                String sequence = sequenceWithBarcode.substring(0, sequenceWithBarcode.length() - leftT);

//                System.out.println(sequence);
                if (sequenceAmount.containsKey(barcode)) {
                    sequenceAmount.put(barcode, sequenceAmount.get(barcode) + 1);
                } else {
                    sequenceAmount.put(barcode, 1);
                }

                if (lengthsMap.containsKey(barcode)) {
                    if (lengthsMap.get(barcode).containsKey(sequence.length())) {
                        lengthsMap.get(barcode).put(sequence.length(), lengthsMap.get(barcode).get(sequence.length()) + 1);
                    } else {
                        lengthsMap.get(barcode).put(sequence.length(), 1);
                    }
                } else {
                    HashMap<Integer, Integer> sequenceLengths = new HashMap<>();
                    sequenceLengths.put(sequence.length(), 1);
                    lengthsMap.put(barcode, sequenceLengths);
                }
            } else {
                unmultiplexedSequences++;
            }
//            System.out.println(sequenceWithAdapter);
            line = bufferedReader.readLine();
            count++;
        }

        System.out.println("Total read: " + count);
        System.out.println("Amount of sequences not multiplexed: " + unmultiplexedSequences);
    }
}
