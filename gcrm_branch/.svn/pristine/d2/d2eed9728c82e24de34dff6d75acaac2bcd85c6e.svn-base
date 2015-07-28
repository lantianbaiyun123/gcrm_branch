package com.baidu.gcrm.common.mail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.baidu.gcrm.common.DateUtils;
import com.baidu.gcrm.common.GcrmConfig;
import com.baidu.gcrm.common.log.LoggerHelper;
import com.baidu.gcrm.common.util.MailUtils;
import com.baidu.gcrm.common.velocity.VelocityUtil;

@Service
public class MailService implements IMailService {

	public static final String LOG_NAME = "mailServiceLog";

	private JavaMailSender mailSender;

	/**
	 * Creates a new MailService object.
	 */
	public MailService() {
	}

	@Override
	public void sendMail(Set<String> tos, Set<String> cctos, String subject, String templateName,
			Map<String, Object> valueMap) {
		String fromEmail =GcrmConfig.getMailFromUser();
		sendMail(fromEmail, tos, cctos, subject, templateName, valueMap);
	}

	@Override
	public void sendMail(String fromEmail, Set<String> tos, Set<String> cctos, String subject, String templateName,
			Map<String, Object> valueMap) {
		sendMail(fromEmail, tos, cctos, subject, templateName, valueMap, false);
	}

	private void sendMail(String fromEmail, Set<String> tos, Set<String> cctos, String subject, String templateName,
			Map<String, Object> valueMap, boolean isAsyn) {
		String emailBody = VelocityUtil.merge(templateName, valueMap);
		MailTemplate template = new MailTemplate();
		template.setFrom(fromEmail);
		template.setBody(emailBody);
		template.setTo(tos);
		template.setSubject(subject);
		template.setCc(cctos);
		if (isAsyn) {
		} else {
			sendMail(template);
		}
	}

	@Override
	public void sendMail(MailTemplate template) {
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, template.getAttachments() != null, "UTF-8");
			Set<String> mailto = template.getTo();
			Set<String> ccto = template.getCc();
			Set<String> bbto = template.getBcc();

			testModeCleanMail(template, mailto, ccto, bbto);

			boolean isExistTo = false;
			boolean isExistCc = false;
			boolean isExistBcc = false;

			if (mailto != null) {
				for (String to : mailto) {
					if (StringUtils.isNotEmpty(to) && isValidAddress(to)) {
						isExistTo = true;
						messageHelper.addTo(to);
					}
				}
			}

			if (ccto != null) {
				for (String cc : ccto) {
					if (StringUtils.isNotEmpty(cc) && isValidAddress(cc)) {
						isExistCc = true;
						messageHelper.addCc(cc);
					}
				}
			}

			if (bbto != null) {
				for (String bb : bbto) {
					if (StringUtils.isNotEmpty(bb) && isValidAddress(bb)) {
						isExistBcc = true;
						messageHelper.addBcc(bb);
					}
				}
			}

			if (!isExistTo && !isExistCc && !isExistBcc) {
				// 记录日志
				writeLog(template, "因无接受人邮件不发送！");

				return;
			}

			messageHelper.setSubject(template.getSubject());
			messageHelper.setText(template.getBody(), true);
			if (StringUtils.isBlank(template.getFrom())) {
				template.setFrom(GcrmConfig.getMailFromUser());
			}
			messageHelper.setFrom(template.getFrom(), template.getFromName());

			// Set<String> attachments = template.getAttachments();

						/*
						 * if (attachments != null) {
						 * for (String attachPath : attachments) {
						 * FileSystemResource file = new FileSystemResource(attachPath);
						 * messageHelper.addAttachment(file.getFilename(), file);
						 * }
						 * }
						 */
			 
			mailSender.send(message);

