package com.luv2code.springdemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.luv2code.springdemo.entity.Customer;
import com.luv2code.springdemo.service.CustomerService;

@Controller
@RequestMapping("/customer")
public class CustomerController {

	// inject Service into this controller. Spring will look for a class that
	// implements CustomerService and inject that
	@Autowired
	private CustomerService customerService;

	@GetMapping("/list")
	public String listCustomers(Model theModel) {

		// get the customers from dao
		List<Customer> theCustomers = customerService.getCustomers();

		// add the customers to model
		theModel.addAttribute("customers", theCustomers);
		return "list-customers";
	}

	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {

		// Since we want to bind an object to this form, create an empty object
		Customer theCustomer = new Customer();

		theModel.addAttribute("customer", theCustomer);

		return "customer-form";
	}

	@PostMapping("/saveCustomer")
	public String saveCustomer(@ModelAttribute("customer") Customer theCustomer) {

		// save customer using our service
		customerService.saveCustomer(theCustomer);
		// to redirect to another controller method
		return "redirect:/customer/list";
	}

	@GetMapping("/showFormForUpdate")
	// Request param is to get param out of url ?=, path variable is for like /{}
	public String showFormForUpdate(@RequestParam("customerID") int theId, Model theModel) {

		// get the customer from our service --> dao --> db
		Customer theCustomer = customerService.getCustomer(theId);

		// set the customer as model attribute to prepopulate the form
		theModel.addAttribute("customer", theCustomer);

		// send over to form. Same jsp used for add new customer, the attribute named
		// stayed the same, customer.
		// This will prepopuate with the customer returned from dao method for
		// getCustomer(theId) in the form
		return "customer-form";
	}
	
	@GetMapping("/delete")
	public String deleteCustomer(@RequestParam("customerID") int theId) {
		// delete the customer
		customerService.deleteCustomer(theId);
		
		return "redirect:/customer/list";
	}
}
