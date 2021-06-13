package com.example.newsapp.Utility;

import android.os.Build;
import android.view.View;
import android.widget.ProgressBar;

import com.facebook.shimmer.ShimmerFrameLayout;

public final class UtilityKs {

    private void UtilityKS() {
    }

    public static void startShimmer( ShimmerFrameLayout shimmerView) {
        /*if (Build.VERSION.SDK_INT >= 21 && Build.VERSION.SDK_INT < 25) {
            progressBar.setVisibility(View.VISIBLE);
        } else {*/

            shimmerView.startShimmer();
            shimmerView.setVisibility(View.VISIBLE);
        /*}*/
    }

    public static void stopShimmer( ShimmerFrameLayout shimmerView) {
       /* if (Build.VERSION.SDK_INT >= 21 && Build.VERSION.SDK_INT < 25) {
            progressBar.setVisibility(View.GONE);
        } else {*/
            //shimmerView.stopShimmerAnimation();
            shimmerView.stopShimmer();
            shimmerView.setVisibility(View.GONE);
        /*}*/
    }
}
