package net.bounceme.chronos.comunicacion.services;

public interface EmailService {
	
	void sendSimpleMessage(String to, String subject, String text); 
}
