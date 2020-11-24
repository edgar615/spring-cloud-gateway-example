package com.github.edgar615.spring.cloud.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("/api/nf/subintf")
public class NfSubintfController {

    private final static Logger LOGGER = LoggerFactory.getLogger(NfSubintfController.class);

    @GetMapping
    public Map<String, String> query(@RequestParam("nfId") String nfId) {
        return Collections.singletonMap("query", nfId);
    }

    @PostMapping
    public Map<String, Object> post(@RequestBody Map<String, Object> data) {
        LOGGER.info("data=>{}", data);
        return Collections.singletonMap("post", data);
    }

    @DeleteMapping
    public Map<String, Object> delete(@RequestBody Map<String, Object> data) {
        LOGGER.info("data=>{}", data);
        return Collections.singletonMap("delete", data);
    }
}
