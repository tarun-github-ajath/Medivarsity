package com.medavarsity.user.medavarsity.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.medavarsity.user.medavarsity.Adapters.SearchedModel;
import com.medavarsity.user.medavarsity.Adapters.SubjectsAdapter;
import com.medavarsity.user.medavarsity.Constants.Config;
import com.medavarsity.user.medavarsity.Constants.ConstantVariabls;
import com.medavarsity.user.medavarsity.Model.PayloadHome;
import com.medavarsity.user.medavarsity.Model.Subjects;
import com.medavarsity.user.medavarsity.Model.Videos;
import com.medavarsity.user.medavarsity.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SearchScreen extends AppCompatActivity {

    ImageView navigation_icon;
    SharedPreferences sharedPreferences;
    EditText search_text;
    List<SearchedModel> filteredData;
    TextView no_data;
    List<Videos> _v_list = new ArrayList<>();
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_screen);

        sharedPreferences = getSharedPreferences(ConstantVariabls.SHARED_FILE, MODE_PRIVATE);
        navigation_icon = (ImageView) findViewById(R.id.navigation_icon);
        search_text = (EditText) findViewById(R.id.search_text);
        recyclerView = (RecyclerView) findViewById(R.id.search_recycl);
        no_data = (TextView) findViewById(R.id.no_data);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.getLayoutManager().setMeasurementCacheEnabled(false);

        navigation_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchScreen.this, /*YoutubeActivity*/DashBoard.class);
                startActivity(intent);
                finish();
            }
        });
        readFromPref();

        search_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(final CharSequence s, int start, int before, int count) {
                if (searchListAdapter != null) {
                    searchListAdapter.getFilter().filter(s);
                }

            }

            @Override
            public void afterTextChanged(final Editable s) {


            }
        });
    }


    PayloadHome payloadHome;
    ArrayList<SearchedModel> searchedModelArrayList = new ArrayList<>();

    SearchListAdapter searchListAdapter;

    private void readFromPref() {
        Gson gson = new Gson();
        String json = sharedPreferences.getString(ConstantVariabls.Home_Model, "");
        payloadHome = gson.fromJson(json, PayloadHome.class);
        if (searchedModelArrayList.size() > 0) {
            searchedModelArrayList.clear();
        }
        searchedModelArrayList = makeMergedList(payloadHome);
        searchListAdapter = new SearchListAdapter(SearchScreen.this, searchedModelArrayList, null);
    }

    class SearchListAdapter extends RecyclerView.Adapter<MyViewHolder> implements Filterable {

        Context context;
        List<SearchedModel> subjectsList;
        LayoutInflater layoutInflater;
        private ItemFilter mFilter = new ItemFilter();

        List<Subjects> filteredSubjects;

        public SearchListAdapter(Context context, List<SearchedModel> subjectsList, List<Subjects> filteredSubjects) {
            this.context = context;
            this.subjectsList = subjectsList;
            filteredData = subjectsList;
            this.filteredSubjects = filteredSubjects;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout./*daily_update_item*/text_recycle, viewGroup, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int position) {

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            myViewHolder.recyclerView.setLayoutManager(layoutManager);
            myViewHolder.recyclerView.setHasFixedSize(true);


            myViewHolder.recyclerView.getLayoutManager().setMeasurementCacheEnabled(false);
            myViewHolder.recyclerView.setNestedScrollingEnabled(false);


            if (filteredSubjects.get(position).getVideos() != null && filteredSubjects.get(position).getVideos().size() > 0) {
                myViewHolder.subject_name.setText(filteredSubjects.get(position).getSubjectname());
                myViewHolder.linearLayout.setVisibility(View.VISIBLE);
                SubjectsAdapter subjectsAdapter = new SubjectsAdapter(context, filteredSubjects.get(position).getVideos(), Config.DEVELOPER_KEY);
                myViewHolder.recyclerView.setAdapter(subjectsAdapter);
            }
        }

        @Override
        public int getItemCount() {
            if (filteredSubjects == null) {
                return 0;
            } else {
                return filteredSubjects.size();
            }
        }

        @Override
        public Filter getFilter() {
            return mFilter;
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout linearLayout;
        RecyclerView recyclerView;
        TextView subject_name, subscription;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            linearLayout = (LinearLayout) itemView.findViewById(R.id.layout_recycl);


            recyclerView = (RecyclerView) itemView.findViewById(R.id.test_recycl);
            subject_name = (TextView) itemView.findViewById(R.id.dailyUpdate);
            subscription = (TextView) itemView.findViewById(R.id.subscribe);
        }
    }

    String filterString;

    private class ItemFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results;
            if (!constraint.toString().equalsIgnoreCase("")) {
                filterString = constraint.toString().toLowerCase().trim();
                results = new FilterResults();

                int count = searchedModelArrayList.size();
                final List<SearchedModel> searchedModels = new ArrayList<>();
                SearchedModel obj = null;

                for (int j = 0; j < count; j++) {
                    obj = new SearchedModel();
                    obj = searchedModelArrayList.get(j);

                    if (obj.getSubjectname().toLowerCase().contains(filterString.toLowerCase())
                            || obj.getVideo_title().toLowerCase().contains(filterString.toLowerCase())
                        /* || obj.getSubject_video_url().toLowerCase().contains(filterString.toLowerCase())*/
                            ) {
                        searchedModels.add(obj);
                    }
                }
                results.values = searchedModels;
                results.count = searchedModels.size();
            } else {
                results = new FilterResults();
            }


            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            filteredData = (List<SearchedModel>) results.values;

            if (filteredData == null || filteredData.size() == 0) {
                no_data.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                recyclerView.invalidate();
            } else {

                no_data.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                ArrayList<Subjects> subjectsArrayList = differentiateSubjects(filteredData);
                System.out.println(subjectsArrayList);
                // removeDuplicacy(subjectsArrayList);

          /*      Set<Subjects> unique = new LinkedHashSet<Subjects>(subjectsArrayList);
                if (subjectsArrayList.size() > 0) {
                    subjectsArrayList.clear();
                }
                subjectsArrayList = new ArrayList<Subjects>(unique);
*/

                SearchListAdapter searchListAdapter = new SearchListAdapter(SearchScreen.this, filteredData, subjectsArrayList);
                recyclerView.setAdapter(searchListAdapter);
              /*  searchListAdapter = new SearchListAdapter(SearchScreen.this, filteredData);
                recyclerView.setAdapter(searchListAdapter);
                try {
                    searchListAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }*/
            }
        }
    }


    private ArrayList<Subjects> differentiateSubjects(List<SearchedModel> filtered) {
        ArrayList<Subjects> subjects = new ArrayList<>();
        Subjects sub_object = null;
        for (int i = 0; i < filtered.size(); i++) {
            int subjectId = filtered.get(i).getSubject_id();
            List<Videos> v_list = new ArrayList<>();
            for (int j = 0; j < filtered.size(); j++) {


                if (filtered.get(j).getSubject_id() == subjectId) {
                    sub_object = new Subjects();
                    sub_object.setSubjectname(filtered.get(j).getSubjectname());
                    Videos videos = new Videos();
                    videos.setId(filtered.get(j).getVideo_id());
                    videos.setVideo_url(filtered.get(j).getSubject_video_url());
                    videos.setVideo_title(filtered.get(j).getVideo_title());
                    videos.setVideo_image_url(filtered.get(j).getSubject_video_url());
                    videos.setSubject_id(filtered.get(j).getSubject_id());
                    v_list.add(videos);


                }
                //filtered.remove()
            }

            sub_object.setVideos(v_list);

            if (subjects != null) {
                if (!checkExist(subjects, sub_object)) {
                    subjects.add(sub_object);
                }
            }
            System.out.println(sub_object);

        }
        return subjects;
    }


    private ArrayList<SearchedModel> makeMergedList(PayloadHome payloadHome) {


        ArrayList<SearchedModel> searchedModelArrayList = new ArrayList<>();
        SearchedModel dailyModel = null;
        SearchedModel searchedModel = null;

        if (payloadHome.getDailyUpdates() != null && payloadHome.getDailyUpdates().size() > 0) {
            for (int j = 0; j < payloadHome.getDailyUpdates().size(); j++) {
                dailyModel = new SearchedModel();
                dailyModel.setSubjectname("Daily Updates");
                dailyModel.setVideo_title(payloadHome.getDailyUpdates().get(j).getTitle());
                dailyModel.setSubject_video_url(payloadHome.getDailyUpdates().get(j).getUrl());

                dailyModel.setSubject_id(11);
                searchedModelArrayList.add(dailyModel);
            }
        }


        if (payloadHome.getSubjects() != null && payloadHome.getSubjects().size() > 0) {
            for (int i = 0; i < payloadHome.getSubjects().size(); i++) {
                List<Videos> videos = payloadHome.getSubjects().get(i).getVideos();
                searchedModel = new SearchedModel();

                searchedModel.setSubjectname(payloadHome.getSubjects().get(i).getSubjectname());
                searchedModel.setSubject_subscription(payloadHome.getSubjects().get(i).getSubscription());
                if (videos != null && videos.size() > 0) {
                    for (int j = 0; j < videos.size(); j++) {

                        searchedModel.setSubject_id(videos.get(j).getSubject_id());
                        searchedModel.setVideo_id(videos.get(j).getId());
                        searchedModel.setVideo_title(videos.get(j).getVideo_title());
                        /*searchedModel.setSubject_video_id(videos.get(j).getId());*/
                        searchedModel.setSubject_video_image_url(videos.get(j).getVideo_image_url());
                        searchedModel.setSubject_video_url(videos.get(j).getVideo_url());
                        searchedModel.setVideo_type(videos.get(j).getVideo_type());

                        searchedModelArrayList.add(searchedModel);
                    }
                } else {
                    searchedModelArrayList.add(searchedModel);
                }
            }
        }
        return searchedModelArrayList;

    }

    private List<Subjects> removeDuplicacy(List<Subjects> subjects) {
        List<Subjects> subjectsList = new ArrayList<>();
      /*  Set<Subjects> s = new TreeSet<Subjects>(nefw Comparator<Subjects>() {

            @Override
            public int compare(Subjects o1, Subjects o2) {
                *//*  if (o1.getVideos() == o2.getVideos()) {*//*
                return 0;
//                }
                // ... compare the two object according to your requirements
            }
        });
        s.addAll(subjects);
        subjects = new ArrayList<>(s);*/
        Set<Subjects> subjects1 = new HashSet<>(subjects);
        subjects.clear();
        subjects.addAll(subjects1);
        return subjects;
    }


    private boolean checkExist(ArrayList<Subjects> subjects, Subjects single_obj) {
        boolean isExist = false;
        for (int i = 0; i < subjects.size(); i++) {
            List<Videos> videosList = subjects.get(i).getVideos();

            /*for (int j = 0; j < videosList.size(); j++) {*/
            if (videosList.get(0).getSubject_id() == single_obj.getVideos().get(0).getSubject_id()) {
                isExist = true;
                break;
            }
//            }

        }
        return isExist;
    }
}
