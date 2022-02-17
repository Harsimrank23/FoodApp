package org.example.foodapp.Activity.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.example.foodapp.Activity.Domain.FoodDomain;
import org.example.foodapp.Activity.Helper.ManagementCard;
import org.example.foodapp.Activity.Interface.ChangeNumberItemsListener;
import org.example.foodapp.Activity.ShowDetailActivity;
import org.example.foodapp.R;

import java.util.ArrayList;

public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.ViewHolder> {

    private ManagementCard mManagementCard;
    private ChangeNumberItemsListener mChangeNumberItemsListener;
    private ArrayList<FoodDomain> mFoodDomains;

    public CardListAdapter(ArrayList<FoodDomain> FoodDomains, Context context,ChangeNumberItemsListener changeNumberItemsListener) {
        this.mFoodDomains = FoodDomains;
        mManagementCard=new ManagementCard(context);
        this.mChangeNumberItemsListener=changeNumberItemsListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View inflate=inflater.inflate(R.layout.viewholder_card,parent,false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.title.setText(mFoodDomains.get(position).getTitle());
        holder.feeEachItem.setText(String.valueOf(mFoodDomains.get(position).getFee()));
        holder.totalEachItem.setText(String.valueOf(Math.round((mFoodDomains.get(position).getNumberInCard()*mFoodDomains.get(position).getFee())*100.0)/100.0));
        holder.num.setText(String.valueOf(mFoodDomains.get(position).getNumberInCard()));

        int drawableResourceId=holder.itemView.getContext().getResources().getIdentifier(mFoodDomains.get(position).getPic(),"drawable",holder.itemView.getContext().getPackageName());

//        Glide is an Image Loader Library
        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId)
                .into(holder.pic);

        holder.plusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mManagementCard.plusNumberFood(mFoodDomains, position, new ChangeNumberItemsListener() {
                    @Override
                    public void changed() {
                        notifyDataSetChanged();
                        mChangeNumberItemsListener.changed();
                    }
                });
            }
        });

        holder.minusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mManagementCard.MinusNumberFood(mFoodDomains, position, new ChangeNumberItemsListener() {
                    @Override
                    public void changed() {
                        notifyDataSetChanged();
                        mChangeNumberItemsListener.changed();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFoodDomains.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title,feeEachItem;
        ImageView pic,plusItem,minusItem;
        TextView totalEachItem,num;
//        ConstraintLayout mainLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title2Txt);
            feeEachItem = itemView.findViewById(R.id.feeEachItem);
            pic = itemView.findViewById(R.id.picCard);
            totalEachItem = itemView.findViewById(R.id.totalEachItem);
            num = itemView.findViewById(R.id.numberItemTxt);
            plusItem = itemView.findViewById(R.id.plusBtnCard);
            minusItem = itemView.findViewById(R.id.minusBtnCard);
        }
    }
}
