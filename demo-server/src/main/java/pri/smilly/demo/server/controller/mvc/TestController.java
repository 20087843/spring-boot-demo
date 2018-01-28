package pri.smilly.demo.server.controller.mvc;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping
    @ApiOperation(value="获取用户列表", notes="")
    public String test() {
        return "hello";
    }

}
