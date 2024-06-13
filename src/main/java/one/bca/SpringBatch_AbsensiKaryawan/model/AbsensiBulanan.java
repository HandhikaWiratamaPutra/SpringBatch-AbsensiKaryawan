package one.bca.SpringBatch_AbsensiKaryawan.model;

import java.sql.Date;
import java.time.Duration;

public class AbsensiBulanan {
    private Long karyawanId;
    private Date bulanDanTahunAbsensi;
    private Duration totalDurasiLembur;
    private int totalKehadiran;

    @Override
    public String toString() {
        return "AbsensiBulanan{" +
                "karyawanId=" + karyawanId +
                ", bulanDanTahunAbsensi=" + bulanDanTahunAbsensi +
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
