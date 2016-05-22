package com.knj.cocktail.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.knj.cocktail.domain.Admin;
import com.knj.cocktail.service.AdminService;


@Controller
public class AdminController {

	private AdminService adminService;
	
	@Autowired
	public void setAdminService(AdminService adminService){
	this.adminService = adminService;
	}
	
	@RequestMapping("login")
	public String showLogin(Model model) {
		return "login";
	}
	
	@RequestMapping("logout")
	public ModelAndView doLogout(Model model,HttpSession session) {
		ModelAndView mv = new ModelAndView("redirect:/login");
		session.removeAttribute("adminLoginInfo");
		return mv;
	}
	
	@RequestMapping("loginProcess")
	public ModelAndView doLoginProcess(HttpSession session, HttpServletRequest request){
        ModelAndView mv = new ModelAndView();
        
        String adminId = request.getParameter("adminId");
		String password = request.getParameter("password");
 
       Admin loginAdmin = adminService.isAdminOk(adminId, password);
		
        if (loginAdmin != null) {
        	session.setAttribute("adminLoginInfo", loginAdmin);
            session.setAttribute("adminId", loginAdmin.getAdminId());
            mv.setViewName("home");
        }
        else {
        	mv.setViewName("failLogin");
        }
        
        return mv;
    } 
	
	
	@RequestMapping("failLogin")
	public String showFailLogin(){
		
		return "failLogin";
	}
	
	@RequestMapping("home")
	public String showHome(){
		
		return "home";
	}
	
	@RequestMapping("/test")
	public void showtest(HttpServletRequest request){ 
		
		System.out.println(request.getParameter("test"));
		
		
		
		
	}
}
