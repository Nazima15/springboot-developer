package me.nazima.springdeveloper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController //안 쓰면 Controller+ResponseBody 두 개 적어줘야 함 (모든 메소드가 데이터만 리턴)
//@Controller(기본적으로 메서드 리턴하는 것이 HTML 파일이름)
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping("/test")
    public List<Member> getAllMembers(){
        return testService.getAllMembers();
    }
}