package com.organic.dogdrip.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
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
import com.organic.dogdrip.aws.S3Manager;
import com.organic.dogdrip.manager.UserInfoManager;
import com.organic.dogdrip.net.BaseApiResponse;
import com.organic.dogdrip.net.NetworkManager;
import com.organic.dogdrip.net.request.WriteDripRequest;
import com.organic.dogdrip.utils.Log;
import com.organic.dogdrip.vo.drip.Drip;

import java.net.URISyntaxException;

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
                });
            }
        });

        btnAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    // For Android KitKat, we use a different intent to ensure
                    // we can
                    // get the file path from the returned intent URI
                    intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);

                } else {
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                }
                intent.setType("image/*");
                startActivityForResult(intent, 0);
            }
        });
    }


    private void requestWriteDrip() {
        if(UserInfoManager.getInstance().getUserInfo() != null){
            WriteDripRequest writeRequst = new WriteDripRequest(this, new BaseApiResponse.OnResponseListener<Drip>() {
                @Override
                public void onResponse(BaseApiResponse<Drip> response) {
                    if (response != null && response.getData() != null && !TextUtils.isEmpty(response.getData().getAuthor())) {
                        Toast.makeText(DripWriteActivity.this, response.getData().getAuthor() + getResources().getString(R.string.write_welcome), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }

                @Override
                public void onError(VolleyError error) {
                    Toast.makeText(DripWriteActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            //TODO : Have to fix blow codes
            String dripContent = etWrite.getText().toString();
            writeRequst.setDataInfo(UserInfoManager.getInstance().getUserInfo().getEmail(), dripContent);
            NetworkManager.getInstance().request(writeRequst);
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
                String path = getPath(mImageUri);
                ivBackground.setImageURI(mImageUri);
//                S3Manager.getInstance().uploadFile(path, new TransferListener() {
//                    @Override
//                    public void onStateChanged(int id, TransferState state) {
//
//                    }
//
//                    @Override
//                    public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
//
//                    }
//
//                    @Override
//                    public void onError(int id, Exception ex) {
//
//                    }
//                });
            } catch (URISyntaxException e) {
                Toast.makeText(this,
                        "Unable to get the file from the given URI.  See error log for details",
                        Toast.LENGTH_LONG).show();
                Log.e(TAG, "Unable to upload file from the given uri" + e);
            }
        }
    }

    /*
     * Gets the file path of the given Uri.
     */
    @SuppressLint("NewApi")
    private String getPath(Uri uri) throws URISyntaxException {
        final boolean needToCheckUri = Build.VERSION.SDK_INT >= 19;
        String selection = null;
        String[] selectionArgs = null;
        // Uri is different in versions after KITKAT (Android 4.4), we need to
        // deal with different Uris.
        if (needToCheckUri && DocumentsContract.isDocumentUri(getApplicationContext(), uri)) {
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
                cursor = getContentResolver()
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
