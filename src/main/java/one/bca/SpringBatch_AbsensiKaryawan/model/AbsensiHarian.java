package one.bca.SpringBatch_AbsensiKaryawan.model;

import java.sql.Time;
import java.util.Date;

public class AbsensiHarian {
    private Long absenId;
    private Long karyawanId;
    private Date tanggalAbsen;
    private Time waktuClockIn;
    private Time waktuClockOut;
    private Time durasiLembur;

    @Override
    public String toString() {
        return "AbsensiHarian{" +
                "absenId=" + absenId +
                ", karyawanId=" + karyawanId +
                ", tanggalAbsen=" + tanggalAbsen +
                ", waktuClockIn=" + waktuClockIn +
                ", waktuClockOut=" + waktuClockOut +
                ", durasiLembur=" + durasiLembur +
                '}';
    }

    // setter getter

    public Long getAbsenId() {
        return absenId;
    }

    public void setAbsenId(Long absenId) {
        this.absenId = absenId;
    }

    public Long getKaryawanId() {
        return karyawanId;
    }

    public void setKaryawanId(Long karyawanId) {
        this.karyawanId = karyawanId;
    }

    public Date getTanggalAbsen() {
        return tanggalAbsen;
    }

    public void setTanggalAbsen(Date tanggalAbsen) {
        this.tanggalAbsen = tanggalAbsen;
    }

    public Time getWaktuClockIn() {
        return waktuClockIn;
    }

    public void setWaktuClockIn(Time waktuClockIn) {
        this.waktuClockIn = waktuClockIn;
    }

    public Time getWaktuClockOut() {
        return waktuClockOut;
    }

    public void setWaktuClockOut(Time waktuClockOut) {
        this.waktuClockOut = waktuClockOut;
    }

    public Time getDurasiLembur() {
        return durasiLembur;
    }

    public void setDurasiLembur(Time durasiLembur) {
        this.durasiLembur = durasiLembur;
    }
}
