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
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.example.foodapp.Activity.Adapter.CardListAdapter;
import org.example.foodapp.Activity.Helper.ManagementCard;
import org.example.foodapp.Activity.Interface.ChangeNumberItemsListener;
import org.example.foodapp.R;

public class CardListActivity extends AppCompatActivity {

    private RecyclerView.Adapter mAdapter;
    private RecyclerView mRecyclerViewList;
    private ManagementCard mManagementCard;
    private TextView totalFeeTxt,taxTxt,delieveryTxt,totalTxt,emptyTxt;
    private double tax;
    private ScrollView mScrollView;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_list);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP)
        {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.orange));
            getWindow().setStatusBarColor(getResources().getColor(R.color.orange));
        }

        mManagementCard=new ManagementCard(this);


        initView();
        initlist();
        calculateCard();
        bottomNavigation();
    }

    private void bottomNavigation(){
        FloatingActionButton floatingActionButton=findViewById(R.id.card_btn2);
        LinearLayout homeBtn=findViewById(R.id.homeBtn);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CardListActivity.this,CardListActivity.class));
            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CardListActivity.this,MainActivity.class));
            }
        });
    }

    private void initlist(){
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mRecyclerViewList.setLayoutManager(linearLayoutManager);

        mAdapter=new CardListAdapter(mManagementCard.getListCard(), this, new ChangeNumberItemsListener() {
            @Override
            public void changed() {
                calculateCard();
            }
        });

        mRecyclerViewList.setAdapter(mAdapter);
        if(mManagementCard.getListCard().isEmpty()){
            emptyTxt.setVisibility(View.VISIBLE);
            mScrollView.setVisibility(View.GONE);
        }else{
            emptyTxt.setVisibility(View.GONE);
            mScrollView.setVisibility(View.VISIBLE);
        }
    }

    private void calculateCard(){
        double percentTax=0.12;
        double delivery=0.0;
        tax=Math.round((mManagementCard.getTotalFee()*percentTax)*100.0)/100.0;
        double total=Math.round((mManagementCard.getTotalFee()+tax+delivery)*100)/100.0;

        double itemTotal=Math.round(mManagementCard.getTotalFee()*100)/100;
        if(itemTotal<250)
            delivery=50.0;
        if(itemTotal!=0)
        {
            totalFeeTxt.setText("Rs "+itemTotal);
            taxTxt.setText("Rs "+tax);
            delieveryTxt.setText("Rs "+delivery);
            totalTxt.setText("Rs "+total);
        }
        else
        {
            emptyTxt.setVisibility(View.VISIBLE);
            mScrollView.setVisibility(View.GONE);
        }
    }
    private void initView(){
        mRecyclerViewList=findViewById(R.id.RecyclerView);
        totalFeeTxt=findViewById(R.id.totalFeeTxt);
        taxTxt=findViewById(R.id.taxTxt);
        delieveryTxt=findViewById(R.id.delieveryTxt);
        totalTxt=findViewById(R.id.totalTxt);
        emptyTxt=findViewById(R.id.emptyTxt);
        mScrollView=findViewById(R.id.scrollView3);
    }
}