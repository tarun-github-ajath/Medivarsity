package com.medavarsity.user.medavarsity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.google.gson.Gson;
import com.medavarsity.user.medavarsity.Adapters.DailyUpdateAdapter;
import com.medavarsity.user.medavarsity.Adapters.MySubjectCheckViewAdapter;
import com.medavarsity.user.medavarsity.Adapters.SubjectAdapter;
import com.medavarsity.user.medavarsity.Constants.CommonMethods;
import com.medavarsity.user.medavarsity.Constants.Config;
import com.medavarsity.user.medavarsity.Constants.ConstantVariabls;
import com.medavarsity.user.medavarsity.Model.PayloadHome;
import com.medavarsity.user.medavarsity.Model.Subjects;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SearchScreen extends AppCompatActivity {

    ImageView navigation_icon;
    SharedPreferences sharedPreferences;
    EditText search_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_screen);

        sharedPreferences = getSharedPreferences(ConstantVariabls.SHARED_FILE, MODE_PRIVATE);
        navigation_icon = (ImageView) findViewById(R.id.navigation_icon);
        search_text = (EditText) findViewById(R.id.search_text);


        navigation_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchScreen.this, YoutubeActivity.class);
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
              /*  if (adapter != null)
                    adapter.getFilter().filter(s);*/

            }

            @Override
            public void afterTextChanged(final Editable s) {


            }
        });
    }


    PayloadHome payloadHome;

    private void readFromPref() {
        Gson gson = new Gson();
        String json = sharedPreferences.getString(ConstantVariabls.Home_Model, "");
        payloadHome = gson.fromJson(json, PayloadHome.class);
    }

    public class CustomComparator implements Comparator<Subjects> {
        @Override
        public int compare(Subjects o1, Subjects o2) {
            String searched = search_text.getText().toString().trim();

            if (searched.equalsIgnoreCase(o1.getSubjectname()) || searched.equalsIgnoreCase(o2.getSubjectname())/*|| searched.equals(o2.getVideos()) || searched.equals(o1.getComments()) ||
                  searched.equals(o2.getComments()) || searched.equals(o1.getNote()) || searched.equals(o2.getNote())*/) {
                return 1;
            }/* else if (searched.contains(o1.getTaskdesc()) || searched.contains(o2.getTaskdesc())
             *//*|| searched.contains(o1.getComments()) || searched.contains(o2.getComments())
                    || searched.contains(o1.getNote()) || searched.contains(o2.getNote())*//*
                  )
          {
              return 0;
          } else {
              return -1;
          }*/ else {
                return 0;
            }

        }
    }


    class SearchListAdapter extends RecyclerView.Adapter<MyViewHolder> implements Filterable {

        Context context;
        List<Subjects> subjectsList;
        LayoutInflater layoutInflater;
        private ItemFilter mFilter = new ItemFilter();

        public SearchListAdapter(Context context, List<Subjects> subjectsList) {
            this.context = context;
            this.subjectsList = subjectsList;

        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout./*daily_update_item*/search_items, viewGroup, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int position) {

          /*  myViewHolder.youtube_thumbnail.initialize(developerKey, new YouTubeThumbnailView.OnInitializedListener() {
                @Override
                public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, final YouTubeThumbnailLoader youTubeThumbnailLoader) {
                    String video_id = CommonMethods.extractVideoId(subjectsList.get(position).get);

                    youTubeThumbnailLoader.setVideo(video_id);
                    youTubeThumbnailLoader.setOnThumbnailLoadedListener(new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
                        @Override
                        public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                            youTubeThumbnailLoader.release();
                            youTubeThumbnailView.setVisibility(View.VISIBLE);
                            myViewHolder.relativeLayoutOverYouTubeThumbnailView.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {

                        }
                    });
                }

                @Override
                public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {

                }
            });
*/
           /* myViewHolder.playButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String video_id = CommonMethods.extractVideoId(dailyUpdateModelArrayList.get(position).getUrl());
                    Intent intent = YouTubeStandalonePlayer.createVideoIntent((Activity) context, Config.DEVELOPER_KEY, video_id);
                    context.startActivity(intent);
                }
            });*/

        }

        @Override
        public int getItemCount() {
            return 0;
        }

        @Override
        public Filter getFilter() {
            return null;
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView subjectTextView, hrtextView;
//        YouTubePlayerSupportFragment youTubePlayerFragment;

        //        YouTubePlayerView youTubePlayerView;
        RelativeLayout relativeLayoutOverYouTubeThumbnailView;
        ImageView playButton;
        YouTubeThumbnailView youtube_thumbnail;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            relativeLayoutOverYouTubeThumbnailView = (RelativeLayout) itemView.findViewById(R.id.relative_yotube);
            youtube_thumbnail = (YouTubeThumbnailView) itemView.findViewById(R.id.youtube_thumbnail);
            playButton = (ImageView) itemView.findViewById(R.id.btnYoutube_player);
            subjectTextView = (TextView) itemView.findViewById(R.id.subject_name);
            hrtextView = (TextView) itemView.findViewById(R.id.hr_rate);
        }
    }

    String filterString;

    private class ItemFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            filterString = constraint.toString().toLowerCase().trim();
            FilterResults results = new FilterResults();
/*
            filterString = constraint.toString().toLowerCase().trim();
            FilterResults results = new FilterResults();
            int count = workOrderModels.size();
            final List<WorkOrderModel> nlist = new ArrayList<>(count);
            if (isTechnicianSelected) {
                WorkOrderModel obj = null;
                filterString = constraint.toString().trim();
                if (filterString.equalsIgnoreCase("All")) {
                    results.values = workOrderModels;
                } else {
                    for (int i = 0; i < workOrderModels.size(); i++) {
                        obj = new WorkOrderModel();
                        obj = workOrderModels.get(i);
                        String fname = obj.getFirstName();
                        String lname = obj.getLastName();
                        String tech_name = fname + " " + lname;
                        if (tech_name.equalsIgnoreCase(filterString)) {
                            nlist.add(obj);
                        }
                    }

                    results.values = nlist;
                    results.count = nlist.size();
                }
                isTechnicianSelected = false;
            } else {
                WorkOrderModel obj = null;
                for (int i = 0; i < count; i++) {
                    obj = new WorkOrderModel();
                    obj = workOrderModels.get(i);

                    if (obj.getFirstName().toLowerCase().contains(filterString) ||
                            obj.getLastName().toLowerCase().contains(filterString) ||
                            obj.getService_address().toLowerCase().contains(filterString) ||
                            obj.getQuote_number().toLowerCase().contains(filterString)
                            || obj.getCompany().toLowerCase().contains(filterString) ||
                            obj.getTechnician_fname().toLowerCase().contains(filterString)
                            || obj.getTechnician_lname().toLowerCase().contains(filterString) ||
                            obj.getCustomer().toLowerCase().contains(filterString) ||
                            obj.getCreated_technician().toLowerCase().contains(filterString)
                            ) {
                        nlist.add(obj);
                    }
                }

                results.values = nlist;
                results.count = nlist.size();
            }
*/


            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

        /*    filteredData = (ArrayList<WorkOrderModel>) results.values;

            if (filteredData.size() == 0) {
                no_text.setVisibility(View.VISIBLE);
                quote_list.setVisibility(View.INVISIBLE);
                quote_list.invalidate();
            } else {
                no_text.setVisibility(View.INVISIBLE);
                quote_list.setVisibility(View.VISIBLE);
                try {
                    notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }*/
        }
    }
}
