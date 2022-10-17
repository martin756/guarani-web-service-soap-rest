package controllers;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import services.CarreraService;



@Controller
@RequestMapping("/carrera")
public class CarreraController {

	@Autowired
	@Qualifier("carreraService")
	private CarreraService carreraService;
	
	
	@GetMapping("/")
	public ModelAndView index() {
		ModelAndView mv= new ModelAndView("carrera/index");
		mv.addObject(carreraService.traerDatos("Licenciatura en Sistemas"));
		return mv;
	}
	
	
}
