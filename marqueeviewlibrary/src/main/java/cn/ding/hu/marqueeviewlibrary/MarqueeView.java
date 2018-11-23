package cn.ding.hu.marqueeviewlibrary;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by harry.ding on 2018/10/30.
 */

public class MarqueeView extends AppCompatTextView implements Runnable {
    private int currentScrollX = 0;
    private boolean isStop = false;
    private int textWidth;
    private boolean isMeasure = false;

    public MarqueeView(Context context) {
        super(context);
    }

    public MarqueeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MarqueeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isMeasure) {
            getTextWidth();
            isMeasure = true;
        }
    }

    private void getTextWidth() {
        Paint paint = this.getPaint();
        String str = this.getText().toString();
        textWidth = (int) paint.measureText(str);
        currentScrollX = -getWidth();
    }

    @Override
    public void run() {
        currentScrollX += 1;
        scrollTo(currentScrollX, 0);
        if (isStop) {
            return;
        }
//        if (getScrollX() <= -(this.getWidth())) {
//            scrollTo(textWidth, 0);
//            currentScrollX = textWidth;
////                    return;
//        }
        if (getScrollX() >= textWidth) {//this.getWidth()
            currentScrollX = -getWidth();
            scrollTo(currentScrollX, 0);
//                    return;
        }
        postDelayed(this, 10);
//        LogUtils.i("bit","currentScrollX:"+currentScrollX);
    }

    public void startScroll() {
        isStop = false;
        this.removeCallbacks(this);
        post(this);
    }

    public void stopScroll() {
        isStop = true;
    }


    @Override
    public void setText(CharSequence text, BufferType type) {
        // TODO Auto-generated method stub
        super.setText(text, type);
//        getTextWidth();
        startScroll();
        isMeasure = false;
    }
}

