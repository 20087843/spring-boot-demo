package pri.smilly.demo.server.controller.mvc;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@Api("Test Controller")
public class TestController {

    @GetMapping
    @ApiOperation(value = "获取用户列表", notes = "")
    public String test() {
        return "hello";
    }

}
