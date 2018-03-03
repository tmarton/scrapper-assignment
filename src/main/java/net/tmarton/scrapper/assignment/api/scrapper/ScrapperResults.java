package net.tmarton.scrapper.assignment.api.scrapper;

import java.util.HashMap;
import java.util.Map;
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

    Map<String, Long> wordFrequencies = new HashMap<>();
}
