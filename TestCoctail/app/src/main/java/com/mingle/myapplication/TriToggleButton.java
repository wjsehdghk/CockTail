package com.mingle.myapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by multimedia on 2016-05-18.
 */
public class TriToggleButton extends Button {

    public int _state = 0;
    public TriToggleButton(Context context) {
        super(context);
        _state = 0;

    }

    public TriToggleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        _state = 0;

    }

    public TriToggleButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        _state = 0;

    }

    private static final int[] STATE_ONE_SET = { R.attr.state_one };
    private static final int[] STATE_TWO_SET = { R.attr.state_two };
    private static final int[] STATE_THREE_SET = { R.attr.state_three };

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 3);

        if (_state == 0) {
            mergeDrawableStates(drawableState, STATE_ONE_SET);
        } else if (_state == 1) {
            mergeDrawableStates(drawableState, STATE_TWO_SET);
        } else if (_state == 2) {
            mergeDrawableStates(drawableState, STATE_THREE_SET);
        }

        return drawableState;
    }

    @Override
    public boolean performClick() {
        nextState();
        return super.performClick();
    }

    private void nextState() {
        _state++;
        if (_state > 2) {
            _state = 0;
        }
        setButtonText();
    }

    private void setButtonText() {
        //TODO
        switch(_state)
        {
            case 0:  ResionExhibitionActivity.state = 0; this.setBackgroundResource(R.drawable.ic_volume_off_black_24dp);
                break;
            case 1:  ResionExhibitionActivity.state = 1; this.setBackgroundResource(R.drawable.ic_vibration_24dp);
                break;
            case 2:  ResionExhibitionActivity.state = 2; this.setBackgroundResource(R.drawable.ic_volume_up_black_24dp);
                break;
            default: this.setText("N/A"); // Should never happen, but just in case
                break;
        }
    }

    public int getState() {
        return _state;
    }
}
