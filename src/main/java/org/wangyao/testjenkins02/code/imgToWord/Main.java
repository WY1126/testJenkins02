package org.wangyao.testjenkins02.code.imgToWord;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RestController
@RequestMapping("aa")
public class Main {
    public static void main(String[] args) {
        HttpServletResponse response = new MockHttpServletResponse();

        List<String> images = new ArrayList<>();
        images.add("https://images.unsplash.com/photo-1775169726139-82f78b87b8a4?ixlib=rb-4.1.0&q=85&fm=jpg&crop=entropy&cs=srgb&dl=vadim-berg-vagP_ymUXUc-unsplash.jpg");
        images.add("https://images.unsplash.com/photo-1773847400278-3b409bd5aa76?ixlib=rb-4.1.0&q=85&fm=jpg&crop=entropy&cs=srgb&dl=clay-banks-lNai4YWMFMc-unsplash.jpg");
        images.add("https://images.unsplash.com/photo-1773085267084-4b09ba258636?ixlib=rb-4.1.0&q=85&fm=jpg&crop=entropy&cs=srgb&dl=matthew-jackson-aQVfIiVS6lg-unsplash.jpg");

//        downloadWordWithPictures(images, response);
    }

    @PostMapping("bb")
    public void download(@RequestBody List<String> imgUrls, HttpServletResponse response) throws IOException {
        downloadWordWithPictures(imgUrls, response);
    }

    public void downloadWordWithPictures(List<String> imgUrls, HttpServletResponse response) {
        // 4 组装图片数据
        List<Map<String, Object>> pictureDataList = new ArrayList<>();
        try {
            for (String filePath : imgUrls) {
                try {

                    URL url = new URL(filePath);

                    try (InputStream is = url.openStream()) {

                        // 创建图片对象
                        PictureRenderData picture = Pictures.ofStream(is, PictureType.JPEG).create();
//                        PictureRenderData picture = new PictureRenderData()

                        Map<String, Object> map = new HashMap<>();

//                        map.put("deviceName", "deviceName "+ filePath);

                        map.put("picture", picture);
                        pictureDataList.add(map);

                    }

                } catch (Exception e) {
                    log.error("图片读取失败: {}", filePath);
                }
            }



            // 5 Word数据
            Map<String, Object> data = new HashMap<>();

//            data.put("workOrderNo", "工单编号");

            data.put("pictures", pictureDataList);

            // 6 读取模板
            InputStream templateStream = this.getClass().getResourceAsStream("/templates/不停电作业现场勘查单A.docx");

            XWPFTemplate template = XWPFTemplate.compile(templateStream).render(data);
            // 7 输出下载
            response.reset();

            response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");

            String fileName = URLEncoder.encode("不停电作业现场勘查单"+ ".docx", "UTF-8");

            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

            OutputStream os = response.getOutputStream();

            template.write(os);

            os.flush();
            os.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private List<List<Map<String, Object>>> splitList(List<Map<String, Object>> list, int pageSize) {
        List<List<Map<String, Object>>> result = new ArrayList<>();
        for (int i = 0; i < list.size(); i += pageSize) {
            result.add(list.subList(i, Math.min(i + pageSize, list.size())));
        }
        return result;
    }
}
