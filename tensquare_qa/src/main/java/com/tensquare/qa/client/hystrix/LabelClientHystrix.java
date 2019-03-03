package com.tensquare.qa.client.hystrix;

import com.tensquare.qa.client.LabelClient;
import entity.Result;
import entity.StatusCode;
import org.springframework.stereotype.Component;

/**
 * @author IceCola
 */
@Component
public class LabelClientHystrix implements LabelClient {
    @Override
    public Result findById(String id) {
        return new Result(false, StatusCode.ERROR,"熔断器启动了");
    }
}
