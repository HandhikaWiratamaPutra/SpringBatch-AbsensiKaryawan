package one.bca.SpringBatch_AbsensiKaryawan.model;

import java.io.Serializable;
import java.time.Duration;

public class HasilAbsensiInput extends AbsensiOutputCSV implements Serializable  {
    String totalDurasiLembur;

    // Constructor, getters, setters, and toString method

    @Override
    public String toString() {
        return "HasilAbsensiInput{" +
                "totalDurasiLembur=" + totalDurasiLembur +
                ", karyawanId=" + karyawanId +
                ", namaDepan='" + namaDepan + '\'' +
                ", namaBelakang='" + namaBelakang + '\'' +
                ", jumlahCutiTersisa=" + jumlahCutiTersisa +
                ", jumlahCutiTelahDiambil=" + jumlahCutiTelahDiambil +
                ", totalDurasiLembur=" + totalDurasiLembur +
                ", totalKehadiran=" + totalKehadiran +
                '}';
    }

    public String getTotalDurasiLembur() {
        return totalDurasiLembur;
    }

    public void setTotalDurasiLembur(String totalDurasiLembur) {
        this.totalDurasiLembur = totalDurasiLembur;
    }
}