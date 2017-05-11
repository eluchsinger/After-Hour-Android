package ch.hsr.afterhour.gui.widgets;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.AppCompatImageButton;
import android.util.AttributeSet;
import android.view.MotionEvent;

import ch.hsr.afterhour.R;

/**
 * Created by eluch on 09.05.2017.
 */

public class SmartImageButton extends AppCompatImageButton {
    //region Constructors
    public SmartImageButton(Context context) {
        super(context);
    }

    public SmartImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SmartImageButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    //endregion Constructors

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Drawable drawable = getDrawable();
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                drawable = DrawableCompat.wrap(drawable);
                DrawableCompat.setTint(drawable.mutate(), ContextCompat.getColor(getContext(), R.color.colorAccent));
                setImageDrawable(drawable);
                break;
            case MotionEvent.ACTION_CANCEL:
                drawable = DrawableCompat.wrap(drawable);
                DrawableCompat.setTint(drawable.mutate(),
                        ContextCompat.getColor(getContext(),
                                R.color.fontColor));
                setImageDrawable(drawable);
                break;
            case MotionEvent.ACTION_UP:
                drawable = DrawableCompat.wrap(drawable);
                DrawableCompat.setTint(drawable.mutate(),
                        ContextCompat.getColor(getContext(),
                                R.color.fontColor));
                setImageDrawable(drawable);
                break;
        }

        return super.onTouchEvent(event);
    }
}
