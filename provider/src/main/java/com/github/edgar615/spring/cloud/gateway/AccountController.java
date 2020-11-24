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

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Map;

/**
 * @author 张雨舟
 * @Date 2020/8/6
 */
@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final static Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

    @GetMapping("/{nfId}")
    public Map<String, String> get(@PathVariable("nfId") String nfId) {
        return Collections.singletonMap("path", nfId);
    }

    @GetMapping
    public Map<String, String> query(@RequestParam("nfId") String nfId) {
        return Collections.singletonMap("query", nfId);
    }

    @GetMapping("/status")
    public Map<String, String> status(@RequestParam("account") String nfId) {
        return Collections.singletonMap("status", nfId);
    }

    @PostMapping
    public Map<String, String> post(@RequestBody Map<String, Object> data) {
        LOGGER.info("data=>{}", data);
        return Collections.singletonMap("foo", "bar");
    }
}
