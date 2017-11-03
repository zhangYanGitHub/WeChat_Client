package com.zhang.chat.utils;

import android.Manifest;
import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 操作sd卡的util
 */
public class SDUtil {

    public static String SDRootDir() {
        createDIR(Environment.getExternalStorageDirectory() + Constant.ROOT_FILE);
        return Environment.getExternalStorageDirectory() + Constant.ROOT_FILE + "/";
    }

    /**
     * 判断文件是否已经存在;
     */
    public static boolean checkFileExists(String filepath) {
        File file = new File(filepath);
        return file.exists();
    }

    /**
     * 在SD卡上创建目录；
     *
     * @param dirpath
     * @return
     */
    public static File createDIR(String dirpath) {
        File dir = new File(dirpath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    /**
     * 在SD卡上创建文件；
     *
     * @param filepath
     * @return
     * @throws IOException
     */
    public static File createFile(String filepath) throws IOException {
        File file = new File(filepath.trim());
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    /**
     * 将一个InputStream中的数据写入至SD卡中
     */
    public static File writeStream2SDCard(Context context, String dirpath, String filename, InputStream input) {
        File file = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)
                && PermisstionUtil.checkPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE, Constant.REQUEST_SYSTEM_WRITE_SD_CODE)) {
            OutputStream output = null;
            try {
                //创建目录；
                createDIR(SDRootDir() + dirpath);
                //在创建 的目录上创建文件；
                file = createFile(SDRootDir() + dirpath + filename);
                output = new FileOutputStream(file);
                byte[] bt = new byte[4 * 1024];
                while (input.read(bt) != -1) {
                    output.write(bt);
                }
                //刷新缓存，
                output.flush();
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    output.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }


    /**
     * 文件重命名
     *
     * @param filePath
     * @param newFilePath
     * @return
     */
    public static boolean renameFileName(String filePath, String newFilePath) {
        File oldfile = new File(filePath);
        File newfile = new File(newFilePath);
        if (!oldfile.exists()) {
            return false;//重命名文件不存在
        }
        if (newfile.exists())//若在该目录下已经有一个文件和新文件名相同，则不允许重命名
            return false;
        else {
            oldfile.renameTo(newfile);
            return true;
        }
    }
}
