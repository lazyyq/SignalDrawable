package kyklab.signaldrawable;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SignalStrength;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SignalDrawable drawable = new SignalDrawable(getApplicationContext());
        //drawable.setLevel(1);

        final ImageView imageView = findViewById(R.id.imageView);
        imageView.setImageDrawable(drawable);
        drawable.setLevel(SignalDrawable.getState(3, 5, false));
        drawable.setSignalState(SignalDrawable.STATE_CARRIER_CHANGE);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*for (int j = 0; j <= 4; ++j) {
                    drawable.setSignalState(j);
                    for (int i = 0; i <= 4; ++i) {
                        drawable.setLevel(SignalDrawable.getState(i, 4 + 1, false));
                        saveDrawable(drawableToBitmap(drawable), "/sdcard/18dip_new/state_" + j + "_signal_" + i + ".png");
                    }
                    for (int i = 0; i <= 4; ++i) {
                        drawable.setLevel(SignalDrawable.getState(i, 4 + 1, true));
                        saveDrawable(drawableToBitmap(drawable), "/sdcard/18dip_new/state_" + j + "_signal_cutout_" + i + ".png");
                    }
                }*//*
                for (int i = 0; i <= 4; ++i) {
                    drawable.setLevel(SignalDrawable.getState(i, 4 + 1, false));
                    saveDrawable(drawableToBitmap(drawable), "/sdcard/18dip_new/signal_" + i + ".png");
                }
                for (int i = 0; i <= 4; ++i) {
                    drawable.setLevel(SignalDrawable.getState(i, 4 + 1, true));
                    saveDrawable(drawableToBitmap(drawable), "/sdcard/18dip_new/signal_cutout_" + i + ".png");
                }
                for (int i = 0; i <= 4; ++i) {
                    drawable.setLevel(SignalDrawable.getState(0, 4 + 1, false));
                    drawable.setSignalState(i);
                    saveDrawable(drawableToBitmap(drawable), "/sdcard/18dip_new/signal_state_" + i + ".png");
                }*/


                //Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.stat_sys_data_connected_lte);
                Bitmap bitmap = getBitmapFromVectorDrawable(getApplicationContext(), R.drawable.stat_sys_data_connected_lte);
                imageView.setImageBitmap(bitmap);
                saveDrawable(bitmap, "/sdcard/lte.png");
                bitmap = getBitmapFromVectorDrawable(getApplicationContext(), R.drawable.stat_sys_data_connected_lte_plus);
                imageView.setImageBitmap(bitmap);
                saveDrawable(bitmap, "/sdcard/lte_plus.png");
            }
        });
    }

    private Bitmap getBitmapFromVectorDrawable(Context context, int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (DrawableCompat.wrap(drawable)).mutate();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    private void saveDrawable(Bitmap bm, String fileName) {
        File file = new File(fileName);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.PNG, 0, fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }
}
