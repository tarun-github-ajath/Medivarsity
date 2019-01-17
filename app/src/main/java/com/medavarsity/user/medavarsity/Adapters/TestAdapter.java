package com.medavarsity.user.medavarsity.Adapters;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.medavarsity.user.medavarsity.Model.ReviewModel;
import com.medavarsity.user.medavarsity.Model.TestModel;
import com.medavarsity.user.medavarsity.R;
import com.medavarsity.user.medavarsity.activities.GiveTest;

import java.util.List;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.TestHolder> {
    Context context;
    private List<TestModel> testModels;
    private LayoutInflater inflater;

    public TestAdapter(Context context, List<TestModel> testModels) {
        this.context = context;
        this.testModels = testModels;
    }

    @NonNull
    @Override
    public TestHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.test_frag_list_item, null);
        return new TestHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TestHolder testHolder, final int position) {
        testHolder.textView_name.setText(testModels.get(position).getTest_name());
        testHolder.textView_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, GiveTest.class);
                intent.putExtra("testId",testModels.get(position).getTest_id());

                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return testModels.size();
    }

    class TestHolder extends RecyclerView.ViewHolder {
        TextView textView_name;
        TestHolder(@NonNull View itemView) {
            super(itemView);
            textView_name = itemView.findViewById(R.id.testName_fragTest);
        }
    }
}
