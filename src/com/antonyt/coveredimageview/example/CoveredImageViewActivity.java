
package com.antonyt.coveredimageview.example;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView.ScaleType;

import com.antonyt.coveredimageview.CoveredImageView;
import com.antonyt.coveredimageview.R;

/**
 * Example activity to demonstrate the {@link CoveredImageView}. Click on the
 * image to repeat the reveal animation.
 */
public class CoveredImageViewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final CoveredImageView image = (CoveredImageView) findViewById(R.id.image);
        image.setScaleType(ScaleType.FIT_START);
        image.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                image.reveal(2000);
            }
        });
    }
}
