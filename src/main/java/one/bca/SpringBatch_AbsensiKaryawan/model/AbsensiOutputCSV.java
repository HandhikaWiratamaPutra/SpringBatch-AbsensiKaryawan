package one.bca.SpringBatch_AbsensiKaryawan.model;

import java.io.Serializable;
import java.time.Duration;

public class AbsensiOutputCSV implements Serializable {
    Long karyawanId;
    String namaDepan;
    String namaBelakang;
    int jumlahCutiTersisa;
    int jumlahCutiTelahDiambil;
    Duration totalDurasiLembur;
    int totalKehadiran;

    // Constructor, getters, setters, and toString method

    @Override
    public String toString() {
        return "AbsensiOutputCSV{" +
                "karyawanId=" + karyawanId +
                ", namaDepan='" + namaDepan + '\'' +
                ", namaBelakang='" + namaBelakang + '\'' +
                ", jumlahCutiTersisa=" + jumlahCutiTersisa +
                ", jumlahCutiTelahDiambil=" + jumlahCutiTelahDiambil +
                ", totalDurasiLembur=" + totalDurasiLembur +
                ", totalKehadiran=" + totalKehadiran +
                '}';
    }

    public Long getKaryawanId() {
        return karyawanId;
    }

    public void setKaryawanId(Long karyawanId) {
        this.karyawanId = karyawanId;
    }

    public String getNamaDepan() {
        return namaDepan;
    }

    public void setNamaDepan(String namaDepan) {
        this.namaDepan = namaDepan;
    }

    public String getNamaBelakang() {
        return namaBelakang;
    }

    public void setNamaBelakang(String namaBelakang) {
        this.namaBelakang = namaBelakang;
    }

    public int getJumlahCutiTersisa() {
        return jumlahCutiTersisa;
    }

    public void setJumlahCutiTersisa(int jumlahCutiTersisa) {
        this.jumlahCutiTersisa = jumlahCutiTersisa;
    }

    public int getJumlahCutiTelahDiambil() {
        return jumlahCutiTelahDiambil;
    }

    public void setJumlahCutiTelahDiambil(int jumlahCutiTelahDiambil) {
        this.jumlahCutiTelahDiambil = jumlahCutiTelahDiambil;
    }

    public Duration getTotalDurasiLembur() {
        return totalDurasiLembur;
    }

    public void setTotalDurasiLembur(Duration totalDurasiLembur) {
        this.totalDurasiLembur = totalDurasiLembur;
    }

    public int getTotalKehadiran() {
        return totalKehadiran;
    }

    public void setTotalKehadiran(int totalKehadiran) {
        this.totalKehadiran = totalKehadiran;
    }
}