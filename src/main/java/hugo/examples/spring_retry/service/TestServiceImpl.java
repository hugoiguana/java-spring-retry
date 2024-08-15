package hugo.examples.spring_retry.service;

import hugo.examples.spring_retry.feing.TestExternalFeing;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.retry.annotation.Recover;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Log4j2
@AllArgsConstructor
public class TestServiceImpl implements TestService {

    final TestExternalFeing testExternalFeing;

    @Override
    public String testCallMethodRetry1(UUID id) {
        log.info("Trying to call testExternalFeing.getExternalTest({})", id);
        return testExternalFeing.getExternalTest(id);
    }

    @Recover
    @SneakyThrows
    public void recover(Exception e, UUID id) {
        log.error("Recover from Error id={} - {}", id, e.getMessage(), e);
        throw e;
    }


}
