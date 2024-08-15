package hugo.examples.spring_retry.controller;


import hugo.examples.spring_retry.service.TestService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("tests")
@AllArgsConstructor
public class TestController {

    final TestService testService;


    @GetMapping(value = "/test", produces = "application/json")
    public ResponseEntity<String> test() {

        testService.testCallMethodRetry();

        return ResponseEntity.ok("test");
    }

}
