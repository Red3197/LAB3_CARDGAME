package com.example.lab_cardmatchinggame;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;

public class hardGame extends Activity implements OnClickListener {
	static final int CARD_NUMBERS = 36;
	ImageButton[] hard_backImages = new ImageButton[CARD_NUMBERS];
	ImageButton[] hard_cardImages = new ImageButton[CARD_NUMBERS];
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
		setContentView(R.layout.activity_hard);
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
				hard_backImages[i].setVisibility(ImageView.VISIBLE);
				hard_cardImages[i].setVisibility(ImageView.INVISIBLE);
			}
			selecting= -1;
		}
		if(matching == CARD_NUMBERS/2){
			Intent intent = new Intent();
			intent.setClass(hardGame.this,
					hardGame.class);
			startActivity(intent);
			hardGame.this.finish();
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
		hard_backImages[s1].setVisibility(ImageView.VISIBLE);
		hard_cardImages[s1].setVisibility(ImageView.INVISIBLE);
		hard_backImages[s2].setVisibility(ImageView.VISIBLE);
		hard_cardImages[s2].setVisibility(ImageView.INVISIBLE);
	}
	
	void onClickHappened(int index){
		hard_backImages[index].setVisibility(ImageView.INVISIBLE);
		hard_cardImages[index].setVisibility(ImageView.VISIBLE);
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
			hard_backImages[i].setVisibility(ImageView.INVISIBLE);
			hard_cardImages[i].setVisibility(ImageView.VISIBLE);
		}
	}
	
	void settingCard(int[][] trueCard){
		for(int i=0; i<CARD_NUMBERS; i++){
			hard_cardImages[i].setBackgroundResource(
					ids[ trueCard[i][0]+(trueCard[i][1]*13) ] );
		}
	}
	
	int[][] randomCard(){
		ArrayList<Integer> setedColor = new ArrayList<Integer>();
		int[][] cards = new int[CARD_NUMBERS][2];
		for(int i=0; i<CARD_NUMBERS; i+=2){
			int sameNumCounter=0;
			int tmp=0;
			cards[i][0] = cards[i+1][0] = (int)(Math.random()*13);
			for(int j=0; j<i; j+=2){
				if(cards[i][0] == cards[j][0]){
					sameNumCounter++;
					tmp=j;
				}
			}
			if(sameNumCounter == 2) i-=2;
			else if(sameNumCounter == 1){
				cards[i][1] = 1;
				cards[i+1][1] = 2;
				cards[tmp][1] = 3;
				cards[tmp+1][1] = 0;
				setedColor.add(i);
				setedColor.add(tmp);
			}
		}
		for(int i=0; i<CARD_NUMBERS; i+=2){
			if(setedColor.contains(i)){
				continue;
			}
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
			hard_cardImages[i].setOnClickListener(hardGame.this);
			hard_backImages[i].setOnClickListener(hardGame.this);
		}
	}
	
	void findView(){
		for(int i=0; i<CARD_NUMBERS; i++){
			hard_cardImages[i] = (ImageButton)findViewById(pickIds[i]);
			hard_backImages[i] = (ImageButton)findViewById(pickBackIds[i]);
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
		pickBackIds[0] = R.id.hard_pick1_b;
		pickBackIds[1] = R.id.hard_pick2_b;
		pickBackIds[2] = R.id.hard_pick3_b;
		pickBackIds[3] = R.id.hard_pick4_b;
		pickBackIds[4] = R.id.hard_pick5_b;
		pickBackIds[5] = R.id.hard_pick6_b;
		pickBackIds[6] = R.id.hard_pick7_b;
		pickBackIds[7] = R.id.hard_pick8_b;
		pickBackIds[8] = R.id.hard_pick9_b;
		pickBackIds[9] = R.id.hard_pick10_b;
		pickBackIds[10] = R.id.hard_pick11_b;
		pickBackIds[11] = R.id.hard_pick12_b;
		pickBackIds[12] = R.id.hard_pick13_b;
		pickBackIds[13] = R.id.hard_pick14_b;
		pickBackIds[14] = R.id.hard_pick15_b;
		pickBackIds[15] = R.id.hard_pick16_b;
		pickBackIds[16] = R.id.hard_pick17_b;
		pickBackIds[17] = R.id.hard_pick18_b;
		pickBackIds[18] = R.id.hard_pick19_b;
		pickBackIds[19] = R.id.hard_pick20_b;
		pickBackIds[20] = R.id.hard_pick21_b;
		pickBackIds[21] = R.id.hard_pick22_b;
		pickBackIds[22] = R.id.hard_pick23_b;
		pickBackIds[23] = R.id.hard_pick24_b;
		pickBackIds[24] = R.id.hard_pick25_b;
		pickBackIds[25] = R.id.hard_pick26_b;
		pickBackIds[26] = R.id.hard_pick27_b;
		pickBackIds[27] = R.id.hard_pick28_b;
		pickBackIds[28] = R.id.hard_pick29_b;
		pickBackIds[29] = R.id.hard_pick30_b;
		pickBackIds[30] = R.id.hard_pick31_b;
		pickBackIds[31] = R.id.hard_pick32_b;
		pickBackIds[32] = R.id.hard_pick33_b;
		pickBackIds[33] = R.id.hard_pick34_b;
		pickBackIds[34] = R.id.hard_pick35_b;
		pickBackIds[35] = R.id.hard_pick36_b;
		pickIds= new int[CARD_NUMBERS];
		pickIds[0] = R.id.hard_pick1;
		pickIds[1] = R.id.hard_pick2;
		pickIds[2] = R.id.hard_pick3;
		pickIds[3] = R.id.hard_pick4;
		pickIds[4] = R.id.hard_pick5;
		pickIds[5] = R.id.hard_pick6;
		pickIds[6] = R.id.hard_pick7;
		pickIds[7] = R.id.hard_pick8;
		pickIds[8] = R.id.hard_pick9;
		pickIds[9] = R.id.hard_pick10;
		pickIds[10] = R.id.hard_pick11;
		pickIds[11] = R.id.hard_pick12;
		pickIds[12] = R.id.hard_pick13;
		pickIds[13] = R.id.hard_pick14;
		pickIds[14] = R.id.hard_pick15;
		pickIds[15] = R.id.hard_pick16;
		pickIds[16] = R.id.hard_pick17;
		pickIds[17] = R.id.hard_pick18;
		pickIds[18] = R.id.hard_pick19;
		pickIds[19] = R.id.hard_pick20;
		pickIds[20] = R.id.hard_pick21;
		pickIds[21] = R.id.hard_pick22;
		pickIds[22] = R.id.hard_pick23;
		pickIds[23] = R.id.hard_pick24;
		pickIds[24] = R.id.hard_pick25;
		pickIds[25] = R.id.hard_pick26;
		pickIds[26] = R.id.hard_pick27;
		pickIds[27] = R.id.hard_pick28;
		pickIds[28] = R.id.hard_pick29;
		pickIds[29] = R.id.hard_pick30;
		pickIds[30] = R.id.hard_pick31;
		pickIds[31] = R.id.hard_pick32;
		pickIds[32] = R.id.hard_pick33;
		pickIds[33] = R.id.hard_pick34;
		pickIds[34] = R.id.hard_pick35;
		pickIds[35] = R.id.hard_pick36;
	}
	
}