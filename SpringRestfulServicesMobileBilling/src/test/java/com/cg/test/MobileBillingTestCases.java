package com.cg.test;

import static org.junit.Assert.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import com.cg.mobilebilling.beans.Bill;
import com.cg.mobilebilling.beans.Customer;
import com.cg.mobilebilling.beans.Plan;
import com.cg.mobilebilling.beans.PostpaidAccount;
import com.cg.mobilebilling.beans.StandardPlan;
import com.cg.mobilebilling.daoservices.BillingDAOServices;
import com.cg.mobilebilling.daoservices.BillingDAOServicesImpl;
import com.cg.mobilebilling.exceptions.BillDetailsNotFoundException;
import com.cg.mobilebilling.exceptions.BillingServicesDownException;
import com.cg.mobilebilling.exceptions.CustomerDetailsNotFoundException;
import com.cg.mobilebilling.exceptions.InvalidBillMonthException;
import com.cg.mobilebilling.exceptions.PlanDetailsNotFoundException;
import com.cg.mobilebilling.exceptions.PostpaidAccountNotFoundException;
import com.cg.mobilebilling.services.BillingServices;
import com.cg.mobilebilling.services.BillingServicesImpl;

public class MobileBillingTestCases {

	
	private static BillingDAOServices billingDaoServices;
	private static BillingServices billingServices;
	
	@BeforeClass
	public static  void setUpMobileBillingTestCases() {
		billingDaoServices=Mockito.mock(BillingDAOServices.class);
		billingServices=new BillingServicesImpl(billingDaoServices);
	}
	
	@Before
	public void setUpTestData() throws SQLException {
		
	}
	
	@Test(expected=BillingServicesDownException.class)
	public void getPlanAllDetailsForInvalidData() throws BillingServicesDownException {
		billingServices.getPlanAllDetails();
	}
	
	@Test
	public void getPlanAllDetailsForValidData() throws BillingServicesDownException {
		billingServices.getPlanAllDetails();
	}
	
	@Test(expected=BillingServicesDownException.class)
	public void acceptCustomerDetailsForInvalidData() throws BillingServicesDownException {
		Customer customer=new Customer(1236);
		billingServices.acceptCustomerDetails(customer);
	}
	
	@Test
	public void acceptCustomerDetailsForValidData() throws BillingServicesDownException {
		Customer expectedId=new Customer(1);
		Customer customer=new Customer(1);
		Customer actualId=billingServices.acceptCustomerDetails(customer);
		assertEquals(expectedId, actualId);
	}
	
	@Test(expected=CustomerDetailsNotFoundException.class)
	public void openPostpaidAccountForInvalidData() throws CustomerDetailsNotFoundException, PlanDetailsNotFoundException, BillingServicesDownException{
		billingServices.openPostpaidMobileAccount(23, 6);
	}
	
	@Test
	public void opopenPostpaidAccountForValidData() throws PlanDetailsNotFoundException, CustomerDetailsNotFoundException, BillingServicesDownException {
		double expectedMobileNo = billingServices.generateUniqueMobileNo();
		double actualMobileNo= billingServices.openPostpaidMobileAccount(1,1);
		assertEquals(expectedMobileNo, actualMobileNo);
	}
	
	@Test (expected=PostpaidAccountNotFoundException.class)
	public void generateMonthlyBillForInvalidData() throws CustomerDetailsNotFoundException, PostpaidAccountNotFoundException, InvalidBillMonthException, BillingServicesDownException, PlanDetailsNotFoundException {
		billingServices.generateMonthlyMobileBill(1, "july", 2, 2, 2, 2, 2);
	}
	
	@Test 
	public void generateMonthlyBillForValidData() throws CustomerDetailsNotFoundException, PostpaidAccountNotFoundException, InvalidBillMonthException, BillingServicesDownException, PlanDetailsNotFoundException {
		Bill expectedBillId= new Bill(1);
		Bill actualBillId= billingServices.generateMonthlyMobileBill(1, "july", 2, 2, 2, 2, 2);
		assertEquals(expectedBillId, actualBillId);
	}
	
	@Test(expected=CustomerDetailsNotFoundException.class)
	public void getCustomerDetailsForInvalidData() throws CustomerDetailsNotFoundException, BillingServicesDownException {
		billingServices.getCustomerDetails(123);
	}
	
