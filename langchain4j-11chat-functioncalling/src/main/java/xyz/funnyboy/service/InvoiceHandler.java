package xyz.funnyboy.service;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class InvoiceHandler
{

    @Resource
    private WeatherService weatherService;

    /**
     * 处理
     *
     * @param companyName
     *            公司名称
     * @param dutyNumber
     *            值班编号
     * @param amount
     *            量
     * @return {@link String }
     * @throws Exception
     *             例外
     * @see {@linktourl <a href=
     *      "https://docs.langchain4j.dev/tutorials/tools/#tool">https://docs.langchain4j.dev/tutorials/tools/#tool</a>}
     */
    // @Tool(name = "开具发票助手", value = "根据用户提交的开票信息进行开票")
    @Tool("根据用户提交的开票信息进行开票")
    public String handle(@P("公司名称")
    String companyName, @P("税号")
    String dutyNumber, @P("金额保留两位有效数字")
    String amount) throws Exception {
        log.info("companyName =>>>> {} dutyNumber =>>>> {} amount =>>>> {}", companyName, dutyNumber, amount);

        // ----------------------------------
        // 这块写自己的业务逻辑，调用redis/rabbitmq/kafka/mybatis/顺丰单据/医疗化验报告/支付接口等第3方
        // ----------------------------------
        final JsonNode weatherV2 = weatherService.getWeatherV2("101010100");
        log.info("weatherV2 =>>>> {}", weatherV2);

        return "开票成功";
    }
}
