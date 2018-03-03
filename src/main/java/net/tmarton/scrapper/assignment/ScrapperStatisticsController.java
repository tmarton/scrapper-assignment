package net.tmarton.scrapper.assignment;

import lombok.extern.java.Log;
import net.tmarton.scrapper.assignment.scrapper.ScrapperResults;
import net.tmarton.scrapper.assignment.scrapper.ScrapperService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
/**
 * Created by tmarton on 03/03/2018.
 */
@Log
@RestController
@RequestMapping("/api")
public class ScrapperStatisticsController {

    private ScrapperService scrapperService;

    public ScrapperStatisticsController(final ScrapperService scrapperService) {
        this.scrapperService = scrapperService;
    }

    @RequestMapping(value = "/scrape", produces = APPLICATION_JSON_UTF8_VALUE)
    public ScrapperResults scrapeUrl(@RequestParam("url") String url) {
        return scrapperService.scrape(url);
    }
}
