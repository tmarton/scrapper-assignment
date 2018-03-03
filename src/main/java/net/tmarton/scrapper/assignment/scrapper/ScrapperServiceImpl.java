package net.tmarton.scrapper.assignment.scrapper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.tmarton.scrapper.assignment.jsoup.TextNodeTraversor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

/**
 * Created by tmarton on 03/03/2018.
 */
@Service
public class ScrapperServiceImpl implements ScrapperService {

    private RestTemplate restTemplate;

    public ScrapperServiceImpl(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ScrapperResults scrape(String url) {
        String html = fetchData(url);

        Element body = Jsoup.parse(html).body();
        List<String> words = tokenizeData(body);

        Map<String, Long> wordFrequencies = words.stream()
                .collect(Collectors.groupingBy(k -> k, HashMap::new, Collectors.counting()));
        List<WordFrequency> sortedFrequencies = wordFrequencies.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(e -> new WordFrequency(e.getKey(), e.getValue()))
                .collect(Collectors.toList());

        String longestWord = wordFrequencies.keySet().stream().max(Comparator.comparing(String::length)).orElse("");
        String mostCommonWord = !sortedFrequencies.isEmpty() ? sortedFrequencies.get(0).getKey() : "";

        return new ScrapperResults(mostCommonWord, longestWord, sortedFrequencies);
    }

    private List<String> tokenizeData(Element elem) {
        List<String> wordChunks = traverse(elem);
        return wordChunks.stream()
                .parallel()
                .flatMap(w -> tokenizeElemText(w))
                .filter(s -> StringUtils.hasText(s))
                .collect(Collectors.toList());
    }

    private Stream<String> tokenizeElemText(String elemText) {
        ArrayList<String> partial = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(elemText, " ");
        while (tokenizer.hasMoreElements()) {
            partial.add(tokenizer.nextToken().trim());
        }
        return partial.stream();
    }

    private List<String> traverse(Element elem) {
        TextNodeTraversor textNodeTraversor = new TextNodeTraversor();
        return textNodeTraversor.traverse(elem);
    }

    private String fetchData(String url) {
        ResponseEntity<String> response = callUrl(url);
        HttpHeaders httpHeaders = response.getHeaders();
        HttpStatus statusCode = response.getStatusCode();
        if (statusCode.equals(HttpStatus.MOVED_PERMANENTLY) || statusCode.equals(HttpStatus.FOUND) || statusCode.equals(HttpStatus.SEE_OTHER)) {
            if (httpHeaders.getLocation() != null) {
                response = callUrl(httpHeaders.getLocation().toString());
            } else {
                throw new IllegalStateException(String.format("Can't fetch data from url %s", url));
            }
        }

        return response.getBody();
    }

    private ResponseEntity<String> callUrl(String url) {
        try{
            return restTemplate.getForEntity(url.trim(), String.class);
        } catch (Exception ex) {
            throw new IllegalStateException(String.format("Can't fetch data from url %s", url));
        }
    }
}
