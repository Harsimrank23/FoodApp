package org.example.foodapp.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.example.foodapp.Activity.Adapter.CategoryAdapter;
import org.example.foodapp.Activity.Adapter.PopularAdapter;
import org.example.foodapp.Activity.Domain.CategoryDomain;
import org.example.foodapp.Activity.Domain.FoodDomain;
import org.example.foodapp.R;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private RecyclerView.Adapter mAdapter,mAdapter2;
    private RecyclerView recyclerViewCategoryList,recyclerViewPopularList;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP)
        {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.orange));
            getWindow().setStatusBarColor(getResources().getColor(R.color.orange));
        }

        recyclerViewCategory();
        recyclerViewPopular();
        bottomNavigation();
    }
    private void bottomNavigation(){
        FloatingActionButton floatingActionButton=findViewById(R.id.card_btn);
        LinearLayout homeBtn=findViewById(R.id.homeBtn);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,CardListActivity.class));
            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,MainActivity.class));
            }
        });
    }

    private void recyclerViewCategory(){
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerViewCategoryList=findViewById(R.id.recyclerView2);
        recyclerViewCategoryList.setLayoutManager(linearLayoutManager);

        ArrayList<CategoryDomain> categoryList=new ArrayList<>();
        categoryList.add(new CategoryDomain("Pizza","cat_1"));
        categoryList.add(new CategoryDomain("Burger","cat_2"));
        categoryList.add(new CategoryDomain("Hotdog","cat_3"));
        categoryList.add(new CategoryDomain("Drink","cat_4"));
        categoryList.add(new CategoryDomain("Donut","cat_5"));

        mAdapter=new CategoryAdapter(categoryList);
        recyclerViewCategoryList.setAdapter(mAdapter);
    }

    private void recyclerViewPopular(){
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerViewPopularList=findViewById(R.id.recyclerView);
        recyclerViewPopularList.setLayoutManager(linearLayoutManager);

        ArrayList<FoodDomain> foodList=new ArrayList<>();
        foodList.add(new FoodDomain("Pepperoni pizza","pizza_img","Slices pepperoni ,Mozzarella cheese ,Fresh oregano ,Ground black pepper ,Pizza sauce",319.00));
        foodList.add(new FoodDomain("Cheese Burger","burger_intro","Lettuce, Special sauce, Cheese Slice",99.00));
        foodList.add(new FoodDomain("Pasta","pasta","Fresh Oregano ,Chilli Flakes, Special Sauces",249.00));

        mAdapter2=new PopularAdapter(foodList);
        recyclerViewPopularList.setAdapter(mAdapter2);
    }
}
