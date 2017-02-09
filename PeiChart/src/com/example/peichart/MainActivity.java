package com.example.peichart;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;

import com.example.peichart.PieChartViewT.OnSpecialTypeClickListener;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		 PieChartView pieChartView = (PieChartView)
		 findViewById(R.id.pie_chart);
		 PieChartView.PieItemBean[] items = new PieChartView.PieItemBean[]{
		 new PieChartView.PieItemBean("”È¿÷", 100),
		 new PieChartView.PieItemBean("¬√––", 200),
		 new PieChartView.PieItemBean("—ßœ∞", 220),
		 };
		 pieChartView.setPieItems(items);

//		PieChartViewT pieChartView = (PieChartViewT) findViewById(R.id.pie_chart);
//		List<PieChartViewT.PieData> mPieDataList = new ArrayList<PieChartViewT.PieData>();
//		mPieDataList.add(new PieChartViewT.PieData("”È¿÷", 0.2f,
//				android.R.color.holo_green_dark));
//		mPieDataList.add(new PieChartViewT.PieData("¬√––", 0.2f,
//				android.R.color.holo_green_dark));
//		mPieDataList.add(new PieChartViewT.PieData("—ßœ∞", 0.6f,
//				android.R.color.holo_green_dark));
//		pieChartView.setPieDataList(mPieDataList);
//		pieChartView
//				.setOnSpecialTypeClickListener(new OnSpecialTypeClickListener() {
//
//					@Override
//					public void onSpecialTypeClick(String type) {
//
//						System.out.println(type);
//					}
//				});
		
//		CircleChartView pieChartViewc = (CircleChartView) findViewById(R.id.pie_chartc);
//		List<Double> numbers = new ArrayList<Double>();
//		numbers.add(100.0);
//		numbers.add(200.0);
//		numbers.add(200.0);
//		pieChartViewc.setNumbers(numbers);
	}

}
