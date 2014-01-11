/*
 *     Created by Daniel Nadeau
 *     daniel.nadeau01@gmail.com
 *     danielnadeau.blogspot.com
 * 
 *     Licensed to the Apache Software Foundation (ASF) under one
       or more contributor license agreements.  See the NOTICE file
       distributed with this work for additional information
       regarding copyright ownership.  The ASF licenses this file
       to you under the Apache License, Version 2.0 (the
       "License"); you may not use this file except in compliance
       with the License.  You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing,
       software distributed under the License is distributed on an
       "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
       KIND, either express or implied.  See the License for the
       specific language governing permissions and limitations
       under the License.
       
       Modified by Auman apps
       auman.apps@gmail.com
       
       Added feature to specify negative bar values
       Added feature to update chart dynamically
 */

package com.echo.holographlibrary;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.drawable.NinePatchDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class BarGraph extends View {

	private final static int VALUE_FONT_SIZE = 13, AXIS_LABEL_FONT_SIZE = 13;
	
    private ArrayList<Bar> mBars = new ArrayList<Bar>();
    private Paint mPaint = new Paint();
    private Rect mRectangle = null;
    private boolean mShowBarText = true;
    private boolean mShowAxis = true;
    private int mIndexSelected = -1;
    private OnBarClickedListener mListener;
    private Bitmap mFullImage;
    private boolean mShouldUpdate = false;
    
    private double mdMaxVal = 0;
    
    private Context mContext = null;
    
    private float getXHeight() {
    	return (float) (getHeight()/2.0);
    }    
    
    public BarGraph(Context context) {
        super(context);
        mContext = context;
    }
    
    public BarGraph(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }
    
    public void setShowBarText(boolean show){
        mShowBarText = show;
    }
    
    public void setShowAxis(boolean show){
        mShowAxis = show;
    }
    
    public void setBars(ArrayList<Bar> points){
        this.mBars = points;
        mShouldUpdate = true;
        postInvalidate();
    }
    
    public ArrayList<Bar> getBars(){
        return this.mBars;
    }
    
    public void resetMaxVal() {
    	mdMaxVal = 0;
    }

    public void onDraw(Canvas ca) {
    	
        if (mFullImage == null || mShouldUpdate) {
            mFullImage = Bitmap.createBitmap(getWidth(), getHeight(), Config.ARGB_8888);
            Canvas canvas = new Canvas(mFullImage);
            canvas.drawColor(Color.TRANSPARENT);
            NinePatchDrawable popup = (NinePatchDrawable)this.getResources().getDrawable(R.drawable.popup_black);
            
            float padding = 30 * mContext.getResources().getDisplayMetrics().density;
            int selectPadding = (int) (4 * mContext.getResources().getDisplayMetrics().density);
                        
            float usableHeight;
            if (mShowBarText) {
                this.mPaint.setTextSize(VALUE_FONT_SIZE * mContext.getResources().getDisplayMetrics().scaledDensity);
                Rect r3 = new Rect();
                this.mPaint.getTextBounds("$", 0, 1, r3);
                usableHeight = getXHeight()-Math.abs(r3.top-r3.bottom)/*-24 * mContext.getResources().getDisplayMetrics().density*/;
            } else {
            	usableHeight = getXHeight();
            }
            
            // Draw x-axis line
            if (mShowAxis){
                mPaint.setColor(Color.BLACK);
                mPaint.setStrokeWidth(2 * mContext.getResources().getDisplayMetrics().density);
                mPaint.setAlpha(50);
                mPaint.setAntiAlias(true);
                float linePadding = 4 * mContext.getResources().getDisplayMetrics().density;
                canvas.drawLine(linePadding, 
                		        getXHeight(), 
                		        getWidth() - linePadding,
                		        getXHeight(), 
                		        mPaint);
            }
            float barWidth = (getWidth() - (padding*2)*mBars.size())/mBars.size();
            
            // Maximum y value = sum of all values.
            float dMaxValTemp = 0;
            for (final Bar bar : mBars) {
            	double dTemp = Math.abs(bar.getValue());
                if (dTemp > dMaxValTemp) {
                	dMaxValTemp = (float) dTemp;
                }
            }
            
            if(dMaxValTemp > mdMaxVal)
	            mdMaxVal = 2 * dMaxValTemp;
            else if(4 * dMaxValTemp < mdMaxVal) 
            	mdMaxVal = 2 * dMaxValTemp;

            mRectangle = new Rect();
            
            int count = 0;
            for (final Bar bar : mBars) {
                // Set bar bounds
                int left  = (int)((padding*2)*count + padding + barWidth*count);
                int right = (int)((padding*2)*count + padding + barWidth*(count+1));
                
                int top   = 0; 
                int bottom = 0;
                
                if(bar.getValue() > 0)
                {
                	top = (int)(getXHeight()-(usableHeight*(bar.getValue()/mdMaxVal)));
                	bottom = (int)(getXHeight());
                }
                else
                {
                	top = (int)(getXHeight());
                	bottom = (int)(getXHeight()+(usableHeight*((-1*bar.getValue())/mdMaxVal)));
                }                
                
                mRectangle.set(left, top, right, bottom);

                // Draw bar
                this.mPaint.setColor(bar.getColor());
                this.mPaint.setAlpha(255);
                canvas.drawRect(mRectangle, this.mPaint);

                // Create selection region
                Path path = new Path();
                path.addRect(new RectF(mRectangle.left-selectPadding, mRectangle.top-selectPadding, mRectangle.right+selectPadding, mRectangle.bottom+selectPadding), Path.Direction.CW);
                bar.setPath(path);
                bar.setRegion(new Region(mRectangle.left-selectPadding, mRectangle.top-selectPadding, mRectangle.right+selectPadding, mRectangle.bottom+selectPadding));
                
                this.mPaint.setColor(Color.BLACK);
                
                // Draw x-axis label text
                if (mShowAxis){
                	this.mPaint.setTextSize(AXIS_LABEL_FONT_SIZE * mContext.getResources().getDisplayMetrics().scaledDensity);
                    int x = (int)(((mRectangle.left+mRectangle.right)/2)-(this.mPaint.measureText(bar.getName())/2));
                	int y = 0;
                	if(bar.getValue() > 0)
	                    y = (int) (getXHeight() + 15 * mContext.getResources().getDisplayMetrics().scaledDensity);
                	else
                		y = (int) (getXHeight() - 4 * mContext.getResources().getDisplayMetrics().scaledDensity);
                	
                    canvas.drawText(bar.getName(), x, y, this.mPaint);
                }

                // Draw value text
                if (mShowBarText){
                    this.mPaint.setTextSize(VALUE_FONT_SIZE * mContext.getResources().getDisplayMetrics().scaledDensity);
                    Rect r2 = new Rect();
                    this.mPaint.getTextBounds(bar.getValueString(), 0, 1, r2);
                    
                    //int boundLeft = (int) (((mRectangle.left+mRectangle.right)/2)-(this.mPaint.measureText(bar.getValueString())/2)-10 * mContext.getResources().getDisplayMetrics().density);
                    //int boundRight = (int)(((mRectangle.left+mRectangle.right)/2)+(this.mPaint.measureText(bar.getValueString())/2)+10 * mContext.getResources().getDisplayMetrics().density);
                  	int boundTop = (int) (mRectangle.top+(r2.top-r2.bottom)-18 * mContext.getResources().getDisplayMetrics().density);
                  	//popup.setBounds(boundLeft, boundTop, boundRight, mRectangle.top);
                    //popup.draw(canvas);
                    
                  	if(bar.getValue() > 0)
                  	{
	                    canvas.drawText(bar.getValueString(), 
	                    		        (int)(((mRectangle.left+mRectangle.right)/2)-(this.mPaint.measureText(bar.getValueString()))/2), 
	                    		        mRectangle.top - 5 * mContext.getResources().getDisplayMetrics().density/*-(mRectangle.top - boundTop)/2f+(float)Math.abs(r2.top-r2.bottom)/2f*0.7f*/, 
	                    		        this.mPaint);
                  	}
                  	else
                  	{
                  		canvas.drawText(bar.getValueString(), 
                		        (int)(((mRectangle.left+mRectangle.right)/2)-(this.mPaint.measureText(bar.getValueString()))/2), 
                		        mRectangle.bottom - (r2.top-r2.bottom)/*+(mRectangle.bottom - boundTop)/2f-(float)Math.abs(r2.top-r2.bottom)/2f*0.7f*/, 
                		        this.mPaint);
                  	}
                }
                if (mIndexSelected == count && mListener != null) {
                    this.mPaint.setColor(Color.parseColor("#33B5E5"));
                    this.mPaint.setAlpha(100);
                    canvas.drawPath(bar.getPath(), this.mPaint);
                    this.mPaint.setAlpha(255);
                }
                count++;
            }
            mShouldUpdate = false;
        }
        
        ca.drawBitmap(mFullImage, 0, 0, null);
        
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        Point point = new Point();
        point.x = (int) event.getX();
        point.y = (int) event.getY();
        
        int count = 0;
        for (Bar bar : mBars){
            Region r = new Region();
            r.setPath(bar.getPath(), bar.getRegion());
            if (r.contains((int)point.x,(int) point.y) && event.getAction() == MotionEvent.ACTION_DOWN){
                mIndexSelected = count;
            } else if (event.getAction() == MotionEvent.ACTION_UP){
                if (r.contains((int)point.x,(int) point.y) && mListener != null){
                    if (mIndexSelected > -1) mListener.onClick(mIndexSelected);
                    mIndexSelected = -1;
                }
            }
            else if(event.getAction() == MotionEvent.ACTION_CANCEL)
            	mIndexSelected = -1;
            
            count++;
        }
        
        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL){
            mShouldUpdate = true;
            postInvalidate();
        }
        
        

        return true;
    }
    
    @Override
    protected void onDetachedFromWindow()
    {
    	if(mFullImage != null)
    		mFullImage.recycle();
    	
    	super.onDetachedFromWindow();
    }
    
    public void setOnBarClickedListener(OnBarClickedListener listener) {
        this.mListener = listener;
    }
    
    public interface OnBarClickedListener {
        abstract void onClick(int index);
    }
}
