package com.github.edgar615.spring.cloud.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Map;

/**
 * @author 张雨舟
 * @Date 2020/8/6
 */
@RestController
@RequestMapping("/api/nf")
public class NfController {

    private final static Logger LOGGER = LoggerFactory.getLogger(NfController.class);

    @GetMapping
    public Map<String, String> query(HttpServletRequest request) {
        String header = request.getHeader("Gn-Request-Id");
        LOGGER.info("Header: Gn-Request-Id=>{}", header);
        return Collections.singletonMap("foo", "bar");
    }

    @GetMapping("/{nfId}")
    public Map<String, String> get(@PathVariable("nfId") String nfId) {
        return Collections.singletonMap("path", nfId);
    }


}
