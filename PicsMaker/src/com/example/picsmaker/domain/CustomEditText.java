package com.example.picsmaker.domain;
import java.io.Serializable;

import com.example.picsmaker.R;

//textview����ʾ����
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

@SuppressLint("ClickableViewAccessibility")
public class CustomEditText extends TextView{

    private Typeface font;
    public float bitmapX;  
    public float bitmapY;  
  //  private textClick mytextClick;
    private float startx;// down�¼�����ʱ����ָ�����view���Ͻ�x��ľ���
    private float starty;// down�¼�����ʱ����ָ�����view���Ͻ�y��ľ���
    private float endx; // move�¼�����ʱ����ָ�����view���Ͻ�x��ľ���
    private float endy; // move�¼�����ʱ����ָ�����view���Ͻ�y��ľ���
    private float left; // DragTV���Ե����ڸ��ؼ��ľ���
    private float top; // DragTV�ϱ�Ե����ڸ��ؼ��ľ���
    private int right; // DragTV�ұ�Ե����ڸ��ؼ��ľ���
    private int bottom; // DragTV�ױ�Ե����ڸ��ؼ��ľ���
    private float hor; // ��������£���ָ��x�᷽���ƶ��ľ���
    private float ver; // ��������£���ָ��y�᷽���ƶ��ľ���
    private float mfX, mfY, msX, msY;
    private boolean isMove = true;
    private float mAngle;
    private int ptrID1 = INVALID_POINTER_ID, ptrID2 = INVALID_POINTER_ID;
    private float oldDist = 0;
    private float textSize = 0;
    private float scale;
    private static final int INVALID_POINTER_ID = -1;
    private MotionEvent mEvent;
    private boolean onefinger;
    
	public CustomEditText(Context context,Typeface _font) {
		super(context);
		// TODO Auto-generated constructor stub
		 //���ÿ̶�����
		this.setTypeface(_font);
		this.font = _font;
		this.bitmapX = 0;
		this.bitmapY = 200;
		this.setHint("Picture Maker");
		this.setClickable(true);
		this.setBackgroundColor(R.drawable.bg_border_stroke);
	}
	
	public Typeface getFontFamily() {
		return this.font;
	}
	
	@Override
	public boolean performClick() {
	  return super.performClick();
	}

	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
	    // �� ע������ʹ�õ��� getAction()������һ��Сβ�͡�
	    switch (event.getAction()){
	        case MotionEvent.ACTION_DOWN:
	        	// ��ָ����
	        	onefinger = true;
                //��ָ��������TextView������
                startx = event.getX();
                starty = event.getY();
                Log.d("HHHH", "onTouch: ACTION_DOWM " + startx);
                ptrID1 = event.getPointerId(event.getActionIndex());
	            break;
	            
	        case MotionEvent.ACTION_POINTER_DOWN:
                isMove = false;
                onefinger = false;

                ptrID2 = event.getPointerId(event.getActionIndex());
                //��ָ��������RelativeLayout������
                msX = mEvent.getX(event.findPointerIndex(ptrID2));
                msY = mEvent.getY(event.findPointerIndex(ptrID2));
                mfX = mEvent.getX(event.findPointerIndex(ptrID1));
                mfY = mEvent.getY(event.findPointerIndex(ptrID1));
                oldDist = spacing(event);
                break;

	        case MotionEvent.ACTION_MOVE:
	            // ��ָ�ƶ�
	        	Log.d("HHHH", "onTouch: ACTION_MOVE ");
                left = this.getX();
                top = this.getY();
                if (isMove) {
                    endx = event.getX();
                    endy = event.getY();
                    hor = (endx - startx);
                    ver = (endy - starty);
                    this.setX(left + hor);
                    this.setY(top + ver);

                }
                else  {
                    //������תģ��
                    float nfX, nfY, nsX, nsY, test;
                    test = event.getX(event.findPointerIndex(ptrID1));

                    nsX = mEvent.getX(event.findPointerIndex(ptrID2));
                    nsY = mEvent.getY(event.findPointerIndex(ptrID2));
                    nfX = mEvent.getX(event.findPointerIndex(ptrID1));
                    nfY = mEvent.getY(event.findPointerIndex(ptrID1));
                    mAngle = angleBetweenLines(mfX, mfY, msX, msY, nfX, nfY, nsX, nsY);
                    this.setRotation(mAngle);
                    //����
                    float newDist = spacing(event);
                    if (newDist > oldDist + 1) {
                        zoom(newDist / oldDist);
                        oldDist = newDist;
                    }
                    if (newDist < oldDist - 1) {
                        zoom(newDist / oldDist);
                        oldDist = newDist;
                    }

                }

	            break;
	        case MotionEvent.ACTION_UP:
	            // ��ָ̧��
	        	performClick();
	            break;
	        case MotionEvent.ACTION_CANCEL:
	            // �¼������� 
	            break;
	        case MotionEvent.ACTION_OUTSIDE:
	            // �������� 
	            break;
	        case MotionEvent.ACTION_POINTER_UP:
                Log.d("HHHH", "onTouch: ACTION_POINTER_UP");
                isMove = true;
                break;
	    }
	    return super.onTouchEvent(event);
	}
	
	
	
	private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }
    private void zoom(float f) {
        this.setTextSize(textSize *= f);
    }

    /**
     * ����տ�ʼ�����������㹹�ɵ�ֱ�ߺͻ��������������㹹��ֱ�ߵĽǶ�
     *
     * @param fX  ��ʼ��һ��x����
     * @param fY  ��ʼ��һ��y����
     * @param sX  ��ʼ�����x����
     * @param sY  ��ʼ�����y����
     * @param nfX �յ�һ��x����
     * @param nfY �յ�һ��y����
     * @param nsX �յ����x����
     * @param nsY �յ����y����
     * @return ���ɵĽǶ�ֵ
     */
    private float angleBetweenLines(float fX, float fY, float sX, float sY, float nfX, float nfY, float nsX, float nsY) {
        float angle1 = (float) Math.atan2((fY - sY), (fX - sX));
        float angle2 = (float) Math.atan2((nfY - nsY), (nfX - nsX));

        float angle = ((float) Math.toDegrees(angle1 - angle2)) % 360;
        if (angle < -180.f) angle += 360.0f;
        if (angle > 180.f) angle -= 360.0f;
        return -angle;
    }
    
    
    public void onClick() {
    	Log.i("HHH", "click!");
    }
	

}
