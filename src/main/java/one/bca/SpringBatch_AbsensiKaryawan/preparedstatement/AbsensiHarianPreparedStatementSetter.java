package one.bca.SpringBatch_AbsensiKaryawan.preparedstatement;

import one.bca.SpringBatch_AbsensiKaryawan.model.AbsensiHarian;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AbsensiHarianPreparedStatementSetter implements ItemPreparedStatementSetter<AbsensiHarian> {

    @Override
    public void setValues(AbsensiHarian item, PreparedStatement ps) throws SQLException {
        ps.setLong(1,item.getAbsenId());
        ps.setLong(2,item.getKaryawanId());
        ps.setDate(3, new Date(item.getTanggalAbsen().getTime()));
        ps.setTime(4, item.getWaktuClockIn());
        ps.setTime(5, item.getWaktuClockOut());
        ps.setTime(6, item.getDurasiLembur());
    }
}
