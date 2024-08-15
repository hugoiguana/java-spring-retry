package hugo.examples.spring_retry.controller;

import hugo.examples.spring_retry.model.Test1Response;
import hugo.examples.spring_retry.service.TestService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.UUID;

@Controller
@Log4j2
@RequestMapping("api/tests")
@AllArgsConstructor
public class TestController {

    final TestService testService;


    @GetMapping(value = "/test1/{id}", produces = "application/json")
    public ResponseEntity<Test1Response> test1(@PathVariable("id") UUID id) {

        log.info("TestController - test1 id={}", id);

        var test = testService.testCallMethodRetry1(id);
        var test1Response = new Test1Response(test);

        return ResponseEntity.ok(test1Response);
    }

}
