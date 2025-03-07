package org.bitznomad.statisticservice.service;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bitznomad.statisticservice.model.Statistic;
import org.bitznomad.statisticservice.repository.StatisticRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class StatisticService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StatisticRepo statisticRepo;
    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(id = "statisticGroup", topics = "statistic")
    public void listen(Statistic statistic) {
        try {
            // Log nhận message
            logger.info("Received: " + statistic);

            statisticRepo.save(statistic);

        } catch (Exception e) {
            // Ghi lại lỗi nhưng không retry
            logger.error("Error processing message", e);

            // Bạn có thể ném lại ngoại lệ hoặc để nó dừng lại tại đây
            // Không retry hoặc không tái thử gửi lại message
            // throw e;  // Nếu bạn muốn ném lại ngoại lệ cho các xử lý khác (ví dụ DLQ)
        }
    }



//    @KafkaListener(id = "dltGroup3", topics = "statistic.DLT")
//    public void dltListen(String in) {
//        logger.info("Received from DLT: ");
//        System.out.println(in);
//    }
}
