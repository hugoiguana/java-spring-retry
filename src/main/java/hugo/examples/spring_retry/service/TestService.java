package hugo.examples.spring_retry.service;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

import java.util.UUID;

public interface TestService {

    @Retryable(
            noRetryFor = NullPointerException.class,
            maxAttemptsExpression = "${hugo.retry.maxAttempts}",
            backoff = @Backoff(delayExpression = "${hugo.retry.maxDelay}")
    )
    String testCallMethodRetry1(UUID id);

}
