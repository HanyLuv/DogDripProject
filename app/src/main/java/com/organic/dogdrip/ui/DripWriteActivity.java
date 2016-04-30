package com.organic.dogdrip.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.android.volley.VolleyError;
import com.organic.dogdrip.R;
import com.organic.dogdrip.aws.AWSUtil;
import com.organic.dogdrip.aws.S3Manager;
import com.organic.dogdrip.image.ImageUtil;
import com.organic.dogdrip.manager.UserInfoManager;
import com.organic.dogdrip.net.BaseApiResponse;
import com.organic.dogdrip.net.NetworkManager;
import com.organic.dogdrip.net.request.WriteDripRequest;
import com.organic.dogdrip.ui.dialog.FootProgressDialog;
import com.organic.dogdrip.utils.Log;
import com.organic.dogdrip.vo.drip.Drip;
import com.organic.dogdrip.vo.user.User;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Random;

/**
 * Created by kwonojin on 16. 4. 27..
 */
public class DripWriteActivity extends BaseActivity {

    private static final String TAG  = "DripWriteActivity";

    private ImageView ivBackground;
    private EditText etWrite;
    private Button btnWrite;
    private Button btnAddImage = null;
    private Uri mImageUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_write);
        initView();
    }

    private void initView(){

        etWrite = (EditText) findViewById(R.id.et_drip_write_input);
        btnWrite = (Button) findViewById(R.id.btn_drip_write_commit);
        btnAddImage  = (Button) findViewById(R.id.btn_drip_write_add_image);
        ivBackground = (ImageView) findViewById(R.id.iv_drip_write_image);
        ivBackground.setAlpha(0.5f);
        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etWrite.getText().toString()) && UserInfoManager.getInstance().getUserInfo() != null) {
                    Toast.makeText(DripWriteActivity.this, getResources().getString(R.string.write_empty_notice), Toast.LENGTH_SHORT).show();
                    return;
                }
                createAlertDialog(getString(R.string.write_notice), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        requestWriteDrip();
                        dialog.dismiss();
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
            }
        });

        btnAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent , 0 );
            }
        });
    }

    private void requestWriteDrip() {

        if(UserInfoManager.getInstance().getUserInfo() != null){
            final User user = UserInfoManager.getInstance().getUserInfo();
            final FootProgressDialog dialog = new FootProgressDialog(this);
            dialog.show();
            final WriteDripRequest writeRequst = new WriteDripRequest(this, new BaseApiResponse.OnResponseListener<Drip>() {
                @Override
                public void onResponse(BaseApiResponse<Drip> response) {
                    if (response != null && response.getData() != null && !TextUtils.isEmpty(response.getData().getAuthor())) {
                        Toast.makeText(DripWriteActivity.this, response.getData().getAuthor() + getResources().getString(R.string.write_welcome), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        finish();
                    }
                }

                @Override
                public void onError(VolleyError error) {
                    Toast.makeText(DripWriteActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            String dripContent = etWrite.getText().toString();
            writeRequst.setDataInfo(user.getEmail(), dripContent);
            if(mImageUri != null){
                new AsyncTask<Void, Void, File>() {
                    @Override
                    protected File doInBackground(Void... params) {
                        File file = null;
                        try {
                            String path = S3Manager.getInstance().getPath(mImageUri);
                            File imageFile = new File(path);
                            if(imageFile.length() > 1024 * 1000){
                                Bitmap originalImage = ImageUtil.getBitmapFromFile(imageFile);
                                Bitmap compressBitmap = ImageUtil.compressBitmap(originalImage, 10);
                                file = ImageUtil.saveBitmapToCache(DripWriteActivity.this , compressBitmap, user.getNickname() + "_" + new Random().nextInt());
                                compressBitmap.recycle();
                            }else{
                                file = imageFile;
                            }

                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }
                        return file;
                    }

                    @Override
                    protected void onPostExecute(final File file) {
                        super.onPostExecute(file);
                        if(file != null && file.exists()){
                            S3Manager.getInstance().uploadFile(file, new S3Manager.TransferDataListener() {
                                @Override
                                public void onStateChanged(int id, TransferState state) {

                                }

                                @Override
                                public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {

                                }

                                @Override
                                public void onError(int id, Exception ex) {
                                    Log.e(TAG, ex.toString());
                                    dialog.dismiss();
                                    Toast.makeText(DripWriteActivity.this, "이미지 업로드 실패", Toast.LENGTH_SHORT).show();
                                    file.delete();

                                }

                                @Override
                                public void onCompleteTransfer(File file, String url) {
                                    writeRequst.setImageUrl(url);
                                    file.delete();
                                    NetworkManager.getInstance().request(writeRequst);
                                }
                            });
                        }
                    }
                }.execute();
            }else{
                NetworkManager.getInstance().request(writeRequst);
            }
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.login_need);
            builder.setPositiveButton(getResources().getText(R.string.ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    UserInfoManager.getInstance().sendNeedLoginBroadcast();
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton(getResources().getText(R.string.cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create().show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            mImageUri = data.getData();
            try {
                String path = S3Manager.getInstance().getPath(mImageUri);
                ivBackground.setImageURI(mImageUri);
            } catch (URISyntaxException e) {
                Toast.makeText(this,
                        "Unable to get the file from the given URI.  See error log for details",
                        Toast.LENGTH_LONG).show();
                Log.e(TAG, "Unable to upload file from the given uri" + e);
            }
        }
    }
}
