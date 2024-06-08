package one.bca.SpringBatch_AbsensiKaryawan.model;

import java.sql.Time;
import java.time.Duration;
import java.util.Date;

public class AbsensiBulanan {
    Long karyawanId;
    Date bulanDanTahunAbsensi;
    Duration totalDurasiLembur;
    int totalKehadiran;
}
