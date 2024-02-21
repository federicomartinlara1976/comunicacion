package net.bounceme.chronos.comunicacion.services;

public interface EmailService {
	
	Boolean sendSimpleMessage(String to, String subject, String text); 
}
