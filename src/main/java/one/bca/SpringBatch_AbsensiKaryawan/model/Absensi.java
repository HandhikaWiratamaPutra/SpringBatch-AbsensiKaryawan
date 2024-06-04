package one.bca.SpringBatch_AbsensiKaryawan.model;

import java.time.Duration;
import java.util.Date;

public class Absensi {
    Long absenId;
    Long karyawanId;
    Date tanggalAbsen;
    Date waktuClockIn;
    Date waktuClockOut;
    Duration durationOverTime;
    Boolean isValid;
}
