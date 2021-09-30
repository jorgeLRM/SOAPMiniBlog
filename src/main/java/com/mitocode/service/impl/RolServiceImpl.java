package com.mitocode.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import com.mitocode.dao.IRolDAO;
import com.mitocode.model.Rol;
import com.mitocode.model.Usuario;
import com.mitocode.model.UsuarioRol;
import com.mitocode.service.IRolService;
import com.mitocode.service.IUsuarioService;

@WebService
public class RolServiceImpl implements IRolService, Serializable {

	@EJB
	private IRolDAO dao;
	
	@Resource
	private WebServiceContext context;
	
	@Inject
	private IUsuarioService service;
	
	public boolean isAutenticar() {
		MessageContext message = context.getMessageContext();
		Map requestHeader = (Map) message.get(MessageContext.HTTP_REQUEST_HEADERS);
		List lstUsuarios = (List) requestHeader.get("usuario");
		List lstClaves = (List) requestHeader.get("clave");
		
		String usuario = "";
		String clave = "";
		
		if(lstUsuarios != null && lstClaves != null) {
			usuario = String.valueOf(lstUsuarios.get(0));
			clave = String.valueOf(lstClaves.get(0));
		}
		
		Usuario us = new Usuario();
		us.setUsuario(usuario);
		us.setContrasena(clave);
		
		us = service.login(us);
		
		return us != null && us.getId() > 0;

	}

	@WebMethod
	@Override
	public int registrar(Rol per) throws Exception {
		int rpta = dao.registrar(per);
		return rpta > 0 ? 1 : 0;
	}

	@WebMethod
	@Override
	public int modificar(Rol per) throws Exception {
		int rpta = dao.modificar(per);
		return rpta > 0 ? 1 : 0;
	}

	@WebMethod
	@Override
	public List<Rol> listar() throws Exception {
		//if(isAutenticar()) {
			return dao.listar();	
		//}
		//return new ArrayList<Rol>();
	}

	@WebMethod
	@Override
	public Rol listarPorId(Rol t) throws Exception {
		return dao.listarPorId(t);
	}

	@WebMethod(operationName = "op_asignar")
	@Override
	public Integer asignar(@WebParam(name = "obj_usuario") Usuario usuario, @WebParam(name = "rol") List<Rol> roles) {
		List<UsuarioRol> usuario_roles = new ArrayList<UsuarioRol>();
		try {
			roles.forEach(r -> {
				UsuarioRol ur = new UsuarioRol();
				ur.setUsuario(usuario);
				ur.setRol(r);
				usuario_roles.add(ur);
			});

			dao.asignar(usuario, usuario_roles);
		} catch (Exception e) {
			return 0;
		}

		return 1;
	}

	@WebMethod
	@Override
	public List<UsuarioRol> listarRolesPorUsuario(Usuario usuario) {
		return dao.listarRolesPorUsuario(usuario);
	}

}
