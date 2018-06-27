package com.freegym.web.alipay.util;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.freegym.web.alipay.config.AlipayConfig;

/* *
 *����AlipayFunction
 *���ܣ�֧�����ӿڹ��ú�����
 *��ϸ������������֪ͨ���������ļ�����õĹ��ú�����Ĵ����ļ�������Ҫ�޸�
 *�汾��3.2
 *���ڣ�2011-03-17
 *˵����
 *���´���ֻ��Ϊ�˷����̻����Զ��ṩ��������룬�̻����Ը���Լ���վ����Ҫ�����ռ����ĵ���д,����һ��Ҫʹ�øô��롣
 *�ô������ѧϰ���о�֧�����ӿ�ʹ�ã�ֻ���ṩһ���ο���
 */

public class AlipayCore {

    /**
     * ���ǩ����
     * @param sArray Ҫǩ�������
     * @return ǩ�����ַ�
     */
    public static String buildMysign(Map<String, String> sArray) {
        String prestr = createLinkString(sArray); //����������Ԫ�أ����ա�����=����ֵ����ģʽ�á�&���ַ�ƴ�ӳ��ַ�
        prestr = prestr + AlipayConfig.key; //��ƴ�Ӻ���ַ����밲ȫУ����ֱ����������
        String mysign = AlipayMd5Encrypt.md5(prestr);
        return mysign;
    }

    /** 
     * ��ȥ�����еĿ�ֵ��ǩ�����
     * @param sArray ǩ�������
     * @return ȥ����ֵ��ǩ���������ǩ�������
     */
    public static Map<String, String> paraFilter(Map<String, String> sArray) {

        Map<String, String> result = new HashMap<String, String>();

        if (sArray == null || sArray.size() <= 0) {
            return result;
        }

        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign")
                || key.equalsIgnoreCase("sign_type")) {
                continue;
            }
            result.put(key, value);
        }

        return result;
    }

    /** 
     * ����������Ԫ�����򣬲����ա�����=����ֵ����ģʽ�á�&���ַ�ƴ�ӳ��ַ�
     * @param params ��Ҫ���򲢲����ַ�ƴ�ӵĲ�����
     * @return ƴ�Ӻ��ַ�
     */
    public static String createLinkString(Map<String, String> params) {

        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        String prestr = "";

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);

            if (i == keys.size() - 1) {//ƴ��ʱ�����������һ��&�ַ�
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }

        return prestr;
    }

    /** 
     * д��־��������ԣ�����վ����Ҳ���ԸĳɰѼ�¼������ݿ⣩
     * @param sWord Ҫд����־����ı�����
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(AlipayConfig.log_path);
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
