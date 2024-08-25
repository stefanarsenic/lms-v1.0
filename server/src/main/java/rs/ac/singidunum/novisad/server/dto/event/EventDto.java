package rs.ac.singidunum.novisad.server.dto.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventDto implements Serializable {

    private String freq;
    private Integer interval;
    private List<String> byweekday;
    private LocalDateTime dtstart;
    private LocalDate until;
    private Integer duration;
}
