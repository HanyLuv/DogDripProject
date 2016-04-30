package com.organic.dogdrip.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by kwonojin on 16. 5. 1..
 */
public class ImageUtil {

    public static Bitmap getBitmapFromFile(File file){
        Bitmap bm = null;
        if(file != null && file.exists()){
            bm = BitmapFactory.decodeFile(file.getAbsolutePath(), null);
        }
        return bm;
    }

    public static Bitmap compressBitmap(Bitmap bm, int percent){
        Bitmap compressBitmap = null;
        if(bm != null && !bm.isRecycled()){
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, percent, baos);//품질 압축 방법, 여기 100 표시 안 압축, 그 압축 후 데이터 저장 중... baos
            ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//이 압축 후 데이터 baos 저장...ByteArrayInputStream중
            compressBitmap = BitmapFactory.decodeStream(isBm, null, null);//자루ByteArrayInputStream데이터 생성 그림
        }
        return compressBitmap;
    }

    public static File saveBitmapToCache(Context context, Bitmap bitmap, @Nullable String fileName){
        File tempImage = new File(context.getCacheDir().getAbsolutePath() + "/" + fileName);
        if(bitmap != null && !bitmap.isRecycled()) {
            FileOutputStream out = null;
            try {
                tempImage.createNewFile();
                out = new FileOutputStream(tempImage);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return tempImage;
    }
}
