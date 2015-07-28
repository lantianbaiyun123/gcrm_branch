package com.baidu.gcrm.common.mail;

import java.util.Map;
import java.util.Set;

public interface IMailService {
	void sendMail(Set<String> tos, Set<String> cctos, String subject, String templateName, Map<String, Object> valueMap);

	void sendMail(String fromEmail, Set<String> tos, Set<String> cctos, String subject, String templateName,
			Map<String, Object> valueMap);

	void sendMail(MailTemplate template);
	void sendMailByWord(MailTemplate template);

}