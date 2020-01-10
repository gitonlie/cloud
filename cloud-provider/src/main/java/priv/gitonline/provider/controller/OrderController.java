package priv.gitonline.provider.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class OrderController {

    @RequestMapping("/addOrder")
    public Map<String,Object> addOrder(String data){
        Map<String,Object> result = new HashMap<>();
        result.put("code","0000");
        result.put("msg","调用成功");
        result.put("data",data);
        return result;
    }

}
