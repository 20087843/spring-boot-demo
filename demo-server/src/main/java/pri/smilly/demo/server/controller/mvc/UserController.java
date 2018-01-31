package pri.smilly.demo.server.controller.mvc;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pri.smilly.demo.server.common.domain.PageBean;
import pri.smilly.demo.server.common.domain.User;
import pri.smilly.demo.server.service.UserService;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/users")
@Api("USER CONTROLLER")
public class UserController {
    @Autowired
    private UserService service;

    @GetMapping(produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    @ApiOperation("get user list in page")
    public PageBean<User> getUsers(
            @RequestParam(defaultValue = "1") @NotNull Integer pageNum,
            @RequestParam(defaultValue = "10") @NotNull Integer pageSize,
            @RequestBody(required = false) User condition) {
        return service.getUsers(pageNum, pageSize, condition);
    }

    @GetMapping("/{userId}")
    @ResponseBody
    @ApiOperation("get a user by user id")
    public User getUser(@PathVariable @NotNull Long userId) {
        return service.getUserById(userId);
    }

    @PostMapping(produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    @ApiOperation("save a user")
    public boolean save(@RequestBody User domain) {
        return service.save(domain);
    }
}
