package org.wangyao.testjenkins02.code.imgToWord;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.data.PictureType;
import com.deepoove.poi.data.Pictures;
import com.deepoove.poi.data.TextRenderData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RestController
@RequestMapping("aaB")
public class MainB {
    public static void main(String[] args) {
    }

    @PostMapping("bbB")
    public void download(@RequestBody List<String> imgUrls, HttpServletResponse response) throws IOException {
        downloadWordWithPictures(imgUrls, response);
    }

    public void downloadWordWithPictures(List<String> imgUrls, HttpServletResponse response) {
        // 4 组装图片数据
        List<Map<String, Object>> pictureDataList = new ArrayList<>();
        try {
            List<Object> pictureList = new ArrayList<>();

            for (int i = 0; i < imgUrls.size(); i++) {
                String filePath = imgUrls.get(i);
                URL url = new URL(filePath);
                try (InputStream is = url.openStream()) {

                    // 创建图片对象
                    PictureRenderData picture = Pictures.ofStream(is, PictureType.JPEG).size(300, 200).create();
                    pictureList.add(picture);

                    // 关键：在图片之间插入分页
//                    if (i < imgUrls.size() - 1) {
//                        pictureList.add(new TextRenderData("\f"));
//                    }

                }

//                String url = imgUrls.get(i);
//
//                byte[] bytes = getImageBytes(url);
//                // 图片
//                ByteArrayPictureRenderData picture = new ByteArrayPictureRenderData(bytes);
//                pictureList.add(picture);
////                // 关键：在图片之间插入分页
//                if (i < imgUrls.size() - 1) {
//                    pictureList.add(new TextRenderData("\f"));
//                }
            }

//            for (int i = 0; i < imgUrls.size(); i++) {
//                String filePath = imgUrls.get(i);
//                URL url = new URL(filePath);
//                try (InputStream is = url.openStream()) {
//
//                    // 创建图片对象
//                    PictureRenderData picture = Pictures.ofStream(is).create();
//
//                    Map<String, Object> map = new HashMap<>();
//
////                        map.put("deviceName", "deviceName "+ filePath);
//
//                    map.put("picture", picture);
//
//                    pictureDataList.add(map);
//
//                }
//            }



            // 5 Word数据
            Map<String, Object> data = new HashMap<>();
//            data.put("workOrderNo", "工单编号");
            data.put("pictures", pictureList);
            // 6 读取模板
            InputStream templateStream = this.getClass().getResourceAsStream("/templates/不停电作业现场勘查单.docx");
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

    private byte[] getImageBytes(String imageUrl) {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            URL url = new URL(imageUrl);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            // 设置超时
//            conn.setConnectTimeout(5000);
//            conn.setReadTimeout(5000);
//            conn.setRequestMethod("GET");
            try (InputStream inputStream = url.openStream()) {
                byte[] buffer = new byte[1024];
                int len;
                while ((len = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, len);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("图片读取失败: " + imageUrl);
        }
        return outputStream.toByteArray();
    }
}

