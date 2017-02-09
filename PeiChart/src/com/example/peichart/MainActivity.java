package com.example.peichart;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		 PieChartView pieChartView = (PieChartView)
		 findViewById(R.id.pie_chart);
		  PieItemBean[] items = new  PieItemBean[]{
		 new  PieItemBean("”È¿÷", 100,Color.RED,"100",Color.RED),
		 new  PieItemBean("¬√––", 200,Color.GREEN,"200",Color.GREEN),
		 new  PieItemBean("—ßœ∞", 220, Color.BLUE,"200", Color.BLUE),
		 };
		  pieChartView.setLineColor(Color.BLACK);
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
