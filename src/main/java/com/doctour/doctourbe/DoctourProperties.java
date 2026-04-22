package com.doctour.doctourbe;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("doctour.app")
@Getter
@Setter
public class DoctourProperties {

    private String frontendUrl;

}
