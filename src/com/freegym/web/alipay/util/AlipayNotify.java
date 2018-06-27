package com.freegym.web.alipay.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import com.freegym.web.alipay.config.AlipayConfig;

/* *
 *����AlipayNotify
 *���ܣ�֧����֪ͨ������
 *��ϸ������֧�������ӿ�֪ͨ����
 *�汾��3.2
 *���ڣ�2011-03-25
 *˵����
 *���´���ֻ��Ϊ�˷����̻����Զ��ṩ��������룬�̻����Ը���Լ���վ����Ҫ�����ռ����ĵ���д,����һ��Ҫʹ�øô��롣
 *�ô������ѧϰ���о�֧�����ӿ�ʹ�ã�ֻ���ṩһ���ο�

 *************************ע��*************************
 *����֪ͨ����ʱ���ɲ鿴���дlog��־��д��TXT�����ݣ������֪ͨ�����Ƿ���
 */
public class AlipayNotify {

    /**
     * ֧����֪ͨ��֤��ַ
     */
    private static final String HTTPS_VERIFY_URL = "https://mapi.alipay.com/gateway.do?service=notify_verify&";

    /**
     * ��֤��Ϣ�Ƿ���֧���������ĺϷ���Ϣ
     * @param params ֪ͨ�������Ĳ�������
     * @return ��֤���
     */
    public static boolean verify(Map<String, String> params) {

        String mysign = getMysign(params);
        String responseTxt = "true";
        if(params.get("notify_id") != null) {responseTxt = verifyResponse(params.get("notify_id"));}
        String sign = "";
        if(params.get("sign") != null) {sign = params.get("sign");}

        //д��־��¼����Ҫ���ԣ���ȡ����������ע�ͣ�
        //String sWord = "responseTxt=" + responseTxt + "\n notify_url_log:sign=" + sign + "&mysign="
        //              + mysign + "\n ���ز���" + AlipayCore.createLinkString(params);
        //AlipayCore.logResult(sWord);


        //��֤
        //responsetTxt�Ľ����true����������������⡢���������ID��notify_idһ����ʧЧ�й�
        //mysign��sign���ȣ��밲ȫУ���롢����ʱ�Ĳ����ʽ���磺���Զ������ȣ��������ʽ�й�
        if (mysign.equals(sign) && responseTxt.equals("true")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * ��ݷ�����������Ϣ�����ǩ����
     * @param Params ֪ͨ�������Ĳ�������
     * @return ��ɵ�ǩ����
     */
    private static String getMysign(Map<String, String> Params) {
        Map<String, String> sParaNew = AlipayCore.paraFilter(Params);//���˿�ֵ��sign��sign_type����
        String mysign = AlipayCore.buildMysign(sParaNew);//���ǩ����
        return mysign;
    }

    /**
    * ��ȡԶ�̷�����ATN���,��֤����URL
    * @param notify_id ֪ͨУ��ID
    * @return ������ATN���
    * ��֤���
    * invalid�������� ��������������ⷵ�ش�����partner��key�Ƿ�Ϊ�� 
    * true ������ȷ��Ϣ
    * false �������ǽ�����Ƿ�������ֹ�˿������Լ���֤ʱ���Ƿ񳬹�һ����
    */
    private static String verifyResponse(String notify_id) {
        //��ȡԶ�̷�����ATN�����֤�Ƿ���֧��������������������

        String partner = AlipayConfig.partner;
        String veryfy_url = HTTPS_VERIFY_URL + "partner=" + partner + "&notify_id=" + notify_id;

        return checkUrl(veryfy_url);
    }

    /**
    * ��ȡԶ�̷�����ATN���
    * @param urlvalue ָ��URL·����ַ
    * @return ������ATN���
    * ��֤���
    * invalid�������� ��������������ⷵ�ش�����partner��key�Ƿ�Ϊ�� 
    * true ������ȷ��Ϣ
    * false �������ǽ�����Ƿ�������ֹ�˿������Լ���֤ʱ���Ƿ񳬹�һ����
    */
    private static String checkUrl(String urlvalue) {
        String inputLine = "";

        try {
            URL url = new URL(urlvalue);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection
                .getInputStream()));
            inputLine = in.readLine().toString();
        } catch (Exception e) {
            e.printStackTrace();
            inputLine = "";
        }

        return inputLine;
    }
}
