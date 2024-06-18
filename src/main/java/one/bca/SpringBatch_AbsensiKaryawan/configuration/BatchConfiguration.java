package one.bca.SpringBatch_AbsensiKaryawan.configuration;

import one.bca.SpringBatch_AbsensiKaryawan.listener.CustomCreateOutputCSVSkipListener;
import one.bca.SpringBatch_AbsensiKaryawan.mapper.AbsensiHarianMapper;
import one.bca.SpringBatch_AbsensiKaryawan.mapper.ReaderHasilAbsensiInputRowMapper;
import one.bca.SpringBatch_AbsensiKaryawan.mapper.ReaderBahanAbsensiBulananRowMapper;
import one.bca.SpringBatch_AbsensiKaryawan.model.*;
import one.bca.SpringBatch_AbsensiKaryawan.partitionoer.PartitionerAbsensiOutput;
import one.bca.SpringBatch_AbsensiKaryawan.preparedstatement.AbsensiBulananPreparedStatementSetter;
import one.bca.SpringBatch_AbsensiKaryawan.preparedstatement.AbsensiHarianPreparedStatementSetter;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.partition.PartitionHandler;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.core.partition.support.TaskExecutorPartitionHandler;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.StepContext;
import org.springframework.batch.core.scope.context.StepSynchronizationManager;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.*;
import org.springframework.batch.item.database.*;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.PostgresPagingQueryProvider;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import java.sql.Date;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Configuration
public class BatchConfiguration {
    //variable step 1
    public static String[] tokens = new String[] {"absen_id", "karyawan_id", "tanggal_absen", "waktu_clockin", "waktu_clockout", "durasi_lembur"};
    public static String INSERT_ABSENSI_HARIAN_SQL = "INSERT INTO "
            + "ABSENSI_HARIAN(absen_id, karyawan_id, tanggal_absen, waktu_clockin, waktu_clockout, durasi_lembur) "
            + "VALUES(?,?,?,?,?,?)";

    //variable step 2
    public static String INSERT_ABSENSI_BULANAN_SQL = "INSERT INTO "
            + "ABSENSI_BULANAN(karyawan_id, bulan_dan_tahun_absensi, total_durasi_lembur, total_kehadiran) "
            + "VALUES(?,?,?,?)";

    //variable step 3
    public static String[] namesofExtractorAbsensiOutputCSV = new String[] { "karyawanId", "namaDepan", "namaBelakang", "jumlahCutiTersisa",
            "jumlahCutiTelahDiambil", "totalDurasiLembur", "totalKehadiran" };


    //main variable
    private final DataSourceTransactionManager transactionManager;
    private final JobRepository jobRepository;
    String lastMonthFirstDayString;
    Date lastMonthFirstDayDate;

    public BatchConfiguration(DataSourceTransactionManager transactionManager, JobRepository jobRepository) {
        this.transactionManager = transactionManager;
        this.jobRepository = jobRepository;

        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Subtract one month from the current date and set the day to the first day of the month
        LocalDate lastMonthFirstDay = currentDate.minusMonths(1).withDayOfMonth(1);

        // Format the last month's first day as a string in the format "YYYY-MM-DD"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        lastMonthFirstDayString = lastMonthFirstDay.format(formatter);

            // Convert LocalDate to java.sql.Date
        lastMonthFirstDayDate = Date.valueOf(lastMonthFirstDay);

        // Output the string representation of the last month's first day
        System.out.println("Last month's first day          : " + lastMonthFirstDayString);
        System.out.println("Last month's first date         : " + lastMonthFirstDayDate);

        //dummy date ke 2024-06-01
        lastMonthFirstDayString = "2024-06-01";

            // Parse the string to LocalDate
        LocalDate dummyDate = LocalDate.parse(lastMonthFirstDayString, DateTimeFormatter.ISO_DATE);

            // Convert LocalDate to java.sql.Date
        lastMonthFirstDayDate = Date.valueOf(dummyDate);

        System.out.println("Last month's first day  [dummy] : " + lastMonthFirstDayString);
        System.out.println("Last month's first date [dummy] : " + lastMonthFirstDayDate);
    }

    //uncomment @Bean jika mau test. jika mau hit pakai rest api, comment @Bean agar tidak jalan otomatis
    @Bean
    public Job jobStart() throws Exception {
        return new JobBuilder("jobPengolahanAbsensiKaryawan", jobRepository)
                .start(stepReadInputAbsensiCSVToDbAbsensiHarian())
                .next(stepAbsensiHarianToBulanan())
                .next(masterCreateOutputCSVAbsensi())
                .build();
    }

