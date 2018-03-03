package net.tmarton.scrapper.assignment.api.scrapper;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.jsoup.Jsoup;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by tmarton on 03/03/2018.
 */
@Service
public class ScrapperServiceImpl implements ScrapperService {

    RestTemplate restTemplate = new RestTemplate();

    public ScrapperResults scrape(String url) {

        String html = fetchData(url);

        String htmlText = Jsoup.parse(html).body().text();
        String[] words = htmlText.split("\\s");

        Map<String, Long> wordFrequencies = Stream.of(words)
                .collect(Collectors.groupingBy(k -> k, HashMap::new, Collectors.counting()));

        String longestWord = wordFrequencies.keySet().stream().max(Comparator.comparing(String::length)).orElse(null);
        String mostCommonWord = wordFrequencies.entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey();

        return new ScrapperResults(mostCommonWord, longestWord, wordFrequencies);
    }

    private String fetchData(String url) {
        ResponseEntity<String> response = callUrl(url);
        HttpHeaders httpHeaders = response.getHeaders();
        HttpStatus statusCode = response.getStatusCode();
        if (statusCode.equals(HttpStatus.MOVED_PERMANENTLY) || statusCode.equals(HttpStatus.FOUND) || statusCode.equals(HttpStatus.SEE_OTHER)) {
            if (httpHeaders.getLocation() != null) {
                response = callUrl(httpHeaders.getLocation().toString());
            } else {
                throw new IllegalStateException(String.format("Can't fetch data from url {}", url));
            }
        }

        return response.getBody();
    }

    private ResponseEntity<String> callUrl(String url) {
        try{
            return restTemplate.getForEntity(url.trim(), String.class);
        } catch (Exception ex) {
            throw new IllegalStateException(String.format("Can't fetch data from url {}", url));
        }
    }
}
