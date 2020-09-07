package com.artatech.inkbook.recipes.core.ui.bluer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.view.View;

import androidx.renderscript.Allocation;
import androidx.renderscript.Element;
import androidx.renderscript.RenderScript;
import androidx.renderscript.ScriptIntrinsicBlur;

public class BlurBuilder {
    private static final float BITMAP_SCALE = 0.1f;
    private static final float BLUR_RADIUS = 7.5f;

    public static Bitmap blur(Context context, Bitmap image) {
        int width = Math.round(image.getWidth() * BITMAP_SCALE);
        int height = Math.round(image.getHeight() * BITMAP_SCALE);

        Bitmap inputBitmap = Bitmap.createScaledBitmap(image, width, height, false);
        Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);

        RenderScript rs = RenderScript.create(context);
        ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        Allocation tmpIn = Allocation.createFromBitmap(rs, inputBitmap);
        Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);
        theIntrinsic.setRadius(BLUR_RADIUS);
        theIntrinsic.setInput(tmpIn);
        theIntrinsic.forEach(tmpOut);
        tmpOut.copyTo(outputBitmap);

        return outputBitmap;
    }

    public static void blur(final Context context, final Bitmap bitmap, final View  view) {
        blur(context, bitmap, view, false, false, false, false);
    }

    @SuppressLint("NewApi")
    public static void blur(final Context context, final Bitmap bitmap, final View  view, boolean roundTL, boolean roundTR, boolean roundBL, boolean roundBR) {

        new AsyncTask<Void, Void, Bitmap>() {

            @Override
            protected Bitmap doInBackground(Void... params) {
                Paint paint = new Paint();
                paint.setFilterBitmap(true);
                Bitmap cropImage = Bitmap.createBitmap(bitmap, 0, bitmap.getHeight() - view.getHeight(), bitmap.getWidth(), view.getHeight());

                return BlurBuilder.blur(context, cropImage);
            }


            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                if (bitmap != null) {
                    int sdk = android.os.Build.VERSION.SDK_INT;



                    if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        view.setBackgroundDrawable(new BitmapDrawable(context.getResources(), getRoundedCornerBitmap(bitmap, 8, roundTL, roundTR, roundBL, roundBR)));
                    } else {
                        view.setBackground(new BitmapDrawable(context.getResources(), getRoundedCornerBitmap(bitmap, 8, roundTL, roundTR, roundBL, roundBR)));
                    }

                }

            }

            private Bitmap getRoundedCornerBitmap(Bitmap bitmap,int roundPixelSize, boolean roundTL, boolean roundTR, boolean roundBL, boolean roundBR) {
                Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
                Canvas canvas = new Canvas(output);

                final Paint paint = new Paint();
                final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
                final RectF rectF = new RectF(rect);

                paint.setAntiAlias(true);
                canvas.drawRoundRect(rectF, roundPixelSize, roundPixelSize, paint);

                if (!roundTL) {
                    Rect rectTL = new Rect(0, 0, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
                    canvas.drawRect(rectTL, paint);
                }
                if (!roundTR) {
                    Rect rectTR = new Rect(bitmap.getWidth() / 2, 0, bitmap.getWidth(), bitmap.getHeight() / 2);
                    canvas.drawRect(rectTR, paint);
                }
                if (!roundBR) {
                    Rect rectBR = new Rect(bitmap.getWidth() / 2, bitmap.getHeight() / 2, bitmap.getWidth(), bitmap.getHeight());
                    canvas.drawRect(rectBR, paint);
                }
                if (!roundBL) {
                    Rect rectBL = new Rect(0, bitmap.getHeight() / 2, bitmap.getWidth() / 2, bitmap.getHeight());
                    canvas.drawRect(rectBL, paint);
                }

                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                canvas.drawBitmap(bitmap, rect, rect, paint);
                return output;
            }
        }.execute();


    }

}
