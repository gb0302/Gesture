package com.liaxi.dell.gesture;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;

public class MainActivity extends Activity implements GestureDetector.OnGestureListener {

    //定义手势检测器实例
    GestureDetector detector;
    ImageView imageView;
    //初始的图片资源
    Bitmap bitmap;
    //定义图片的宽、高
    int width,height;
    //记录当前的缩放比例
    float currentScale = 1;
    //控制图片缩放的Matrx 对象
    Matrix matrix;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //创建手势检测器
        detector = new GestureDetector(this,this);
        imageView  = (ImageView)findViewById(R.id.imager);
        matrix = new Matrix();
        //获取被缩放的原图片
        bitmap = BitmapFactory.decodeResource(
                this.getResources(),R.drawable.a
        );
        //获得位图宽
        width = bitmap.getWidth();
        //获得位图高
        height = bitmap.getHeight();
        //设置ImaeView初始化时显示的图片
        imageView.setImageBitmap(BitmapFactory.decodeResource(this.getResources(),R.drawable.a));
    }

    @Override
    public boolean onTouchEvent(MotionEvent me) {
        //将该Activity上的触碰事件交给GesturDetector处理
        return detector.onTouchEvent(me);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
        velocityX = velocityX > 4000 ? 4000 : velocityX;
        velocityX = velocityX < -4000 ? -4000 : velocityX;
        //根据手势的速度来计算缩放比，如果velocityX>0,放大图像，否则缩小图像
        currentScale += currentScale * velocityX / 4000.0f;
        //重置Matrix
        matrix.reset();
        //缩放Matrix
        matrix.setScale(currentScale,currentScale,160,200);
        BitmapDrawable tmp = (BitmapDrawable)imageView.getDrawable();
        //如狗图片还未收回，先强制收回该图片
        if(!tmp.getBitmap().isRecycled())
        {
            tmp.getBitmap().recycle();
        }
        //根据原始位图和Matrix创建新图片
        Bitmap bitmap2 = Bitmap.createBitmap(bitmap,0,0,width,height,matrix,true);
        //显示新的位图
        imageView.setImageBitmap(bitmap2);
        return true;
    }
}
