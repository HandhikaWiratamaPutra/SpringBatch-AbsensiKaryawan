package one.bca.SpringBatch_AbsensiKaryawan.model;

import java.sql.Time;
import java.time.Duration;
import java.util.Date;

public class AbsensiHarian {
    Long absenId;
    Long karyawanId;
    Date tanggalAbsen;
    Time waktuClockIn;

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

    public Duration getDurasiLembur() {
        return durasiLembur;
    }

    public void setDurasiLembur(Duration durasiLembur) {
        this.durasiLembur = durasiLembur;
    }

    Time waktuClockOut;
    Duration durasiLembur;
}
