package xyz.funnyboy.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dashscope.aigc.imagesynthesis.ImageSynthesis;
import com.alibaba.dashscope.aigc.imagesynthesis.ImageSynthesisParam;
import com.alibaba.dashscope.aigc.imagesynthesis.ImageSynthesisResult;
import com.alibaba.dashscope.exception.NoApiKeyException;

import cn.hutool.json.JSONUtil;
import dev.langchain4j.community.model.dashscope.WanxImageModel;
import dev.langchain4j.data.image.Image;
import dev.langchain4j.model.output.Response;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class WanxImageModelController
{
    @Autowired
    private WanxImageModel wanxImageModel;

    /**
     * 生图
     *
     * @return {@link String }
     * @throws IOException
     *             ioexception
     * @see {@linktourl <a href=
     *      "http://localhost:9006/image/create2">http://localhost:9006/image/create2</a>}
     */
    @GetMapping(value = "/image/create2")
    public String createImageContent2() throws IOException {
        final Response<Image> response = wanxImageModel.generate("美女");
        final String url = response.content().url().toString();
        log.info("图片地址: {}", url);
        return url;
    }

    /**
     * 生图
     *
     * @return {@link String }
     * @throws IOException
     *             ioexception
     * @see {@linktourl <a href=
     *      "http://localhost:9006/image/create3">http://localhost:9006/image/create3</a>}
     */
    @GetMapping(value = "/image/create3")
    public String createImageContent3() throws IOException {
        String prompt = "近景镜头，18岁的中国女孩，古代服饰，圆脸，正面看着镜头，" + "民族优雅的服装，商业摄影，室外，电影级光照，半身特写，精致的淡妆，锐利的边缘。";
        final ImageSynthesisParam param = ImageSynthesisParam.builder().apiKey(System.getenv("Alibaba-APIKey"))
                .model(ImageSynthesis.Models.WANX_V1).prompt(prompt).style("<watercolor>").n(1).size("1024*1024")
                .build();

        final ImageSynthesis imageSynthesis = new ImageSynthesis();
        ImageSynthesisResult result;
        try {
            result = imageSynthesis.call(param);
        }
        catch (NoApiKeyException e) {
            throw new RuntimeException(e);
        }

        final String jsonStr = JSONUtil.toJsonStr(result);
        log.info("图片地址: {}", jsonStr);
        return jsonStr;
    }
}
