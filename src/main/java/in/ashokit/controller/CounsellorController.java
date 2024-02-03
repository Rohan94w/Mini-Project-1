package in.ashokit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import in.ashokit.binding.DashboardResponse;
import in.ashokit.entity.Counsellor;
import in.ashokit.service.CounsellorService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class CounsellorController {

	@Autowired
	private CounsellorService counsellorSvc;
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest req,Model model) { // display login page
		
		HttpSession session = req.getSession(false);
		session.invalidate();
		return "redirect:/";
	}
	
	@GetMapping("/")
	public String index(Model model) { // display login page
		model.addAttribute("counsellor", new Counsellor());
		return "loginView";
	}
	
	
	@PostMapping("/login")
	public String handleLogin(Counsellor c,HttpServletRequest req, Model model) {

		Counsellor obj = counsellorSvc.loginCheck(c.getEmail(), c.getPwd());
		
		if(obj==null) {
			model.addAttribute("errMsg","Invalid Credential");
			return "loginView";
		}
		
		HttpSession session = req.getSession(true);
		session.setAttribute("CID", obj.getCid());
		
		return "redirect:dashboard";
	}
	
	
	
	
	@GetMapping("/dashboard")
	public String buildDashboard(HttpServletRequest req, Model model) {

		HttpSession session = req.getSession(false);
		Object obj = session.getAttribute("CID");
		Integer cid = (Integer)obj;
		
		DashboardResponse dashboardInfo = counsellorSvc.getDashboardInfo(cid);
		model.addAttribute("dashboard", dashboardInfo);
		return "dashboardView";
	}
	
	
	

	@GetMapping("/register")
	public String regPage(Model model) { // display signup page
		model.addAttribute("counsellor", new Counsellor());
		return "registerView";
	}
	
	
	

	@PostMapping("/register")
	public String handleRegistration(Counsellor c, Model model) {

		String msg = counsellorSvc.saveCounsellor(c);
		model.addAttribute("msg", msg);
		return "redirect:/";	
	}

	@GetMapping("/forgot-pwd")
	public String recoverPwdPage(Model model) { // display fpwd page

		return "forgotPwdView";
	}
	
	
	
	
	@GetMapping("/recover-pwd")
	public String recoverPwd(String email,Model model) {
		
		boolean status = counsellorSvc.recoverPwd(email);
		
		if(status) {
			model.addAttribute("smgs", "Password Send To Your Email ID");
		}else {
			model.addAttribute("errmsg", "Please Enter Valid Email ID");
		}
		return "forgotPwdView";
	}

}
