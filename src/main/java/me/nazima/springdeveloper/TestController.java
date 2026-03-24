package me.nazima.springdeveloper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TestController {

    @Autowired
    private TestService testService;

    // 전체 조회
    @GetMapping("/test")
    public ResponseEntity<List<Member>> getAllMembers() {
        return ResponseEntity.ok(testService.getAllMembers());
    }

    // 생성
    @PostMapping("/test")
    public ResponseEntity<Member> createMember(@RequestBody Member member) {
        return ResponseEntity.ok(testService.saveMember(member));
    }

    @PostMapping("/test2")
    public ResponseEntity<String> test(){
        return new ResponseEntity<String>("Hello World", HttpStatus.CREATED);
    }
}