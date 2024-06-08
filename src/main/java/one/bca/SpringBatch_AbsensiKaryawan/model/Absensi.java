package one.bca.SpringBatch_AbsensiKaryawan.model;

import java.sql.Time;
import java.time.Duration;
import java.util.Date;

public class Absensi {
    Long absenId;
    Long karyawanId;
    Date tanggalAbsen;
    Time waktuClockIn;
    Time waktuClockOut;
    Duration durationOverTime;
    Boolean isValid;
}
