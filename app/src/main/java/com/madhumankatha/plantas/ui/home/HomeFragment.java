package com.madhumankatha.plantas.ui.home;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.madhumankatha.plantas.MainActivity;
import com.madhumankatha.plantas.R;
import com.madhumankatha.plantas.databinding.FragmentHomeBinding;
import com.madhumankatha.plantas.models.ImageSearch;
import com.madhumankatha.plantas.models.Products;
import com.madhumankatha.plantas.network.RetrofitApp;
import com.madhumankatha.plantas.utlis.RecyclerViewClickInterface;
import com.madhumankatha.plantas.utlis.VerticalItemDeco;
import com.madhumankatha.plantas.utlis.VerticalSpaceItemDeco;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Fragment implements RecyclerViewClickInterface {

    public static final String TAG = MainActivity.class.getSimpleName();

    private static final int ACTIVITY_START_CAMERA_APP = 0;
    private String mImageFileLocation = "";

    private FragmentHomeBinding binding;
    private ProductsViewModel viewModel;
    private UserViewModel userViewModel;
    private ProductsAdapter adapter;
    private List<Products> _products;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater,container,false);

        viewModel = new ViewModelProvider(requireActivity())
                .get(ProductsViewModel.class);

        userViewModel = new ViewModelProvider(requireActivity())
                .get(UserViewModel.class);

        String username = userViewModel.getSelected().getValue().getUsername();
        binding.tvMenu.setText("Hello, " + username.toUpperCase());

        BottomNavigationView navBar = requireActivity().findViewById(R.id.nav_view);
        navBar.setVisibility(View.VISIBLE);

        binding.recyclerView.setHasFixedSize(true);

        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, LinearLayout.VERTICAL);
        //binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerView.setLayoutManager(manager);
        int verticalSpacing = 20;
        VerticalSpaceItemDeco itemDecorator =
                new VerticalSpaceItemDeco(verticalSpacing);
        VerticalItemDeco shadowItemDecorator =
                new VerticalItemDeco(requireContext(), R.drawable.drop_shadow);
        binding.recyclerView.addItemDecoration(itemDecorator);
        binding.recyclerView.addItemDecoration(shadowItemDecorator);


        binding.btnImageSearch.setOnClickListener(v -> {
            takePhoto(v);
        });

        viewModel.getProductsLiveData().observe(getViewLifecycleOwner(),products -> {
            if(products != null){
                _products = products;
                display(products);
            }else {
                Toast.makeText(requireContext(), "No Network!!", Toast.LENGTH_SHORT).show();
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                display(_products.stream().filter(products -> products.getName().contains(s)).collect(Collectors.toList()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void takePhoto(View view) {
        Intent callCameraApplicationIntent = new Intent();
        callCameraApplicationIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);

        File photoFile = null;
        try {
            photoFile = createImageFile();

        } catch (IOException e) {
            e.printStackTrace();
        }
        //callCameraApplicationIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));

        String authorities = requireActivity().getPackageName() + ".fileprovider";
        Uri imageUri = FileProvider.getUriForFile(requireContext(), authorities, photoFile);
        callCameraApplicationIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

        startActivityForResult(callCameraApplicationIntent, ACTIVITY_START_CAMERA_APP);


    }

    public void onActivityResult (int requestCode, int resultCode, Intent data) {
        if(requestCode == ACTIVITY_START_CAMERA_APP && resultCode == RESULT_OK) {
            // Toast.makeText(this, "Picture taken successfully", Toast.LENGTH_SHORT).show();
            // Bundle extras = data.getExtras();
            // Bitmap photoCapturedBitmap = (Bitmap) extras.get("data");
            // mPhotoCapturedImageView.setImageBitmap(photoCapturedBitmap);
            // Bitmap photoCapturedBitmap = BitmapFactory.decodeFile(mImageFileLocation);
            // mPhotoCapturedImageView.setImageBitmap(photoCapturedBitmap);
            setReducedImageSize();
        }
    }

    File createImageFile() throws IOException {

        String timeStamp = new SimpleDateFormat("HHmm").format(new Date());
        String imageFileName = "PLANTAS_" + timeStamp + "_";
        //File storageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File storageDirectory = requireContext().getFilesDir();

        File image = File.createTempFile(imageFileName,".jpg", storageDirectory);
        mImageFileLocation = image.getCanonicalPath();

        return image;

    }

    void setReducedImageSize() {
        int targetImageViewWidth = binding.imageView.getWidth();
        int targetImageViewHeight = binding.imageView.getHeight();;

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mImageFileLocation, bmOptions);
        int cameraImageWidth = bmOptions.outWidth;
        int cameraImageHeight = bmOptions.outHeight;

        int scaleFactor = Math.min(cameraImageWidth/targetImageViewWidth, cameraImageHeight/targetImageViewHeight);
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inJustDecodeBounds = false;

        Bitmap photoReducedSizeBitmp = BitmapFactory.decodeFile(mImageFileLocation, bmOptions);
        binding.imageView.setImageBitmap(photoReducedSizeBitmp);

        uploadFile(mImageFileLocation);
    }

    private void uploadFile(String imagePath){
        File image = new File(imagePath);

        Log.d(TAG, "uploadFile: " + imagePath);

        // Request Body For Image
        RequestBody requestImage = RequestBody.create(MediaType.parse("image/jpg"), image);

        MultipartBody.Part fileToUpload = MultipartBody.Part
                .createFormData("file",image.getName(), requestImage);

        //Request Bod to send Text
        RequestBody imageDesc = RequestBody.create(MediaType.parse("text/plain"),"Madhu");

        RetrofitApp.getInstance().getAppApi().uploadImage(fileToUpload,imageDesc).enqueue(new Callback<ImageSearch>() {
            @Override
            public void onResponse(Call<ImageSearch> call, Response<ImageSearch> response) {
                Log.d(TAG, "onResponse: " + response.message());

                if (response.isSuccessful()){
                    Log.d(TAG, "onResponse: " + response.body().isStatus());
                    Log.d(TAG, "onResponse: " + response.body().getMessage());

                    String name = response.body().getMessage();
                    String caps = name.substring(0, 1).toUpperCase() + name.substring(1);
                    binding.edSearch.setText(caps);
                    //binding.btnCapture.setText(response.body().getMessage());
                }


            }

            @Override
            public void onFailure(Call<ImageSearch> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                Log.d(TAG, "onFailure: " + t.toString());
            }
        });
    }

    @Override
    public void onItemClickListener(View view, int position) {
        Log.d("TAG", "onItemClickListener: " + position);
        viewModel.setSelected(adapter.getArticleAt(position));
        Navigation.findNavController(view).navigate(R.id.action_navigation_home_to_detailsProductsFragments);
    }

    private void display(List<Products> products){
        adapter = new ProductsAdapter(products,this::onItemClickListener,requireContext());
        binding.recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}