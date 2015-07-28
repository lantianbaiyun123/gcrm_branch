package com.baidu.gcrm.common;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import com.baidu.gcrm.common.i18n.I18nMsg;
import com.baidu.gcrm.common.log.LoggerHelper;
import com.baidu.gcrm.log.model.ModifyRecord;

public class CommonHelper {
    /**
     * Prefix for system property placeholders: "${"
     */
    public static final String PLACEHOLDER_PREFIX = "${";

    /**
     * Suffix for system property placeholders: "}"
     */
    public static final String PLACEHOLDER_SUFFIX = "}";
    private static final char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
            'e', 'f'};

    /**
     * DOCUMENT ME!
     */
    public static final String INDEX_FILED_SPERATER = " ";

    /**
     * 私有构建器，本类中的所有方法均为静态引用
     */
    private CommonHelper() {
    }

    /**
     * DOCUMENT ME!
     *
     * @param indexField DOCUMENT ME!
     * @return DOCUMENT ME!
     */
    public static String createIndexField(Collection<String> indexField) {
        if ((indexField == null) || indexField.isEmpty()) {
            return "";
        }

        StringBuilder result = new StringBuilder(INDEX_FILED_SPERATER);

        for (String f : indexField) {
            if (StringUtils.isNotEmpty(f)) {
                result.append(f).append(INDEX_FILED_SPERATER);
            }
        }

        return result.toString();
    }

    /**
     * DOCUMENT ME!
     *
     * @param indexField DOCUMENT ME!
     * @return DOCUMENT ME!
     */
    public static String[] splitIndexField(String indexField) {
        if (StringUtils.isNotBlank(indexField)) {
            indexField = indexField.trim();
            indexField = indexField.replaceAll("\\s+", INDEX_FILED_SPERATER);

            return indexField.split(INDEX_FILED_SPERATER);
        }

        return new String[]{};
    }

    @SuppressWarnings("unchecked")
    public static Object parseData(String value, Class type) {
        if (value == null) {
            return null;
        }

        if (!type.isAssignableFrom(String.class) && value.trim().length() == 0) {
            return null;
        }

        try {
            if (type.isAssignableFrom(Date.class)) {
                try {
                    return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(value);
                } catch (ParseException e) {
                    return new SimpleDateFormat("yyyy-MM-dd").parse(value);
                }
            }

            if (type.isAssignableFrom(byte[].class)) {
                return value.getBytes();
            }

            Constructor constructor = type.getConstructor(new Class[]{String.class});

            return constructor.newInstance(new Object[]{value});
        } catch (Exception e) {
            // ignore
        }

        return null;
    }

    /**
     * 根据类型转换对象
     */
    public static Object parseObject(String initialValue, String dataType) {
        Object ret = null;

        if ((initialValue == null) || (initialValue.trim().length() == 0)) {
            if (dataType.equals("FLOAT")) {
                ret = 0f;
            } else if (dataType.equals("INTEGER")) {
                ret = 0d;
            } else if (dataType.equals("DATETIME")) {
                ret = new Date();
            } else if (dataType.equals("BOOLEAN")) {
                ret = false;
            } else {
                ret = "";
            }
        } else {
            if (dataType.equals("FLOAT")) {
                Float value = 0.0f;

                try {
                    value = new Float(initialValue);
                } catch (NumberFormatException ignored) {
                }
                ret = value;
            } else if (dataType.equals("INTEGER")) {
                Integer value = 0;

                try {
                    value = Integer.valueOf(initialValue);
                } catch (NumberFormatException nfe) {
                    try {
                        Double d = Double.valueOf(initialValue);
                        value = d.intValue();
                    } catch (NumberFormatException ignored) {
                    }
                }

                ret = value;
            } else if (dataType.equals("DATETIME")) {
                Date value = new Date();

                try {
                    value = DateFormat.getDateInstance().parse(initialValue);
                } catch (ParseException pe) {
                    try {
                        value = new Date(Long.parseLong(initialValue));
                    } catch (NumberFormatException ignored) {
                    }
                }

                ret = value;
            } else if (dataType.equals("BOOLEAN")) {
                Boolean value = Boolean.FALSE;

                if (initialValue.equalsIgnoreCase("true") || initialValue.equals("1")) {
                    value = Boolean.TRUE;
                }

                ret = value;
            } else {
                ret = initialValue;
            }
        }

        return ret;
    }

    /**
     * 将特定字符串分隔成List
     *
     * @param specialStr 待分隔字符串
     * @param split      分隔符
     */
    public static List<String> splitString2List(String specialStr, String split) {
        List<String> retValue = new ArrayList<String>();

        if (StringUtils.isEmpty(specialStr)) {
            return retValue;
        }

        if (StringUtils.isEmpty(split)) {
            if (StringUtils.isNotBlank(specialStr)) {
                retValue.add(specialStr);
            }

            return retValue;
        }

        String[] strings = specialStr.split(split);

        for (String value : strings) {
            if (StringUtils.isNotEmpty(value)) {
                retValue.add(value);
            }
        }

        return retValue;
    }

    /**
     * DOCUMENT ME!
     *
     * @param beanClass    DOCUMENT ME!
     * @param beanInstance DOCUMENT ME!
     * @param fieldName    DOCUMENT ME!
     * @return DOCUMENT ME!
     */
    @SuppressWarnings("unchecked")
    public static Object getBeanFieldValueByName(Class beanClass, Object beanInstance, String fieldName) {
        try {
            Field field = beanClass.getDeclaredField(fieldName);

            if (!field.isAccessible()) {
                field.setAccessible(true);
            }

            return field.get(beanInstance);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param beanClass    DOCUMENT ME!
     * @param beanInstance DOCUMENT ME!
     * @param arrayNum     DOCUMENT ME!
     * @param fieldName    DOCUMENT ME!
     * @param fieldValue   DOCUMENT ME!
     */
    @SuppressWarnings("unchecked")
    public static void setBeanFiledValue(Class beanClass, Object beanInstance, Integer arrayNum, String fieldName,
                                         Object fieldValue) {
        try {
            Field field = beanClass.getDeclaredField(fieldName);
            Class fieldType = field.getType();

            if ((fieldType != String.class) && fieldValue instanceof String) {
                if (fieldType == Date.class) { // 搜索索引时可能使用，不是很通用请注意使用，有的date类型不是这个格式存储
                    fieldValue = DateUtils.getString2Date(DateUtils.YYYYMMDDHHMM, (String) fieldValue); // "yyyyMMddHHmm"
                } else {
                    Constructor stringCons = null;

                    try {
                        stringCons = fieldType.getConstructor(String.class);
                    } catch (Exception ex) { // do noting
                    }

                    if (stringCons != null) {
                        fieldValue = stringCons.newInstance(fieldValue);
                    }
                }
            }

            // 如果是数组类型的话，不按照对象赋值了
            if ((arrayNum != null) && beanInstance instanceof Object[]) {
                Object[] obj = (Object[]) beanInstance;
                obj[arrayNum] = fieldValue;
            } else {
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }

                field.set(beanInstance, fieldValue);
            }
        } catch (Exception ex) {
            // ignore
        }
    }


    /**
     * DOCUMENT ME!
     *
     * @param ch DOCUMENT ME!
     */
    public static boolean isNumber(char ch) {
        return ((ch >= '0') && (ch <= '9'));
    }

    /**
     * 验证电话是否合法
     *
     * @param phone 待验证邮件地址
     */
    public static boolean validatePhone(String phone) {
        Pattern p = Pattern.compile("1[3|5|8]\\d{9}");
        Matcher m = p.matcher(phone);

        return m.matches();
    }

    /**
     * Resolve ${...} placeholders in the given text, replacing them with
     * corresponding system property values.
     *
     * @param text       the String to resolve
     * @param properties DOCUMENT ME!
     * @return the resolved String
     * @see #PLACEHOLDER_PREFIX
     * @see #PLACEHOLDER_SUFFIX
     */
    public static String resolvePlaceholders(String text, Properties properties) {
        StringBuilder buf = new StringBuilder(text);
        int startIndex = text.indexOf(PLACEHOLDER_PREFIX);

        while (startIndex != -1) {
            int endIndex = buf.toString().indexOf(PLACEHOLDER_SUFFIX, startIndex + PLACEHOLDER_PREFIX.length());

            if (endIndex != -1) {
                String placeholder = buf.substring(startIndex + PLACEHOLDER_PREFIX.length(), endIndex);
                int nextIndex = endIndex + PLACEHOLDER_SUFFIX.length();

                try {
                    String propVal = properties.getProperty(placeholder);

                    if (propVal == null) {
                        propVal = System.getenv(placeholder);
                    }

                    if (propVal != null) {
                        buf.replace(startIndex, endIndex + PLACEHOLDER_SUFFIX.length(), propVal);
                        nextIndex = startIndex + propVal.length();
                    }
                } catch (Throwable ex) {
                    // do nothing
                }

                startIndex = buf.toString().indexOf(PLACEHOLDER_PREFIX, nextIndex);
            } else {
                startIndex = -1;
            }
        }

        return buf.toString();
    }

    /**
     * MD5加密
     *
     * @param src String 要加密的串
     * @return String 加密后的字符串
     */
    public static String md5Encoding(String src) {
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

    /**
     * DOCUMENT ME!
     *
     * @param className  类名称
     * @param properties 属性名称列表
     * @param orderBy    排序
     */
    public static String convertProps2Hql(String className, String[] properties, String[] orderBy) {
        String alias = "_" + className.toLowerCase();
        StringBuilder stringBuilder = new StringBuilder(" from " + className + " " + alias);

        if ((properties != null) && (properties.length > 0)) {
            stringBuilder.append(" where ");
        }

        for (int i = 0; (properties != null) && (i < properties.length); i++) {
            String fieldCondition = properties[i];
            stringBuilder.append(alias).append(".").append(fieldCondition);

            if (i < (properties.length - 1)) {
                stringBuilder.append(" and ");
            }
        }

        if ((orderBy != null) && (orderBy.length > 0)) {
            stringBuilder.append(" order by ");

            for (String odby : orderBy) {
                String[] obs = odby.split(":");
                stringBuilder.append(" ").append(alias).append(".").append(obs[0]).append(" ").append("1".equals(obs[1]) ? "asc" : "desc ").append(",");
            }

            stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());
        }

        return stringBuilder.toString();
    }

    /**
     * 生成随即密码
     *
     * @param pwd_len     生成的密码的总长度
     * @param isNumOrChar 是否只包含数字或字母
     * @return 密码的字符串
     */
    public static String genRandomNum(int pwd_len, boolean isNumOrChar) {
        // 26个字母+10个数字,去掉0，O，0，l,1,I六个
        String iniStr = "abcdefghijkmnpqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ23456789!@#$%^*()_-+=";

        if (isNumOrChar) {
            iniStr = "abcdefghijkmnpqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ23456789";
        }

        char[] str = iniStr.toCharArray();
        int strlength = str.length;
        StringBuilder pwd = new StringBuilder("");
        Random r = new Random();
        boolean isComplexPwd = false; // 是否为符合规范的随机码

        while (!isComplexPwd) {
            for (int i = 0; i < pwd_len; i++) {
                // 生成随机数，取绝对值，防止生成负数，
                pwd.append(str[Math.abs(r.nextInt(strlength))]);
            }

            isComplexPwd = isComplexPwd(pwd.toString());

            // 是否复杂
            if (!isComplexPwd) {
                pwd = new StringBuilder(""); // 清空pwd
            }
        }

        return pwd.toString();
    }

    /**
     * 判断输入是否是一个由 0-9 / A-Z / a-z /特殊字符4个种的3个类型组成
     */
    private static boolean isComplexPwd(String pwd) {
        boolean isComplexPwd = false;
        int intScore = 0;
        Pattern pattern = Pattern.compile("[a-z]");
        Matcher matcher = pattern.matcher(pwd);

        if (matcher.find()) {
            intScore++;
        }

        pattern = Pattern.compile("[A-Z]");
        matcher = pattern.matcher(pwd);

        if (matcher.find()) {
            intScore++;
        }

        pattern = Pattern.compile("\\d");
        matcher = pattern.matcher(pwd);

        if (matcher.find()) {
            intScore++;
        }

        pattern = Pattern.compile("[!,@#$%^&*?_~]");
        matcher = pattern.matcher(pwd);

        if (matcher.find()) {
            intScore++;
        }

        if (intScore >= 3) {
            isComplexPwd = true;
        }

        return isComplexPwd;
    }

    /**
     * DOCUMENT ME!
     *
     * @param a DOCUMENT ME!
     */
    @SuppressWarnings("unchecked")
    public static String deepToString(Object[] a) {
        if (a == null) {
            return "null";
        }

        int bufLen = 20 * a.length;

        if ((a.length != 0) && (bufLen <= 0)) {
            bufLen = Integer.MAX_VALUE;
        }

        StringBuilder buf = new StringBuilder(bufLen);
        deepToString(a, buf, new HashSet());

        return buf.toString();
    }

    /**
     * 显示值
     */
    public static String deepToString(Object[] a, int maxSize) {
        String str = deepToString(a);

        if (str.length() > maxSize) {
            return str.substring(0, maxSize);
        }

        return str;
    }

    @SuppressWarnings("unchecked")
    private static void deepToString(Object[] a, StringBuilder buf, Set<Object[]> targetValues) {
        if (a == null) {
            buf.append("null");

            return;
        }

        targetValues.add(a);
        buf.append('[');

        for (int i = 0; i < a.length; i++) {
            if (i != 0) {
                buf.append(", ");
            }

            Object element = a[i];

            if (element == null) {
                buf.append("null");
            } else {
                Class eClass = element.getClass();

                if (eClass.isArray()) {
                    if (eClass == byte[].class) {
                        buf.append("[....]"); // Arrays.toString((byte[])
                        // element));
                    } else if (eClass == short[].class) {
                        buf.append(Arrays.toString((short[]) element));
                    } else if (eClass == int[].class) {
                        buf.append(Arrays.toString((int[]) element));
                    } else if (eClass == long[].class) {
                        buf.append(Arrays.toString((long[]) element));
                    } else if (eClass == char[].class) {
                        buf.append(Arrays.toString((char[]) element));
                    } else if (eClass == float[].class) {
                        buf.append(Arrays.toString((float[]) element));
                    } else if (eClass == double[].class) {
                        buf.append(Arrays.toString((double[]) element));
                    } else if (eClass == boolean[].class) {
                        buf.append(Arrays.toString((boolean[]) element));
                    } else { // element is an array of object references

                        if (targetValues.contains(element)) {
                            buf.append("[...]");
                        } else {
                            deepToString((Object[]) element, buf, targetValues);
                        }
                    }
                } else if (element instanceof Collection) {
                    deepToString(((Collection) element).toArray(), buf, targetValues);
                    deepToString(((Collection) element).toArray());
                } else {
                    // element is non-null and not an array
                    try {
                        // 自己或父类实现了toString方法，调用自己的
                        /*
                               * if(!element.getClass().getMethod("toString").
                               * getDeclaringClass().equals(Object.class)) {
                               * buf.append(element.toString()); } else {
                               * buf.append(ReflectionToStringBuilder
                               * .toString(element)); }
                               */
                        buf.append(element.toString());
                    } catch (Exception e) {
                        // ignore
                    }
                }
            }
        }

        buf.append("]");
        targetValues.remove(a);
    }

    /**
     * 计算单层文件夹下的文件大小和
     */
    public static long computeDirSize(String dirPath) {
        File dirFile = new File(dirPath);

        if (!dirFile.exists()) {
            return 0;
        }

        long totalSize = 0L;
        File[] files = dirFile.listFiles();

        for (File file : files) {
            if (file.isDirectory()) {
                continue;
            }

            totalSize += file.length();
        }

        return totalSize;
    }

    /**
     * 获取本地的ip
     */
    public static String getLocalHostIp() {
        String hostNameIP = "";
        try {
            hostNameIP = java.net.InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            LoggerHelper.err(CommonHelper.class, "获取本地ip出错", e);
        }

        return hostNameIP;
    }

    public static String getLocalHost() {
        String hostNameIP = "";

        try {
            hostNameIP = java.net.InetAddress.getLocalHost().toString();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return hostNameIP;
    }

    /**
     * 为url添加http
     */
    public static String handleUrl(String url) {
        if (StringUtils.isNotEmpty(url)) {
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "http://" + url;
            }
        }

        return url;
    }

    /**
     * 将parameterMap附加在href中
     */
    @SuppressWarnings("unchecked")
    public static String getParametersUrlPatten(Map parameterMap, String href) {
        if (parameterMap == null) {
            return "";
        }

        // 解析当前href中的参数
        List<String> myParams = new ArrayList<String>();
        int n;

        if ((n = href.indexOf('?')) > 0) {
            String p = href.substring(n + 1);
            myParams.addAll(java.util.Arrays.asList(p.split("&")));
        }

        StringBuilder ret = new StringBuilder();
        Set set = parameterMap.entrySet();

        for (Object elem : set) {
            Map.Entry entry = (Map.Entry) elem;
            String key = entry.getKey().toString();
            Object value = entry.getValue();

            if (value instanceof String[]) {
                String[] vs = (String[]) value;

                for (String v : vs) {
                    String p = key + "=" + v;

                    if (myParams.contains(p)) {
                        continue;
                    }

                    ret.append(p);
                    ret.append("&");
                }
            } else {
                String p = key + "=" + value;

                // 如果已经存在，则不再添加了
                if (myParams.contains(p)) {
                    continue;
                }

                ret.append(p);
                ret.append("&");
            }
        }

        if (ret.length() > 0) {
            ret.deleteCharAt(ret.length() - 1);
        }

        return ret.toString();
    }

    /**
     * 解析url参数
     */
    public static Map<String, String> parseQueryParameter(String queryParameter) {
        Map<String, String> map = new HashMap<String, String>();

        if (StringUtils.isEmpty(queryParameter)) {
            return map;
        }

        int index = queryParameter.lastIndexOf("=");
        int pos = queryParameter.length();

        while (index != -1) {
            String value = queryParameter.substring(index + 1, pos);
            pos = index;
            index = queryParameter.lastIndexOf("&", index);

            String keyName = queryParameter.substring(index + 1, pos);
            pos = index;
            index = queryParameter.lastIndexOf("=", pos);
            map.put(keyName, value);
        }

        return map;
    }

    /**
     * 将对象列表生成suggest的xml字符串
     *
     * @param list           对象列表
     * @param keyProperty    key属性名称
     * @param valueProperty  value属性名称
     * @param infoProperties info属性名称，当为空时，不获取
     */
    public static <C> String toSuggestXml(Collection<C> list, String keyProperty, String valueProperty,
                                          String... infoProperties) {
        StringBuilder suggestXml = new StringBuilder();
        suggestXml.append("<results>\n");

        if (list != null) {
            for (C c : list) {
                if (c == null) {
                    continue;
                }
                Object key = null;

                if (StringUtils.isNotEmpty(keyProperty)) {
                    try {
                        key = PropertyUtils.getProperty(c, keyProperty);
                    } catch (Exception e) {
                        // ignore
                    }
                }

                Object value = null;

                if (StringUtils.isNotEmpty(valueProperty)) {
                    try {
                        value = PropertyUtils.getProperty(c, valueProperty);
                    } catch (Exception e) {
                        // ignore
                    }
                }

                if (value == null) {
                    value = "";
                }
                Object info = null;
                if (infoProperties != null && infoProperties.length == 1 && StringUtils.isNotEmpty(infoProperties[0])) {

                    try {
                        info = PropertyUtils.getProperty(c, infoProperties[0]);
                    } catch (Exception e) {
                        // ignore
                    }
                }
                if (info == null) {
                    info = "";
                }

                if (key != null) {
                    suggestXml.append("<rs id=\"").append(key).append("\" info=\"").append(info).append("\">").append(value).append("</rs>\n");
                }
            }
        }

        suggestXml.append("</results>");

        return suggestXml.toString();
    }

    /**
     * 去除字符串两端的空格，并且如果字符串末尾含有/, \ , 则去除
     */
    public static String dealStringTail(String originString) {
        if (originString == null) {
            return null;
        }

        String ret = originString.trim();
        for (int i = ret.length() - 1; i >= 0; i--) {
            if ((ret.charAt(i) == '/') || (ret.charAt(i) == '\\')) {
                ret = ret.substring(0, i);
            } else {
                break;
            }
        }

        return ret;

    }

    /**
     * 判断一个集合里面的字符串是否全是null或者""
     *
     * @param collection 要判断的集合
     * @return 集合本身为空或者元素全部为空，返回true，其余情况均为false
     */
    public static boolean checkCollectionItemsNull(Collection<String> collection) {
        if (CollectionUtils.isEmpty(collection)) {
            return true;
        }
        boolean allNull = true;
        for (String str : collection) {
            if (!StringUtils.isBlank(str)) {
                allNull = false;
                break;
            }
        }
        return allNull;
    }

    /**
     * 格式话显示字符串，转化html特殊字符，把回车符合换成<br>
     */
    public static String convertString(String inputString) {
        // 转化html特殊字符
        inputString = inputString.replaceAll("&", "&amp;");
        inputString = inputString.replaceAll("\"", "&quot;");
        inputString = inputString.replaceAll("<", "&lt;");
        inputString = inputString.replaceAll(">", "&gt;");
        inputString = inputString.replaceAll(" ", "&nbsp;");

        return inputString;
    }

    /**
     * 可逆的加密算法
     *
     * @param srcString 需要加密的字符串
     * @return 加密后的字符串
     */
    public static String encrypt(String srcString) {
        try {
            return new DESPlus().encrypt(srcString);
        } catch (Exception e) {
            throw new RuntimeException("加密错误！ ", e);

        }
    }

    /**
     * 解密方法
     *
     * @param srcString 需要解密的字符串
     * @return 还原后的字符串
     */
    public static String decrypt(String srcString) {
        try {
            return new DESPlus().decrypt(srcString);
        } catch (Exception e) {
            throw new RuntimeException("解密错误！ ", e);

        }
    }

    /**
     * 实现了I18nMsg转化为String的消息的一个Help方法
     *
     * @param i18nMsgs 需要转化的I18n消息
     * @return 转化后的结果
     */
    public static List<String> convertI18nMsg(Collection<I18nMsg> i18nMsgs) {
        List<String> convertResults = new ArrayList<String>();
        if (CollectionUtils.isEmpty(i18nMsgs)) {
            return convertResults;
        }
        for (I18nMsg i18nMsg : i18nMsgs) {
            convertResults.add(i18nMsg.getShowMessage());
        }
        return convertResults;
    }

    /**
     * 实现了I18nMsg转化为String的消息的一个Help方法
     *
     * @param i18nMsgs 需要转化的I18n消息
     * @return 转化后的结果
     */
    public static String convertI18nMsgToString(Collection<I18nMsg> i18nMsgs) {
        StringBuilder sb = new StringBuilder();
        if (CollectionUtils.isEmpty(i18nMsgs)) {
            return sb.toString();
        }
        for (I18nMsg i18nMsg : i18nMsgs) {
            sb.append(i18nMsg.getShowMessage());
            sb.append("\n");
        }

        if (sb.length() > 0) {
            sb.delete(sb.length() - 1, sb.length());
        }
        return sb.toString();
    }

    /**
     * 生成一个按照split分割的字符串
     */
    public static String generateCommaSplitFromCollection(Collection<String> sources, String split) {
        if (CollectionUtils.isEmpty(sources)) {
            return String.valueOf("");
        }

        StringBuilder buffer = new StringBuilder();
        int i = 0;
        for (String element : sources) {
            buffer.append(element);
            if (i != sources.size() - 1) {
                buffer.append(split);
            }
            if (i % 8 == 0) {
                buffer.append("<br/>");
            }
            i++;
        }
        return buffer.toString();
    }

    /**
     * 生成用户密码,密码8位,包含数字,字母和特殊字符
     */
    public static String generatePassword() {
        String number = "0123456789";
        String letter = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String special = "!@#$%^&*";
        Random r = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 2; i++) {
            sb.append(number.charAt(r.nextInt(number.length())));
            sb.append(letter.charAt(r.nextInt(letter.length())));
            sb.append(special.charAt(r.nextInt(special.length())));
            sb.append(letter.charAt(r.nextInt(letter.length())));
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            int pos = r.nextInt(8 - i);
            char char1 = sb.charAt(pos);
            stringBuilder.append(char1);
            sb.deleteCharAt(pos);
        }
        return stringBuilder.toString();
    }
    
    public static String escapeHtml(String html){
    	html = html.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        return html;
    }
	  /**
     * 字符串替换
     * @param origStr
     * @param key
     * @param startTag
     * @param endTag
     * @return
     */
	public static String repStr(String origStr, String key, String startTag, String endTag) {
		if (StringUtils.isBlank(origStr)) {
			return "";
		}
		return origStr.replaceAll("((?i)"+key+")", startTag + "$1" + endTag);
	}
	/**
	 * 过滤xss注入 字符串
	 * @param xssString
	 * @return
	 */
	public static String xssFilter(String xssString){
		return xssString == null ? null : xssString.replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;");
	}
	public static String reXssFilter(String xssString){
		return xssString == null ? null : xssString.replaceAll("&lt;", "<")
				.replaceAll("&gt;", ">");
	}
	
    public static boolean isIncludingChinese(String userName) {
		String regEx = "[\u4e00-\u9fa5]";   
		Pattern pat = Pattern.compile(regEx);  
		
		Matcher matcher = pat.matcher(userName);     
        boolean flg = false;  
        if (matcher.find())    {    
            flg = true;   
        }     
        return flg;  
	}
    
    public static Locale convertLocale(LocaleConstants localeConstants){
    	String[] str = localeConstants.toString().split("_");
		Locale locale = Locale.ENGLISH;
		if(str != null){
			locale = new Locale(str[0], str[1]);
		}
		return locale;
    }
    
    /**
     * 判断当前请求是否是Ajax请求
     * @param request
     * @return
     */
	public static boolean isAjaxRequest(HttpServletRequest request) {
		String ajaxHeader = request.getHeader("X-Requested-With");
		if (StringUtils.isEmpty(ajaxHeader)) {
			return false;
		}
		else if(ajaxHeader.equals("XMLHttpRequest")){
			return true;
		}
		return true;
	}
	
	/**
	 * 判断当前请求是否是文件上传
	 * @param request
	 * @return
	 */
	public static boolean isFileUpload(HttpServletRequest request){
		String contentType = request.getHeader("Content-Type");
		if (StringUtils.isEmpty(contentType)) {
			return false;
		}
		else if (contentType.contains("multipart/form-data")) {
			return true;
		}
		return false;
	}
	
	/**
	 * 判断是否指定字段有过修改
	 * @param modifyRecords 修改记录列表
	 * @param keyList 字段列表, 修改的字段可以小于此列表
	 * @param className 类名
	 * @return  如果不是修改了keyList中的字段，则返回null。<br/>
     *         否则返回 key对应的oldValue和newValue, 格式：Map<key, new String[]{oldValue, newValue}>
	 */
	public static Map<String, String []> isKeysOnlyModified(List<ModifyRecord> modifyRecords, List<String> keyList, List<String> classNames) {
		Map<String, String[]> resultMap = new HashMap<String, String[]>();
		if (null != modifyRecords && modifyRecords.size() <= keyList.size()) {
			for (ModifyRecord record : modifyRecords) {
				if (classNames.contains(record.getTableName()) && keyList.contains(record.getModifyField())) {
					resultMap.put(record.getModifyField(), new String [] {record.getOldValue(), record.getNewValue()});
				} else {
					return null;
				}
			}
			if (resultMap.size() == modifyRecords.size()) {
				return resultMap;
			}
		}
		return null;
	}
}
