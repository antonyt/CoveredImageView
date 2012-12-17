
package com.antonyt.coveredimageview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Slowly reveals an image by changing the clip bounds. See {@link #reveal(int)}
 * .
 */
public class CoveredImageView extends ImageView {

    private static final int UNINITIALIZED_START_TIME = -1;

    private Rect mRect = new Rect();
    private boolean mIsAnimating = false;

    private long mStartTime;
    private int mDurationMs;
    private long endTime;

    public CoveredImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public CoveredImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CoveredImageView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mIsAnimating) {
            // Only start timing from first frame of animation
            if (mStartTime == UNINITIALIZED_START_TIME) {
                mStartTime = SystemClock.uptimeMillis();
                endTime = mStartTime + mDurationMs;
            }

            // Adjust clip bounds according to the time fraction
            canvas.getClipBounds(mRect);
            long currentTime = SystemClock.uptimeMillis();
            if (currentTime < endTime) {
                float timeFraction = (currentTime - mStartTime) / (mDurationMs * 1f);
                int alpha = Math.round(mRect.width() * timeFraction);
                mRect.right = mRect.left + alpha;
                canvas.clipRect(mRect);
            } else {
                mIsAnimating = false;
            }
        }

        // Draw current state
        super.onDraw(canvas);

        // Request another draw operation until time is up
        if (mIsAnimating) {
            invalidate();
        }
    }

    /**
     * Reveals the image content over the period specified in milliseconds.
     * 
     * @param durationMs
     */
    public void reveal(int durationMs) {
        mIsAnimating = true;
        mStartTime = UNINITIALIZED_START_TIME;
        mDurationMs = durationMs;
        invalidate();
    }
}
