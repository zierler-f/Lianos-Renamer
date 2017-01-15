package at.zierler.privat.lianosrenamer.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Episode {

    private Show show;
    private Integer seasonNumber;
    private Integer episodeNumber;
    private String episodeTitle;

}
