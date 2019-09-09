package pl.sda.eventorganizer;

import io.codearte.jfairy.Fairy;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import pl.sda.eventorganizer.service.EventService;
import java.time.*;
import java.time.format.DateTimeFormatter;


@Component
public class FalseData implements ApplicationRunner {

    private EventService eventService;

    public FalseData(EventService eventService) {

        this.eventService = eventService;
    }

    public long getRandomDateLong(){
        LocalDateTime begin = LocalDateTime.now().plusDays(2L);
        LocalDateTime end = begin.plusMonths(12L);
        ZonedDateTime zdt = begin.atZone(ZoneId.systemDefault());
        ZonedDateTime zdt2 = end.atZone(ZoneId.systemDefault());
        long beginTime = zdt.toInstant().toEpochMilli();
        long endTime = zdt2.toInstant().toEpochMilli();
        long diff = endTime - beginTime + 1;
        return beginTime + (long) (Math.random() * diff);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        LocalDateTime randomTime;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        Fairy fairy = Fairy.create();
        for (int i = 0; i < 10; i++){
            randomTime = Instant.ofEpochMilli(getRandomDateLong()).atZone(ZoneId.systemDefault()).toLocalDateTime();
           eventService.createNewEvent(fairy.textProducer().latinWord(i+1), fairy.textProducer().paragraph(i*10), randomTime, randomTime.plusHours(2L));

        }
    }
}
