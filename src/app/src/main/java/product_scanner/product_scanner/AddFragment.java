package product_scanner.product_scanner;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

/**
 * Created by Nguyen Khang on 12/7/2017.
 */

public class AddFragment extends Fragment {
    private EditText price,name,barcode;
    private ImageView imageView;
    private Bitmap img;
   private Button next,pickimage;
    private Spinner spinner;
    private ArrayAdapter<CharSequence> adapter;
    private final int CAMERA_REQUEST=101,PICK_IMAGE=102;
    private ProgressDialog progressDialog;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_insert, container, false);
        findView(v);
        setUpSpinner();
        setOnClick();
        if( !((ResideMenu)getActivity()).barcodeid.equals(""))
            this.barcode.setText(((ResideMenu)getActivity()).barcodeid);

    v.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if(motionEvent.getAction()== MotionEvent.ACTION_DOWN){
                ((ResideMenu)getActivity()).hidekeyboard(view);
            }
            return true;

        }
    });

        return v;

    }
    private void setUpProgressDialog(String title,String mes){
        progressDialog=new ProgressDialog(getContext());
        progressDialog.setTitle(title);
        progressDialog.setMessage(mes);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }

    private void setOnClick() {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    return ;
                }
                Intent i= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i,CAMERA_REQUEST);
            }
        });
        pickimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isFilled()) {
                    if (((ResideMenu) getActivity()).internet.isOnline()) {
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        img.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        byte[] data = baos.toByteArray();
                        StorageReference ref = MyFirebaseStorage.storageRef.child(((ResideMenu) getActivity()).barcodeid + ".jpg");
                        setUpProgressDialog(getResources().getString(R.string.upload), getResources().getString(R.string.please_wait) + "...");
                        UploadTask uploadTask = ref.putBytes(data);
                        uploadTask.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle unsuccessful uploads
                                progressDialog.dismiss();
                                Toast.makeText(getContext(), getResources().getString(R.string.please_sign_in), Toast.LENGTH_SHORT).show();
                            }
                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                                MyFirebaseDatabase.uploadData(progressDialog, getContext(), name.getText().toString(), barcode.getText().toString(), price.getText().toString(), spinner.getSelectedItem().toString(), downloadUrl.toString());
                            }
                        });
                    }
                    else
                        Toast.makeText(getContext(),getResources().getString(R.string.please_turn_on_the_internet),Toast.LENGTH_SHORT).show();


                }
                else{
                    Toast.makeText(getContext(),getResources().getString(R.string.please_fill_the_blank),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isFilled() {
        return (!price.getText().toString().equals("") && !name.getText().toString().equals(""));
    }

    private void findView(View v) {
        price=v.findViewById(R.id.input_price);
        name=v.findViewById(R.id.input_name);
        barcode=v.findViewById(R.id.input_id);
        spinner=v.findViewById(R.id.spinner_place);
        imageView=v.findViewById(R.id.image);
         next=v.findViewById(R.id.btn_next);
        pickimage=v.findViewById(R.id.btn_getImage);
          img= BitmapFactory.decodeResource(getResources(),R.drawable.product_icon);
          imageView.setImageBitmap(img);
    }
    private void setUpSpinner(){
        adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.place_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode== Activity.RESULT_OK) {
            switch (requestCode){
                case CAMERA_REQUEST:{
                    img=(Bitmap) data.getExtras().get("data");
                    imageView.setImageBitmap(img);
                    break;
                }
                case PICK_IMAGE:{

                         Uri selectedImage = data.getData();

                    try {
                        img = decodeUri(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(),getResources().getString(R.string.please_choose_image_from_local_folder),Toast.LENGTH_SHORT).show();
                    }
                    imageView.setImageBitmap(img);


                    break;
                }

            }

        }
    }


    private Bitmap decodeUri(Uri selectedImage) throws FileNotFoundException {

        // Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream((getActivity()).getContentResolver().openInputStream(selectedImage), null, o);

        // The new size we want to scale to
        final int REQUIRED_SIZE = 140;

        // Find the correct scale value. It should be the power of 2.
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp / 2 < REQUIRED_SIZE
                    || height_tmp / 2 < REQUIRED_SIZE) {
                break;
            }
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        // Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream((getActivity()).getContentResolver().openInputStream(selectedImage), null, o2);

    }
}
