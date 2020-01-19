package priv.gitonline.consumer.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
public class OrderController {
    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/addOrder")
    @HystrixCommand(fallbackMethod = "failed",commandProperties = {
            //设置执行hystrix命令包裹方法的超时时间，超过这个时间则执行回退逻辑
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "1000"),
            //断路器开关
            @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),
            //设置用于触发跳闸的滚动窗口的最小失败请求数的阈值
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "20"),
            //设置触发断路器后允许再次执行请求前，拒绝请求的时间，单位为毫秒
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "5000"),
            //设置触发断路器，走降级逻辑的默认百分比最小阈值
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "30")
    })
    public Map<String,Object> addOrder(String data){
        Map<String,Object> params = new HashMap<>();
        params.put("data",data);
        Map<String,Object> result = restTemplate.postForObject("http://cloud-provider/addOrder?data={data}",null,Map.class,params);
        return result;
    }

    public Map<String,Object> failed(String data){
        Map<String,Object> result = new HashMap<>();
        result.put("code","9999");
        result.put("msg","调用失败");
        result.put("data",data);
        return result;
    }

}
