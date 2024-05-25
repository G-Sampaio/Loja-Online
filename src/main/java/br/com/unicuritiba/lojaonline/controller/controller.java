package br.com.unicuritiba.lojaonline.controller;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.unicuritiba.lojaonline.models.users;
import br.com.unicuritiba.lojaonline.repositories.usersRepositories;

@Controller
public class controller {
	@Autowired
	private usersRepositories usersRepositories;
	
	@GetMapping("/administrativo")
	public String acessarController() {
		return"administrativo/home";
	}
	
	@GetMapping("/cadastro")
	public ModelAndView acessarCadastro(users users) {
		ModelAndView mv = new ModelAndView("administrativo/usuarios/cadastro");
		mv.addObject("users",users);	
		return mv;
	}
	
	@GetMapping("/lista")
	public ModelAndView acessarLista() {
		ModelAndView mv = new ModelAndView("administrativo/usuarios/lista");
		mv.addObject("listaUsers", usersRepositories.findAll());
		return mv;
	}
	
	@GetMapping("/lista/editar/{id}")
	public ModelAndView editarUser(@PathVariable("id")Long id) {
		Optional<users> user = usersRepositories.findById(id);
		return acessarCadastro(user.get());
	}
	
	@GetMapping("/lista/remover/{id}")
	public ModelAndView removerUser(@PathVariable("id")Long id) {
		Optional<users> user = usersRepositories.findById(id);
		usersRepositories.delete(user.get());
		return acessarLista();
	
	}
	@PostMapping("/administrativo/usuarios/salvar")
	public ModelAndView salvar(users users, BindingResult result) {
		if(result.hasErrors()) {
			return acessarCadastro(users);
		}
		usersRepositories.saveAndFlush(users);
		return acessarCadastro(new users());
		
		
	}
}
