package com.femass.ds1.requerimentofemass.util;

import java.io.IOException;
import java.util.Properties;

import com.outjected.email.api.SessionConfig;
import com.outjected.email.impl.SimpleMailConfig;

public class SimpleMailTemplete {

	public SessionConfig enviarEmail() throws IOException{
		Properties props = new Properties();
		props.load(getClass().getResourceAsStream("/emails/configSimpleMail.properties"));

		SimpleMailConfig config = new SimpleMailConfig();
		config.setServerHost(props.getProperty("mail.smtp.host"));
		config.setServerPort(Integer.parseInt(props.getProperty("mail.smtp.port")));
		config.setEnableSsl(Boolean.parseBoolean(props.getProperty("mail.smtp.ssl")));
		config.setEnableTls(Boolean.parseBoolean(props.getProperty("mail.smtp.starttls.enable")));
		config.setAuth(Boolean.parseBoolean(props.getProperty("mail.smtp.auth")));
		config.setUsername(props.getProperty("mail.username"));
		config.setPassword(props.getProperty("mail.password"));

		// implementando as configurações
		SessionConfig configsession = config;
		return configsession;
		
		/*fazer esta parte no bean*/
		/*MailMessage message = new MailMessageImpl(configsession);

		message.to("alexmansan@gmail.com").subject("Pedido de Envio de senha.")
			.bodyHtml(new VelocityTemplate(getClass().getResourceAsStream("/emails/" + template)))
			.put("usuario", funcionarioCadastro)
			.put("locale", new Locale("pt", "BR"))
			.send();*/
	}
}
