package hugo.examples.spring_retry.feing;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(value = "testExternal", url = "https://external/tests/")
public interface TestExternalFeing {

    @GetMapping(value = "/external/{id}", produces = "application/json")
    String getExternalTest(@PathVariable("id") UUID id);

}
