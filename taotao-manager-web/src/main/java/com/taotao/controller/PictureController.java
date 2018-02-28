package com.taotao.controller;

import com.taotao.common.utils.JsonUtils;
import com.taotao.utils.FastDFSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 31364 on 2018/2/28.
 * description: 图片上传的controller
 */
@Controller
@RequestMapping(value = "/pic")
public class PictureController {

    @Value(value = "${IMAGE_SERVER_URL}")
    private String IMAGE_SERVER_URL;

    /**
     * 图片上传的方法
     *
     * @param uploadFile 上传的图片
     * @return 上传的结果，是由map对象转的json字符串
     */
    @RequestMapping(value = "/upload", produces = MediaType.TEXT_PLAIN_VALUE + ";charset=utf-8")
    @ResponseBody
    public String fileUpload(MultipartFile uploadFile) {
        try {
//            获取文件的扩展名
            String originalFilename = uploadFile.getOriginalFilename();
            String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
//            创建一个FastDFS的客户端
            FastDFSClient fastDFSClient = new FastDFSClient("classpath:fast_dfs.properties");
//            执行上传处理
            String path = fastDFSClient.uploadFile(uploadFile.getBytes(), extName);
//            拼接返回的url和ip地址，拼装成完整的url
            String url = IMAGE_SERVER_URL + path;
//            返回map
            Map<String, Object> map = new HashMap<>();
            map.put("error", 0);
            map.put("url", url);
            String json = JsonUtils.objectToJson(map);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> map = new HashMap<>();
            map.put("error", 1);
            map.put("message", "图片上传失败");
            String json = JsonUtils.objectToJson(map);
            return json;
        }
    }
}
