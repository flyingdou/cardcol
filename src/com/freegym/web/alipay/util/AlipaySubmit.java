package com.freegym.web.alipay.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.NameValuePair;

import com.freegym.web.alipay.config.AlipayConfig;
import com.freegym.web.alipay.util.httpClient.HttpProtocolHandler;
import com.freegym.web.alipay.util.httpClient.HttpRequest;
import com.freegym.web.alipay.util.httpClient.HttpResponse;
import com.freegym.web.alipay.util.httpClient.HttpResultType;

/* *
 *����AlipaySubmit
 *���ܣ�֧�������ӿ������ύ��
 *��ϸ������֧�������ӿڱ?HTML�ı�����ȡԶ��HTTP���
 *�汾��3.2
 *���ڣ�2011-03-17
 *˵����
 *���´���ֻ��Ϊ�˷����̻����Զ��ṩ��������룬�̻����Ը���Լ���վ����Ҫ�����ռ����ĵ���д,����һ��Ҫʹ�øô��롣
 *�ô������ѧϰ���о�֧�����ӿ�ʹ�ã�ֻ���ṩһ���ο���
 */

public class AlipaySubmit {

    /**
     * ���Ҫ�����֧�����Ĳ�������
     * @param sParaTemp ����ǰ�Ĳ�������
     * @return Ҫ����Ĳ�������
     */
    private static Map<String, String> buildRequestPara(Map<String, String> sParaTemp) {
        //��ȥ�����еĿ�ֵ��ǩ�����
        Map<String, String> sPara = AlipayCore.paraFilter(sParaTemp);
        //���ǩ����
        String mysign = AlipayCore.buildMysign(sPara);

        //ǩ������ǩ��ʽ���������ύ��������
        sPara.put("sign", mysign);
        sPara.put("sign_type", AlipayConfig.sign_type);

        return sPara;
    }

    /**
     * �����ύ�?HTML���
     * @param sParaTemp �����������
     * @param gateway ��ص�ַ
     * @param strMethod �ύ��ʽ������ֵ��ѡ��post��get
     * @param strButtonName ȷ�ϰ�ť��ʾ����
     * @return �ύ�?HTML�ı�
     */
    public static String buildForm(Map<String, String> sParaTemp, String gateway, String strMethod,
                                   String strButtonName) {
        //�������������
        Map<String, String> sPara = buildRequestPara(sParaTemp);
        List<String> keys = new ArrayList<String>(sPara.keySet());

        StringBuffer sbHtml = new StringBuffer();

        sbHtml.append("<form id=\"alipaysubmit\" name=\"alipaysubmit\" action=\"" + gateway
                      + "_input_charset=" + AlipayConfig.input_charset + "\" method=\"" + strMethod
                      + "\">");

        for (int i = 0; i < keys.size(); i++) {
            String name = (String) keys.get(i);
            String value = (String) sPara.get(name);

            sbHtml.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\"/>");
        }

        //submit��ť�ؼ��벻Ҫ����name����
        sbHtml.append("<input type=\"submit\" value=\"" + strButtonName + "\" style=\"display:none;\"></form>");
        sbHtml.append("<script>document.forms['alipaysubmit'].submit();</script>");

        return sbHtml.toString();
    }

    /**
     * MAP��������ת����NameValuePair����
     * @param properties  MAP��������
     * @return NameValuePair��������
     */
    private static NameValuePair[] generatNameValuePair(Map<String, String> properties) {
        NameValuePair[] nameValuePair = new NameValuePair[properties.size()];
        int i = 0;
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            nameValuePair[i++] = new NameValuePair(entry.getKey(), entry.getValue());
        }

        return nameValuePair;
    }

    /**
     * ����ģ��Զ��HTTP��POST���󣬻�ȡ֧�����ķ���XML������
     * @param sParaTemp �����������
     * @param gateway ��ص�ַ
     * @return ֧��������XML������
     * @throws Exception
     */
    public static String sendPostInfo(Map<String, String> sParaTemp, String gateway)
                                                                                    throws Exception {
        //�������������
        Map<String, String> sPara = buildRequestPara(sParaTemp);

        HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();

        HttpRequest request = new HttpRequest(HttpResultType.BYTES);
        //���ñ��뼯
        request.setCharset(AlipayConfig.input_charset);

        request.setParameters(generatNameValuePair(sPara));
        request.setUrl(gateway+"_input_charset="+AlipayConfig.input_charset);

        HttpResponse response = httpProtocolHandler.execute(request);
        if (response == null) {
            return null;
        }
        
        String strResult = response.getStringResult();

        return strResult;
    }
}
