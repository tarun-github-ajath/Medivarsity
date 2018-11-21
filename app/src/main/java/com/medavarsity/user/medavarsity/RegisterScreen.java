package com.medavarsity.user.medavarsity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.medavarsity.user.medavarsity.Adapters.CollegeAdapter;
import com.medavarsity.user.medavarsity.Model.CollegeModel;
import com.medavarsity.user.medavarsity.Model.CollegeResponse;
import com.medavarsity.user.medavarsity.Model.CreateStudent;
import com.medavarsity.user.medavarsity.Model.RegisterStudentResponse;
import com.medavarsity.user.medavarsity.NetworkCalls.ApiClient;
import com.medavarsity.user.medavarsity.NetworkCalls.ApiInterface;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterScreen extends AppCompatActivity {

    EditText editText_useremail, editText_userpassword, editText_username, editText_contactnum;
    Button btn_SignUp;
    Spinner spinner_collegeSelection, spinner_yearSelection;
    RadioButton male_radio, female_radio;
    ApiInterface apiInterface;

    String selected_college = "";
    String selected_year = "";
    String selected_gender = "";
    ArrayList<CollegeModel> collegeModelArrayList;
    CollegeAdapter collegeAdapter;
    String[] years = new String[]{"Select Year", "1990", "2001", "2003", "2004", "2005", "2006", "2009"};

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);
        initializeIds();


        spinner_yearSelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_year = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                doRegisterStudent();
            }
        });

        male_radio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    selected_gender = "Male";
                }
            }
        });
        female_radio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selected_gender = "Female";
                }
            }
        });
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
            Call<RegisterStudentResponse> createStudentCall = apiInterface.registerStudent(username, email, number,
                    password, selected_college, selected_year);
            createStudentCall.enqueue(new Callback<RegisterStudentResponse>() {
                @Override
                public void onResponse(Call<RegisterStudentResponse> call, Response<RegisterStudentResponse> response) {

                    RegisterStudentResponse registerStudentResponse = response.body();
                    String message = registerStudentResponse.getMessage();
                    Toast.makeText(RegisterScreen.this, message, Toast.LENGTH_SHORT).show();
                    emptyFields();

                }

                @Override
                public void onFailure(Call<RegisterStudentResponse> call, Throwable t) {

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
    }
}
