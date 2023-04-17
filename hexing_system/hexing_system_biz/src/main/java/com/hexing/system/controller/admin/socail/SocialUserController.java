package com.hexing.system.controller.admin.socail;

import com.hexing.common.enums.UserTypeEnum;
import com.hexing.common.pojo.CommonResult;
import com.hexing.system.controller.admin.socail.vo.SocialUserBindReqVO;
import com.hexing.system.controller.admin.socail.vo.SocialUserUnbindReqVO;
import com.hexing.system.convert.social.SocialUserConvert;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static com.hexing.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "管理后台 - 社交用户")
@RestController
@RequestMapping("/system/social-user")
@Validated
public class SocialUserController {


    @PostMapping("/bind")
    @Operation(summary = "社交绑定，使用 code 授权码")
    public CommonResult<Boolean> socialBind(@RequestBody @Valid SocialUserBindReqVO reqVO) {
        return CommonResult.success(true);
    }

    @DeleteMapping("/unbind")
    @Operation(summary = "取消社交绑定")
    public CommonResult<Boolean> socialUnbind(@RequestBody SocialUserUnbindReqVO reqVO) {
        return CommonResult.success(true);
    }

}
