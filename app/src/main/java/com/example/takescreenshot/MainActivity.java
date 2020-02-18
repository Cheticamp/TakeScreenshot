package com.example.takescreenshot;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

public class MainActivity extends AppCompatActivity {

    private CardView cardView;
    private Button button;
    private ConstraintLayout parentLayoutA;
    private ConstraintLayout layoutB;
    private ConstraintLayout childLayout;
    private ConstraintSet innerSet;
    private ConstraintSet outerSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CreateCardViewProgrammatically();
    }

    public void CreateCardViewProgrammatically() {

        cardView = new CardView(this);
        childLayout = new ConstraintLayout(this);
        parentLayoutA = new ConstraintLayout(this);
        layoutB = new ConstraintLayout(this);
        button = new Button(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            layoutB.setClipToOutline(true);
        }

        cardView.setRadius(150);

        button.setText("Click!!");
        button.setTextColor(Color.BLACK);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeScreenShot();
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            parentLayoutA.setId(View.generateViewId());
            layoutB.setId(View.generateViewId());
            childLayout.setId(View.generateViewId());
            cardView.setId(View.generateViewId());
            button.setId(View.generateViewId());
        }

        innerSet = new ConstraintSet();
        outerSet = new ConstraintSet();

        final ConstraintLayout.LayoutParams parentParams = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT
        );

        layoutB.setLayoutParams(parentParams);
        childLayout.setLayoutParams(parentParams);
        cardView.setCardElevation(0);
        cardView.setPadding(50, 50, 50, 50);

        parentLayoutA.addView(layoutB);
        layoutB.addView(cardView);
        cardView.addView(childLayout);
        childLayout.addView(button);

        innerSet.centerHorizontally(button.getId(), ConstraintSet.PARENT_ID);
        innerSet.centerVertically(button.getId(), ConstraintSet.PARENT_ID);
        innerSet.constrainHeight(button.getId(), 200);

        outerSet.connect(cardView.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 200);
        outerSet.connect(cardView.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 200);
        outerSet.centerHorizontally(cardView.getId(), ConstraintSet.PARENT_ID);
        outerSet.centerVertically(cardView.getId(), ConstraintSet.PARENT_ID);
        outerSet.constrainHeight(cardView.getId(), 1500);

        innerSet.applyTo(childLayout);
        outerSet.applyTo(layoutB);

        setContentView(parentLayoutA);

        parentLayoutA.setBackgroundColor(Color.YELLOW);
        layoutB.setBackgroundColor(Color.MAGENTA);
        cardView.setCardBackgroundColor(Color.GREEN);
        childLayout.setBackgroundColor(Color.BLUE);
    }

    public void takeScreenShot() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ViewImage.Companion.getScreenShotFromView(parentLayoutA, this, (bm) -> {
                setContentView(R.layout.activity_main);
                ((ImageView) findViewById(R.id.imageView)).setImageBitmap(bm);
                return null;
            });
        } else {
            Bitmap bm = ViewImage.Companion.getScreenShot(parentLayoutA);
            setContentView(R.layout.activity_main);
            ((ImageView) findViewById(R.id.imageView)).setImageBitmap(bm);
        }
    }
}
