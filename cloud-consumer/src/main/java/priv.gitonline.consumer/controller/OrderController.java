package priv.gitonline.consumer.controller;

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
    public Map<String,Object> addOrder(String data){
        Map<String,Object> params = new HashMap<>();
        params.put("data",data);
        Map<String,Object> result = restTemplate.postForObject("http://cloud-provider/addOrder?data={data}",null,Map.class,params);
        return result;
    }

}
