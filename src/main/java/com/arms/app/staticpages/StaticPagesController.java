package com.arms.app.staticpages;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class StaticPagesController {
	
	@RequestMapping("")
	public String home(){
		return "StaticPages/home";
	}
	
	@RequestMapping("help")
	public String help(){
		return "StaticPages/help";
	}
	
	@RequestMapping("about")
	public String about(){
		return "StaticPages/about";
	}
	
	@RequestMapping("contact")
	public String contact(){
		return "StaticPages/contact";
	}
	
	//Ç±Ç±Ç©ÇÁâ∫ÇÕÉ_É~Å[
	@RequestMapping("list")
	public String list(){
		return "Users/list";
	}
	
	@RequestMapping("show_follow")
	public String showFollow(){
		return "Users/show_follow";
	}
	
	@RequestMapping("show")
	public String show(){
		return "Users/show";
	}
	
	@RequestMapping("add")
	public String add(){
		return "Register/add";
	}
	
	@RequestMapping("login")
	public String login(){
		return "Secure/login";
	}
	
	@RequestMapping("edit")
	public String edit(){
		return "Users/edit";
	}
	
	@RequestMapping("add2")
	public String add2(){
		return "Users/add";
	}
	

}
