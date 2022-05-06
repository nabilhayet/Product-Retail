package com.cognixia.jump.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtil {

	private final String SECRET_KEY = "jump";
	
	//extract username from token
	public String extractUsername(String token) {
		
		return extractClaim(token, Claims::getSubject);
	}
	
	public Date extractExpiration(String token) {
		
		return extractClaim(token, Claims::getExpiration);
	}
	
	// takes a token and a claims resolver to find out what the claims are for that particular token
	// so find that data that was passed in through the token and be able to access it again (username, expiration date)
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
		
	}

	private Claims extractAllClaims(String token) {
		
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}
	
	private Boolean isTokenExpired(String token) {
		
		return extractExpiration(token).before(new Date());
		
	}
	
	//return created token, after successful authentication
	public String generateTokens(UserDetails userDetails) {
		
		Map<String, Object> claims = new HashMap<>();
		
		return createToken(claims, userDetails.getUsername());
		
	}
	
	//creates token
	private String createToken(Map<String, Object> claims, String subject) {
		
		//set the claims (roles, privileges, etc.)
		//subject (principle) being authenticated
		//set when the token is issued
		//set expiration
		//sign it with algorithm and secret key to make it authentic
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(subject)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 *10))
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY)
				.compact();
	}
	
	public Boolean validateToken(String token, UserDetails userDetails) {
		
		final String username = extractUsername(token);
		
		return (username.equals(userDetails.getUsername())  && !isTokenExpired(token));
		
	}
}
