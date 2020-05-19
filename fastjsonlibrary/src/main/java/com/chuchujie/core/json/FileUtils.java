package com.chuchujie.core.json;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by yanghuan on 2018/4/26.
 */

 class FileUtils {
    public static File createFile(String path) throws IOException {
        File distFile = new File(path);
        if (!distFile.exists()) {
            File dir = distFile.getParentFile();
            if (dir != null && !dir.exists()) {
                dir.mkdirs();
            }
            distFile.createNewFile();
        }
        return distFile;
    }

    /**
     * 保存一个字节数组流到指定路径中
     *
     * @param data
     * @param path
     */
    public static void save(byte[] data, String path) throws IOException {
        FileOutputStream os = new FileOutputStream(createFile(path));
        os.write(data, 0, data.length);
        os.flush();
        os.close();
    }

    /**
     * 读取小文件的全部内容
     *
     * @param path
     * @return
     */
    public static String readFileContent(String path) {
        // TODO: 2016/4/5  应该依据文件的实际长度；动态分配Buffer大小，以便一次性读取完成文件内容；
        String result = null;
        FileInputStream is = null;

        try {
            File file = new File(path);
            is = new FileInputStream(file);
            byte[] readBuffer;
            readBuffer = streamToBytes(is, (int) file.length());
            result = new String(readBuffer, 0, (int) file.length());
//            result = readBuffer.toString();   // 这种方案有问题！
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != is) {
                    is.close();
                }
            } catch (Exception e) {

            }
        }

        return result;
    }


    private static byte[] streamToBytes(InputStream in, int length) throws IOException {
        byte[] bytes = new byte[length];
        int count;
        int pos = 0;
        while (pos < length && ((count = in.read(bytes, pos, length - pos)) != -1)) {
            pos += count;
        }

        if (pos != length) {
            throw new IOException("Expected " + length + " bytes, read " + pos + " bytes");
        }
        return bytes;
    }
}
