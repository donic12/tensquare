package com.tensquare.qa.client;

import com.tensquare.qa.client.hystrix.LabelClientHystrix;
import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author IceCola
 */
@FeignClient(value = "tensquare-base", fallback = LabelClientHystrix.class)
public interface LabelClient {
    /**
     * 根据id查找Label
     * @param id
     * @return
     */
    @GetMapping("/label/{id}")
    public Result findById(@PathVariable("id") String id);
}