	@Test
	public void getCustomerDetailsForValidData() throws CustomerDetailsNotFoundException, BillingServicesDownException {
		Customer expectedId=new Customer(1);
		Customer actualId= billingServices.getCustomerDetails(1);
		assertEquals(expectedId, actualId);
	}
	
	@Test
	public void getAllCustomerDetails() throws BillingServicesDownException {
		List<Customer> expectedCustomerList= new ArrayList<>(billingDaoServices.getAllCustomers());
		List<Customer> actualCustomerList= billingServices.getAllCustomerDetails();
		assertEquals(expectedCustomerList, actualCustomerList);
		
	}
	
	@Test (expected=CustomerDetailsNotFoundException.class)
	public void getPostpaidAccountDetailsForInvalidCustomerId() throws CustomerDetailsNotFoundException, PostpaidAccountNotFoundException, BillingServicesDownException {
		billingServices.getPostPaidAccountDetails(1, 1);
	}
	
	@Test (expected=PostpaidAccountNotFoundException.class)
	public void getPostpaidAccountDetailsForInvalidMobileNo() throws CustomerDetailsNotFoundException, PostpaidAccountNotFoundException, BillingServicesDownException {
		billingServices.getPostPaidAccountDetails(1, 1);
	}
	
	@Test
	public void getPostpaidAccountDetailsForValidData() throws CustomerDetailsNotFoundException, PostpaidAccountNotFoundException, BillingServicesDownException {
		PostpaidAccount expectedMobileNo= new PostpaidAccount(1);
		PostpaidAccount actualMobileNo= billingServices.getPostPaidAccountDetails(1, 1);
		assertEquals(expectedMobileNo, actualMobileNo);
	} 
	
	@Test(expected=PostpaidAccountNotFoundException.class)
	public void getCustomerAllPostpaidAccountsDetailsForInvalidData() throws CustomerDetailsNotFoundException, BillingServicesDownException {
		billingServices.getCustomerAllPostpaidAccountsDetails(1);
	}
	
	@Test
	public void getCustomerAllPostpaidAccountsDetailsForValidData() throws CustomerDetailsNotFoundException, BillingServicesDownException {
		List<PostpaidAccount> expectedAccountList= new ArrayList<>(billingDaoServices.getCustomerPostPaidAccounts(1));
		List<PostpaidAccount> actualAccountList= billingServices.getCustomerAllPostpaidAccountsDetails(1);
		assertEquals(expectedAccountList, actualAccountList);
	}
	
	@Test(expected=PostpaidAccountNotFoundException.class)
	public void getMobileBillDetailsForInvalidMobileNo() throws CustomerDetailsNotFoundException, PostpaidAccountNotFoundException, InvalidBillMonthException, BillDetailsNotFoundException, BillingServicesDownException {
		billingServices.getMobileBillDetails(56, "july");
	}
	
	@Test(expected=InvalidBillMonthException.class)
	public void getMobileBillDetailsForInvalidMonth() throws CustomerDetailsNotFoundException, PostpaidAccountNotFoundException, InvalidBillMonthException, BillDetailsNotFoundException, BillingServicesDownException {
		billingServices.getMobileBillDetails(996, "august");
	}
	
	@Test(expected=BillDetailsNotFoundException.class)
	public void getMobileBillDetailsFirInvalidData() throws CustomerDetailsNotFoundException, PostpaidAccountNotFoundException, InvalidBillMonthException, BillDetailsNotFoundException, BillingServicesDownException {
		billingServices.getMobileBillDetails(89, "august");
	}
	
	@Test
	public void getMobileBillDetailsForValidData() throws CustomerDetailsNotFoundException, PostpaidAccountNotFoundException, InvalidBillMonthException, BillDetailsNotFoundException, BillingServicesDownException {
		Bill expectedBillId= new Bill(1);
		Bill actualBillId= billingServices.getMobileBillDetails(96, "july");
		assertEquals(expectedBillId, actualBillId);
	}
	
	@Test(expected=PostpaidAccountNotFoundException.class)
	public void getCustomerPostPaidAccountAllBillDetailsForInvalidData() throws CustomerDetailsNotFoundException, PostpaidAccountNotFoundException, BillingServicesDownException, BillDetailsNotFoundException {
		billingServices.getCustomerPostPaidAccountAllBillDetails(96);
	}
	
