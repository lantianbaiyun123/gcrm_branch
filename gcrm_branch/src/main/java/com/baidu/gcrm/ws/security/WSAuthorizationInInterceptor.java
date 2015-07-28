package com.baidu.gcrm.ws.security;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.cxf.binding.soap.interceptor.SoapHeaderInterceptor;
import org.apache.cxf.headers.Header;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.baidu.gcrm.common.GcrmConfig;
import com.baidu.gcrm.common.exception.CRMRuntimeException;
import com.baidu.gcrm.common.log.LoggerHelper;

/**
 * 解析bpm回调gcrm服务时soap头中包含的用户名和密码，soap头的具体定义如下：
 * <p>
 * <a><</a>soap:Header<a>></a>
 * <br>&nbsp;&nbsp;<a><</a>AuthenticationToken<a>></a>
 * <br>&nbsp;&nbsp;&nbsp;&nbsp;<a><</a>GWFPUserName<a>></a>用户名称<a><</a>/GWFPUserName<a>></a>
 * <br>&nbsp;&nbsp;&nbsp;&nbsp;<a><</a>GWFPUserPassword<a>></a>通过md5加密的密码<a><</a>/GWFPUserPassword<a>></a>
 * <br>&nbsp;&nbsp;<a><</a>/AuthenticationToken<a>></a>
 * <br><a><</a>/soap:Header<a>></a></br>
 * </p>
 * @author anhuan
 *
 */
public class WSAuthorizationInInterceptor extends SoapHeaderInterceptor {

	@SuppressWarnings("unchecked")
    public void handleMessage(Message message) throws Fault {

        List<Header> headers = (List<Header>) message.get(Header.HEADER_LIST);

        Element authElement = null;

        if (headers != null) {
            for (Header header : headers) {
                if (header.getObject() instanceof Element &&
                        ((Element) header.getObject()).getNodeName().endsWith("AuthenticationToken")) {
                    authElement = (Element) header.getObject();
                }
            }
        }

        Element userNameElement = null;
        Element passwordElement = null;

        if (authElement != null) {
            userNameElement = getElementByTagName(authElement, "gwfpUserName");
            passwordElement = getElementByTagName(authElement, "gwfpUserPassword");
        }

        if ((authElement == null) || (userNameElement == null) || (passwordElement == null)) {
            throw new Fault(new CRMRuntimeException("缺少用户认证信息或者结构不正确，请联系GCRM管理员！"));
        }

        String username = userNameElement.getFirstChild().getNodeValue();
        String password = passwordElement.getFirstChild().getNodeValue();
        
        HttpServletRequest request = (HttpServletRequest) message.get(AbstractHTTPDestination.HTTP_REQUEST);
        String ip = request.getRemoteAddr();
        if (!GcrmConfig.getConfigValueByKey("ws.password").equals(password)) {
        	throw new Fault(new CRMRuntimeException("密码错误！"));
        }
        
        LoggerHelper.info(getClass(), "用户{}, ip{}验证成功", username, ip);
    }

    /**
     * DOCUMENT ME!
     *
     * @param parent DOCUMENT ME!
     * @param name DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    private Element getElementByTagName(Element parent, String name) {
        NodeList nodeList = parent.getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node instanceof Element && node.getNodeName().equals(name)) {
                return (Element) node;
            }
        }

        return null;
    }
}
