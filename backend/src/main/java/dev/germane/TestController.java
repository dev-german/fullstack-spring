package dev.germane;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    record Test(String result){}

    @GetMapping("/test")
    public Test getTest(){
        return new Test("test");
    }

    @GetMapping("/test2")
    public Test getTest2(){
        return new Test("test2");
    }
}