    //step 1 start here to read CSV and write to db absensi harian
    public Step stepReadInputAbsensiCSVToDbAbsensiHarian() throws Exception {
        return new StepBuilder("stepReadInputAbsensiCSVToDbAbsensiHarian", jobRepository)
                .<AbsensiHarian, AbsensiHarian>chunk(3, transactionManager)
                .reader(itemReader())
                .writer(itemWriter())
                .build();
    }

    @Bean
    public ItemReader<AbsensiHarian> itemReader() {
        FlatFileItemReader<AbsensiHarian> itemReader = new FlatFileItemReader<>();
        itemReader.setLinesToSkip(1);
        itemReader.setResource(new FileSystemResource("data/absensi.csv"));

        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames(tokens);

        DefaultLineMapper<AbsensiHarian> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(new AbsensiHarianMapper());

        itemReader.setLineMapper(lineMapper);
        itemReader.open(new ExecutionContext());
        return itemReader;
    }

    @Bean
    public ItemWriter<AbsensiHarian> itemWriter() {
        return new JdbcBatchItemWriterBuilder<AbsensiHarian>()
                .dataSource(Objects.requireNonNull(transactionManager.getDataSource()))
                .sql(INSERT_ABSENSI_HARIAN_SQL)
                .itemPreparedStatementSetter(new AbsensiHarianPreparedStatementSetter())
                .build();
    }

    //step 1 ended here
    //step 2 start here to read table absensi_harian and write to table absensi_bulanan
    public Step stepAbsensiHarianToBulanan() throws Exception {
        return new StepBuilder("stepAbsensiHarianToBulanan", jobRepository)
                .<AbsensiBulanan, AbsensiBulanan>chunk(7, transactionManager)
                .reader(readBahanAbsensiBulanan())
                .processor(itemProcessorAbsensiBulanan())
                .writer(itemWriterAbsensiBulanan())
                .build();
    }

    @Bean
    public JdbcPagingItemReader<AbsensiBulanan> readBahanAbsensiBulanan() throws Exception {
        return new JdbcPagingItemReaderBuilder<AbsensiBulanan>()
                .dataSource(Objects.requireNonNull(transactionManager.getDataSource()))
                .queryProvider(queryProviderOfReadBahanAbsensiHarianDanBulanan())
                .rowMapper(new ReaderBahanAbsensiBulananRowMapper())
                .pageSize(2) // Set page size as needed
                .name("pagingItemReader")
                .saveState(false)
                .build();
    }

    public PagingQueryProvider queryProviderOfReadBahanAbsensiHarianDanBulanan() throws Exception {
//        System.out.println("queryProviderOfReadBahanAbsensiHarianDanBulanan started");
        PostgresPagingQueryProvider factory = new PostgresPagingQueryProvider();

        factory.setSelectClause("karyawan_id, COUNT(1) AS total_kehadiran, SUM(EXTRACT(EPOCH FROM durasi_lembur)) AS total_durasi_lembur ");
        factory.setFromClause("FROM absensi_harian ");
        factory.setWhereClause("WHERE waktu_clockin IS NOT NULL AND waktu_clockout IS NOT NULL ");
        factory.setGroupClause("GROUP BY karyawan_id ");

        Map<String, Order> sortKeys = new HashMap<>();
        sortKeys.put("karyawan_id", Order.ASCENDING);
        factory.setSortKeys(sortKeys);
        factory.init(Objects.requireNonNull(transactionManager.getDataSource()));
//        System.out.println("querry provider ended");
        return factory;
    }

    @Bean
    public ItemProcessor<AbsensiBulanan, AbsensiBulanan> itemProcessorAbsensiBulanan() {
        return new ItemProcessor<AbsensiBulanan, AbsensiBulanan>() {
            @Override
            public AbsensiBulanan process(AbsensiBulanan item) throws Exception {
                item.setBulanDanTahunAbsensi(lastMonthFirstDayDate);
                return item;
            }
        };
    }

    @Bean
    public ItemWriter<AbsensiBulanan> itemWriterAbsensiBulanan() {
        return new JdbcBatchItemWriterBuilder<AbsensiBulanan>()
                .dataSource(Objects.requireNonNull(transactionManager.getDataSource()))
                .sql(INSERT_ABSENSI_BULANAN_SQL)
                .itemPreparedStatementSetter(new AbsensiBulananPreparedStatementSetter())
                .build();
    }

