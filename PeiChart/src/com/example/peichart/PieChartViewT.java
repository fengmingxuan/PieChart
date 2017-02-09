package com.example.peichart;

import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class PieChartViewT extends View {
    //��ͼ��ɫ��������
    private Paint mOuterLinePaint;
    //��״ͼ����
    private Paint mPiePaint;
    //��Բ����
    private Paint mInnerPaint;
    //��״ͼ��Բ�뾶
    private float mRadius =  ScreenUtils.dpToPx(getContext(), 60) + OUTER_LINE_WIDTH;
    //���ɱ�״ͼ�����ݼ���
    private List<PieData> mPieDataList;
    //���ƻ��ε�sweep����
    private float[] mPieSweep;
    //��״ͼ����Ч��
    private PieChartAnimation mAnimation;
    //��ʼ�������ڵĽǶ�
    private static final int START_DEGREE = -90;

    private static final int PIE_ANIMATION_VALUE = 100;
    //��Բ�߿�Ŀ��
    private static int OUTER_LINE_WIDTH = 3;
    //����ʱ��
    private static final int ANIMATION_DURATION = 800;

    private RectF mRectF = new RectF();
    //Բ����
    private static final float PI = 3.1415f;

    private static final int PART_ONE = 1;

    private static final int PART_TWO = 2;

    private static final int PART_THREE = 3;

    private static final int PART_FOUR = 4;


    public void setOnSpecialTypeClickListener(OnSpecialTypeClickListener listener) {
        this.mListener = listener;
    }

    private OnSpecialTypeClickListener mListener;

    public PieChartViewT(Context context) {
        super(context);
        init();
    }

    public PieChartViewT(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PieChartViewT(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    //��ʼ�����ʺ�Ч������
    private void init() {
        mOuterLinePaint = new Paint();
        mOuterLinePaint.setAntiAlias(true);
        mOuterLinePaint.setStyle(Style.STROKE);
        mOuterLinePaint.setStrokeWidth(OUTER_LINE_WIDTH);
        mOuterLinePaint.setColor(Color.WHITE);

        mPiePaint = new Paint();
        mPiePaint.setAntiAlias(true);
        mPiePaint.setStyle(Style.FILL);
        //���ö���
        mAnimation = new PieChartAnimation();
        mAnimation.setDuration(ANIMATION_DURATION);

        mInnerPaint = new Paint();
        mInnerPaint.setColor(Color.WHITE);
        mInnerPaint.setStyle(Style.FILL);
        mInnerPaint.setAntiAlias(true);
        initRectF();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mPieDataList != null && !mPieDataList.isEmpty()) {
            //��ʼ�Ǵ�-90��λ�ÿ�ʼ��
            float pieStart = START_DEGREE;
            if (mPieSweep == null) {
                mPieSweep = new float[mPieDataList.size()];
            }
            for (int i = 0; i < mPieDataList.size(); i++) {
                //���û�����ɫ
                mPiePaint.setColor(getResources().getColor(mPieDataList.get(i).getColorId()));
                //���ƻ��������Թ��ɱ�״ͼ
                float pieSweep = mPieDataList.get(i).getValue() * 360;
                canvas.drawArc(mRectF, pieStart, mPieSweep[i], true, mPiePaint);
                canvas.drawArc(mRectF, pieStart, mPieSweep[i], true, mOuterLinePaint);
                //��ȡ��һ�����ε����
                pieStart += pieSweep;
            }
        } else {
            //������ʱ����ʾ��ɫԲ��
            mPiePaint.setColor(Color.parseColor("#dadada"));//��ɫ
            canvas.drawCircle(mRadius, mRadius, mRadius, mPiePaint);
        }
        drawInnerCircle(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int length = (int) (2 * mRadius);
        setMeasuredDimension(length, length);
    }

    /**
     * ������Ҫ���Ƶ����ݼ���
     */
    public void setPieDataList(List<PieData> pieDataList) {
        this.mPieDataList = pieDataList;
        if (mPieSweep == null) {
            mPieSweep = new float[mPieDataList.size()];
        }
        startAnimation(mAnimation);
    }

    /**
     * ������Բ�뾶
     *
     * @param radius ��Բ�뾶 dpΪ��λ
     **/
    public void setOuterRadius(float radius) {
        this.mRadius = ScreenUtils.dpToPx(getContext(), radius) + OUTER_LINE_WIDTH ;
        initRectF();
    }

    /**
     * ��ʼ�����ƻ������ھ��ε��ĵ�����
     **/
    private void initRectF() {
        mRectF.left = 0;
        mRectF.top = 0;
        mRectF.right = 2 * mRadius;
        mRectF.bottom = 2 * mRadius;
    }

    private class PieChartAnimation extends Animation {
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            mPieSweep = new float[mPieDataList.size()];
            if (interpolatedTime < 1.0f) {
                for (int i = 0; i < mPieDataList.size(); i++) {
                    mPieSweep[i] = (mPieDataList.get(i).getValue() * PIE_ANIMATION_VALUE) * interpolatedTime / PIE_ANIMATION_VALUE * 360;
                }
            } else {
                for (int i = 0; i < mPieDataList.size(); i++) {
                    mPieSweep[i] = mPieDataList.get(i).getValue() * 360;
                }
            }
            invalidate();
        }
    }

    protected void drawInnerCircle(Canvas canvas) {
        canvas.drawCircle(mRadius, mRadius, (float) (mRadius * 0.72), mInnerPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                doOnSpecialTypeClick(event);
                break;
        }
        return super.onTouchEvent(event);
    }


    private void doOnSpecialTypeClick(MotionEvent event) {
        if (mPieDataList == null || mPieDataList.isEmpty()) return;
        float eventX = event.getX();
        float eventY = event.getY();
        double alfa = 0;
        float startArc = 0;
        //�����λ�õ�Բ�ľ����ƽ��
        double distance = Math.pow(eventX - mRadius, 2) + Math.pow(eventY - mRadius, 2);
        //�жϵ���������Ƿ��ڻ���
        if (distance < Math.pow(mRadius, 2) && distance > Math.pow(0.72 * mRadius, 2)) {
            int which = touchOnWhichPart(event);
            switch (which) {
                case PART_ONE:
                    alfa = Math.atan2(eventX - mRadius, mRadius - eventY) * 180 / PI;
                    break;
                case PART_TWO:
                    alfa = Math.atan2(eventY - mRadius, eventX - mRadius) * 180 / PI + 90;
                    break;
                case PART_THREE:
                    alfa = Math.atan2(mRadius - eventX, eventY - mRadius) * 180 / PI + 180;
                    break;
                case PART_FOUR:
                    alfa = Math.atan2(mRadius - eventY, mRadius - eventX) * 180 / PI + 270;
                    break;
            }
            for (PieData data : mPieDataList) {
                startArc = startArc + data.getValue() * 360;
                if (alfa != 0 && alfa < startArc) {
                    if (mListener != null) mListener.onSpecialTypeClick(data.getType());
                    break;
                }
            }
        }
    }

    /**
     *    4 |  1
     * -----|-----
     *    3 |  2
     * Բ���ֳ��ĵȷݣ��жϵ����԰����һ����
     */
    private int touchOnWhichPart(MotionEvent event) {
        if (event.getX() > mRadius) {
            if (event.getY() > mRadius) return PART_TWO;
            else return PART_ONE;
        } else {
            if (event.getY() > mRadius) return PART_THREE;
            else return PART_FOUR;
        }
    }

    public interface OnSpecialTypeClickListener {
        void onSpecialTypeClick(String type);
    }

    public static class PieData {

        private String type;

        private float value;

        private int colorId;

        public PieData(String type, float value, int colorId) {
            this.type = type;
            this.value = value;
            this.colorId = colorId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public float getValue() {
            return value;
        }

        public void setValue(float value) {
            this.value = value;
        }

        public int getColorId() {
            return colorId;
        }

        public void setColorId(int colorId) {
            this.colorId = colorId;
        }
    }
}