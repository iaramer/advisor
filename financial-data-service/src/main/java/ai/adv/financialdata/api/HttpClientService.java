package ai.adv.financialdata.api;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class HttpClientService {

  public String get(String url) {
    String result = "";
    try {
      HttpGet httpGet = new HttpGet(url);
      try (CloseableHttpResponse response = HttpClients.createDefault().execute(httpGet)) {
        HttpEntity entity = response.getEntity();
        InputStream inputStream = entity.getContent();
        result = IOUtils.toString(inputStream, StandardCharsets.UTF_8);

        EntityUtils.consume(entity);
      }
    } catch (Exception e) {
      log.error(e.getMessage());
    }
    return result;
  }

  public String post(String url) {
    String result = "";
    try {
      HttpPost httpPost = new HttpPost(url);

      List<NameValuePair> nvps = new ArrayList<>();
      nvps.add(new BasicNameValuePair("username", "vip"));
      nvps.add(new BasicNameValuePair("password", "secret"));

      httpPost.setEntity(new UrlEncodedFormEntity(nvps));
      try (CloseableHttpResponse response = HttpClients.createDefault().execute(httpPost)) {
        HttpEntity entity = response.getEntity();
        InputStream inputStream = entity.getContent();
        result = IOUtils.toString(inputStream, StandardCharsets.UTF_8);

        EntityUtils.consume(entity);
      }
    } catch (Exception e) {
      log.error(e.getMessage());
    }
    return result;
  }
}
