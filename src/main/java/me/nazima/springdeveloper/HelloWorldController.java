package me.nazima.springdeveloper;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
    @GetMapping("/hello")
    public String hello(@RequestParam String name) {
        return "반갑습니다 " + name + "님";
    }

    //http://localhost/student?firstName=Sungchul&lastName=Park
    @GetMapping("/student")
    public Student getStudent(@RequestParam("firstName") String firstName,@RequestParam("lastName") String lastName){
        return new Student(firstName, lastName);
    }

    //http://localhost/student/ChanHo/Park
    @GetMapping("/student/{firstName}/{lastName}")
    public Student getStudent2(@PathVariable("firstName") String firstName, @PathVariable("lastName")String lastName){
        return new Student(firstName, lastName);
    }

}
