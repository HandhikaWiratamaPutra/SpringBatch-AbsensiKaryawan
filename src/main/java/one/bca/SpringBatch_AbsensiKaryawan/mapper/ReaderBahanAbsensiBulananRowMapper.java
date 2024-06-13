package one.bca.SpringBatch_AbsensiKaryawan.mapper;

import one.bca.SpringBatch_AbsensiKaryawan.model.AbsensiBulanan;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;

@Component
public class ReaderBahanAbsensiBulananRowMapper implements RowMapper<AbsensiBulanan> {

    @Override
    public AbsensiBulanan mapRow(ResultSet rs, int rowNum) throws SQLException {
        AbsensiBulanan absensiBulanan = new AbsensiBulanan();

        absensiBulanan.setKaryawanId(rs.getLong("karyawan_id"));
        absensiBulanan.setTotalKehadiran(rs.getInt("total_kehadiran"));
        absensiBulanan.setTotalDurasiLembur(Duration.ofSeconds(rs.getLong("total_durasi_lembur")));
//        System.out.println("absensiBulanan : " + absensiBulanan.toString());
        return absensiBulanan;
    }
}
