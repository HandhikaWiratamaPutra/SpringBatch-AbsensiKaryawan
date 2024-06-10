package one.bca.SpringBatch_AbsensiKaryawan.mapper;

import one.bca.SpringBatch_AbsensiKaryawan.model.AbsensiHarian;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;

@Component
public class AbsensiHarianRowMapper implements RowMapper<AbsensiHarian> {

    @Override
    public AbsensiHarian mapRow(ResultSet rs, int rowNum) throws SQLException {
        AbsensiHarian absensiHarian = new AbsensiHarian();
        absensiHarian.setKaryawanId(rs.getLong("karyawan_id"));
        absensiHarian.setTanggalAbsen(rs.getDate("tanggal_absen"));
        absensiHarian.setDurasiLembur(Duration.parse(rs.getString("durasi_lembur")));
        return absensiHarian;
    }
}
