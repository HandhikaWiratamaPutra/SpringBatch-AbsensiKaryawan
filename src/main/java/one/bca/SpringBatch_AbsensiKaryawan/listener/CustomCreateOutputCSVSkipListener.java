package one.bca.SpringBatch_AbsensiKaryawan.listener;

import one.bca.SpringBatch_AbsensiKaryawan.model.AbsensiOutputCSV;
import org.springframework.batch.core.SkipListener;
import org.springframework.stereotype.Component;

@Component
public class CustomCreateOutputCSVSkipListener implements SkipListener<AbsensiOutputCSV, AbsensiOutputCSV> {
    @Override
    public void onSkipInRead(Throwable t) {
        System.out.println("skip reader CreateOutputCSV dengan error message : " + t.getMessage());
    }

    @Override
    public void onSkipInWrite(AbsensiOutputCSV item, Throwable t) {
        System.out.println("skip writer CreateOutputCSV karayanId : " + item.getKaryawanId() + " | namaDepan : " + item.getNamaDepan() + " - dengan error message : " + t.getMessage());
    }

    @Override
    public void onSkipInProcess(AbsensiOutputCSV item, Throwable t) {
        System.out.println("skip process CreateOutputCSV karyawanId : " + item.getKaryawanId() + " | namaDepan : " + item.getNamaDepan() + " - dengan error message : " + t.getMessage());
    }
}
