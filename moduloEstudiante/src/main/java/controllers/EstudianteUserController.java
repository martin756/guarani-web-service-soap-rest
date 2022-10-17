package controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import services.implementation.IEstudianteUserService;
import services.EstudianteUserService;
import entities.EstudianteUser;

@RequestMapping("/estudiante")
@Controller
public class EstudianteUserController {
	@Autowired
	public EstudianteUserService estudianteUserService;
	
	@GetMapping("/lista")
	public ResponseEntity<?> traerEstudiante(){
		List<EstudianteUser> lEstudiante= estudianteUserService.listar();
		return new ResponseEntity<List<EstudianteUser>>(lEstudiante,HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> traerDueñoId(@PathVariable(name="id") int id){		
		java.util.Optional<EstudianteUser> estudianteUserBuscado = estudianteUserService.traerId(id);
		if(estudianteUserBuscado.isEmpty()) {
			return new ResponseEntity<String>("El estudiante buscado no existe", HttpStatus.NOT_FOUND);
		} else return new ResponseEntity<EstudianteUser>(estudianteUserBuscado.get(), HttpStatus.FOUND);
	}
	
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<?> eliminarUsuario(@PathVariable("id")int id) throws Exception{
			estudianteUserService.delete(id);
			return new ResponseEntity<String>("El usuario con id="+id+" fue eliminado", HttpStatus.OK);
	}
	
	@PutMapping("/actualizar/{id}")
	public ResponseEntity<?> actualizarUsuario(@RequestBody EstudianteUser estudianteUserModificado, @PathVariable(name = "id") int id) throws Exception{
	
		java.util.Optional<EstudianteUser> eu =estudianteUserService.traerId(id);
		if(eu.isEmpty()) {
			return new ResponseEntity<String>("Este usuario no existe", HttpStatus.BAD_REQUEST);
		}else {
			estudianteUserModificado.setId(id);
			return new ResponseEntity<EstudianteUser>(estudianteUserService.saveOrUpdate(estudianteUserModificado), HttpStatus.OK);
		}
	}
	
	@PostMapping("/agregar")
	public ResponseEntity<?> nuevoEstudianteUser(@RequestBody EstudianteUser estudianteUserNuevo) throws Exception{
		java.util.Optional<EstudianteUser> d = estudianteUserService.listarDNI(estudianteUserNuevo.getDni());
		if(d.isEmpty()) {
			return new ResponseEntity<EstudianteUser>(estudianteUserService.saveOrUpdate(estudianteUserNuevo), HttpStatus.CREATED);
		}else {
			
			return new ResponseEntity<String>("El estudiante ingresado ya exite", HttpStatus.OK);
		}
		
		
	}



	/************************************************ */

	@PostMapping("/save")
	public String guardar(@Validated @ModelAttribute("Estudiante") EstudianteUser eu, Model model) {
		
		try {
			System.out.println(eu);
			estudianteUserService.saveOrUpdate(eu);
		} catch (Exception e) {
			model.addAttribute("errorMsg",e.getMessage());
			return "conductor/agregarConductor";
		}
		return "redirect:/";
	}
	

	
	@GetMapping("listaConductores")
	public String listarConductores(Model model) {
		model.addAttribute("duenios",estudianteUserService.listar());
		
		return "conductor/traerConductores";
	}
	
	

	
	
	


}
