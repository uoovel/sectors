package com.codeurmas.sectors.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.codeurmas.sectors.model.Person;
import com.codeurmas.sectors.service.PersonService;

@Controller
public class FrontPageController {
	@Autowired
	private PersonService personService;

	@RequestMapping("/")
	public String frontPage(Model model) {
		System.out.println("FrontPageController100:");
		Person person = new Person();
		model.addAttribute("person", person);
		return "index.html";
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@ModelAttribute("person") Person person) {
		
		System.out.println("FrontPageController200:" + person.getName());
		personService.save(person);
		
		return "redirect:/";
		
	}
	
}
