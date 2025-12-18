package in.akshat.customer_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@RefreshScope
public class CustomerConfig {

    @Value("${customer.message}")
    private String message;

    public String getMessage() {
        return message;
    }
}