			// 记录日志
			writeLog(template, "发送成功");
		} catch (Exception ex) {
			LoggerHelper.err(MailService.class, ex.getMessage(), ex);
		}
	}

	/**
	 * 记录日志
	 * 
	 * @param template
	 * @param other DOCUMENT ME!
	 */
	private void writeLog(MailTemplate template, String other, String... rejects) {
		String timeStr = "时间：" + DateUtils.getDate2String(new Date()) + "\n";
		other += "\n";
		Set<String> mailSet = new HashSet<String>();
		StringBuilder mailTo = new StringBuilder();

		if ((template.getTo() != null) && !template.getTo().isEmpty()) {
			mailSet.addAll(template.getTo());
			mailTo.append("收件人:");
			for (String to : template.getTo()) {
				mailTo.append(to).append(",");
			}

			mailTo.append("\n");
		}

		if ((template.getCc() != null) && !template.getCc().isEmpty()) {
			mailSet.addAll(template.getCc());
			mailTo.append("抄送人:");
			for (String cc : template.getCc()) {
				mailTo.append(cc).append(",");
			}

			mailTo.append("\n");
		}

		if ((template.getBcc() != null) && !template.getBcc().isEmpty()) {
			mailSet.addAll(template.getBcc());
			mailTo.append("密送人:");
			for (String bcc : template.getBcc()) {
				mailTo.append(bcc).append(",");
			}

			mailTo.append("\n");
		}

		if ((rejects != null) && (rejects.length > 0)) {
			mailTo.append("因个人设置而没有发送:");

			for (String bcc : rejects) {
				mailTo.append(bcc).append(",");
			}

			mailTo.append("\n");
		}

		String from = "发件人:" + template.getFrom() + "\n";

		String subject = "主题:" + template.getSubject() + "\n";
		String text = "内容:" + template.getBody() + "\n\n";
		Set<String> attachments = template.getAttachments();
		String attach = "";
		if (attachments != null) {
			attach = "附件名称:" + attachments.toString() + "\n\n";
		}
		LoggerHelper.info(LOG_NAME, timeStr + other + mailTo.toString() + from + subject + text + attach);

	}

	/**
	 * 是否为合法的地址
	 * 
	 * @param address
	 * @return
	 */
	private boolean isValidAddress(String address) {
		InternetAddress[] arrayOfInternetAddress = null;

		try {
			arrayOfInternetAddress = InternetAddress.parse(address);
		} catch (AddressException e) {
			// ignore
			return false;
		}

		if ((arrayOfInternetAddress == null) || (arrayOfInternetAddress.length != 1)) {
			return false;
		}

		return true;
	}

	/**
	 * 测试模式下.过滤收件人.从配置文件中得到.配置文件位置WebContent/config/mail_test_setup.properties
	 * 
	 * @param template
	 * @param mailto
	 * @param ccto
	 * @param bbto
	 */
	private void testModeCleanMail(MailTemplate template, Set<String> mailto, Set<String> ccto, Set<String> bbto) {
		String workMode = GcrmConfig.getConfigValueByKey("work.mode");
		if (!"release".equals(workMode)) {
			List<String> testMailList = new ArrayList<String>();
			StringBuilder testMailHint = new StringBuilder();
			if (mailto != null) {
				testMailHint.append("<br><br><b>测试模式,邮件过滤</b><br>原收件人:");
				for (String mail : mailto) {
					testMailHint.append(mail).append("&nbsp;");
				}
				mailto.clear();
				// 添加系统管理员
				mailto.addAll(MailUtils.getEmailListFromStr(GcrmConfig.getConfigValueByKey("gcrm.test.mode.mail")));
			}

			if (ccto != null) {
				testMailHint.append("<br>原抄送人:");

				for (String mail : ccto) {
					testMailHint.append(mail).append("&nbsp;");
				}
				ccto.retainAll(testMailList);
			}

			if (bbto != null) {
				testMailHint.append("<br>原密送人:");

				for (String mail : bbto) {
					testMailHint.append(mail).append("&nbsp;");
				}

				bbto.retainAll(testMailList);
			}

			template.setBody(template.getBody() + testMailHint);

			String testSubjectHint = "GCRM测试环境，";
			template.setSubject(testSubjectHint + template.getSubject());
		}
	}

	public JavaMailSender getMailSender() {
		return mailSender;
	}

	@Autowired
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	@Override
	public void sendMailByWord(MailTemplate template) {
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
			Set<String> mailto = template.getTo();
			Set<String> ccto = template.getCc();
			Set<String> bbto = template.getBcc();

			testModeCleanMail(template, mailto, ccto, bbto);

			boolean isExistTo = false;
			boolean isExistCc = false;
			boolean isExistBcc = false;

			if (mailto != null) {
				for (String to : mailto) {
					if (StringUtils.isNotEmpty(to) && isValidAddress(to)) {
						isExistTo = true;
						messageHelper.addTo(to);
					}
				}
			}

			if (ccto != null) {
				for (String cc : ccto) {
					if (StringUtils.isNotEmpty(cc) && isValidAddress(cc)) {
						isExistCc = true;
						messageHelper.addCc(cc);
					}
				}
			}

			if (bbto != null) {
				for (String bb : bbto) {
					if (StringUtils.isNotEmpty(bb) && isValidAddress(bb)) {
						isExistBcc = true;
						messageHelper.addBcc(bb);
					}
				}
			}

			if (!isExistTo && !isExistCc && !isExistBcc) {
				// 记录日志
				writeLog(template, "因无接受人邮件不发送！");

				return;
			}

			messageHelper.setSubject(template.getSubject());
			messageHelper.setText(template.getBody(), true);
			if (StringUtils.isBlank(template.getFrom())) {
				template.setFrom(GcrmConfig.getMailFromUser());
			}
			messageHelper.setFrom(template.getFrom(), template.getFromName());			
			 if (template.getInputStream() != null) {			
				 String wordName = template.getWordattachment();
				 ByteArrayDataSource ba = new ByteArrayDataSource(template.getInputStream(),"'application/msword'");
				 messageHelper.addAttachment(wordName,ba);
			  
			  }
			 
			mailSender.send(message);
		
			// 记录日志
			writeLog(template, "发送成功");
		} catch (Exception ex) {
			LoggerHelper.err(MailService.class, ex.getMessage(), ex);
		}finally{
			try{
				if(template.getInputStream()!=null){
				   template.getInputStream().close();
					}
				} catch (IOException e) {
					LoggerHelper.err(MailService.class, "文件流关闭失败"+e.getMessage(), e);
				}
			}
		
	}
}
