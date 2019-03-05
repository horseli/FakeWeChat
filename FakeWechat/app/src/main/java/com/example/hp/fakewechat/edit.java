package com.example.hp.fakewechat;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

public class edit extends AppCompatActivity {

    private final int CHOOSE_PHOTO = 2;
    private TextInputLayout textInputLayout;
    private TextInputEditText textInputEditText;
    private ImageView imageView;
    private Person person;
    private MydatabaseHelper mydatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Intent intent = getIntent();
        mydatabaseHelper = new MydatabaseHelper(this, "Person.db", null, 1);
        person = (Person) intent.getSerializableExtra("person");
        imageView = (ImageView) findViewById(R.id.imageView_edit);
        imageView.setImageBitmap(BitmapFactory.decodeFile(person.getImg()));
        textInputLayout = (TextInputLayout) findViewById(R.id.textInputLayout_edit);
        Button chooseFromAlbum = (Button) findViewById(R.id.choose_from_album_edit);
        textInputEditText = (TextInputEditText) findViewById(R.id.textInputEditText);
        textInputEditText.setText(person.getName());
        chooseFromAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(edit.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(edit.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    openAlbum();
                }
            }
        });
        final Button delete_button = (Button) findViewById(R.id.button_delete);
        final Button edit_button = (Button) findViewById(R.id.button_edit);
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase sqLiteDatabase = mydatabaseHelper.getWritableDatabase();
                sqLiteDatabase.delete("Person","id=?",new String[] {String.valueOf(person.getId())});
                delete_button.setEnabled(false);
                finish();
            }
        });
        edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = ((BitmapDrawable) ((ImageView) imageView).getDrawable()).getBitmap();
                File sdRoot = Environment.getExternalStorageDirectory();
                File file1 = new File(sdRoot,"img/");
                File file;
                if(!file1.exists())
                    file1.mkdirs();
                do {
                    UUID uuid = UUID.randomUUID();
                    String file_name = uuid.toString();
                    file = new File(file1,file_name + ".png");
                } while(file.exists());
                try {
                    file = new File(file1,file.getName());
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG,10,fileOutputStream);
                    fileOutputStream.flush();
                    fileOutputStream.close();
                }catch (Exception e){

                }
                SQLiteDatabase sqLiteDatabase = mydatabaseHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("name", textInputLayout.getEditText().getText().toString());
                values.put("img", file.getAbsolutePath());
                sqLiteDatabase.update("Person", values, "id=?", new String[]{String.valueOf(person.getId())});
                edit_button.setEnabled(false);
                finish();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    openAlbum();
                } else {
                    Toast.makeText(this,"You denied the permission",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    private void openAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        switch (requestCode) {
            case CHOOSE_PHOTO:

                //判断手机系统版本号
                if (Build.VERSION.SDK_INT >= 19) {
                    //4.4及以上系统使用该方法处理图片
                    handleImageOnKitKat(data);
                } else {
                    //4.4以下系统使用该方法处理图片
                    handleImageBeforeKitKat(data);
                }
                break;
            default:
                break;
        }
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            //如果是document类型的Uri,则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.document".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];//解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        selection);
                Log.i("TEST", "1");
            } else if ("com.android.providers.downloads.documents".equals
                    (uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse(
                        "content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
                Log.i("TEST", "1");
            }

        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            //如果是content类型的Uri，则使用普通方法处理
            imagePath = getImagePath(uri, null);
            Log.i("TEST", "2");
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            //如果是file类型的Uri，直接获取图片路径即可
            imagePath = uri.getPath();
            Log.i("TEST", "3");
        }
        displayImage(imagePath);
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        //通过uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.
                        Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath) {
        if(imagePath !=null){
            Bitmap bit = BitmapFactory.decodeFile(imagePath);
            Matrix matrix = new Matrix();
            matrix.setScale(0.2f, 0.2f);
            Bitmap bm = Bitmap.createBitmap(bit, 0, 0, bit.getWidth(),
                    bit.getHeight(), matrix, true);
            imageView.setImageBitmap(bm);
        }else{
            Toast.makeText(this, "failed to get image", Toast.LENGTH_LONG).show();
        }
    }
}
