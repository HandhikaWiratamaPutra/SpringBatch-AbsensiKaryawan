package one.bca.SpringBatch_AbsensiKaryawan.partitionoer;

import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;

import java.util.HashMap;
import java.util.Map;

public class PartitionerAbsensiOutput implements Partitioner {
    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
//        System.out.println("PartitionerAbsensiOutput started");
        Map<String, ExecutionContext> result = new HashMap<>();

        int fromChar = 1;
        int toChar = gridSize;


        for (int i = 1; i <= gridSize; i++) {
            toChar = (26 * i / gridSize);    //ada 26 karakter dari A-Z dan asumsi semua nama depan akan berawalan huruf kapital
            ExecutionContext value = new ExecutionContext();
            value.putString("fromChar", String.valueOf((char)('A'- 1 + fromChar)));
            value.putString("toChar", String.valueOf((char)('A'- 1 + toChar)));
            value.putString("fileName", "data/absensi_output" + i + ".csv");
            System.out.println("value partitioner ke-" + i + "= "+ value + " | fromChar = "+ fromChar + " | toChar = " + toChar);
            result.put("partition" + i, value);
            fromChar = toChar + 1;
        }
//        System.out.println("PartitionerAbsensiOutput ended");
        return result;
    }
}
