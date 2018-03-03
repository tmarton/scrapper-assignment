package net.tmarton.scrapper.assignment.scrapper;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by tmarton on 03/03/2018.
 */
@Data
@AllArgsConstructor
public class ScrapperResults {

    String mostCommonWord;

    String longestWord;

    List<WordFrequency> wordFrequencies;
}
