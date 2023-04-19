package com.hexing.web.controller.common;

import cn.dev33.satoken.annotation.SaIgnore;
import com.hexing.common.core.domain.R;
import com.hexing.common.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author firerock_tech
 */
@SaIgnore
@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
public class NotifyController {

    @PostMapping("/sync/order")
    public R<Void> syncOrder(@RequestBody Object order) {
        log.error(JsonUtils.toJsonString(order));
        return R.ok();
    }
}
