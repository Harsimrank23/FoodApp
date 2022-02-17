package org.example.foodapp.Activity.Helper;

import android.content.Context;
import android.widget.Toast;

import org.example.foodapp.Activity.Domain.FoodDomain;
import org.example.foodapp.Activity.Interface.ChangeNumberItemsListener;

import java.util.ArrayList;

public class ManagementCard {
    // the tiny db class is a class for storing data and objects on shared preferences which we have slightly modified and added to the food domain
    private Context mContext;
    private TinyDB mTinyDB;

    public ManagementCard(Context context) {
        this.mContext = context;
        this.mTinyDB=new TinyDB(context);
    }

    public void insertFood(FoodDomain item){
        ArrayList<FoodDomain> listFood=getListCard();
        boolean existAlready=false;
        int n=0;
        for(int i=0;i<listFood.size();i++){
            if(listFood.get(i).getTitle().equals(item.getTitle())){
                existAlready=true;
                n=i;
                break;
            }
        }
        if(existAlready){
            listFood.get(n).setNumberInCard(item.getNumberInCard());
        }else {
            listFood.add(item);
        }
        mTinyDB.putListObject("CardList",listFood);
        Toast.makeText(mContext,"Added to your Cart",Toast.LENGTH_SHORT).show();
    }
    public ArrayList<FoodDomain> getListCard(){
        return mTinyDB.getListObject("CardList");
    }

    public void plusNumberFood(ArrayList<FoodDomain> listFood, int position, ChangeNumberItemsListener changeNumberItemsListener){
        listFood.get(position).setNumberInCard(listFood.get(position).getNumberInCard()+1);
        mTinyDB.putListObject("CardList",listFood);
        changeNumberItemsListener.changed();
    }

    public void MinusNumberFood(ArrayList<FoodDomain> listFood, int position, ChangeNumberItemsListener changeNumberItemsListener){
        if (listFood.get(position).getNumberInCard() == 1) {
            listFood.remove(position);
        }
        else {
            listFood.get(position).setNumberInCard(listFood.get(position).getNumberInCard()-1);
        }
        mTinyDB.putListObject("CardList",listFood);
        changeNumberItemsListener.changed();
    }

    public Double getTotalFee(){
        ArrayList<FoodDomain> listFood2=getListCard();
        double fee=0;
        for(int i=0;i<listFood2.size();i++){
            fee=fee+(listFood2.get(i).getFee()*listFood2.get(i).getNumberInCard());
        }
        return fee;
    }
}
