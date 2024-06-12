package one.bca.SpringBatch_AbsensiKaryawan.mapper;

import one.bca.SpringBatch_AbsensiKaryawan.model.AbsensiHarian;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

import java.net.BindException;
import java.sql.Time;

public class AbsensiHarianMapper implements FieldSetMapper<AbsensiHarian> {

    @Override
    public AbsensiHarian mapFieldSet(FieldSet fieldSet) {
        AbsensiHarian absensiHarian = new AbsensiHarian();
        absensiHarian.setAbsenId(fieldSet.readLong("absen_id"));
        absensiHarian.setKaryawanId(fieldSet.readLong("karyawan_id"));
        absensiHarian.setTanggalAbsen(fieldSet.readDate("tanggal_absen"));
        absensiHarian.setWaktuClockIn(Time.valueOf(fieldSet.readString("waktu_clockin")));
        absensiHarian.setWaktuClockOut(Time.valueOf(fieldSet.readString("waktu_clockout")));
        absensiHarian.setDurasiLembur(Time.valueOf(fieldSet.readString("durasi_lembur")));
        return absensiHarian;
    }
}
