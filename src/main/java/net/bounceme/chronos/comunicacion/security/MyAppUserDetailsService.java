package net.bounceme.chronos.comunicacion.security;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import net.bounceme.chronos.comunicacion.data.dao.DaoQueries;
import net.bounceme.chronos.comunicacion.model.UserInfo;

@Service
public class MyAppUserDetailsService implements UserDetailsService {

	@Autowired
	@Qualifier(DaoQueries.NAME)
	private DaoQueries daoQueries;

	@Override
	public UserDetails loadUserByUsername(String userName) {
		UserInfo activeUserInfo = getActiveUser(userName);

		if (activeUserInfo != null) {
			GrantedAuthority authority = new SimpleGrantedAuthority(activeUserInfo.getRole());
			return new User(activeUserInfo.getUserName(), activeUserInfo.getPassword(), Arrays.asList(authority));
		}
		else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	private UserInfo getActiveUser(String userName) {
		Short enabled = 1;

		Map<String, Object> parameters = new HashMap<>();
		parameters.put("username", userName);
		parameters.put("enabled", enabled);

		Optional<UserInfo> oUser = daoQueries.executeScalarNamedQuery("usuarioActivo", parameters, Boolean.TRUE);

		return oUser.isPresent() ? oUser.get() : null;
	}
}
