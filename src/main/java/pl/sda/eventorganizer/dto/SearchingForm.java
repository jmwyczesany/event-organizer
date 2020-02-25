package pl.sda.eventorganizer.dto;

import lombok.Data;

@Data
public class SearchingForm {

    private String titlePhrase;

    private TimeRange timeRange;

    @Override
    public String toString() {
        return "SearchingForm{" +
                "titlePhrase='" + titlePhrase + '\'' +
                ", timeRange=" + timeRange +
                '}';
    }
}
