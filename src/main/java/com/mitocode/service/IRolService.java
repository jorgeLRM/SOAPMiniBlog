package com.mitocode.service;

import java.util.List;

import com.mitocode.model.Rol;
import com.mitocode.model.Usuario;
import com.mitocode.model.UsuarioRol;

public interface IRolService extends IService<Rol> {

	Integer asignar(Usuario usuario, List<Rol> roles);
	List<UsuarioRol> listarRolesPorUsuario(Usuario usuario);
}
