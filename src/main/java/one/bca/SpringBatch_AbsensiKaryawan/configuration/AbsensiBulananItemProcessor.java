package one.bca.SpringBatch_AbsensiKaryawan.configuration;

import one.bca.SpringBatch_AbsensiKaryawan.model.AbsensiBulanan;
import one.bca.SpringBatch_AbsensiKaryawan.model.AbsensiHarian;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

@Component
public class AbsensiBulananItemProcessor implements ItemProcessor<AbsensiHarian, AbsensiBulanan> {

    private Map<Long, AbsensiBulanan> rekapBulanan = new HashMap<>();

    @Override
    public AbsensiBulanan process(AbsensiHarian absensiHarian) throws Exception {
        LocalDate currentDate = absensiHarian.getTanggalAbsen().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate firstDayOfMonth = currentDate.withDayOfMonth(1);

        AbsensiBulanan absensiBulanan = rekapBulanan.getOrDefault(absensiHarian.getKaryawanId(), new AbsensiBulanan());
        absensiBulanan.setKaryawanId(absensiHarian.getKaryawanId());
        absensiBulanan.setBulanDanTahunAbsensi(java.sql.Date.valueOf(firstDayOfMonth));

        Duration totalDurasiLembur = absensiBulanan.getTotalDurasiLembur() == null ? Duration.ZERO : absensiBulanan.getTotalDurasiLembur();
        totalDurasiLembur = totalDurasiLembur.plus(absensiHarian.getDurasiLembur());
        absensiBulanan.setTotalDurasiLembur(totalDurasiLembur);

        int totalKehadiran = absensiBulanan.getTotalKehadiran() + 1;
        absensiBulanan.setTotalKehadiran(totalKehadiran);

        rekapBulanan.put(absensiHarian.getKaryawanId(), absensiBulanan);

        return absensiBulanan;
    }
}
