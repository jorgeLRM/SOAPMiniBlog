package com.mitocode.dao;

import java.util.List;

import javax.ejb.Local;

import com.mitocode.model.Rol;
import com.mitocode.model.Usuario;
import com.mitocode.model.UsuarioRol;

@Local
public interface IRolDAO extends IDAO<Rol> {

	Integer asignar(Usuario us, List<UsuarioRol> roles);
	List<UsuarioRol> listarRolesPorUsuario(Usuario usuario);
}
