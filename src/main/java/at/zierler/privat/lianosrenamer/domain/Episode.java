package at.zierler.privat.lianosrenamer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Episode {

    private String name;
    private Integer season;
    private Integer number;

}
