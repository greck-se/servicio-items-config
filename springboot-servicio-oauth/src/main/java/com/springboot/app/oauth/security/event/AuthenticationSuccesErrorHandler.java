package com.springboot.app.oauth.security.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.springboot.app.commons.usuarios.models.entity.Usuario;
import com.springboot.app.oauth.service.IUsuarioService;

import feign.FeignException;

@Component
public class AuthenticationSuccesErrorHandler implements AuthenticationEventPublisher {

	private Logger log = LoggerFactory.getLogger(AuthenticationSuccesErrorHandler.class);

	@Autowired
	private IUsuarioService usuarioServ;

	@Override
	public void publishAuthenticationSuccess(Authentication authentication) {
		UserDetails user = (UserDetails) authentication.getPrincipal();
		log.info("Succes Login: {}", user.getUsername());
		
		Usuario usuario = usuarioServ.findByUsername(authentication.getName());
		if(usuario.getIntentos() != null && usuario.getIntentos() > 0) {
			usuario.setIntentos(0);
			usuarioServ.update(usuario, usuario.getId());
		}
				
	}

	@Override
	public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
		log.info("Error Login: {}", exception.getMessage());
		try {
			Usuario usuario = usuarioServ.findByUsername(authentication.getName());
			if (usuario.getIntentos() == null) {
				usuario.setIntentos(0);
			}
			log.info("Intentos actual es de: {}", usuario.getIntentos());
			usuario.setIntentos(usuario.getIntentos() + 1);
			log.info("Intentos despues es de: {}", usuario.getIntentos());
			if(usuario.getIntentos() >= 3) {
				log.error("El usuario {} deshabilitado por maximos intentos", usuario.getUsername());
				usuario.setEnabled(false);
			}
			
			usuarioServ.update(usuario, usuario.getId());
		} catch (FeignException fe) {
			log.error("El usuario {} no existe en el sistema", authentication.getName());
		}

	}

}