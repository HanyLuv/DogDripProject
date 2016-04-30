package com.organic.dogdrip.aws;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferType;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.organic.dogdrip.utils.Log;
import com.organic.dogdrip.vo.config.AppConfig;

import java.io.File;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Created by kwonojin on 16. 4. 29..
 */
public class S3Manager {

    private static final String TAG  = "S3Manager";

    public interface TransferDataListener extends TransferListener{

        void onCompleteTransfer(File file, String url);
    }

    private TransferUtility transferUtility;
    private Context mContext = null;
    private AppConfig mAppConfig = null;

    private static S3Manager sInstance = null;

    public static S3Manager getInstance(){
        if(sInstance == null){
            throw new IllegalStateException("S3Manager must be init in Application");
        }
        return sInstance;
    }

    public static void init(Context context, AppConfig config){
        if(sInstance == null){
            sInstance = new S3Manager(context, config);
        }
    }

    private S3Manager(Context context, AppConfig config){
        mContext = context;
        mAppConfig = config;
        transferUtility = AWSUtil.getTransferUtility(mContext, getAppConfig().getCognitoPoolId());
    }

    public List<TransferObserver> getUploadTransfers(TransferListener ll){
        List<TransferObserver> list = transferUtility.getTransfersWithType(TransferType.UPLOAD);
        for(TransferObserver o : list){
            o.setTransferListener(ll);
        }
        return list;
    }

    public void uploadFile(Uri uri, TransferDataListener ll){
        try {
            String path  = getPath(uri);
            uploadFile(path, ll);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            if(ll != null){
                ll.onError(0, e);
            }
        }
    }

    public void uploadFile(String filePath, TransferDataListener ll){
        uploadFile(new File(filePath), ll);
    }

    public void uploadFile(final File file, final TransferDataListener ll) {
        if(file != null && file.exists()){
            TransferObserver observer = transferUtility.upload(getAppConfig().getBuketName(), file.getName(),
                    file);
        /*
         * Note that usually we set the transfer listener after initializing the
         * transfer. However it isn't required in this sample app. The flow is
         * click upload button -> start an activity for image selection
         * startActivityForResult -> onActivityResult -> beginUpload -> onResume
         * -> set listeners to in progress transfers.
         */
            // observer.setTransferListener(new UploadListener());
            observer.setTransferListener(new TransferDataListener() {
                @Override
                public void onCompleteTransfer(File file, String url) {
                    if(ll != null){
                        ll.onCompleteTransfer(file, url);
                    }
                }

                @Override
                public void onStateChanged(int id, TransferState state) {
                    Log.d(TAG, "onStateChanged: state = " + state);
                    Log.d(TAG, "onStateChanged: id = " + id);
                    if(state == TransferState.COMPLETED){
                        String filePath = transferUtility.getTransferById(id).getAbsoluteFilePath();
                        if(filePath != null){
                            String url = AWSUtil.getS3Client(mContext, getAppConfig().getCognitoPoolId()).getResourceUrl(getAppConfig().getBuketName(), file.getName());
                            Log.d(TAG, "onStateChanged: url = " + url);
                            onCompleteTransfer(new File(filePath), url);
                        }
                    }
                    if(ll != null){
                        ll.onStateChanged(id, state);
                    }
                }

                @Override
                public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                    Log.d(TAG, "currentByte = " + bytesCurrent + " / total = " + bytesTotal);
                    if(ll != null){
                        ll.onProgressChanged(id, bytesCurrent, bytesTotal);
                    }

                }

                @Override
                public void onError(int id, Exception ex) {
                    ex.printStackTrace();
                    if(ll != null){
                        ll.onError(id, ex);
                    }
                }
            });

        }
    }

    public String getBucketName(){
        return getAppConfig().getBuketName();
    }

    public TransferUtility getTransferUtility(){
        return transferUtility;
    }

    private AppConfig getAppConfig(){
        return mAppConfig;
    }


    /*
     * Gets the file path of the given Uri.
     */
    @SuppressLint("NewApi")
    public String getPath(Uri uri) throws URISyntaxException {
        final boolean needToCheckUri = Build.VERSION.SDK_INT >= 19;
        String selection = null;
        String[] selectionArgs = null;
        // Uri is different in versions after KITKAT (Android 4.4), we need to
        // deal with different Uris.
        if (needToCheckUri && DocumentsContract.isDocumentUri(mContext.getApplicationContext(), uri)) {
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                return Environment.getExternalStorageDirectory() + "/" + split[1];
            } else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                uri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
            } else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("image".equals(type)) {
                    uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                selection = "_id=?";
                selectionArgs = new String[] {
                        split[1]
                };
            }
        }
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {
                    MediaStore.Images.Media.DATA
            };
            Cursor cursor = null;
            try {
                cursor = mContext.getContentResolver()
                        .query(uri, projection, selection, selectionArgs, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
}
