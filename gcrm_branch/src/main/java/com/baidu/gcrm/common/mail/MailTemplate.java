package com.baidu.gcrm.common.mail;

import java.io.ByteArrayInputStream;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;


public class MailTemplate {
    private String subject; //主题
    private String body; //邮件正文
    private Set<String> to; //收件人
    private Set<String> cc; //抄送
    private Set<String> bcc; //密送
    private Set<String> attachments; //附件路径
    private String from; //发送人邮件	
    private String fromName; //发送人姓名
    private Integer templateType; //消息类型
    private String  wordattachment;//word附件名称
    private ByteArrayInputStream inputStream;//文件流
	/**
     * Creates a new MailTemplate object.
     */
    public MailTemplate() {}
    
	public MailTemplate(String subject, String body, Set<String> to,
			Set<String> cc, String from) {
		this.subject = subject;
		this.body = body;
		this.to = to;
		this.cc = cc;
		this.from = from;
	}





	/**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getFromName() {
        return fromName;
    }

    /**
     * DOCUMENT ME!
     *
     * @param fromName DOCUMENT ME!
     */
    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getBody() {
        return body;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getSubject() {
        return subject;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Set<String> getTo() {
        return to;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Set<String> getAttachments() {
        return attachments;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Set<String> getCc() {
        return cc;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getFrom() {
        return from;
    }

    /**
     * DOCUMENT ME!
     *
     * @param body DOCUMENT ME!
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * DOCUMENT ME!
     *
     * @param subject DOCUMENT ME!
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * DOCUMENT ME!
     *
     * @param attachments DOCUMENT ME!
     */
    public void setAttachments(Set<String> attachments) {
        this.attachments = attachments;
    }

    /**
     * DOCUMENT ME!
     *
     * @param from DOCUMENT ME!
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * DOCUMENT ME!
     *
     * @param attachmentfilePath DOCUMENT ME!
     */
    public void addAttachment(String attachmentfilePath) {
        if (this.getAttachments() == null) {
            this.attachments = new HashSet<String>();
        }

        this.attachments.add(attachmentfilePath);
    }

    /**
     * DOCUMENT ME!
     *
     * @param to DOCUMENT ME!
     */
    public void addTo(String to) {
        if (this.to == null) {
            this.to = new HashSet<String>();
        }

        this.to.add(to);
    }

    /**
     * DOCUMENT ME!
     *
     * @param cc DOCUMENT ME!
     */
    public void addCc(String cc) {
        if (this.cc == null) {
            this.cc = new HashSet<String>();
        }

        this.cc.add(cc);
    }

    /**
     * DOCUMENT ME!
     *
     * @param bb DOCUMENT ME!
     */
    public void addBcc(String bb) {
        if (this.bcc == null) {
            this.bcc = new HashSet<String>();
        }

        this.bcc.add(bb);
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Set<String> getBcc() {
        return bcc;
    }

    /**
     * DOCUMENT ME!
     *
     * @param bb DOCUMENT ME!
     */
    public void setBcc(Set<String> bb) {
        this.bcc = bb;
    }

    /**
     * DOCUMENT ME!
     *
     * @param to DOCUMENT ME!
     */
    public void setTo(Set<String> to) {
        this.to = to;
    }

    /**
     * DOCUMENT ME!
     *
     * @param cc DOCUMENT ME!
     */
    public void setCc(Set<String> cc) {
        this.cc = cc;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Integer getTemplateType() {
        return templateType;
    }

    /**
     * DOCUMENT ME!
     *
     * @param templateType DOCUMENT ME!
     */
    public void setTemplateType(Integer templateType) {
        this.templateType = templateType;
    }

  

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
	public String toString() {
		return new ToStringBuilder(this).append("subject", subject).append("to", to).append("cc", cc)
				.append("from", from).append("fromName", fromName).append("body", body)
				.append("attachments", attachments).toString();
	}

	/**
	 * @return the wordattachment
	 */
	public String getWordattachment() {
		return wordattachment;
	}

	/**
	 * @param wordattachment the wordattachment to set
	 */
	public void setWordattachment(String wordattachment) {
		this.wordattachment = wordattachment;
	}

	/**
	 * @return the inputStream
	 */
	public ByteArrayInputStream getInputStream() {
		return inputStream;
	}

	/**
	 * @param inputStream the inputStream to set
	 */
	public void setInputStream(ByteArrayInputStream inputStream) {
		this.inputStream = inputStream;
	}
}
