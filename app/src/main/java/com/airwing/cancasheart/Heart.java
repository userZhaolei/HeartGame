package com.airwing.cancasheart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * create by zhaolei 2019/2/18
 */
public class Heart extends View {

    private int width;
    private int height;
    private RectF rectfTopLeft;
    private RectF rectfTopRight;
    private RectF rectfBottom;
    private RectF rectfLeft;
    private RectF rectfRight;
    private Paint whitePaint;
    private Paint paint;

    public Heart(Context context) {
        super(context);
        init();
    }

    public Heart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Heart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.TRANSPARENT);//设置背景透明
        canvas.rotate(45, getMeasuredWidth() / 2, getMeasuredHeight() / 2);//旋转45°，使心形变正

        rectfTopLeft.set(2, height / 3 + 2, 2 * width / 3 , height );//心形的左上部绘制位置
        rectfTopRight.set(width / 3 , 2, width, 2 * height / 3);//心形的右上部绘制位置
        rectfBottom.set(width / 3, height / 3, width , height );//心形的下部绘制位置
        rectfLeft.set(width / 3, height - 4, width, height);//心形左边描边位置
        rectfRight.set(width - 4, height / 3, width, height);//心形右边描边位置

        canvas.drawRect(rectfBottom, paint);//心下部
      /*  canvas.drawRect(rectfLeft, whitePaint);//心下部左边描边
        canvas.drawRect(rectfRight, whitePaint);//心下部右边描边
                canvas.drawArc(rectfTopLeft, 90, 185, false, whitePaint);//心左上角描边
        canvas.drawArc(rectfTopRight, 175, 186, false, whitePaint);//心右上角描边*/
        /**
         先画心的下部正方形，再画心的左上角、右上角，是为了覆盖正方形的左上角的点
         这里给心左上角、右上角描边，修改起始角度和经过角度，是为了将上面正方形的左上角红色覆盖
         */
        canvas.drawArc(rectfTopLeft, 90, 180, true, paint);//心左上角
        canvas.drawArc(rectfTopRight, 180, 180, true, paint);//心右上角

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //取宽的3/4来绘制，让旋转图形过后，心形能够完全展示
        width = 3 * getMeasuredWidth() / 4;
        //取高的3/4来绘制，让旋转图形过后，心形能够完全展示
        height = 3 * getMeasuredHeight() / 4;
    }


    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);

        whitePaint = new Paint();
        whitePaint.setAntiAlias(true);
        whitePaint.setColor(Color.WHITE);
        whitePaint.setStrokeWidth(4.0f);
        whitePaint.setStyle(Paint.Style.STROKE);

        rectfTopLeft = new RectF();
        rectfTopRight = new RectF();
        rectfBottom = new RectF();
        rectfLeft = new RectF();
        rectfRight = new RectF();
    }
}
