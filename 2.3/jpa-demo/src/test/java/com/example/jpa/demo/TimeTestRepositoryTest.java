package com.example.jpa.demo;

import com.example.jpa.demo.db.TimeTest;
import com.example.jpa.demo.db.TimeTestRepository;
//import com.mysql.cj.util.TimeUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.Instant;
import java.util.Date;
import java.util.TimeZone;

import static org.apache.commons.lang3.time.DateUtils.MILLIS_PER_SECOND;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TimeTestRepositoryTest {
    @Autowired
    private TimeTestRepository timeTestRepository;

    @Test
    @Rollback(value = false)
    public void testSaveTime() {
//        timeTestRepository.save(TimeTest.builder().dateType(Instant.now()).dateTimeType(Instant.now()).timeStampType(Instant.now()).timeType(Instant.now()).build());
        Iterable<TimeTest> r = timeTestRepository.findAll();
        r.forEach(timeTest -> {
            System.out.println(timeTest);
        });
    }

    public static void main(String[] args) {
//        SimpleDateFormat sdf = TimeUtil.getSimpleDateFormat(null, "''yyyy-MM-dd HH:mm:ss", TimeZone.getTimeZone("Asia/Shanghai"));
//        System.out.println(sdf.format(new Date()));
//        SimpleDateFormat sdf2 = TimeUtil.getSimpleDateFormat(null, "''yyyy-MM-dd HH:mm:ss", TimeZone.getTimeZone("UTC"));
//        System.out.println(sdf2.format(new Date()));

        Instant now = Clock.system(TimeZone.getTimeZone("Asia/Shanghai").toZoneId()).instant();
        System.out.println(now);
        now.atZone(TimeZone.getTimeZone("Asia/Shanghai").toZoneId());
        Timestamp stamp = new Timestamp(now.getEpochSecond() * MILLIS_PER_SECOND);
//        stamp.nanos = instant.getNano();
        System.out.println(stamp);


        Instant now2 = Clock.system(TimeZone.getTimeZone("UTC").toZoneId()).instant();
        System.out.println(now2);
        now2.atZone(TimeZone.getTimeZone("UTC").toZoneId());
        Timestamp stamp2 = new Timestamp(now2.getEpochSecond() * MILLIS_PER_SECOND);
//        stamp.nanos = instant.getNano();
        System.out.println(stamp2);

        System.currentTimeMillis();

    }
}
