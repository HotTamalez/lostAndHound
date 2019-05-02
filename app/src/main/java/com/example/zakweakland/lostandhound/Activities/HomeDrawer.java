package com.example.zakweakland.lostandhound.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zakweakland.lostandhound.Fragments.ChatFragment;
import com.example.zakweakland.lostandhound.Fragments.HomeFragment;
import com.example.zakweakland.lostandhound.Fragments.ProfileFragment;
import com.example.zakweakland.lostandhound.Fragments.SettingsFragment;
import com.example.zakweakland.lostandhound.Fragments.SubmitFragment;
import com.example.zakweakland.lostandhound.Models.Post;
import com.example.zakweakland.lostandhound.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class HomeDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

        FirebaseAuth mAuth;
        FirebaseUser currentUser;
        private static final int PRegCde = 2;
        private static final int REQUEST_CODE = 2;
        public Spinner dropDownBreed;
        Dialog popupAddPost;
        ImageView popupImagePost;
        EditText popupDogName, popupDogAge, popupAdditionalInfo;
        Button popupAddPostBtn;
        List<String> dogBreeds = new ArrayList<>();
        private Uri pickedImageUri = null;
        String[] dogBreedList = { "Select a Dog Breed","Affenpinscher", "Afghan Hound", "Ainu", "Airedale Terrier", "Akita Alaskan Husky",
                "Alaskan Klee Kai", "Alaskan Malamute", "American Bulldog", "American Eskimo Dog", "American Foxhound",
                "American Pit Bull Terrier", "American Staffordshire Terrier", "American Water Spaniel", "Anatolian Shepherd Dog",
                "Australian Cattle Dog", "Australian Shepherd", "Australian Terrier", "Basenji", "Basset Hound", "Beagle",
                "Bearded Collie", "Beauceron", "Bedlington terrier", "Belgian Malinois", "Belgian sheepdog", "Belgian Tervuren",
                "Bergamasco", "Bernese Mountain Dog", "Bichon Frise", "Black and Tan Coonhound", "Black Russian Terrier",
                "Bloodhound", "Bolognese", "Border Collie", "Border Terrier", "Borzoi", "Boston Terrier",
                "Bouvier des Flandres", "Boxer", "Biard", "Brittany" ,"Brussels Griffon", "Bull Terrier", "Bulldog",
                "Bullmastiff", "Cairn Terrier", "Canaan Dog", "Cane Corso", "Cardigan Welsh Corgi", "Catahoula Leopard Dog",
                "Cavalier King Charles Spaniel", "Cesky Terrier", "Chesapeake Bey Retriever", "Chihuahua", "Chinese Crested",
                "Chinese Shar-Pei", "Chinook", "Chow Chow", "Clumber Spaniel", "Cocker Spaniel", "Collie", "Coton de tular",
                "Curly-coated Retriever", "Dauchshund", "Dalmation", "Dandie Dinmount Terrier", "Dingo", "Doberman Pinscher",
                "English Cocker Spaniel", "English Foxhoud", "English Setter", "English Springer Spaniel", "English Toy Spaniel",
                "Estrela Mountain Dog", "Field Spaniel", "Finnish Spitz", "Flat-coated Retriever", "French Bulldog",
                "German Pinscher", "German Shepard", "German short-haired Pointer", "German wire-haired Pointer",
                "Giant Schnauzer", "Glen of Imaal Terrier", "Golden Retriever", "Gordon Setter", "Great Dane", "Great Pyreness",
                "Greater Swiss mountain dog", "Greyhound", "Harrier", "Havanese", "Ibizan Hound", "Icelandic Sheepdog",
                "Irish Red and White Setter", "Irish Setter", "Irish Terrier", "Irish Water Spaniel", "Irish Wolfhound",
                "Italian Greyhound", "Jack Russell Terrier", "Japanese Chin", "Japanese Terrier", "Keeshond", "Kerry Beagle",
                "Kerry Blue Terrier", "Komondor", "Kuvasz", "Labradoodle", "Labrador Retreiver", "Lakeland Terrier",
                "Lancashire Heeler", "Leonberber", "Lhasa Apso", "Lowchen", "Maltese", "Manchester Terrier", "Mastiff",
                "Miniature Bull Terrier", "Miniature Pinscher", "Miniature Schnauzer", "Neapolitan Mastiff",
                "Newfoundland", "Norfolk Terrier", "Nowegian Elkhound", "Norewegian Lundehund", "Norwich Terrier",
                "Nova Scotia Duck Tolling Retriever", "Old English Sheepdog", "Otterhound", "Papillon", "Parson Russell Terrier",
                "Pekingese", "Pembroke Welsh Corgi", "Petit Basset Griffon Vendeen", "Pharaoh Hound", "Plott", "Pointer",
                "Ploish Lowland Sheepdog", "Pomeranian", "Poodle", "Portuguese Water Dog", "Pug", "Puli",
                "Rat Terrier", "Redbone Coonhound", "Rhodesian Ridgeback", "Rottweiler", "Saint Bernard", "Saluki",
                "Samoyed", "Schipperke", "Scottish Deerhound", "Sealyham Terrier", "Shetland Sheepdog", "Shiba Inu",
                "Shih Tzu", "Siberian Husky", "Silky Terrier", "Skye Terrier", "Sloughi", "Smooth Fox Terrier",
                "Soft Coated Wheaten Terrier", "Spanish Mastiff", "Spinone Italiano", "Staffordshire Bull Terrier",
                "Standard Shnauzer", "Sussex Spaniel", "Swedish Vallhund", "Thai Ridgeback", "Tibetan Mastiff",
                "Tibetan Terrier", "Toy Fox Terrier", "Toy Manchester", "Toy Poodle", "Transylvanian Hound",
                "Vizsla", "Volpino Italiano", "Weimaraner", "Welsh Springer Spaniel", "Welsh Terrier",
                "West Highland White Terrier", "Whippet", "Wire Fox Terrier", "Wirehaired Pointing Griffon",
                "Xoloitzcuintli", "Yorkshire Terrier" };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_drawer2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();


        initPopup();
        setupPopupImageClick();



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               popupAddPost.show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        updateNavHeader();

        // set home fragment as default

        getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();
    }

    // click image to open gallery to post image
    private void setupPopupImageClick() {
        popupImagePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestForPermission();
            }
        });
    }

    // get user permission for gallery
    private void requestForPermission(){
        if (ContextCompat.checkSelfPermission(HomeDrawer.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(HomeDrawer.this, Manifest.permission.READ_EXTERNAL_STORAGE)){
                Toast.makeText(HomeDrawer.this, "Please click accept for required permission.", Toast.LENGTH_SHORT).show();
            }
            else {
                ActivityCompat.requestPermissions(HomeDrawer.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PRegCde);
            }
        }
        else {
            openGallery();
        }
    }

    // open gallery and wait for user to finish
    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE && data != null){
            // user has picked image
            // save image reference to Uri variable
            pickedImageUri = data.getData();
            popupImagePost.setImageURI(pickedImageUri);

        }
    }

    // start popup view
    private void initPopup() {
        popupAddPost = new Dialog(this);
        popupAddPost.setContentView(R.layout.popup_add_post);
        popupAddPost.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupAddPost.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.WRAP_CONTENT);
        popupAddPost.getWindow().getAttributes().gravity = Gravity.TOP;


        // spinner
        dropDownBreed = popupAddPost.findViewById(R.id.submitDogBreedSpinner);
        // image
        popupImagePost = popupAddPost.findViewById(R.id.submitDogPhoto);
        // dog name and age
        popupDogName = popupAddPost.findViewById(R.id.submitDogName);
        popupDogAge = popupAddPost.findViewById(R.id.submitDogAge);
        // additional info
        popupAdditionalInfo = popupAddPost.findViewById(R.id.submitAdditionalInformation);
        // submit post button
        popupAddPostBtn = popupAddPost.findViewById(R.id.submitLostDogButton);

        // put items in spinner
        for (String s: dogBreedList) {
            dogBreeds.add(s);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, dogBreeds){

            @Override
            public boolean isEnabled(int position) {
                if (position == 0){
                    return false;
                } return true;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);

                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        dropDownBreed.setAdapter(adapter);


        popupAddPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (dropDownBreed.getSelectedItem() != "Select a Dog Breed"
                        && !popupDogName.getText().toString().isEmpty()
                        && !popupDogAge.getText().toString().isEmpty()
                        && !popupAdditionalInfo.getText().toString().isEmpty()
                        && pickedImageUri != null ) {

                    //fields are checked
                    //create post object and add to firebase

                    StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("post_images");
                    final StorageReference imageFilePath = storageReference.child(pickedImageUri.getLastPathSegment());
                    imageFilePath.putFile(pickedImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String imageDownloadLink = uri.toString();
                                    // create Post object

                                    Post post = new Post(dropDownBreed.getSelectedItem().toString(),
                                            popupDogName.getText().toString(),
                                            popupDogAge.getText().toString(),
                                            popupAdditionalInfo.getText().toString(),
                                            imageDownloadLink,
                                            currentUser.getUid());

                                    addPost(post);

                                }


                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    showMessage(e.getMessage());
                                }
                            });

                        }
                    });


                }
                else {
                    showMessage("Please make sure all fields are filled out properly");
                }

            }
        });

    }

    private void addPost(Post post) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Posts").push();

        // generate unique id
        String key = reference.getKey();
        post.setPostKey(key);

        // add post data to firebase
        reference.setValue(post).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                showMessage("Post Added Successfully");
                popupAddPost.dismiss();
            }
        });



    }

    private void showMessage(String message) {
        Toast.makeText(HomeDrawer.this, message, Toast.LENGTH_LONG).show();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            getSupportActionBar().setTitle("Home");
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();

        } else if (id == R.id.nav_profile) {
            getSupportActionBar().setTitle("Profile");
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new ProfileFragment()).commit();

        } else if (id == R.id.nav_chat) {
            getSupportActionBar().setTitle("Chat");
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new ChatFragment()).commit();

        } else if (id == R.id.nav_submit) {
            getSupportActionBar().setTitle("Submit");
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new SubmitFragment()).commit();

        } else if (id == R.id.nav_settings) {
            getSupportActionBar().setTitle("Settings");
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new SettingsFragment()).commit();

        } else if (id == R.id.nav_logout) {

            FirebaseAuth.getInstance().signOut();
            Intent loginActivity = new Intent(this, LoginActivity.class);
            startActivity(loginActivity);
            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void updateNavHeader(){

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View viewHeader = navigationView.getHeaderView(0);
        TextView navUsername = viewHeader.findViewById(R.id.nav_username);
        TextView navEmail = viewHeader.findViewById(R.id.nav_user_email);
        ImageView navUserPhoto = viewHeader.findViewById(R.id.nav_user_photo);

        navEmail.setText(currentUser.getEmail());
        navUsername.setText(currentUser.getDisplayName());

        // use glide for user photo when photo is implemented


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.
                INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (popupAddPost != null) {
            popupAddPost.dismiss();
            popupAddPost = null;
        }
    }
}
