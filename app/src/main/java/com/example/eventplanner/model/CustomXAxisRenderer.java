package com.example.eventplanner.model;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.renderer.XAxisRenderer;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class CustomXAxisRenderer extends XAxisRenderer {

    private int targetLabelIndex = -1; // Index of the label to color
    private int targetLabelColor = Color.RED; // Color for the target label

    public CustomXAxisRenderer(ViewPortHandler viewPortHandler, XAxis xAxis, Transformer trans) {
        super(viewPortHandler, xAxis, trans);
    }

    public void setTargetLabel(int index, int color) {
        this.targetLabelIndex = index;
        this.targetLabelColor = color;
    }

    @Override
    protected void drawLabel(Canvas c, String formattedLabel, float x, float y, MPPointF anchor, float angleDegrees) {
        // Get the current label's index (assuming your XAxis labels are indexed sequentially)
        int currentLabelIndex = (int) (x / mAxis.mAxisRange); // This is a simplified way to get index, might need adjustment based on your data setup.

        if (currentLabelIndex == targetLabelIndex) {
            mAxisLabelPaint.setColor(targetLabelColor);
        } else {
            mAxisLabelPaint.setColor(mAxis.getTextColor()); // Reset to default color
        }

        super.drawLabel(c, formattedLabel, x, y, anchor, angleDegrees);
    }
}