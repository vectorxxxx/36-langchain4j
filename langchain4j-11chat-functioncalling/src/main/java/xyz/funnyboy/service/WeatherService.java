package xyz.funnyboy.service;

import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * 气象服务
 *
 * @author uxiah
 * @date 2025/07/29
 */
@Slf4j
@Service
public class WeatherService
{
    // 和风天气开发服务

    // 替换成你自己的和风天气API密钥
    private static final String API_KEY = System.getenv("weatherAPI");
    // 调用的url地址和指定的城市，本案例以北京为例
    private static final String BASE_URL = "https://devapi.qweather.com/v7/weather/now?location=%s&key=%s";

    /**
     * 获取 Weather v2
     *
     * @param city
     *            城市
     * @return {@link JsonNode }
     * @throws Exception
     *             例外
     * @see {@linktourl <a href=
     *      "https://dev.qweather.com/">https://dev.qweather.com/</a>}
     */
    public JsonNode getWeatherV2(String city) throws Exception {
        // 1 传入调用地址url和apikey
        String url = String.format(BASE_URL, city, API_KEY);

        // 2 使用默认配置创建HttpClient实例
        var httpClient = HttpClients.createDefault();

        // 3 创建请求工厂并将其设置给RestTemplate，开启微服务调用和风天气开发服务
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
        // 4 RestTemplate微服务调用
        String response = null;
        try {
            response = new RestTemplate(factory).getForObject(url, String.class);
        }
        catch (Exception e) {
            log.error("调用和风天气API失败", e);
        }

        // 5 解析JSON响应获得第3方和风天气返回的天气预报信息
        JsonNode jsonNode = new ObjectMapper().readTree(response);

        // 6
        // 想知道具体信息和结果请查看https://dev.qweather.com/docs/api/weather/weather-now/#response
        return jsonNode;
    }
}
