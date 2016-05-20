package com.knj.cocktail.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.knj.cocktail.domain.Member;
import com.knj.cocktail.service.MemberService;

@Controller
public class MemberController {


	private MemberService memberService;

	@Autowired
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}
	
	@RequestMapping("home")
	public String showHome(Model model) {
		return "home";
	}
	@RequestMapping("logout")
	public ModelAndView doLogout(Model model,HttpSession session) {
		ModelAndView mv = new ModelAndView("redirect:/login");
		session.removeAttribute("userLoginInfo");
		return mv;
	}
	@RequestMapping("login")
	public String showLogin(Model model) {
		return "login";
	}
	
	@RequestMapping("loginProcess")
	public ModelAndView loginProcess(Member member, HttpSession session, HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:/login");
 
       Member loginMember = memberService.isMemberOk(member.getMemberId(), member.getPassword());
 
        if (loginMember != null) {
            session.setAttribute("userLoginInfo", loginMember);
            session.setAttribute("memberId", loginMember.getMemberId());
        }
        return mv;
    } 
	
	
	
}
