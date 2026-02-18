package com.devsxplore.thesis.accounts.adapter.in.security;

import java.util.HashSet;
import java.util.Map;
import com.devsxplore.thesis.accounts.application.port.in.command.RegisterAccountCommand;
import com.devsxplore.thesis.accounts.application.port.in.usecase.RegisterOrUpdateAccountUseCase;
import com.devsxplore.thesis.accounts.domain.model.UserAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

	private final DefaultOAuth2UserService defaultService = new DefaultOAuth2UserService();

	private final RegisterOrUpdateAccountUseCase registerOrUpdateAccountUseCase;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = this.defaultService.loadUser(userRequest);

		var attributes = oAuth2User.getAttributes();
		var authorities = new HashSet<GrantedAuthority>();

		String githubLogin = readString(attributes.get("login"));
		String githubName = readString(attributes.get("name"));
		Long githubId = readGithubId(attributes.get("id"));

		UserAccount account = this.registerOrUpdateAccountUseCase
			.registerOrUpdate(new RegisterAccountCommand(githubId, githubLogin, githubName));

		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

		if (account.getRole() != null) {
			authorities.add(new SimpleGrantedAuthority("ROLE_" + account.getRoleAsString()));
		}
		Map<String, Object> cleanAttributes = Map.of("id", githubId, "login", githubLogin, "name", githubName);

		return new CurrentUser(githubId, githubLogin, githubName, cleanAttributes, authorities);
	}

	private Long readGithubId(Object id) {
		if (id instanceof Number number) {
			return number.longValue();
		}
		throw new IllegalArgumentException("GitHub id is missing");
	}

	private String readString(Object value) {
		return value == null ? null : String.valueOf(value);
	}

}
