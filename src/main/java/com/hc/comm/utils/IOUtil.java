package com.hc.comm.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @Author: 梁云亮
 * @Date 2020/5/1 16:01
 * @Description:
 */
public class IOUtil {

    /**
     * InputStream转字符串
     * @param is
     * @return
     * @throws Exception
     */
    public String inputStream2String(InputStream is) throws Exception{  //字节流转为字符流
        BufferedReader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
        StringBuffer buffer = new StringBuffer();
        String line = "";
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        reader.close();
        return buffer.toString();
    }

    /**
     * 字符串转InputStream
     * @param str
     * @return
     */
    public InputStream string2InputStream(String str) {
        ByteArrayInputStream stream = new ByteArrayInputStream(str.getBytes());
        return stream;
    }

}
