package recruit.saas.user.service.controller;

import org.springframework.web.bind.annotation.*;
import recruit.saas.common.context.CurrentContext;
import recruit.saas.user.service.common.CommonPageResponse;
import recruit.saas.common.rest.CommonRestResponse;
import recruit.saas.common.vo.AdminVO;
import recruit.saas.user.service.assembler.AdminAssembler;
import recruit.saas.user.service.entity.Admin;
import recruit.saas.user.service.param.AssignAdminParam;
import recruit.saas.user.service.param.DeleteAdminParam;
import recruit.saas.user.service.param.ResetAdminPasswordParam;
import recruit.saas.user.service.service.AdminService;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Optional;

import static recruit.saas.common.rest.CommonResultCode.*;

/**
 * @author ZhangShenao
 * @date 2023/3/3 9:59 AM
 * Description 管理员用户API
 */
@RestController
@RequestMapping("/admin")
public class AdminUserController {
    @Resource
    private AdminService adminService;

    @Resource
    private AdminAssembler adminAssembler;

    //获取当前登录的管理员用户信息
    @GetMapping("/current")
    public CommonRestResponse<AdminVO> current() {
        Optional<AdminVO> adminVO = CurrentContext.getCurrentAdminUser();
        return adminVO.map(CommonRestResponse::success).orElseGet(() -> CommonRestResponse.ofResultCode(NOT_LOGIN));
    }

    //分配新的管理员账号
    @PostMapping("/assign")
    public CommonRestResponse<?> assign(@RequestBody @Valid AssignAdminParam param) {
        Admin entity = adminAssembler.assignParam2Entity(param);
        boolean succ = adminService.create(entity);
        if (!succ) {
            return CommonRestResponse.ofResultCode(ASSIGN_ADMIN_FAIL);
        }
        return CommonRestResponse.success();
    }

    //查询管理员账号列表
    @GetMapping("/list")
    public CommonRestResponse<CommonPageResponse<AdminVO>> list(@RequestParam("since") int since,
                                                                @RequestParam("limit") int limit,
                                                                @RequestParam(value = "user_name", required = false) String username) {
        CommonPageResponse<AdminVO> page = adminService.listByPage(since, limit, username);
        return CommonRestResponse.success(page);
    }

    //删除管理员账号
    @PostMapping("/delete")
    public CommonRestResponse<?> delete(@Valid @RequestBody DeleteAdminParam param) {
        boolean succ = adminService.deleteById(param.getId());
        if (!succ) {
            return CommonRestResponse.ofResultCode(DELETE_ADMIN_FAIL);
        }
        return CommonRestResponse.success();
    }

    //重置管理员密码
    @PostMapping("/password/reset")
    public CommonRestResponse<?> resetPassword(@Valid @RequestBody ResetAdminPasswordParam param) {
        boolean succ = adminService.resetPassword(param.getId(), param.getOriginPassword(), param.getNewPassword(), param.getAffirmNewPassword());
        if (!succ) {
            return CommonRestResponse.ofResultCode(DELETE_ADMIN_FAIL);
        }
        return CommonRestResponse.success();
    }
}