    //step 2 ended here
    //step 3 start here to read table absensi_bulanan and table karyawan and write to Abensi Output CSV
    public Step masterCreateOutputCSVAbsensi() throws Exception {
        return new StepBuilder("masterCreateOutputCSVAbsensi", jobRepository)
                .partitioner("partitionerOutputCSVAbsensi", partitionerAbsensiOutputCSV())
                .partitionHandler(partitionAbsensiOutputCSVHandler())
                .build();
    }

    @Bean
    public Partitioner partitionerAbsensiOutputCSV() {
        return new PartitionerAbsensiOutput();
    }

    @Bean
    public PartitionHandler partitionAbsensiOutputCSVHandler() throws Exception {
        TaskExecutorPartitionHandler handler = new TaskExecutorPartitionHandler();
        handler.setTaskExecutor(new SimpleAsyncTaskExecutor());
        handler.setStep(stepCreateOutputCSVAbsensi());
        handler.setGridSize(3); //number of partition. max value 26 karena alfabet dari 'A' to 'Z' hanya ada 26
        return handler;
    }

    public Step stepCreateOutputCSVAbsensi() throws Exception {
        return new StepBuilder("stepCreateOutputCSVAbsensi", jobRepository)
                .<HasilAbsensiInput, HasilAbsensiOutput>chunk(2, transactionManager)
                .reader(readBahanAbsensiOutputCSV())
                .processor(new ItemProcessor<HasilAbsensiInput, HasilAbsensiOutput>() {
                    @Override
                    public HasilAbsensiOutput process(HasilAbsensiInput hasilAbsensiInput) throws Exception {
                        HasilAbsensiOutput hasilAbsensiOutput = new HasilAbsensiOutput();

                        hasilAbsensiOutput.setKaryawanId(hasilAbsensiInput.getKaryawanId());
                        hasilAbsensiOutput.setNamaDepan(hasilAbsensiInput.getNamaDepan());
                        hasilAbsensiOutput.setNamaBelakang(hasilAbsensiInput.getNamaBelakang());
                        hasilAbsensiOutput.setJumlahCutiTersisa(hasilAbsensiInput.getJumlahCutiTersisa());
                        hasilAbsensiOutput.setJumlahCutiTelahDiambil(hasilAbsensiInput.getJumlahCutiTelahDiambil());
                        hasilAbsensiOutput.setTotalKehadiran(hasilAbsensiInput.getTotalKehadiran());

                        // Mengubah nilai Duration ke String
                        String totalDurasiLemburString = hasilAbsensiInput.getTotalDurasiLembur();

                        // Mengonversi String total_durasi_lembur ke Duration total_durasi_lembur
                        if (totalDurasiLemburString != null) {
                            // Parsing interval ke dalam format yang sesuai
                            String[] parts = totalDurasiLemburString.split(" ");
                            long days = 0;
                            long hours = 0;
                            long minutes = 0;
                            long seconds = 0;
                            for (int i = 0; i < parts.length; i++) {
                                if (parts[i].contains("day")) {
                                    days = Long.parseLong(parts[i - 1]);
                                } else if (parts[i].contains(":")) {
                                    String[] timeParts = parts[i].split(":");
                                    hours = Long.parseLong(timeParts[0]);
                                    minutes = Long.parseLong(timeParts[1]);
                                    seconds = Long.parseLong(timeParts[2]);
                                }
                            }
                            // Menghitung total durasi dalam detik
                            long totalSeconds = (days * 24 * 3600) + (hours * 3600) + (minutes * 60) + seconds;
                            // Membuat objek Duration
                            Duration totalDurasiLembur = Duration.ofSeconds(totalSeconds);
                            hasilAbsensiOutput.setTotalDurasiLembur(totalDurasiLembur);
                        } else {
                            hasilAbsensiOutput.setTotalDurasiLembur(Duration.ZERO);
                        }

                        if (hasilAbsensiInput.getKaryawanId().equals(4L) ||
                                hasilAbsensiInput.getKaryawanId().equals(1L) ||
                                hasilAbsensiInput.getKaryawanId().equals(6L) ||
                                hasilAbsensiInput.getKaryawanId().equals(8L)) {
                            try {
                                throw new Exception("Pengkondisian Error di Processor step 3");
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                        return hasilAbsensiOutput;
                    }
                })
                .writer(writeAbsensiOutputCSV(null))
                .faultTolerant()
                .skip(Exception.class)
//                .skipLimit(1) // bikin gagal
                .skipLimit(2)   // tetap sukses karena .skipLimit ini per thread dari partitionernya
                .listener(new CustomCreateOutputCSVSkipListener())
                .build();
    }


    @Bean
    @StepScope
    public JdbcPagingItemReader<HasilAbsensiInput> readBahanAbsensiOutputCSV() throws Exception {
        return new JdbcPagingItemReaderBuilder<HasilAbsensiInput>()
                .dataSource(Objects.requireNonNull(transactionManager.getDataSource()))
                .queryProvider(queryProviderOfReadBahanAbsensiOutputCSV())
                .rowMapper(new ReaderHasilAbsensiInputRowMapper())
                .pageSize(2) // Set page size as needed
                .name("pagingItemReader")
                .build();
    }

    public PagingQueryProvider queryProviderOfReadBahanAbsensiOutputCSV() throws Exception {
//        System.out.println("querry provider started by-" + Thread.currentThread().getName());
        StepContext stepContext = StepSynchronizationManager.getContext();
        ExecutionContext executionContext = stepContext.getStepExecution().getExecutionContext();
        String fromChar = executionContext.getString("fromChar");
        String toChar = executionContext.getString("toChar");
//        System.out.println("fromChar = " + fromChar + " | toChar = " + toChar);

        PostgresPagingQueryProvider factory = new PostgresPagingQueryProvider();

        // SELECT k.karyawan_id AS "k.karyawan_id" -> di set alias sebagai "k.karyawan_id" karena karyawan_id dimiliki dua table.
        // jika hanya SELECT karyawan_id, Postgresql tidak bisa tahu jika hanya find dari karyawan_id saja karena ambiguous
        // jika hanya SELECT k.karyawan_id, ResultSet tidak bisa sort dari k.karywan_id karena tahu resultnya "karyawan_id"
        // maka dari itu di set alias ke "k.karyawan_id" agar tetap cocok dengan sintaks sql, dan ResultSet tahu kolomnya

        factory.setSelectClause("SELECT k.karyawan_id AS \"k.karyawan_id\", k.nama_depan, k.nama_belakang, k.jumlah_cuti_tersisa, k.jumlah_cuti_telah_diambil, ab.total_durasi_lembur, ab.total_kehadiran");
        factory.setFromClause("FROM karyawan k, absensi_bulanan ab");
        factory.setWhereClause("WHERE k.karyawan_id = ab.karyawan_id " +
                "AND ab.bulan_dan_tahun_absensi = '" + lastMonthFirstDayString + "' " +
                "AND (SUBSTRING(k.nama_depan, 1, 1) BETWEEN '"+ fromChar + "' AND '" + toChar + "') ");
        Map<String, Order> sortKeys = new HashMap<>();
        sortKeys.put("k.karyawan_id", Order.ASCENDING);
        factory.setSortKeys(sortKeys);
        factory.init(Objects.requireNonNull(transactionManager.getDataSource()));
//        System.out.println("querry provider ended");
        return factory;
    }

    @Bean
    @StepScope
    public ItemWriter<HasilAbsensiOutput> writeAbsensiOutputCSV(@Value("#{stepExecutionContext['fileName']}") String fileName){

//        System.out.println("ItemWriter started");

        FlatFileItemWriter<HasilAbsensiOutput> itemWriter = new FlatFileItemWriter<>();
        itemWriter.setResource(new FileSystemResource(fileName));

        DelimitedLineAggregator<HasilAbsensiOutput> aggregator = new DelimitedLineAggregator<HasilAbsensiOutput>();
        aggregator.setDelimiter(",");

        BeanWrapperFieldExtractor<HasilAbsensiOutput> fieldExtractor = new BeanWrapperFieldExtractor<HasilAbsensiOutput>();
        fieldExtractor.setNames(namesofExtractorAbsensiOutputCSV);

        aggregator.setFieldExtractor(fieldExtractor);
        itemWriter.setLineAggregator(aggregator);

        itemWriter.setHeaderCallback(writer -> {
                writer.write("karyawan_id, nama_depan, nama_belakang, jumlah_cuti_tersisa, " +
                        "jumlah_cuti_telah_diambil, total_durasi_lembur, total_kehadiran");
        });
        itemWriter.open(new ExecutionContext());
        return itemWriter;
    }
    //step 3 ended here
}
