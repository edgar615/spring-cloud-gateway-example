package com.github.edgar615.spring.cloud.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

/**
 * @author 张雨舟
 * @Date 2020/8/6
 */
@RestController
@RequestMapping("/api/vni/accounts")
public class VniAccountController {

    private final static Logger LOGGER = LoggerFactory.getLogger(VniAccountController.class);

    @GetMapping
    public Map<String, String> query(@RequestParam("vni") String vni) {
        return Collections.singletonMap("query", vni);
    }
}
