package com.organic.dogdrip.aws;

import android.content.Context;

import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferType;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.organic.dogdrip.utils.Log;
import com.organic.dogdrip.vo.config.AppConfig;

import java.io.File;
import java.util.List;

/**
 * Created by kwonojin on 16. 4. 29..
 */
public class S3Manager {

    private static final String TAG  = "S3Manager";

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

    public void uploadFile(String filePath, TransferListener ll){
        uploadFile(new File(filePath), ll);
    }

    public void uploadFile(final File file, final TransferListener ll) {
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
            observer.setTransferListener(new TransferListener() {
                @Override
                public void onStateChanged(int id, TransferState state) {
                    if(state == TransferState.COMPLETED){
                        String filePath = transferUtility.getTransferById(id).getAbsoluteFilePath();
                        if(filePath != null){
                            String url = AWSUtil.getS3Client(mContext, getAppConfig().getCognitoPoolId()).getResourceUrl(getAppConfig().getBuketName(), file.getName());
                            Log.d(TAG, "onStateChanged: url = " + url);
                        }
                    }
                    if(ll != null){
                        ll.onStateChanged(id, state);
                    }
                }

                @Override
                public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                    if(ll != null){
                        ll.onProgressChanged(id, bytesCurrent, bytesTotal);
                    }
                }

                @Override
                public void onError(int id, Exception ex) {
                    if(ll != null){
                        ll.onError(id, ex);
                    }
                }
            });
        }
    }

    private AppConfig getAppConfig(){
        return mAppConfig;
    }

}
