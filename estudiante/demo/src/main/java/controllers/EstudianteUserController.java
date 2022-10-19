package controllers;

import java.util.List;
import java.util.Optional;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import services.EstudianteUserService;
import entities.EstudianteUser;
import repositories.EstudianteUserRepository;

@RequestMapping("/estudiante")
@Controller
public class EstudianteUserController {
	
	
	
	@Autowired
	@Qualifier("estudianteUserService")
	public EstudianteUserService estudianteUserService;
	
	
	
	@PostMapping("/{id}")
	    ResponseEntity<EstudianteUser> actualizarUsuario( @PathVariable  Integer id, @RequestParam  String direccion, @RequestParam String email, @RequestParam String password){
	        return new ResponseEntity<>(estudianteUserService.saveOrUpdate(id, direccion, email, password), HttpStatus.ACCEPTED);
	    }
	 
	 @GetMapping("/estudiante/{id}")
	    ResponseEntity<EstudianteUser> findById(@PathVariable("id") Integer id){
	        return new ResponseEntity<>(estudianteUserService.findById(id), HttpStatus.OK);
	    }
	
	
	

	
	
	
	}
	
	
	



