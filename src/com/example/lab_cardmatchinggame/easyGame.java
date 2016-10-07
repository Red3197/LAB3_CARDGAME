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

public class easyGame extends Activity implements OnClickListener {

	static final int CARD_NUMBERS = 6;
	ImageButton[] backImages = new ImageButton[CARD_NUMBERS];
	ImageButton[] cardImages = new ImageButton[CARD_NUMBERS];
	int[] ids;
	int selecting ;
	int counter;
	int[][] ansCard = new int[CARD_NUMBERS][2];
	int s1 , s2 ;
	boolean tip ;
	int matching;
	
	@Override 
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_easy);
		counter= 0;
		matching= 0;
		selecting= -1;
		setIds();
		findView();
		setlisten();
		ansCard = randomCard();
		settingCard(ansCard);
		rememberTime();
	}

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
		if(counter == 0){
			for(int i=0; i<CARD_NUMBERS; i++) {
				backImages[i].setVisibility(ImageView.VISIBLE);
				cardImages[i].setVisibility(ImageView.INVISIBLE);
			}
			counter++;
		}
		if(tip){
			tipTime();
			tip = false;
			return;
		}
		if(matching == CARD_NUMBERS/2){
			Intent intent = new Intent();
			intent.setClass(easyGame.this,
					easyGame.class);
			startActivity(intent);
			easyGame.this.finish();
		}
		switch(v.getId()){
		case R.id.pick1_b:
			onClickHappened(0);
			break;
		case R.id.pick2_b:
			onClickHappened(1);
			break;
		case R.id.pick3_b:
			onClickHappened(2);
			break;
		case R.id.pick4_b:
			onClickHappened(3);
			break;
		case R.id.pick5_b:
			onClickHappened(4);
			break;
		case R.id.pick6_b:
			onClickHappened(5);
			break;
		default:
			return;
		}
		counter++;
	}
	
	void tipTime(){
		backImages[s1].setVisibility(ImageView.VISIBLE);
		cardImages[s1].setVisibility(ImageView.INVISIBLE);
		backImages[s2].setVisibility(ImageView.VISIBLE);
		cardImages[s2].setVisibility(ImageView.INVISIBLE);
	}
	
	void onClickHappened(int index){
		backImages[index].setVisibility(ImageView.INVISIBLE);
		cardImages[index].setVisibility(ImageView.VISIBLE);
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
			backImages[i].setVisibility(ImageView.INVISIBLE);
			cardImages[i].setVisibility(ImageView.VISIBLE);
		}
	}
	
	void settingCard(int[][] trueCard){
		for(int i=0; i<CARD_NUMBERS; i++){
			cardImages[i].setBackgroundResource(ids[ trueCard[i][0]+(trueCard[i][1]*13) ] ); //thisCard[i][0] + (thisCard[i][1]*13)
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
		cardImages[0].setOnClickListener(this);
		backImages[0].setOnClickListener(this);
		cardImages[1].setOnClickListener(this);
		backImages[1].setOnClickListener(this);
		cardImages[2].setOnClickListener(this);
		backImages[2].setOnClickListener(this);
		cardImages[3].setOnClickListener(this);
		backImages[3].setOnClickListener(this);
		cardImages[4].setOnClickListener(this);
		backImages[4].setOnClickListener(this);
		cardImages[5].setOnClickListener(this);
		backImages[5].setOnClickListener(this);
	}
	
	void findView(){
		cardImages[0] = (ImageButton)findViewById(R.id.pick1);
		backImages[0] = (ImageButton)findViewById(R.id.pick1_b);
		cardImages[1] = (ImageButton)findViewById(R.id.pick2);
		backImages[1] = (ImageButton)findViewById(R.id.pick2_b);
		cardImages[2] = (ImageButton)findViewById(R.id.pick3);
		backImages[2] = (ImageButton)findViewById(R.id.pick3_b);
		cardImages[3] = (ImageButton)findViewById(R.id.pick4);
		backImages[3] = (ImageButton)findViewById(R.id.pick4_b);
		cardImages[4] = (ImageButton)findViewById(R.id.pick5);
		backImages[4] = (ImageButton)findViewById(R.id.pick5_b);
		cardImages[5] = (ImageButton)findViewById(R.id.pick6);
		backImages[5] = (ImageButton)findViewById(R.id.pick6_b);
		
	}
	
	void setIds(){
		ids = new int[53];
		for(int i=0; i<52; i++){
			ids[i] = 0x7f020003 + i ;
		}
		ids[52] = R.drawable.backs;
	}
	
}