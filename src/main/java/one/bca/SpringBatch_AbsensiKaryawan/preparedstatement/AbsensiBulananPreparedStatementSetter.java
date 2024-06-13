package one.bca.SpringBatch_AbsensiKaryawan.preparedstatement;

import one.bca.SpringBatch_AbsensiKaryawan.model.AbsensiBulanan;
import one.bca.SpringBatch_AbsensiKaryawan.model.AbsensiHarian;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AbsensiBulananPreparedStatementSetter implements ItemPreparedStatementSetter<AbsensiBulanan> {

    @Override
    public void setValues(AbsensiBulanan item, PreparedStatement ps) throws SQLException {
        ps.setLong(1,item.getKaryawanId());
        ps.setDate(2, (Date) item.getBulanDanTahunAbsensi());
        if (item.getTotalDurasiLembur() != null) {
            long seconds = item.getTotalDurasiLembur().getSeconds();
            long absSeconds = Math.abs(seconds); //absolut, alias menghilangkan nilai minus jika ada

            long days = absSeconds / 86400;
            long hours = (absSeconds % 86400) / 3600;
            long minutes = (absSeconds % 3600) / 60;
            long secs = absSeconds % 60;

            String interval = String.format("P%dDT%dH%dM%dS", days, hours, minutes, secs);
            ps.setObject(3, interval, java.sql.Types.OTHER); // Use java.sql.Types.OTHER for INTERVAL type
        } else {
            ps.setObject(3, null);
        }
        ps.setInt(4, item.getTotalKehadiran());
    }
}
