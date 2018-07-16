package de.adorsys.aspsp.xs2a.integtest.stepdefinitions;

import de.adorsys.aspsp.xs2a.integtest.model.TestData;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;


@Data
@Component
public class Context {

    @Value("${xs2a.baseUrl}")
    private String baseUrl;

    String scaApproach;
    String paymentProduct;
    String accessToken;
    TestData testData;
    ResponseEntity<HashMap> response;
}
