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
        absensiHarian.setWaktuClockIn((Time) fieldSet.readDate("waktu_cloc_in"));
        absensiHarian.setWaktuClockOut((Time) fieldSet.readDate("waktu_clockout"));
        absensiHarian.setDurasiLembur((Time) fieldSet.readDate("durasi_lembur"));
        return absensiHarian;
    }
}
