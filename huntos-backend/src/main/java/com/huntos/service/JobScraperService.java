package com.huntos.service;

import com.huntos.domain.UserProfile;
import com.huntos.dto.RawJob;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class JobScraperService {

    private final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36";
    private final RestTemplate restTemplate = new RestTemplate();

    public List<RawJob> scrapeLinkedIn(UserProfile profile) {
        List<RawJob> jobs = new ArrayList<>();
        try {
            String keywords = profile.getTargetRole() != null ? URLEncoder.encode(profile.getTargetRole(), StandardCharsets.UTF_8) : "";
            String location = profile.getPreferredLocations() != null ? URLEncoder.encode(profile.getPreferredLocations(), StandardCharsets.UTF_8) : "Worldwide";
            String url = "https://www.linkedin.com/jobs/search/?keywords=" + keywords + "&location=" + location;

            Document doc = Jsoup.connect(url)
                    .userAgent(USER_AGENT)
                    .timeout(5000)
                    .get();

            Elements jobCards = doc.select(".base-card");
            for (Element card : jobCards) {
                RawJob job = new RawJob();
                job.setPlatform("LinkedIn");
                job.setTitle(card.select(".base-search-card__title").text().trim());
                job.setCompany(card.select(".base-search-card__subtitle").text().trim());
                job.setLocation(card.select(".job-search-card__location").text().trim());
                job.setApplyUrl(card.select(".base-card__full-link").attr("href"));
                if (!job.getTitle().isEmpty()) {
                    jobs.add(job);
                }
            }
        } catch (Exception e) {
            System.err.println("Error scraping LinkedIn: " + e.getMessage());
        }
        return jobs;
    }

    public List<RawJob> scrapeNaukri(UserProfile profile) {
        List<RawJob> jobs = new ArrayList<>();
        try {
            String keywords = profile.getTargetRole() != null ? profile.getTargetRole().replace(" ", "-").toLowerCase() : "jobs";
            String url = "https://www.naukri.com/" + keywords + "-jobs";

            Document doc = Jsoup.connect(url)
                    .userAgent(USER_AGENT)
                    .timeout(5000)
                    .get();

            Elements jobCards = doc.select(".jobTuple");
            for (Element card : jobCards) {
                RawJob job = new RawJob();
                job.setPlatform("Naukri");
                job.setTitle(card.select(".title").text().trim());
                job.setCompany(card.select(".companyInfo .subTitle").text().trim());
                job.setLocation(card.select(".location").text().trim());
                job.setSalary(card.select(".salary").text().trim());
                job.setApplyUrl(card.select(".title").attr("href"));
                if (!job.getTitle().isEmpty()) {
                    jobs.add(job);
                }
            }
        } catch (Exception e) {
            System.err.println("Error scraping Naukri: " + e.getMessage());
        }
        return jobs;
    }

    public List<RawJob> searchViaSerpApi(UserProfile profile) {
        List<RawJob> jobs = new ArrayList<>();
        try {
            String target = profile.getTargetRole() != null ? profile.getTargetRole() : "jobs";
            String loc = profile.getPreferredLocations() != null ? profile.getPreferredLocations() : "";
            String query = URLEncoder.encode(target + " " + loc, StandardCharsets.UTF_8);
            String apiKey = "YOUR_SERPAPI_KEY"; // Replace with actual API key
            String url = "https://serpapi.com/search.json?engine=google_jobs&q=" + query + "&hl=en&api_key=" + apiKey;

            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            if (response != null && response.containsKey("jobs_results")) {
                List<Map<String, Object>> jobsResults = (List<Map<String, Object>>) response.get("jobs_results");
                for (Map<String, Object> jobResult : jobsResults) {
                    RawJob job = new RawJob();
                    job.setPlatform("Google Jobs");
                    job.setTitle((String) jobResult.get("title"));
                    job.setCompany((String) jobResult.get("company_name"));
                    job.setLocation((String) jobResult.get("location"));
                    job.setDescription((String) jobResult.get("description"));
                    
                    if (jobResult.containsKey("share_link")) {
                        job.setApplyUrl((String) jobResult.get("share_link"));
                    }
                    jobs.add(job);
                }
            }
        } catch (Exception e) {
            System.err.println("Error calling SerpApi: " + e.getMessage());
        }
        return jobs;
    }
}
