package com.github.hindmasj.tipcalculator;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class TipCalculator extends Activity{
	private static final String BILL_TOTAL="BILL_TOTAL";
	private static final String CUSTOM_PERCENT="CUSTOM_PERCENT";
	
	private double currentBillTotal;
	private int currentCustomPercent;
	private EditText tip10EditText;
	private EditText total10EditText;
	private EditText tip15EditText;
	private EditText total15EditText;
	private EditText tip20EditText;
	private EditText total20EditText;
	private EditText tipCustomEditText;
	private EditText totalCustomEditText;
	private EditText billEditText;
	private TextView customTipTextView;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		if(savedInstanceState==null){
			currentBillTotal=0.0;
			currentCustomPercent=18;
		}else{
			currentBillTotal=savedInstanceState.getDouble(BILL_TOTAL);
			currentCustomPercent=savedInstanceState.getInt(CUSTOM_PERCENT);
		}
		
		tip10EditText=(EditText)findViewById(R.id.tip10EditText);
		tip15EditText=(EditText)findViewById(R.id.tip15EditText);
		tip20EditText=(EditText)findViewById(R.id.tip20EditText);
		total10EditText=(EditText)findViewById(R.id.total10EditText);
		total15EditText=(EditText)findViewById(R.id.total15EditText);
		total20EditText=(EditText)findViewById(R.id.total20EditText);
		tipCustomEditText=(EditText)findViewById(R.id.tipCustomEditText);
		totalCustomEditText=(EditText)findViewById(R.id.totalCustomEditText);
		
		customTipTextView=(TextView)findViewById(R.id.customTipTextView);
		
		billEditText=(EditText)findViewById(R.id.billEditText);
		billEditText.addTextChangedListener(billEditTextWatcher);
		
		SeekBar customSeekBar=(SeekBar)findViewById(R.id.customSeekBar);
		customSeekBar.setOnSeekBarChangeListener(customSeekBarListener);
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState){
		super.onSaveInstanceState(outState);
		outState.putDouble(BILL_TOTAL, currentBillTotal);
		outState.putInt(CUSTOM_PERCENT, currentCustomPercent);
	}
	
	private void updateStandard(){
		double tip=currentBillTotal*.1;
		double total=currentBillTotal+tip;
		tip10EditText.setText(String.format("%0.20f", tip));
		total10EditText.setText(String.format("%0.20f", total));
		
		tip=currentBillTotal*.15;
		total=currentBillTotal+tip;
		tip15EditText.setText(String.format("%0.20f", tip));
		total15EditText.setText(String.format("%0.20f", total));

		tip=currentBillTotal*.2;
		total=currentBillTotal+tip;
		tip20EditText.setText(String.format("%0.20f", tip));
		total20EditText.setText(String.format("%0.20f", total));
	}
	
	private void updateCustom(){
		customTipTextView.setText(currentCustomPercent+"%");
		
		double tip=currentBillTotal*currentCustomPercent*.01;
		double total=currentBillTotal+tip;
		tipCustomEditText.setText(String.format("%0.20f", tip));
		totalCustomEditText.setText(String.format("%0.20f", total));		
	}

	private OnSeekBarChangeListener customSeekBarListener=new OnSeekBarChangeListener(){
		@Override
		public void onProgressChanged(SeekBar seekBar,int Progress, boolean fromUser){
			currentCustomPercent=seekBar.getProgress();
			updateCustom();
		}
		
		@Override
		public void onStartTrackingTouch(SeekBar seekBar){}
		
		@Override
		public void onStopTrackingTouch(SeekBar seekBar){}
	};
	
	private TextWatcher billEditTextWatcher=new TextWatcher(){
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count){
			try{
				currentBillTotal=Double.parseDouble(s.toString());
			}catch(NumberFormatException e){
				currentBillTotal=0.0;
			}
			updateStandard();
			updateCustom();
		}
		
		@Override
		public void afterTextChanged(Editable s){}
			
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after){}
		
	};
	
	
	
}

