package com.medavarsity.user.medavarsity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.medavarsity.user.medavarsity.Adapters.CollegeAdapter;
import com.medavarsity.user.medavarsity.Constants.CommonMethods;
import com.medavarsity.user.medavarsity.Model.CollegeModel;
import com.medavarsity.user.medavarsity.Model.CollegeResponse;
import com.medavarsity.user.medavarsity.Model.RegisterStudentResponse;
import com.medavarsity.user.medavarsity.Model.StudentResponse;
import com.medavarsity.user.medavarsity.NetworkCalls.ApiClient;
import com.medavarsity.user.medavarsity.NetworkCalls.ApiInterface;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterScreen extends AppCompatActivity {

    ImageView user_image;
    EditText editText_useremail, editText_userpassword, editText_username, editText_contactnum;
    Button btn_SignUp;
    Spinner spinner_collegeSelection, spinner_yearSelection;
    RadioButton male_radio, female_radio;
    ApiInterface apiInterface;
    TextView signIn;
    String selected_college = "";
    String selected_year = "";
    String selected_gender = "";
    ArrayList<CollegeModel> collegeModelArrayList;
    CollegeAdapter collegeAdapter;
    String[] years = new String[]{"Select Year", "1990", "2001", "2003", "2004", "2005", "2006", "2009"};
    LoginButton facebook_register;
    CallbackManager callbackManager;
    ProgressDialog progressBar;
    Button btn_custom_fb_login;
    LoginManager loginManager;
    List<String> permissionNeeds = Arrays.asList(/*"user_photos",*/ "email",
            /*"user_birthday",*/ "public_profile");

    Button fb_custom;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);
        initializeIds();
        callbackManager = CallbackManager.Factory.create();
        loginManager = LoginManager.getInstance();

        spinner_yearSelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_year = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        progressBar = new ProgressDialog(RegisterScreen.this);

      /*  btn_custom_fb_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginManager.logInWithReadPermissions(RegisterScreen.this, permissionNeeds);
            }
        });*/
        user_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePicker();
            }
        });
        btn_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (CommonMethods.isNetworkAvailable(RegisterScreen.this)) {
                    doRegisterStudent();
                } else {
                    Toast.makeText(RegisterScreen.this, "Please check Internet connection", Toast.LENGTH_SHORT).show();
                }

            }
        });

        male_radio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    female_radio.setChecked(false);
                    selected_gender = "Male";
                }
            }
        });
        female_radio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    male_radio.setChecked(false);
                    selected_gender = "Female";
                }
            }
        });

        loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                System.out.println(loginResult);
                getUserDetails(loginResult);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

                System.out.println(error.toString());
            }
        });


        fb_custom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                facebook_register.performClick();
            }
        });
        facebook_register.setReadPermissions(permissionNeeds);


        facebook_register.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                System.out.println(loginResult);
                getUserDetails(loginResult);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

                if (error.getMessage().contains("CONNECTION_FAILURE:")) {
                    Toast.makeText(RegisterScreen.this, "Please check your internet connections", Toast.LENGTH_SHORT).show();
                }
                System.out.println(error.toString());
            }
        });


    }

    private void getUserDetails(LoginResult loginResult) {
        GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {

                String loginresultResponse = object.toString();
                System.out.println("facebook object" + " " + loginresultResponse);
                try {
                    JSONObject jsonObject = new JSONObject(loginresultResponse);
                    StudentResponse studentResponse = new StudentResponse();
                    studentResponse.setFacebook_id(jsonObject.has("id") ? jsonObject.getString("id") : "");
                    studentResponse.setEmail(jsonObject.has("email") ? jsonObject.getString("email") : "");
                    studentResponse.setName(jsonObject.has("name") ? jsonObject.getString("name") : "");
                    JSONObject pictureObject = jsonObject.getJSONObject("picture");
                    JSONObject dataObj = pictureObject.getJSONObject("data");
                    String imageUrl = dataObj.has("url") ? dataObj.getString("url") : "";

                    studentResponse.setImage_url(imageUrl);
                    fillData(studentResponse);
                   /* Intent intent = new Intent(RegisterScreen.this,
                            DashBoard.class);
                    intent.putExtra("student_info", studentResponse);*/
                    // startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                System.out.println(loginresultResponse);
            }
        });

        Bundle permission_param = new Bundle();
        permission_param.putString("fields", "id,name,email,picture.width(100).height(100)");
        graphRequest.setParameters(permission_param);
        graphRequest.executeAsync();

    }

    ArrayAdapter<String> yearsadapter;

    private void doRegisterStudent() {

        String email = editText_useremail.getText().toString().trim();
        String password = editText_userpassword.getText().toString().trim();
        String username = editText_username.getText().toString().trim();
        String number = editText_contactnum.getText().toString().trim();

        if (email.equalsIgnoreCase("") || password.equalsIgnoreCase("") || username.equalsIgnoreCase("")
                || number.equalsIgnoreCase("") || selected_college.equalsIgnoreCase("") || selected_year.equalsIgnoreCase("")
            /* || selected_gender.equalsIgnoreCase("")*/) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        } else if (selected_year.equalsIgnoreCase("Select Year") || selected_college.equalsIgnoreCase("Select college")) {
            Toast.makeText(this, "Please select valid values", Toast.LENGTH_SHORT).show();
        } else {
            progressBar.setMessage("Please wait...");
            progressBar.show();
            Call<RegisterStudentResponse> createStudentCall = apiInterface.registerStudent(username, email, number,
                    password, selected_college, selected_year);
            createStudentCall.enqueue(new Callback<RegisterStudentResponse>() {
                @Override
                public void onResponse(Call<RegisterStudentResponse> call, Response<RegisterStudentResponse> response) {

                    RegisterStudentResponse registerStudentResponse = response.body();
                    System.out.println("register response: " + registerStudentResponse.getStatus());
                    String message = registerStudentResponse.getMessage();
                    if (registerStudentResponse.getStatus() == 1) {
                        emptyFields();
                        Toast.makeText(RegisterScreen.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                        navigateLogin();
                    } else {
                        Toast.makeText(RegisterScreen.this, message, Toast.LENGTH_SHORT).show();
                        // progressBar.hide();

                    }
                    progressBar.hide();


                }

                @Override
                public void onFailure(Call<RegisterStudentResponse> call, Throwable t) {
                    System.out.println(t.getMessage());
                }
            });
        }
    }


    private void initializeIds() {
        editText_useremail = (EditText) findViewById(R.id.register_email);
        editText_username = (EditText) findViewById(R.id.register_name);
        editText_userpassword = (EditText) findViewById(R.id.register_password);
        editText_contactnum = (EditText) findViewById(R.id.register_contact);
        btn_SignUp = (Button) findViewById(R.id.btn_Signup);
        spinner_collegeSelection = (Spinner) findViewById(R.id.college_selection);
        spinner_yearSelection = (Spinner) findViewById(R.id.year_selection);
        male_radio = (RadioButton) findViewById(R.id.male);
        female_radio = (RadioButton) findViewById(R.id.female);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        signIn = (TextView) findViewById(R.id.already_signin);
        facebook_register = (LoginButton) findViewById(R.id.fb_reg_login);
        user_image = (ImageView) findViewById(R.id.user_image);
        fb_custom = (Button) findViewById(R.id.fb);

        //  btn_custom_fb_login = (Button) findViewById(R.id.fb_login_custom);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterScreen.this, LoginScreen.class);
                startActivity(intent);
                finish();
            }
        });
        Call<CollegeResponse> collge_name = apiInterface.doGetCollegeList();
        collge_name.enqueue(new Callback<CollegeResponse>() {
            @Override
            public void onResponse(Call<CollegeResponse> call, Response<CollegeResponse> response) {
                CollegeResponse collegeRespons = response.body();
                collegeModelArrayList = new ArrayList<>();

                CollegeModel zeroth_index = new CollegeModel();
                zeroth_index.setCollege_name("Select college");
                zeroth_index.setId(0);
                collegeModelArrayList.add(0, zeroth_index);

                collegeModelArrayList.addAll(collegeRespons.getCollegeModels());

                collegeAdapter = new CollegeAdapter(RegisterScreen.this, collegeModelArrayList);
                spinner_collegeSelection.setAdapter(collegeAdapter);
                System.out.println(collegeRespons);

            }

            @Override
            public void onFailure(Call<CollegeResponse> call, Throwable t) {
                call.cancel();
            }
        });


        spinner_collegeSelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_college = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        yearsadapter = new ArrayAdapter<String>(RegisterScreen.this, R.layout.college_list_item, R.id.collegesName, years);
        spinner_yearSelection.setAdapter(yearsadapter);
    }

    private void emptyFields() {

        editText_username.setText("");
        editText_contactnum.setText("");
        editText_userpassword.setText("");
        editText_useremail.setText("");
        spinner_yearSelection.setSelection(0);
        spinner_collegeSelection.setSelection(0);
        male_radio.setChecked(false);
        female_radio.setChecked(false);
        navigateLogin();

    }

    private void navigateLogin() {
        Intent intent = new Intent(RegisterScreen.this, LoginScreen.class);
        startActivity(intent);
    }

    Bitmap bitmap;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK
                && null != data) {
            Uri selectedImage = data.getData();
            user_image.setImageURI(selectedImage);
        }
    }

    private void fillData(StudentResponse studentResponse) {
        editText_username.setText(studentResponse.getName());
        editText_contactnum.setText(studentResponse.getContact_no());
        editText_useremail.setText(studentResponse.getEmail());

        if (!studentResponse.getImage_url().equalsIgnoreCase("")) {
            Picasso.with(RegisterScreen.this).load(studentResponse.getImage_url()).fit()/*.centerCrop()*/
                    .placeholder(R.drawable.default_image)
                    .error(R.drawable.default_image)
                    .into(user_image);
        }

    }

    int PICK_IMAGE = 2;

    private void imagePicker() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }
}
