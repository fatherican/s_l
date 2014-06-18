package cn.njcit.controller.fileOperator;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by YK on 2014-06-09.
 */
@Controller
@RequestMapping("/fileController")
public class FileController {

    @RequestMapping(value="/upload", method = RequestMethod.POST)
    public void  upload(MultipartHttpServletRequest multipartRequest, HttpServletResponse response) {
        MultipartFile file = multipartRequest.getFile("uploadFile");
        

    }
}
