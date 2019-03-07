package fi.haagahelia.Bookstore.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fi.haagahelia.Bookstore.domain.SignupForm;
import fi.haagahelia.Bookstore.domain.User;
import fi.haagahelia.Bookstore.domain.UserRepository;

@Controller
public class UserController {
		@Autowired
	private UserRepository repository;
		
	@RequestMapping(value = "signup")
	public String addBook(Model model) {
		model.addAttribute("signupform", new SignupForm());
		return "signup";
	}
	
			
	@RequestMapping(value = "saveuser", method = RequestMethod.POST)
	public String save(@Valid @ModelAttribute("signupform") SignupForm signupForm, BindingResult bindingResult) {
		if (!bindingResult.hasErrors()) {
			if (signupForm.getPassword().equals(signupForm.getPasswordCheck())) {
				String pwd = signupForm.getPassword();
				BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
				String hashPwd = bc.encode(pwd);
				
				User newUser = new User();
				newUser.setPasswordHash(hashPwd);
				newUser.setUsername(signupForm.getUsername());
				newUser.setRole("USER");
				if (repository.findByUsername(signupForm.getUsername()) == null) {
					repository.save(newUser);
				}
				else {
					bindingResult.rejectValue("username", "err.username", "The username is already in use");
					return "signup";
				}
			}
			else {
				bindingResult.rejectValue("passwordCheck", "err.passCheck", "The passwords do not match");
				return "signup";
			}
		}
		else {
			return "signup";
		}
		return "redirect:/login";
	}
}
