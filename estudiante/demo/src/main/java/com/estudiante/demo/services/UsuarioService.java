package com.estudiante.demo.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.estudiante.demo.models.Usuario;
import com.estudiante.demo.repositories.UsuarioRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService{

    Logger logger = LoggerFactory.getLogger(UsuarioService.class);
    UsuarioRepository repository ;

    @Autowired
    UsuarioService(UsuarioRepository usuarioRepository){
        this.repository = usuarioRepository;
    }

    

    public List<Usuario> getAllUsuarios(){
       List<Usuario> result = new ArrayList<>();
       repository.findAll().forEach(result::add);
        for (Usuario u: result) {
            logger.info("Usuario "+ u.toString());
        }
       return result;
    }

    public int guardarUsuario(Usuario u) {
        return repository.save(u).id;
    }

    public Usuario getUsuario(int id){
        return repository.findById(id).get();
    }
    }

