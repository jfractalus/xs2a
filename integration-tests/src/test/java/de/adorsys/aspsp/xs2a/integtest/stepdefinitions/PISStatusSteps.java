package de.adorsys.aspsp.xs2a.integtest.stepdefinitions;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import de.adorsys.aspsp.xs2a.domain.pis.SinglePayments;
import de.adorsys.aspsp.xs2a.integtest.model.TestData;
import de.adorsys.aspsp.xs2a.integtest.util.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@FeatureFileSteps
public class PISStatusSteps {

    @Autowired
    @Qualifier("xs2a")
    private RestTemplate restTemplate;

    @Autowired
    private Context context;

    /* see GlobalSteps.java
        @Given("^PSU is logged in$")
    */

    @And("^created a payment status request with of a not existing payment-id (.*) using the payment product (.*)$")
    public void setPaymentParameters(String paymentId, String paymentProduct) throws IOException {
        context.setPaymentProduct(paymentProduct);
        context.setPaymentId(paymentId);

    }

    @When("^PSU requests the status of the payment$")
    public void sendPaymentStatusRequest() {

        HttpHeaders headers = new HttpHeaders();
        headers.add("x-request-id", "2f77a125-aa7a-45c0-b414-cea25a116035");
        headers.add("Authorization", "Bearer " + context.getAccessToken());

        HttpEntity<SinglePayments> entity = new HttpEntity (headers);

        // TO DO: clarify return type, actual String
        ResponseEntity<HashMap> response = restTemplate.exchange(
            context.getBaseUrl() + "/payments/" + context.getPaymentProduct() + "/" + context.getPaymentId() + "/status",
            HttpMethod.GET,
            entity,
            HashMap.class);

        context.setResponse(response);
    }

    @Then("^an appropriate response code and the status (.*) is delivered to the PSU$")
    public void checkStatus(String dataFileName) {

        File jsonFile = new File("src/test/resources/data-input/pis/status/" + dataFileName);


    }

    private HttpStatus convertStringToHttpStatusCode(String code){
        return HttpStatus.valueOf(Integer.valueOf(code));
    }

}