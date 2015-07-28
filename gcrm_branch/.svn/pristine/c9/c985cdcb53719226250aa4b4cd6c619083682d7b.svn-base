package com.baidu.gcrm.bpm.security;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.cxf.binding.soap.SoapHeader;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.headers.Header;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.Phase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class CommonAuthorizationOutHandler extends AbstractSoapInterceptor {
	private String username;
	private String password;
	
	
    public CommonAuthorizationOutHandler() {
        super(Phase.WRITE);
    }
    
    protected abstract String getTokenTag();
    
    protected abstract String getUserNameTag();
    
    protected abstract String getPasswordTag();
    
    protected abstract String getLoginUserTag();
    
    protected abstract boolean isPasswordEncode();

    public void handleMessage(SoapMessage paramT) throws Fault {
        List<Header> headers = paramT.getHeaders();
        if(headers==null) {
            headers=new ArrayList<Header>();
        }
        Document document = DOMUtils.createDocument();

        Element auth = document.createElement(getTokenTag());

        Element username_el =document.createElement(getUserNameTag());
        username_el.setTextContent(username);

        Element password_el = document.createElement(getPasswordTag());
        String modifiedPassword = password;
        if(isPasswordEncode()){
        	modifiedPassword = md5Encoding(password);
        }
        password_el.setTextContent(modifiedPassword);
        
        auth.appendChild(username_el);
        auth.appendChild(password_el);
        QName qName = new QName("uri:org.apache.cxf");
        headers.add(new SoapHeader(qName,auth));
        paramT.put(Header.HEADER_LIST, headers);
    }

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public static String md5Encoding(String src) {
		final char[] hexDigits = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
        };
        try {
            byte[] strTemp = src.getBytes();
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);

            byte[] md = mdTemp.digest();
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;

            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }

            return new String(str);
        } catch (Exception e) {
            return src;
        }
    }
}
