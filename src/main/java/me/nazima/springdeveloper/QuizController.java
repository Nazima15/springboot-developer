package me.nazima.springdeveloper;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/quiz") // 공통 경로 지정 가능
public class QuizController {

    // ---------------- GET 요청 ----------------
    @GetMapping
    public ResponseEntity<String> quiz(@RequestParam("code") int code) {
        switch (code) {
            case 1:
                // 실제 생성된 리소스 URI를 넣으면 좋음 (예: /quiz/123)
                return ResponseEntity.created(null).body("Created");
            case 2:
                return ResponseEntity.badRequest().body("Bad Request");
            default:
                return ResponseEntity.ok("ok");
        }
    }

    // ---------------- POST 요청 ----------------
    @PostMapping
    public ResponseEntity<String> quiz2(@RequestBody Code code) {
        switch (code.value()) {
            case 1:
                // Forbidden 접근 불가
                return ResponseEntity.status(403).body("Forbidden");
            default:
                return ResponseEntity.ok("ok");
        }
    }
}

// ---------------- DTO / Record ----------------
record Code(int value) {}