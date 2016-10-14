package com.example.lab_cardmatchinggame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;

public class normalGame extends Activity implements OnClickListener {
	//FIXME QQQQQ wheres BUGGGGGG
	static final int CARD_NUMBERS = 20;
	ImageButton[] normal_backImages = new ImageButton[CARD_NUMBERS];
	ImageButton[] normal_cardImages = new ImageButton[CARD_NUMBERS];
	int[] ids;
	int[] pickBackIds;
	int[] pickIds;
	int selecting ;
	int[][] ansCard;
	int s1 , s2 ;
	boolean tip ;
	int matching;
	
	@Override 
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_normal);
		matching= 0;
		selecting= -2;
		tip= false;
		s1 = s2 = 0;
		setIds();
		setPickIds();
		findView();
		setlisten();
		ansCard = randomCard();
		settingCard(ansCard);
		rememberTime();
	}
/*
	public void onClick(View v){}
	void setIds(){}
	void setPickIds(){}
	void findView(){}
	void setlisten(){}
	//int[][] randomCard(){return new int[][];}
	//void settingCard(int[][]){}
	void rememberTime(){}
	*/
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void onClick(View v){
		if(selecting == -2){
			for(int i=0; i<CARD_NUMBERS; i++) {
				normal_backImages[i].setVisibility(ImageView.VISIBLE);
				normal_cardImages[i].setVisibility(ImageView.INVISIBLE);
			}
			selecting= -1;
		}
		if(matching == CARD_NUMBERS/2){
			Intent intent = new Intent();
			intent.setClass(normalGame.this,
					normalGame.class);
			startActivity(intent);
			normalGame.this.finish();
		}
		if(tip){
			tipTime();
			tip = false;
		}else{		
			for(int i=0; i<CARD_NUMBERS; i++){
				if(v.getId() == pickBackIds[i]){
					onClickHappened(i);
				}
			}
		}
	}
	
	void tipTime(){
		normal_backImages[s1].setVisibility(ImageView.VISIBLE);
		normal_cardImages[s1].setVisibility(ImageView.INVISIBLE);
		normal_backImages[s2].setVisibility(ImageView.VISIBLE);
		normal_cardImages[s2].setVisibility(ImageView.INVISIBLE);
	}
	
	void onClickHappened(int index){
		normal_backImages[index].setVisibility(ImageView.INVISIBLE);
		normal_cardImages[index].setVisibility(ImageView.VISIBLE);
		if(check())
			compare(index);
		else
			selecting= index;
	}
	
	boolean check(){
		if(selecting == -1)
			return false;
		else
			return true;
	}
	
	void compare(int index){
		s1=selecting ;
		s2=index ;

		if( ansCard[s1][0] != ansCard[s2][0] ){
			tip=true;
		}
		else{
			matching++ ;
		}
		selecting= -1;
	}
	
	void rememberTime(){
		for(int i=0; i<CARD_NUMBERS; i++) {
			normal_backImages[i].setVisibility(ImageView.INVISIBLE);
			normal_cardImages[i].setVisibility(ImageView.VISIBLE);
		}
	}
	
	void settingCard(int[][] trueCard){
		for(int i=0; i<CARD_NUMBERS; i++){
			normal_cardImages[i].setBackgroundResource(
					ids[ trueCard[i][0]+(trueCard[i][1]*13) ] );
		}
	}
	
	int[][] randomCard(){
		int[][] cards = new int[CARD_NUMBERS][2];
		for(int i=0; i<CARD_NUMBERS; i+=2){
			cards[i][0] = cards[i+1][0] = (int)(Math.random()*13);
			for(int j=0; j<i; j+=2){
				if(cards[i][0] == cards[j][0]) i-=2;
			}
		}
		for(int i=0; i<CARD_NUMBERS; i+=2){
			cards[i][1] = (int)(Math.random()*4);
			cards[i+1][1] = (int)(Math.random()*4);
			if( cards[i][1] == cards[i+1][1] ) i-=2;
		}
		int[] tmp= new int[CARD_NUMBERS];
		for(int i=0; i<CARD_NUMBERS; i++){
			tmp[i] = (int)(Math.random()*CARD_NUMBERS);
			for(int j=0; j<i; j++){
				if(tmp[i]==tmp[j]) i--;
			}
		}
		int[][] trueCard = new int[CARD_NUMBERS][2];
		for(int i=0; i<CARD_NUMBERS; i++){
			trueCard[tmp[i]][0] = cards[i][0];
			trueCard[tmp[i]][1] = cards[i][1];
			//cardIdIndex[tmp[i]] = cards[i][0] + cards[i][1]*13;
		}
		return trueCard;
	}
	
	void setlisten(){
		for(int i=0; i<CARD_NUMBERS; i++){
			normal_cardImages[i].setOnClickListener(normalGame.this);
			normal_backImages[i].setOnClickListener(normalGame.this);
		}
	}
	
	void findView(){
		for(int i=0; i<CARD_NUMBERS; i++){
			normal_cardImages[i] = (ImageButton)findViewById(pickIds[i]);
			normal_backImages[i] = (ImageButton)findViewById(pickBackIds[i]);
		}
	}
	
	void setIds(){
		ids = new int[53];
		for(int i=0; i<52; i++){
			ids[i] = 0x7f020003 + i ;
		}
		ids[52] = R.drawable.backs;
	}
	void setPickIds(){
		pickBackIds= new int[CARD_NUMBERS];
		pickBackIds[0] = R.id.normal_pick1_b;
		pickBackIds[1] = R.id.normal_pick2_b;
		pickBackIds[2] = R.id.normal_pick3_b;
		pickBackIds[3] = R.id.normal_pick4_b;
		pickBackIds[4] = R.id.normal_pick5_b;
		pickBackIds[5] = R.id.normal_pick6_b;
		pickBackIds[6] = R.id.normal_pick7_b;
		pickBackIds[7] = R.id.normal_pick8_b;
		pickBackIds[8] = R.id.normal_pick9_b;
		pickBackIds[9] = R.id.normal_pick10_b;
		pickBackIds[10] = R.id.normal_pick11_b;
		pickBackIds[11] = R.id.normal_pick12_b;
		pickBackIds[12] = R.id.normal_pick13_b;
		pickBackIds[13] = R.id.normal_pick14_b;
		pickBackIds[14] = R.id.normal_pick15_b;
		pickBackIds[15] = R.id.normal_pick16_b;
		pickBackIds[16] = R.id.normal_pick17_b;
		pickBackIds[17] = R.id.normal_pick18_b;
		pickBackIds[18] = R.id.normal_pick19_b;
		pickBackIds[19] = R.id.normal_pick20_b;
		pickIds= new int[CARD_NUMBERS];
		pickIds[0] = R.id.normal_pick1;
		pickIds[1] = R.id.normal_pick2;
		pickIds[2] = R.id.normal_pick3;
		pickIds[3] = R.id.normal_pick4;
		pickIds[4] = R.id.normal_pick5;
		pickIds[5] = R.id.normal_pick6;
		pickIds[6] = R.id.normal_pick7;
		pickIds[7] = R.id.normal_pick8;
		pickIds[8] = R.id.normal_pick9;
		pickIds[9] = R.id.normal_pick10;
		pickIds[10] = R.id.normal_pick11;
		pickIds[11] = R.id.normal_pick12;
		pickIds[12] = R.id.normal_pick13;
		pickIds[13] = R.id.normal_pick14;
		pickIds[14] = R.id.normal_pick15;
		pickIds[15] = R.id.normal_pick16;
		pickIds[16] = R.id.normal_pick17;
		pickIds[17] = R.id.normal_pick18;
		pickIds[18] = R.id.normal_pick19;
		pickIds[19] = R.id.normal_pick20;
	}
	
}