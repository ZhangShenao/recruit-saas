package recruit.saas.user.service.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import recruit.saas.common.rest.CommonRestResponse;
import recruit.saas.user.service.entity.Stu;
import recruit.saas.user.service.param.CreateStuParam;
import recruit.saas.user.service.service.StuService;

import javax.annotation.Resource;

/**
 * @author ZhangShenao
 * @date 2023/2/17 11:17 AM
 * Description Stu接口
 */
@RestController
@RequestMapping("/stu")
public class StuController {
    @Resource
    private StuService service;

    //创建Stu
    @PostMapping("/create")
    public CommonRestResponse<Boolean> create(@RequestBody CreateStuParam param) {
        Stu entity = param.toEntity();
        boolean succ = service.create(entity);
        return CommonRestResponse.success(succ);
    }
}
