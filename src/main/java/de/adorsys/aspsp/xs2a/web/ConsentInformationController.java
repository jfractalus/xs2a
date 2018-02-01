package de.adorsys.aspsp.xs2a.web;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.adorsys.aspsp.xs2a.service.ConsentService;
import de.adorsys.aspsp.xs2a.spi.domain.Account;
import de.adorsys.aspsp.xs2a.spi.domain.Balances;
import de.adorsys.aspsp.xs2a.spi.domain.SingleAccountAccess;
import de.adorsys.aspsp.xs2a.spi.domain.TransactionStatus;
import de.adorsys.aspsp.xs2a.spi.domain.aic.AICAccountsList;
import de.adorsys.aspsp.xs2a.spi.domain.aic.AICInformationRequestBody;
import de.adorsys.aspsp.xs2a.spi.domain.aic.AICInformationResponseBody;
import de.adorsys.aspsp.xs2a.spi.domain.aic.AICStatusResponseBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
* Created by aro on 27.11.17
*/

@RestController
@SuppressWarnings("unused")
@RequestMapping(path = "api/v1/consents")
@Api(value="api/v1/consents", tags="AISP Consents", description="Provides access to the PSU Consents")
public class ConsentInformationController {
	
	private static final Logger log = LoggerFactory.getLogger(PaymentInitiationController.class);
	
	@Autowired
    private ConsentService consentService;
	
	@ApiOperation(value = "Creats an account information consent resource at the ASPSP regarding access to accounts specified in this request")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "OK",response=AICInformationResponseBody.class),
    @ApiResponse(code = 400, message = "Bad request") })
    @RequestMapping( method = RequestMethod.POST)
	public  ResponseEntity<AICInformationResponseBody> createAICForAccounts(@RequestBody AICInformationRequestBody aICInformationRequestBody) {
		return new ResponseEntity<AICInformationResponseBody>(createAIC(aICInformationRequestBody), HttpStatus.OK);
	}

	@ApiOperation(value = "Creats an account information consent resource at the ASPSP to return a list of all accessible accounts",
				 notes = "if withBalance is true then the balance is on the list off all payments accounts ")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response= Account[].class),
	@ApiResponse(code = 400, message = "Bad request") })
	@RequestMapping(value = "/account-list", method = RequestMethod.POST)
	public  Resource<Account[]> createAICRessource( 
			 @ApiParam (name="with-balance",value="If contained, this function reads the list of accessible payment accounts including the balance.")
	         @RequestParam(name="with-balance", required=true) Boolean withBalance) {
		
		Link link = linkTo(ConsentInformationController.class).withSelfRel();
	 	return new Resource<Account[]>(getAllAcounts(withBalance),link);
		
	}
	
	@ApiOperation(value = "Check the status of an account information consent resource")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response=AICStatusResponseBody.class),
    @ApiResponse(code = 400, message = "Bad request") })
	@RequestMapping(value = "/{consentID}/status", method = RequestMethod.GET)
	public ResponseEntity<AICStatusResponseBody>  getStatusForAIC( @PathVariable("consentID") String consentID) {
		
		return new ResponseEntity<AICStatusResponseBody>(getTansactionStatus(consentID), HttpStatus.OK);
	}

	@ApiOperation(value = " Returns the content of an account information consent object")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response=AICAccountsList.class),
    @ApiResponse(code = 400, message = "Bad request") })
	@RequestMapping(value = "/{consentID}", method = RequestMethod.GET)
	public ResponseEntity<AICAccountsList> getInformationsForAIC(@PathVariable("consentID") String consentID) {
		return new ResponseEntity<AICAccountsList>(getAccountsList(consentID), HttpStatus.OK);
	}
	
	@ApiOperation(value = " Delete information consent object")
    @ApiResponses(value = { @ApiResponse(code = 204, message = "No Content"),
    @ApiResponse(code = 400, message = "Bad request") })
	@RequestMapping(value = "/{consentID}", method = RequestMethod.DELETE)
	 public HttpEntity<Void> deleteAIC(@PathVariable String consentID) {
	     return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	 }
	
	private AICInformationResponseBody createAIC(AICInformationRequestBody aICInformationRequestBody) {
		
		return null;
	}

	private AICAccountsList getAccountsList(String consentID) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private AICStatusResponseBody getTansactionStatus(String consentID) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private Account[] getAllAcounts(Boolean withBalance) {
		Account account = new Account();
		List<Account> accounts = new ArrayList<Account>();
		accounts.add(account);
		if (!withBalance){
			for (Account ac : accounts) {
				ac.setBalances(null);
			}
		}
		return (Account[])accounts.toArray();
	}

	
}