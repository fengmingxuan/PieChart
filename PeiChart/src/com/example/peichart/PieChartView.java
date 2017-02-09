/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-2-9上午9:53:17
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.example.peichart;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-2-9上午9:53:17
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.MeasureSpec;

public class PieChartView extends View {
    private int screenW, screenH;
    /**
     * The paint to draw text, pie and line.
     */
    private Paint textPaint, piePaint, linePaint;
 
    /**
     * The center and the radius of the pie.
     */
    private int pieCenterX, pieCenterY, pieRadius;
    /**
     * The oval to draw the oval in.
     */
    private RectF pieOval;
    /**选中*/
    private RectF selectPieOval;
 
    private float smallMargin;
    
    private int lineColor;
 
//    private int[] mPieColors = new int[]{Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.MAGENTA, Color.CYAN};
 
    private PieItemBean[] mPieItems;
    private float totalValue;
 
    public PieChartView(Context context) {
        super(context);
 
        init(context);
    }
 
    public PieChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
 
        init(context);
    }
 
    public PieChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
 
        init(context);
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode == MeasureSpec.AT_MOST
                || heightMode == MeasureSpec.AT_MOST) {
        }
        setMeasuredDimension(width, height);

    }
     
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
      //init screen
        screenW = getMeasuredWidth();
        screenH = getMeasuredHeight();
 
        pieCenterX = screenW / 2;
        pieCenterY = screenH / 2;
        pieRadius = screenW / 4;
        
 
        selectPieOval = new RectF();
        pieOval = new RectF();
        pieOval.left = pieCenterX - pieRadius;
        pieOval.top = pieCenterY - pieRadius;
        pieOval.right = pieCenterX + pieRadius;
        pieOval.bottom = pieCenterY + pieRadius;

    }
   
 
    private void init(Context context) {
        
    	smallMargin = dpToPx(context, 5);
        //The paint to draw text.
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(dpToPx(context, 16));
 
        //The paint to draw circle.
        piePaint = new Paint();
        piePaint.setAntiAlias(true);
        piePaint.setStyle(Paint.Style.FILL);
 
        //The paint to draw line to show the concrete text
        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setStrokeWidth(dpToPx(context, 1));
        
 
    }
 
    //The degree position of the last item arc's center.
    private float lastDegree = 0;
    //The count of the continues 'small' item.
    private int addTimes = 0;
 
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
 
        if (mPieItems != null && mPieItems.length > 0) {
            float start = 0.0f;
            for (int i = 0; i < mPieItems.length; i++) {
                //draw pie
//                piePaint.setColor(mPieColors[i % mPieColors.length]);
            	piePaint.setColor(mPieItems[i].getItemColor());
                float sweep = mPieItems[i].getItemValue() / totalValue * 360;
                
                System.out.println("selectPie=="+selectPie);
                //新添加 
                if (selectPie >= 0 && i == selectPie) {
                	selectPieOval = new RectF();
                	selectPieOval.left = pieCenterX - pieRadius;
                	selectPieOval.top = pieCenterY - pieRadius;
                	selectPieOval.right = pieCenterX + pieRadius;
                	selectPieOval.bottom = pieCenterY + pieRadius;
                     
                	 
                    Point point = points.get(selectPie);
                    int middle = (point.x + point.y) / 2;
                    if (middle <= 90) {
                        int top = (int) (Math.sin(Math.toRadians(middle)) * 15);
                        int left = (int) (Math.cos(Math.toRadians(middle)) * 15);
                        selectPieOval.left += left;
                        selectPieOval.right += left;
                        selectPieOval.top += top;
                        selectPieOval.bottom += top;
                    }
                    if (middle > 90 && middle <= 180) {
                        middle = 180 - middle;
                        int top = (int) (Math.sin(Math.toRadians(middle)) * 15);
                        int left = (int) (Math.cos(Math.toRadians(middle)) * 15);
                        selectPieOval.left -= left;
                        selectPieOval.right -= left;
                        selectPieOval.top += top;
                        selectPieOval.bottom += top;
                    }
                    if (middle > 180 && middle <= 270) {
                        middle = 270 - middle;
                        int left = (int) (Math.sin(Math.toRadians(middle)) * 15);
                        int top = (int) (Math.cos(Math.toRadians(middle)) * 15);
                        selectPieOval.left -= left;
                        selectPieOval.right -= left;
                        selectPieOval.top -= top;
                        selectPieOval.bottom -= top;
                    }
                    if (middle > 270 && middle <= 360) {
                        middle = 360 - middle;
                        int top = (int) (Math.sin(Math.toRadians(middle)) * 15);
                        int left = (int) (Math.cos(Math.toRadians(middle)) * 15);
                        selectPieOval.left += left;
                        selectPieOval.right += left;
                        selectPieOval.top -= top;
                        selectPieOval.bottom -= top;
                    }
//                    piePaint.setColor(mPieColors[i % mPieColors.length]);
                    piePaint.setColor(mPieItems[i].getItemColor());
                    canvas.drawArc(selectPieOval, start, sweep, true,
                    		piePaint);
                } else {
                	 
                    canvas.drawArc(pieOval, start, sweep, true, piePaint);
                }
                points.get(i).x = (int) start;
                points.get(i).y = (int) (start + sweep);
              //新添加 end 
                
              // canvas.drawArc(pieOval, start, sweep, true, piePaint);
 
                //draw line away from the pie
                float radians = (float) ((start + sweep / 2) / 180 * Math.PI);
                float lineStartX = pieCenterX + pieRadius * 0.7f * (float) (Math.cos(radians));
                float lineStartY = pieCenterY + pieRadius * 0.7f * (float) (Math.sin(radians));
 
                float lineStopX, lineStopY;
                float rate;
                if (getOffset(start + sweep / 2) > 60) {
                    rate = 1.3f;
                } else if (getOffset(start + sweep / 2) > 30) {
                    rate = 1.2f;
                } else {
                    rate = 1.1f;
                }
                //If the item is very small, make the text further away from the pie to avoid being hided by other text.
                if (start + sweep / 2 - lastDegree < 30) {
                    addTimes++;
                    rate += 0.2f * addTimes;
                } else {
                    addTimes = 0;
                }
                lineStopX = pieCenterX + pieRadius * rate * (float) (Math.cos(radians));
                lineStopY = pieCenterY + pieRadius * rate * (float) (Math.sin(radians));
                linePaint.setColor(lineColor);
                canvas.drawLine(lineStartX, lineStartY, lineStopX, lineStopY, linePaint);
 
                //write text
                String itemTypeText = mPieItems[i].getItemType();
//                String itemPercentText = (mPieItems[i].getItemValue() /(1f*totalValue) * 100) + "%";
                String itemPercentText = mPieItems[i].getItemLineType();;
 
                textPaint.setColor(mPieItems[i].getItemTextColor());
                float itemTypeTextLen = textPaint.measureText(itemTypeText);
                float itemPercentTextLen = textPaint.measureText(itemPercentText);
                float lineTextWidth = Math.max(itemTypeTextLen, itemPercentTextLen);
 
                float textStartX = lineStopX;
                float textStartY = lineStopY - smallMargin;
                float percentStartX = lineStopX;
                float percentStartY = lineStopY + textPaint.getTextSize();
                if (lineStartX > pieCenterX) {
                    textStartX += (smallMargin + Math.abs(itemTypeTextLen - lineTextWidth) / 2);
                    percentStartX += (smallMargin + Math.abs(itemPercentTextLen - lineTextWidth) / 2);
                } else {
                    textStartX -= (smallMargin + lineTextWidth - Math.abs(itemTypeTextLen - lineTextWidth) / 2);
                    percentStartX -= (smallMargin + lineTextWidth - Math.abs(itemPercentTextLen - lineTextWidth) / 2);
                }
                canvas.drawText(itemTypeText, textStartX, textStartY, textPaint);
                //draw percent text
                canvas.drawText(itemPercentText, percentStartX, percentStartY, textPaint);
 
                //draw text underline
                float textLineStopX = lineStopX;
                if (lineStartX > pieCenterX) {
                    textLineStopX += (lineTextWidth + smallMargin * 2);
                } else {
                    textLineStopX -= (lineTextWidth + smallMargin * 2);
                }
                linePaint.setColor(lineColor);
                canvas.drawLine(lineStopX, lineStopY, textLineStopX, lineStopY, linePaint);
 
                lastDegree = start + sweep / 2;
                start += sweep;
            }
        }
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float x = event.getX();
            float y = event.getY();
            int radius = 0;
            // 第一象限
            if (x >= getMeasuredWidth() / 2 && y >= getMeasuredHeight() / 2) {
                radius = (int) (Math.atan((y - getMeasuredHeight() / 2) * 1.0f
                        / (x - getMeasuredWidth() / 2)) * 180 / Math.PI);
                System.out.println("第一象限");
            }
            // 第二象限
            if (x <= getMeasuredWidth() / 2 && y >= getMeasuredHeight() / 2) {
                radius = (int) (Math.atan((getMeasuredWidth() / 2 - x)
                        / (y - getMeasuredHeight() / 2))
                        * 180 / Math.PI + 90);
                System.out.println("第二象限");
            }
            // 第三象限
            if (x <= getMeasuredWidth() / 2 && y <= getMeasuredHeight() / 2) {
                radius = (int) (Math.atan((getMeasuredHeight() / 2 - y)
                        / (getMeasuredWidth() / 2 - x))
                        * 180 / Math.PI + 180);
                System.out.println("第三象限");
            }
            // 第四象限
            if (x >= getMeasuredWidth() / 2 && y <= getMeasuredHeight() / 2) {
                radius = (int) (Math.atan((x - getMeasuredWidth() / 2)
                        / (getMeasuredHeight() / 2 - y))
                        * 180 / Math.PI + 270);
                System.out.println("第四象限");
            }
            for (int i = 0; i < points.size(); i++) {
                Point point = points.get(i);
                System.out.println("radius=="+radius+";point.x="+point.x+";point.y="+point.y+"(x+y)/2=="+((point.x+point.y)/2));
                if (point.x <= radius && point.y >= radius) {
                	selectPie = i;
                    System.out.println("i=="+i);
                    invalidate();
                    return true;
                }
            }
            return true;
        	
        }
        return super.onTouchEvent(event);
    }
    
    
    
    private List<Point> points;
    private int selectPie = -1;
 
    public PieItemBean[] getPieItems() {
        return mPieItems;
    }
 
    public void setPieItems(PieItemBean[] pieItems) {
        this.mPieItems = pieItems;
        points = new ArrayList<Point>();
        totalValue = 0;
        for (PieItemBean item : mPieItems) {
            totalValue += item.getItemValue();
            Point point = new Point();
            points.add(point);
        }
 
        invalidate();
    }
 
    private float getOffset(float radius) {
        int a = (int) (radius % 360 / 90);
        switch (a) {
            case 0:
                return radius;
            case 1:
                return 180 - radius;
            case 2:
                return radius - 180;
            case 3:
                return 360 - radius;
        }
 
        return radius;
    }
   
    
    public void setSelectPie(int selectPie) {
		this.selectPie = selectPie;
		 invalidate();
	}

	public   float dpToPx(Context context, float dp) {
		if (context == null) {
			return -1;
		}
		return dp * context.getResources().getDisplayMetrics().density;
	}
    

	public static int getScreenW(Context context) {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);

		int width = wm.getDefaultDisplay().getWidth();
		return width;
	}

	public static int getScreenH(Context context) {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);

		int height = wm.getDefaultDisplay().getHeight();
		return height;
	}

	public int getLineColor() {
		return lineColor;
	}

	public void setLineColor(int lineColor) {
		this.lineColor = lineColor;
	}
}	
	
