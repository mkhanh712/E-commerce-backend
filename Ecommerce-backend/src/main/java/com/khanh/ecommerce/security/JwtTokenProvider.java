package com.khanh.ecommerce.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {
	private final String secretRaw;
	private SecretKey jwtSecretKey;
	private final long jwtExpirationMs;
	
	public JwtTokenProvider(
			@Value("${jwt.secret}") String secretRaw,						
			@Value("${jwt.expiration}") long jwtExpirationMs
	){
		this.secretRaw = secretRaw;
		this.jwtExpirationMs = jwtExpirationMs;
	}
	
	@PostConstruct
	private void init() {
		try {
			byte[] decoded = Decoders.BASE64.decode(secretRaw);
			this.jwtSecretKey = Keys.hmacShaKeyFor(decoded);
		}catch(Exception ex) {
			this.jwtSecretKey = Keys.hmacShaKeyFor(secretRaw.getBytes());
		}
	}
	
	public String generateToken(String username, List<String> roles) {
		Date now = new Date();
		Date expiry = new Date(now.getTime() + jwtExpirationMs);
		
		return Jwts.builder()
				.subject(username)
				.claim("roles", roles)
				.issuedAt(now)
				.expiration(expiry)
				.signWith(jwtSecretKey)
				.compact();
	}
	
	public String getUsernameFromToken(String token) {
		return Jwts.parser()
                .verifyWith(jwtSecretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getRolesFromToken(String token) {
		return (List<String>)Jwts.parser()
				.verifyWith(jwtSecretKey)
				.build()
				.parseSignedClaims(token)
				.getPayload()
				.get("roles");
	}
	
	public boolean validateToken(String token) {
		try {
			Jwts.parser()
					.verifyWith(jwtSecretKey)
					.build()
					.parseSignedClaims(token);
			return true;
		}catch(Exception e) {
			return false;
		}
	}
}