	@Test
	public void getCustomerPostPaidAccountAllBillDetailsForValidData() throws CustomerDetailsNotFoundException, PostpaidAccountNotFoundException, BillingServicesDownException, BillDetailsNotFoundException {
		
		List<Bill> expectedBillList= new ArrayList<>(billingDaoServices.getCustomerPostPaidAccountAllBills(36));
		List<Bill> actualBillList= billingServices.getCustomerPostPaidAccountAllBillDetails(36);
		assertEquals(expectedBillList, actualBillList);
	}
	
	@Test(expected=CustomerDetailsNotFoundException.class)
	public void testChangePlanForInvalidCustomerId() throws CustomerDetailsNotFoundException, PostpaidAccountNotFoundException, PlanDetailsNotFoundException, BillingServicesDownException {
		billingServices.changePlan(7,33, 199);
	}
	
	@Test(expected=PostpaidAccountNotFoundException.class)
	public void testChangePlanForInvalidMobileNo() throws CustomerDetailsNotFoundException, PostpaidAccountNotFoundException, PlanDetailsNotFoundException, BillingServicesDownException {
		billingServices.changePlan(7,33, 199);
	}
	
	@Test(expected=PlanDetailsNotFoundException.class)
	public void testChangePlanForInvalidPlanId() throws CustomerDetailsNotFoundException, PostpaidAccountNotFoundException, PlanDetailsNotFoundException, BillingServicesDownException {
		billingServices.changePlan(7,33, 199);
	}
	
	@Test
	public void testChangePlanForValidData() throws CustomerDetailsNotFoundException, PostpaidAccountNotFoundException, PlanDetailsNotFoundException, BillingServicesDownException {
		Plan expectedPlanId= new Plan(199); 
		PostpaidAccount actualPlanId = billingServices.changePlan(1, 1, 199);
		assertEquals(expectedPlanId,actualPlanId);
	}
	
	@Test(expected=PostpaidAccountNotFoundException.class)
	public void closeCustomerPostPaidAccountForInvalidMobileNo() throws CustomerDetailsNotFoundException, PostpaidAccountNotFoundException, BillingServicesDownException {
		billingServices.closeCustomerPostPaidAccount(96);
	}
	
	@Test
	public void closeCustomerPostPaidAccountForValidMobileNo() throws CustomerDetailsNotFoundException, PostpaidAccountNotFoundException, BillingServicesDownException {
		boolean expectedResponse= true;
		boolean actualResponse = billingServices.closeCustomerPostPaidAccount(96);
		assertEquals(expectedResponse, actualResponse);
	}
	
	@Test(expected=CustomerDetailsNotFoundException.class)
	public void testDeleteCustomerForInvalidCustomerId() throws BillingServicesDownException, CustomerDetailsNotFoundException {
		billingServices.deleteCustomer(8);
	}
	
	@Test
	public void testDeleteCustomerForValidCustomerId() throws BillingServicesDownException, CustomerDetailsNotFoundException {
		boolean expectedResponse= true;
		boolean actualResponse = billingServices.deleteCustomer(9);
		assertEquals(expectedResponse, actualResponse);
	}
	
	@Test(expected=CustomerDetailsNotFoundException.class)
	public void getCustomerPostPaidAccountPlanDetailsForInvalidCustomerId() throws CustomerDetailsNotFoundException, PostpaidAccountNotFoundException, BillingServicesDownException {
		billingServices.getCustomerPostPaidAccountPlanDetails(89, 56);
	}
	
	@Test(expected=PostpaidAccountNotFoundException.class)
	public void getCustomerPostPaidAccountPlanDetailsForInvalidMobileNo() throws CustomerDetailsNotFoundException, PostpaidAccountNotFoundException, BillingServicesDownException {
		billingServices.getCustomerPostPaidAccountPlanDetails(89, 56);
	}
	
	@Test
	public void getCustomerPostPaidAccountPlanDetailsForValidData() throws CustomerDetailsNotFoundException, PostpaidAccountNotFoundException, BillingServicesDownException {
		Plan expectedPlanId= new Plan (199);
		PostpaidAccount actualPlanId= billingServices.getCustomerPostPaidAccountPlanDetails(9, 2);
		assertEquals(expectedPlanId,actualPlanId);
	}
	
	@Test(expected=PlanDetailsNotFoundException.class)
	public void testGetsPlanForInvalidData() {
		billingServices.getsPlan(78); 
	}
	
	@Test
	public void testGetsPlanForValidData() {
		StandardPlan expectedPlanId= new StandardPlan(199);
		StandardPlan actualPlanId= billingServices.getsPlan(199);
		assertEquals(expectedPlanId,actualPlanId);
	}
	
	
}


