package com.example.lmc_eatz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.lmc_eatz.Interface.ItemClickListener;
import com.example.lmc_eatz.Model.Food;
import com.example.lmc_eatz.ViewHolder.FoodViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class FoodList extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference foodList;
    String categoryId="";
    FirebaseRecyclerAdapter<Food, FoodViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        //Firebase
        database = FirebaseDatabase.getInstance();
        foodList = database.getReference("Food");

        recyclerView = (RecyclerView)findViewById(R.id.recycler_food);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //get Intent here
        if(getIntent() != null)
            categoryId = getIntent().getStringExtra("CategoryId");
        if(!categoryId.isEmpty() && categoryId != null)
        {
            loadListFood(categoryId);
        }
    }

    private void loadListFood(String categoryId) {
     adapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(Food.class,
             R.layout.food_item,
             FoodViewHolder.class,
             foodList.orderByChild("MenuId").equalTo(categoryId) //Like : Select * from Foods where MenuId=
             ) {
         @Override
         protected void populateViewHolder(FoodViewHolder viewHolder, Food model, int position) {
            viewHolder.food_name.setText(model.getName());

             Picasso.with(getBaseContext()).load(model.getImage())
                     .into(viewHolder.food_image);
             final Food local = model;
             viewHolder.setItemClickListener(new ItemClickListener() {
                 @Override
                 public void onClick(View view, int position, boolean isLongClick) {
                     Toast.makeText(FoodList.this,""+local.getName(),Toast.LENGTH_SHORT).show();
                 }
             });
         }
     };
     //set Adapter

        recyclerView.setAdapter(adapter);

    }
}
