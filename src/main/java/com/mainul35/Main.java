package com.mainul35;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        try {

            HttpGet request = new HttpGet("http://www.cochranelibrary.com/home/topic-and-review-group-list.html?page=topic");

            // add request headers
            request.addHeader("authority", "www.cochranelibrary.com");
            request.addHeader("method", "GET");
            request.addHeader("path", "/en/cdsr/reviews/topics?p_p_id=scolaristopics_WAR_scolaristopics&p_p_lifecycle=2&p_p_state=normal&p_p_mode=view&p_p_resource_id=topics-list&p_p_cacheability=cacheLevelPage");
            request.addHeader("scheme", "https");
            request.addHeader("accept", "application/json, text/javascript, */*; q=0.01");
            request.addHeader("accept-encoding", "gzip, deflate, utf-8");
            request.addHeader("accept-language", "en-US,en;q=0.9,ak-GH;q=0.8,ak;q=0.7,bn-BD;q=0.6,bn;q=0.5,agq-CM;q=0.4,agq;q=0.3,ar-XB;q=0.2,ar;q=0.1,bm-ML;q=0.1,bm;q=0.1");
            request.addHeader("cookie", "__cfduid=ddefdc8727c106b02a827e7418c09e5811607410477; JSESSIONID=cspbwgreclprt160x1~A76E936E121B39FC32FD29A114B6C71C; GUEST_LANGUAGE_ID=en_US; COOKIE_SUPPORT=true; AMCVS_1B6E34B85282A0AC0A490D44%40AdobeOrg=1; _ga=GA1.2.646149860.1607410483; _gid=GA1.2.1402442269.1607410483; __utma=160318562.646149860.1607410483.1607410483.1607410483.1; __utmc=160318562; __utmz=160318562.1607410483.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); AMCV_1B6E34B85282A0AC0A490D44%40AdobeOrg=-894706358%7CMCIDTS%7C18605%7CMCMID%7C74892146639034437512620001372660878538%7CMCAAMLH-1608015282%7C3%7CMCAAMB-1608015282%7CRKhpRz8krg2tLO6pguXWp5olkAcUniQYPHaMWWgdJ3xzPWQmdj0y%7CMCOPTOUT-1607417682s%7CNONE%7CMCAID%7CNONE%7CvVersion%7C2.3.0; _sdsat_MCID=74892146639034437512620001372660878538; s_cc=true; SID_REP=aaaX9yy7IOpkJ44BPE-yx; SCOLAUTHSESSIONID=A59699008509887DAB4465A76D8E4021; seen-cookie-message=yes; _gat_gtag_UA_189672_63=1; __utmt_6fc43dded90f3ff84bd3f7e59f616f6e=1; __utmt_9d8d89c5f608a8fcab351a62719dc606=1; __utmb=160318562.6.10.1607410483; LFR_SESSION_STATE_20159=1607411497317; __atuvc=3%7C50; __atuvs=5fcf2336c6845a88002; SCOL_SESSION_TIMEOUT=1740 Tue, 08 Dec 2020 07:40:37 GMT; s_sq=wileyclibhighwire%3D%2526c.%2526a.%2526activitymap.%2526page%253DAdvanced%252520Search%252520%25257C%252520Cochrane%252520Library%2526link%253DRun%252520search%2526region%253DadvancedSearchForm%2526pageIDType%253D1%2526.activitymap%2526.a%2526.c%2526pid%253DAdvanced%252520Search%252520%25257C%252520Cochrane%252520Library%2526pidt%253D1%2526oid%253D%252520Run%252520search%2526oidt%253D3%2526ot%253DSUBMIT");
            request.addHeader("dnt", "1");
            request.addHeader("referer", "https://www.cochranelibrary.com/cdsr/reviews/topics");
            request.addHeader("sec-fetch-dest", "empty");
            request.addHeader("sec-fetch-mode", "cors");
            request.addHeader("sec-fetch-site", "same-origin");
            request.addHeader("user-agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.66 Safari/537.36");
            request.addHeader("x-requested-with", "XMLHttpRequest");
//            request.addHeader(HttpHeaders.USER_AGENT, "Googlebot");

            CloseableHttpResponse response = null;
            response = httpClient.execute(request);
            synchronized(response) {
                response.wait(7000);
            }
            try {

                // Get HttpResponse Status
                System.out.println(response.getProtocolVersion());              // HTTP/1.1
                System.out.println(response.getStatusLine().getStatusCode());   // 200
                System.out.println(response.getStatusLine().getReasonPhrase()); // OK
                System.out.println(response.getStatusLine().toString());        // HTTP/1.1 200 OK

                HttpEntity entity = response.getEntity();
                StringBuilder stringBuilder = new StringBuilder("");
                if (entity != null) {
                    BufferedReader rd = new BufferedReader
                            (new InputStreamReader(
                                    response.getEntity().getContent()));

                    String line = "";
                    while ((line = rd.readLine()) != null) {
                        stringBuilder.append(line+"\n");
                    }
                    System.out.println(stringBuilder.toString());
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                response.close();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            httpClient.close();
        }

    }
}
