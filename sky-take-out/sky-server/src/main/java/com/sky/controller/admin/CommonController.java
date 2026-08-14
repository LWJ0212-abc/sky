package com.sky.controller.admin;

import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import com.sky.service.CommonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * @projectName: sky-take-out
 * @package: com.sky.controller.admin.admin
 * @className: CommonController
 * @author: lwj
 * @description:
 * @date: 2026/6/20 21:07
 * @version: 1.0
 */

@Slf4j
@RequestMapping("/admin/common")
@Api(tags = "通用接口")
@RestController
public class CommonController {

    @Autowired
    private CommonService commonService;

    /**
     * @param file:
      * @return Resutl<String>
     * @author lwj
     * @description 保存上传文件
     * @date 2026/6/20 21:14
     */
    @PostMapping("/upload")
    @ApiOperation("文件上传")
    public Result<String> upload(MultipartFile file) {
        log.info("文件上传：{}",file);
        try{
            //原始文件名
            String fileName = file.getOriginalFilename();
            //文件后缀
            String extension=fileName.substring(fileName.lastIndexOf("."));
            //新文件名称
            String objectName = UUID.randomUUID().toString()+extension;

            //文件请求路径
            String filepath=commonService.upload(file.getBytes(),objectName);

            return Result.success(filepath);
        }catch (Exception e){
            log.error("上传文件出错：{}",e.getMessage());
        }
        return Result.error(MessageConstant.UPLOAD_FAILED);
    }
}
