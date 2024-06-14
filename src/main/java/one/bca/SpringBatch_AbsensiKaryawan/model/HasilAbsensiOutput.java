package one.bca.SpringBatch_AbsensiKaryawan.model;

import java.io.Serializable;
import java.time.Duration;

public class HasilAbsensiOutput extends AbsensiOutputCSV implements Serializable  {
    Duration totalDurasiLembur;

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

    public Duration getTotalDurasiLembur() {
        return totalDurasiLembur;
    }

    public void setTotalDurasiLembur(Duration totalDurasiLembur) {
        this.totalDurasiLembur = totalDurasiLembur;
    }
}