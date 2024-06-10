package one.bca.SpringBatch_AbsensiKaryawan.model;

import java.sql.Time;
import java.time.Duration;
import java.util.Date;

public class AbsensiBulanan {
    Long karyawanId;
    Date bulanDanTahunAbsensi;
    Duration totalDurasiLembur;
    int totalKehadiran;

    // getter and setter:
    public Long getKaryawanId() {
        return karyawanId;
    }

    public void setKaryawanId(Long karyawanId) {
        this.karyawanId = karyawanId;
    }

    public Date getBulanDanTahunAbsensi() {
        return bulanDanTahunAbsensi;
    }

    public void setBulanDanTahunAbsensi(Date bulanDanTahunAbsensi) {
        this.bulanDanTahunAbsensi = bulanDanTahunAbsensi;
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
