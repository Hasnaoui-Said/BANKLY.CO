package co.bankly.users.rest.provided;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hello")
public class HelloMicUsers {
    @RequestMapping(method = RequestMethod.GET, value = "/")
    public String hello(){
        return "micro service users works!!";
    }
}
