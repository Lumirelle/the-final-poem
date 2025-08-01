package com.cool.modules.user.controller.app;

import cn.hutool.json.JSONObject;
import com.cool.core.annotation.CoolRestController;
import com.cool.core.base.BaseController;
import com.cool.core.request.R;
import com.cool.core.util.CoolSecurityUtil;
import com.cool.core.util.EntityUtils;
import com.cool.modules.user.entity.UserInfoEntity;
import com.cool.modules.user.service.UserInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;

@RequiredArgsConstructor
@Tag(name = "用户信息", description = "用户信息")
@CoolRestController
public class AppUserInfoController extends BaseController<UserInfoService, UserInfoEntity> {

    private final UserInfoService userInfoService;

    @Override
    protected void init(HttpServletRequest request, JSONObject requestParams) {
    }

    @Operation(summary = "用户个人信息", description = "获得App、小程序或者其他应用的用户个人信息")
    @GetMapping("/person")
    public R person() {
        Long userId = CoolSecurityUtil.getCurrentUserId();
        UserInfoEntity userInfoEntity = userInfoService.person(userId);
        return R.ok(EntityUtils.toMap(userInfoEntity,
            "password"));
    }

    @Operation(summary = "更新用户信息")
    @PostMapping("/updatePerson")
    public R updatePerson(@RequestAttribute JSONObject requestParams) {
        UserInfoEntity infoEntity = requestParams.toBean(UserInfoEntity.class);
        infoEntity.setId(CoolSecurityUtil.getCurrentUserId());
        return R.ok(
            userInfoService.updateById(infoEntity)
        );
    }

    @Operation(summary = "用户个人档案", description = "获得用户的个人患者/陪诊师档案")
    @GetMapping("/profile")
    public R profile() {
        Long userId = CoolSecurityUtil.getCurrentUserId();
        return R.ok(userInfoService.profile(userId));
    }

    @Operation(summary = "用户个人档案", description = "设置用户的个人患者/陪诊师档案")
    @PostMapping("/addProfile")
    public R addProfile(@RequestAttribute() JSONObject requestParams) {
        Long userId = CoolSecurityUtil.getCurrentUserId();
        return R.ok(userInfoService.addProfile(userId, requestParams));
    }

    @Operation(summary = "用户个人档案", description = "更新用户的个人患者/陪诊师档案")
    @PostMapping("/updateProfile")
    public R updateProfile(@RequestAttribute() JSONObject requestParams) {
        Long userId = CoolSecurityUtil.getCurrentUserId();
        return R.ok(userInfoService.updateProfile(userId, requestParams));
    }

    @Operation(summary = "更新用户密码")
    @PostMapping("/updatePassword")
    public R updatePassword(
        @RequestAttribute JSONObject requestParams
    ) {
        String password = requestParams.get("password", String.class);
        String code = requestParams.get("code", String.class);
        userInfoService.updatePassword(CoolSecurityUtil.getCurrentUserId(), password, code);
        return R.ok();
    }

    @Operation(summary = "注销")
    @PostMapping("/logoff")
    public R logoff() {
        userInfoService.logoff(CoolSecurityUtil.getCurrentUserId());
        return R.ok();
    }

    @Operation(summary = "绑定手机号")
    @PostMapping("/bindPhone")
    public R bindPhone(
        @RequestAttribute JSONObject requestParams) {
        String phone = requestParams.get("phone", String.class);
        String code = requestParams.get("code", String.class);
        userInfoService.bindPhone(CoolSecurityUtil.getCurrentUserId(), phone, code);
        return R.ok();
    }

}
