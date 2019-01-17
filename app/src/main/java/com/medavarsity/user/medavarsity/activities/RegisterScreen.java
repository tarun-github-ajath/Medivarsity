package com.medavarsity.user.medavarsity.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
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
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.medavarsity.user.medavarsity.Adapters.CollegeAdapter;
import com.medavarsity.user.medavarsity.Adapters.StateAdapter;
import com.medavarsity.user.medavarsity.Global.GlobalProps;
import com.medavarsity.user.medavarsity.Methods.CommonMethods;
import com.medavarsity.user.medavarsity.Constants.ConstantVariables;
import com.medavarsity.user.medavarsity.Model.CollegeModel;
import com.medavarsity.user.medavarsity.Model.CollegeResponse;
import com.medavarsity.user.medavarsity.Model.RegisterStudentResponse;
import com.medavarsity.user.medavarsity.Model.StateModel;
import com.medavarsity.user.medavarsity.Model.StateResponse;
import com.medavarsity.user.medavarsity.Model.StudentResponse;
import com.medavarsity.user.medavarsity.NetworkCalls.ApiClient;
import com.medavarsity.user.medavarsity.NetworkCalls.ApiInterface;
import com.medavarsity.user.medavarsity.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterScreen extends AppCompatActivity {

    // ImageView user_image;
    CircleImageView user_image, camera_icon;
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

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    LoginManager loginManager;
    List<String> permissionNeeds = Arrays.asList(/*"user_photos",*/ "email",
            /*"user_birthday",*/ "public_profile");

    Button fb_custom;
    String social_id = "";
    String reg_type = "0";
    String image_url = "";
    Intent intent;
    CommonMethods mCommonMethods;
    SharedPreferences sharedPreferences;

    @BindView(R.id.state_selection)
    Spinner spinner_state;
    private ArrayList<CollegeModel> collegeModelArrayListDummy;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);
        initializeIds();
        ButterKnife.bind(this);
        callbackManager = CallbackManager.Factory.create();
        loginManager = LoginManager.getInstance();
        sharedPreferences = getSharedPreferences(ConstantVariables.SHARED_FILE, MODE_PRIVATE);
        intent = getIntent();
        mCommonMethods = new CommonMethods(RegisterScreen.this);

        spinner_collegeSelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                selected_college = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

