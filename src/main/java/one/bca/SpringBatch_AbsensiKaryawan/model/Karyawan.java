package one.bca.SpringBatch_AbsensiKaryawan.model;

import java.time.Duration;
import java.util.Date;

public class Karyawan {
    Long karyawanId;
    String nama;
    String tempatTinggal;
    Date tanggalLahir;
    int jumlahCutiTersisa;
    Duration durasiLembur;
}