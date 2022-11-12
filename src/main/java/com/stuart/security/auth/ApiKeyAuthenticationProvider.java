package com.stuart.security.auth;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class ApiKeyAuthenticationProvider implements AuthenticationProvider {


    @Override
    public Authentication authenticate(Authentication preAuthorizedAuthentication) throws AuthenticationException {
        var principal = preAuthorizedAuthentication.getPrincipal();
        Client client = getMatchingClientByClientToken(principal.toString());
        System.out.println(client.getUser());
        return new UsernamePasswordAuthenticationToken(client.getUser(), client.getToken(), client.getRoles());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }

    private Client getMatchingClientByClientToken(String clientToken) {
        List<Client> enabledClients = getClients();
        return enabledClients.stream()
                .filter(enabledClient -> enabledClient.getToken().equals(clientToken))
                .findFirst()
                .orElseThrow(() -> new BadCredentialsException("Bad token"));
    }

    private List<Client> getClients() {
        List<Client> clients = new ArrayList<>();
        clients.add(new Client("user", "abcd", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))));
        clients.add(new Client("admin", "1234", Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"))));
        return clients;
    }
}
