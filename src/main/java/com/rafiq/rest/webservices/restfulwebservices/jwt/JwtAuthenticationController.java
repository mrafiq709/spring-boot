package com.rafiq.rest.webservices.restfulwebservices.jwt;


import java.time.Instant;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtAuthenticationController {

//    private final JwtTokenService tokenService;
    private JwtEncoder jwtEncoder;
    
//    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationController(JwtEncoder jwtEncoder) {
    	this.jwtEncoder = jwtEncoder;
//        this.tokenService = tokenService;
//        this.authenticationManager = authenticationManager;
    }
    
    @PostMapping("/authenticate")
    public JwtResponse authenticate (Authentication authentication) {
        
        return new JwtResponse(createToken(authentication));
    }

	private String createToken(Authentication authentication) {
        var claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(60 * 30))
                .subject(authentication.getName())
                .claim("scope", createScope(authentication))
                .build();

		return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
	}

	private String createScope(Authentication authentication) {
        var scope = authentication
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        return scope;
	}

//    @PostMapping("/authenticate")
//    public ResponseEntity<JwtTokenResponse> generateToken(
//            @RequestBody JwtTokenRequest jwtTokenRequest) {
//        
//        var authenticationToken = 
//                new UsernamePasswordAuthenticationToken(
//                        jwtTokenRequest.username(), 
//                        jwtTokenRequest.password());
//        
//        var authentication = 
//                authenticationManager.authenticate(authenticationToken);
//        
//        var token = tokenService.generateToken(authentication);
//        
//        return ResponseEntity.ok(new JwtTokenResponse(token));
//    }
}

record JwtResponse(String token) {};


