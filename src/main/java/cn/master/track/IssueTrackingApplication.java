package cn.master.track;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author jingll
 */
@SpringBootApplication
@MapperScan("cn.master.track.mapper")
@EnableScheduling
public class IssueTrackingApplication {

    public static void main(String[] args) {
        SpringApplication.run(IssueTrackingApplication.class, args);
    }

}
