package priv.gitonline.consumer.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
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
    @HystrixCommand(fallbackMethod = "failed")
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