//        checkAlreadyLogin();
        getExtras();
       /* user_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePicker();
            }
        });*/

        camera_icon.setOnClickListener(new View.OnClickListener() {
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
                    /*StudentResponse studentResponse*/
                    studentResponse = new StudentResponse();
                    studentResponse.setFacebook_id(jsonObject.has("id") ? jsonObject.getString("id") : "");
                    studentResponse.setEmail(jsonObject.has("email") ? jsonObject.getString("email") : "");
                    studentResponse.setName(jsonObject.has("name") ? jsonObject.getString("name") : "");
                    JSONObject pictureObject = jsonObject.getJSONObject("picture");
                    JSONObject dataObj = pictureObject.getJSONObject("data");
                    String imageUrl = dataObj.has("url") ? dataObj.getString("url") : "";
                    studentResponse.setImage_url(imageUrl);

                    LoginScreen.setGlobalProps(studentResponse);
                    GlobalProps.getInstance().saveStudentResponseInPref(studentResponse,sharedPreferences);

                    Intent intent = new Intent(RegisterScreen.this,DashBoard.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);


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
        String number = editText_contactnum.getText().toString();

        if (email.equalsIgnoreCase("") || password.equalsIgnoreCase("") || username.equalsIgnoreCase("")
                || number.equalsIgnoreCase("") || selected_year.equalsIgnoreCase("")
            /* || selected_gender.equalsIgnoreCase("")*/) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        } else if (selected_year.equalsIgnoreCase("Select Year")) {
            Toast.makeText(this, "Please select valid values", Toast.LENGTH_SHORT).show();
        } else if (!email.matches(emailPattern)) {
            Toast.makeText(this, "Please enter valid email address!", Toast.LENGTH_SHORT).show();
        } else if (password.length() < 6) {
            Toast.makeText(this, "Password should not be less than six digit!", Toast.LENGTH_SHORT).show();
        } else if (number.length() < 10) {
            Toast.makeText(this, "Please enter valid contact number!", Toast.LENGTH_SHORT).show();
        } else {
            mCommonMethods.showCommonDialog(RegisterScreen.this, "Please wait...");

            if (studentResponse != null) {
                if (!studentResponse.getFacebook_id().equalsIgnoreCase("")) {
                    social_id = studentResponse.getFacebook_id();
                    reg_type = "1";
                    image_url = studentResponse.getImage_url();
                }
            }

            Call<RegisterStudentResponse> createStudentCall = apiInterface.registerStudent(username, email, number,
                    password, "college", selected_year, social_id, reg_type, image_url);
            createStudentCall.enqueue(new Callback<RegisterStudentResponse>() {
                @Override
                public void onResponse(@NonNull Call<RegisterStudentResponse> call, @NonNull Response<RegisterStudentResponse> response) {

                    RegisterStudentResponse registerStudentResponse = response.body();
                    Log.i("register response: " , String.valueOf(registerStudentResponse.getPayload().getOtp()));
                    assert registerStudentResponse != null;
                    String studentId = String.valueOf(registerStudentResponse.getPayload().getStudentId());
                    String contactNumber = registerStudentResponse.getPayload().getContactNo();
                    String message = registerStudentResponse.getMessage();
                    int otp = registerStudentResponse.getPayload().getOtp();
                    if (registerStudentResponse.getStatus() == 1) {
                        navigateOtpScreen(otp,studentId,contactNumber);
                    } else {
                        Toast.makeText(RegisterScreen.this, message, Toast.LENGTH_SHORT).show();
                    }

                    mCommonMethods.cancelDialog();
                }

                @Override
                public void onFailure(Call<RegisterStudentResponse> call, Throwable t) {
                    System.out.println(t.getMessage());
                    mCommonMethods.cancelDialog();
                }
            });
        }
    }

    private void initializeIds() {
        editText_useremail = findViewById(R.id.register_email);
        editText_username = findViewById(R.id.register_name);
        editText_userpassword = findViewById(R.id.register_password);
        editText_contactnum = findViewById(R.id.register_contact);
        btn_SignUp = findViewById(R.id.btn_Signup);
        spinner_collegeSelection = findViewById(R.id.college_selection);
        spinner_yearSelection = findViewById(R.id.year_selection);
        male_radio = findViewById(R.id.male);
        female_radio = findViewById(R.id.female);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        signIn = findViewById(R.id.already_signin);
        facebook_register = findViewById(R.id.fb_reg_login);
        // user_image = (ImageView) findViewById(R.id.user_image);
        user_image = findViewById(R.id.profile_image);
        camera_icon = findViewById(R.id.iv_camera);
        fb_custom = findViewById(R.id.fb);

        male_radio.setChecked(true);
        //  btn_custom_fb_login = (Button) findViewById(R.id.fb_login_custom);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterScreen.this, LoginScreen.class);
                startActivity(intent);
                //finish();
            }
        });
        getStates();

        collegeModelArrayListDummy = new ArrayList<>();
        resetCollegeSpinnerHint();

        collegeAdapter = new CollegeAdapter(RegisterScreen.this, collegeModelArrayListDummy);
        spinner_collegeSelection.setAdapter(collegeAdapter);

        yearsadapter = new ArrayAdapter<String>(RegisterScreen.this, R.layout.register_spinner_itemview, R.id.textView, years);
        spinner_yearSelection.setAdapter(yearsadapter);

        spinner_yearSelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selected_year = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner_yearSelection.setSelection(1);
    }

    private void resetCollegeSpinnerHint() {
        CollegeModel collegeModel = new CollegeModel();
        collegeModel.setCollege_name("Select College");
        collegeModelArrayListDummy.clear();
        collegeModelArrayListDummy.add(0,collegeModel);
    }

    private void getStates(){
        Call<StateResponse> call = apiInterface.getStates();
        call.enqueue(new Callback<StateResponse>() {
            @Override
            public void onResponse(@NonNull Call<StateResponse> call, @NonNull Response<StateResponse> response) {
                Log.i("response", String.valueOf(response.body()));

                assert response.body() != null;
                final ArrayList<StateModel> stateModelsResponse = response.body().getStateModels();
                StateModel stateModel = new StateModel();
                stateModel.setStateName("Select State");
                stateModelsResponse.add(0, stateModel);

                StateAdapter StateAdapter = new StateAdapter(RegisterScreen.this,stateModelsResponse);
                spinner_state.setAdapter(StateAdapter);

                spinner_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        resetCollegeSpinnerHint();
                        spinner_collegeSelection.setSelection(0);
                        int selected_stateId = stateModelsResponse.get(position).getStateId();
                        getColleges(selected_stateId);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onFailure(Call<StateResponse> call, Throwable t) {
            }
        });
    }

    private void getColleges(int stateId){
        Call<CollegeResponse> collge_name = apiInterface.doGetCollegeList(stateId);
        collge_name.enqueue(new Callback<CollegeResponse>() {
            @Override
            public void onResponse(@NonNull Call<CollegeResponse> call, @NonNull Response<CollegeResponse> response) {

                if(response.raw().code() != 404 || response.body() != null) {

                    assert response.body() != null;
//                    collegeModelArrayListDummy = response.body().getCollegeModels();

                    Log.i("colleges", String.valueOf(collegeModelArrayListDummy.get(0).getCollege_name()));

                    collegeModelArrayListDummy.addAll(response.body().getCollegeModels());
                    collegeAdapter.notifyDataSetChanged();

                    spinner_collegeSelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            selected_college = collegeModelArrayListDummy.get(position).getCollege_name();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                }
            }

            @Override
            public void onFailure(@NonNull Call<CollegeResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
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
    }

    private void checkAlreadyLogin() {
        boolean is_firsttime = sharedPreferences.getBoolean(ConstantVariables.IS_FIRST_TIME, false);

        if (is_firsttime) {
            Gson gson = new Gson();
            String studentResponse = sharedPreferences.getString(ConstantVariables.LOGIN_STUDENT_OBJECT,"");
            assert studentResponse != null;
            if(!studentResponse.equals(""))
            {
                StudentResponse studentResponseObj = gson.fromJson(studentResponse,StudentResponse.class);
                LoginScreen.setGlobalProps(studentResponseObj);
                Intent intent = new Intent(RegisterScreen.this, DashBoard.class);
                startActivity(intent);
            }


        }
    }

    private void navigateOtpScreen(int otp,String studentId,String contactNo) {
        Intent intent = new Intent(RegisterScreen.this, EnterOtp.class);
        intent.putExtra("otp",otp);
        intent.putExtra("studentId",studentId);
        intent.putExtra("contactNo",contactNo);
        startActivity(intent);
    }


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

        if (studentResponse != null) {
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


    }

    int PICK_IMAGE = 2;

    private void imagePicker() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    StudentResponse studentResponse;

    private void getExtras() {

        studentResponse = (StudentResponse) intent.getSerializableExtra(ConstantVariables.NON_VALID_FB_STUDENT);
        fillData(studentResponse);
    }

}
