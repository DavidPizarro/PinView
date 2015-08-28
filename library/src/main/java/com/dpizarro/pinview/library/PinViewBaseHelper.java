package com.dpizarro.pinview.library;

import android.app.Service;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/*
 * Copyright (C) 2015 David Pizarro
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
abstract class PinViewBaseHelper extends LinearLayout implements TextWatcher, View.OnFocusChangeListener {

    private static final String LOG_TAG = PinViewBaseHelper.class.getSimpleName();

    /**
     * Attributes
     */
    int mNumberPinBoxes         = PinViewSettings.DEFAULT_NUMBER_PIN_BOXES;
    int mNumberCharacters       = PinViewSettings.DEFAULT_NUMBER_CHARACTERS;
    String mSplit               = PinViewSettings.DEFAULT_SPLIT;
    boolean mKeyboardMandatory  = PinViewSettings.DEFAULT_KEYBOARD_MANDATORY;
    boolean mDeleteOnClick      = PinViewSettings.DEFAULT_DELETE_ON_CLICK;
    boolean mMaskPassword       = PinViewSettings.DEFAULT_MASK_PASSWORD;
    boolean mNativePinBox       = PinViewSettings.DEFAULT_NATIVE_PIN_BOX;
    int mCustomDrawablePinBox   = PinViewSettings.DEFAULT_CUSTOM_PIN_BOX;
    int mColorTextPinBoxes      = PinViewSettings.DEFAULT_TEXT_COLOR_PIN_BOX;
    int mColorTextTitles        = PinViewSettings.DEFAULT_TEXT_COLOR_TITLES;
    int mColorSplit             = PinViewSettings.DEFAULT_COLOR_SPLIT;
    float mTextSizePinBoxes;
    float mTextSizeTitles;
    float mSizeSplit;
    String[] mPinTitles;

    private int currentFocus;
    boolean lastCompleted = false;
    private InputMethodManager inputMethodManager;
    LinearLayout mLinearLayoutPinTexts;
    LinearLayout mLinearLayoutPinBoxes;
    int[] pinBoxesIds;
    int[] pinTitlesIds;
    int[] pinSplitsIds;

    /**
     * Default constructor
     *
     * @param context Context of constructor
     * @param attrs {@link PinView}
     */
    public PinViewBaseHelper(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (isInEditMode()) {
            createEditModeView(context);
        } else {
            createView(context);
            getAttributes(context, attrs);
        }
    }

    /**
     * This method inflates the PinView to be visible from Preview Layout
     *
     * @param context {@link PinViewBaseHelper} needs a context to inflate Edit layout
     */
    private void createEditModeView(Context context) {
        ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.pin_view_edit_mode, this,
                        true);
    }

    /**
     * This method inflates the PinView
     *
     * @param context {@link PinViewBaseHelper} needs a context to inflate the layout
     */
    private void createView(Context context) {
        ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.pin_view, this,
                true);
        inputMethodManager = (InputMethodManager) getContext().getSystemService(Service.INPUT_METHOD_SERVICE);
        mLinearLayoutPinTexts = (LinearLayout) findViewById(R.id.ll_pin_texts);
        mLinearLayoutPinBoxes = (LinearLayout) findViewById(R.id.ll_pin_edit_texts);
    }

    /**
     * Retrieve styles attributes
     */
    private void getAttributes(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PinView);

        if (typedArray != null) {
            try {
                mNumberPinBoxes = typedArray
                        .getInteger(R.styleable.PinView_numberPinBoxes, PinViewSettings.DEFAULT_NUMBER_PIN_BOXES);
                mMaskPassword = typedArray
                        .getBoolean(R.styleable.PinView_password, PinViewSettings.DEFAULT_MASK_PASSWORD);
                mNumberCharacters = typedArray
                        .getInteger(R.styleable.PinView_numberCharacters, PinViewSettings.DEFAULT_NUMBER_CHARACTERS);
                mSplit = typedArray.getString(R.styleable.PinView_split);
                mKeyboardMandatory = typedArray
                        .getBoolean(R.styleable.PinView_keyboardMandatory, PinViewSettings.DEFAULT_KEYBOARD_MANDATORY);
                mDeleteOnClick = typedArray
                        .getBoolean(R.styleable.PinView_deleteOnClick, PinViewSettings.DEFAULT_DELETE_ON_CLICK);
                mNativePinBox = typedArray
                        .getBoolean(R.styleable.PinView_nativePinBox, PinViewSettings.DEFAULT_NATIVE_PIN_BOX);
                mCustomDrawablePinBox = typedArray
                        .getResourceId(R.styleable.PinView_drawablePinBox, PinViewSettings.DEFAULT_CUSTOM_PIN_BOX);
                mColorTextPinBoxes = typedArray
                        .getColor(R.styleable.PinView_colorTextPinBox,
                                getResources().getColor(PinViewSettings.DEFAULT_TEXT_COLOR_PIN_BOX));
                mColorTextTitles = typedArray
                        .getColor(R.styleable.PinView_colorTextTitles,
                                getResources().getColor(PinViewSettings.DEFAULT_TEXT_COLOR_TITLES));
                mColorSplit = typedArray
                        .getColor(R.styleable.PinView_colorSplit,
                                getResources().getColor(PinViewSettings.DEFAULT_COLOR_SPLIT));
                mTextSizePinBoxes = typedArray
                        .getDimension(R.styleable.PinView_textSizePinBox,
                                getResources().getDimension(PinViewSettings.DEFAULT_TEXT_SIZE_PIN_BOX));
                mTextSizeTitles = typedArray
                        .getDimension(R.styleable.PinView_textSizeTitles,
                                getResources().getDimension(PinViewSettings.DEFAULT_TEXT_SIZE_TITLES));
                mSizeSplit = typedArray
                        .getDimension(R.styleable.PinView_sizeSplit,
                                getResources().getDimension(PinViewSettings.DEFAULT_SIZE_SPLIT));

                int titles;
                titles = typedArray.getResourceId(R.styleable.PinView_titles, -1);
                if (titles != -1) {
                    setTitles(getResources().getStringArray(titles));
                }

                if (this.mNumberPinBoxes != 0) {
                    setPin(this.mNumberPinBoxes);
                }

            } catch (Exception e) {
                Log.e(LOG_TAG, "Error while creating the view PinView: ", e);
            } finally {
                typedArray.recycle();
            }
        }
    }


    /**
     * Generate a PinBox {@link EditText} with all attributes to add to {@link PinView}
     *
     * @param i index of new PinBox
     * @param inputType inputType to new PinBox
     * @return new PinBox
     */
    EditText generatePinBox(int i, int inputType) {
        EditText editText = (EditText) LayoutInflater.from(getContext()).inflate(R.layout.partial_pin_box, this, false);
        int generateViewId = PinViewUtils.generateViewId();
        editText.setId(generateViewId);
        editText.setTag(i);
        if (inputType != -1) {
            editText.setInputType(inputType);
        }
        setStylePinBox(editText);

        editText.addTextChangedListener(this);
        editText.setOnFocusChangeListener(this);
        pinBoxesIds[i] = generateViewId;

        return editText;
    }

    /**
     * Set a PinBox with all attributes
     *
     * @param editText to set attributes
     */
    private void setStylePinBox(EditText editText) {
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(mNumberCharacters)});

        if (mMaskPassword) {
            editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        else{
            editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }

        if (mNativePinBox) {
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                //noinspection deprecation
                editText.setBackgroundDrawable(new EditText(getContext()).getBackground());
            } else {
                editText.setBackground(new EditText(getContext()).getBackground());
            }
        } else {
            editText.setBackgroundResource(mCustomDrawablePinBox);
        }

        if (mColorTextPinBoxes != PinViewSettings.DEFAULT_TEXT_COLOR_PIN_BOX) {
            editText.setTextColor(mColorTextPinBoxes);
        }
        editText.setTextSize(PinViewUtils.convertPixelToDp(getContext(), mTextSizePinBoxes));
    }

    /**
     * Generate a Split {@link TextView} with all attributes to add to {@link PinView}
     *
     * @param i index of new split
     * @return new split
     */
    TextView generateSplit(int i) {
        TextView split = new TextView(getContext());
        int generateViewId = PinViewUtils.generateViewId();
        split.setId(generateViewId);
        setStylesSplit(split);
        pinSplitsIds[i] = generateViewId;
        return split;
    }

    /**
     * Generate a Title {@link TextView} with all attributes to add to {@link PinView}
     *
     * @param i index of new Title
     * @return new title
     */
    TextView generatePinText(int i, String[] titles) {
        TextView pinTitle = (TextView) LayoutInflater.from(getContext())
                .inflate(R.layout.partial_pin_text, this, false);
        int generateViewId = PinViewUtils.generateViewId();
        pinTitle.setId(generateViewId);
        pinTitle.setText(titles[i]);
        setStylesPinTitle(pinTitle);
        pinTitlesIds[i] = generateViewId;
        return pinTitle;
    }

    /**
     * Set a Title with all attributes
     *
     * @param pinTitle to set attributes
     */
    private void setStylesPinTitle(TextView pinTitle) {
        if (mColorTextTitles != PinViewSettings.DEFAULT_TEXT_COLOR_TITLES) {
            pinTitle.setTextColor(mColorTextTitles);
        }
        pinTitle.setTextSize(PinViewUtils.convertPixelToDp(getContext(), mTextSizeTitles));
    }

    /**
     * Set style to all PinBoxes
     */
    void setStylesPinBoxes() {
        for (int i = 0; i < mNumberPinBoxes; i++) {
            setStylePinBox(getPinBox(i));
        }
    }

    /**
     * Set style to all Titles
     */
    void setStylePinTitles() {
        for (int i = 0; i < mPinTitles.length; i++) {
            setStylesPinTitle(getPinTitle(i));
        }
    }

    /**
     * Set style to all Splits
     */
    void setStylesSplits() {
        for (int i = 0; i < pinSplitsIds.length; i++) {
            setStylesSplit(getSplit(i));
        }
    }

    /**
     * Set a Split with all attributes
     *
     * @param split to set attributes
     */
    private void setStylesSplit(TextView split) {
        if(split!=null){
            split.setText(mSplit);
            split.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            split.setGravity(Gravity.CENTER_VERTICAL);
            if (mColorSplit != PinViewSettings.DEFAULT_COLOR_SPLIT) {
                split.setTextColor(mColorSplit);
            }
            split.setTextSize(PinViewUtils.convertPixelToDp(getContext(), mSizeSplit));
        }
    }

    /**
     * Set focus on an empty PinBox or notify that {@link PinView} is completed.
     *
     * @param index PinBox position
     */
    private void chooseNextAction(int index) {
        if (index == -1) {
            notifyPinViewCompleted();
        } else {
            moveToPinBox(index);
        }
    }

    /**
     * Set the focus on an empty PinBox {@link EditText}
     *
     * @param index PinBox Position
     */
    private void moveToPinBox(int index) {
        findViewById(pinBoxesIds[index]).requestFocus();
    }

    EditText getPinBox(int i) {
        return (EditText) findViewById(pinBoxesIds[i]);
    }

    private TextView getPinTitle(int i) {
        return (TextView) findViewById(pinTitlesIds[i]);
    }

    private TextView getSplit(int i) {
        return (TextView) findViewById(pinSplitsIds[i]);
    }

    /**
     * Check for an empty PinBox {@link EditText} from the current PinBox.
     * Set focus in the next empty PinBox or notify that {@link PinView} is completed.
     */
    private void checkPinBoxesAvailable() {

        int index = -1;
        int i = currentFocus + 1;
        while (i != currentFocus) {

            if (i > (mNumberPinBoxes - 1)) {
                i = 0;
            }

            if (pinBoxIsEmpty(i)) {
                index = i;
                break;
            }
            i++;
        }
        chooseNextAction(index);
    }

    /**
     * Set results in current {@link PinView}, considering the number of character PinBoxes and each PinBox.
     *
     * @param pinResults saved results to set
     */
    void setPinResults(String pinResults) {
        for (int i = 0; i < mNumberPinBoxes; i++) {
            if (pinResults != null) {
                int start = i*mNumberCharacters;
                String valuePinBox = pinResults.substring(start, start + mNumberCharacters);
                if (!valuePinBox.trim().isEmpty()) {
                    getPinBox(i).setText(valuePinBox);
                }
                else{
                    break;
                }
            }
        }
    }

    /**
     * Check for an empty PinBox {@link EditText} from the first.
     * Set focus in the first empty PinBox or notify that {@link PinView} is completed.
     */
    void checkPinBoxesAvailableOrder() {

        int index = -1;
        for (int i = 0; i < mNumberPinBoxes; i++) {
            if (pinBoxIsEmpty(i)) {
                index = i;
                break;
            }
        }
        chooseNextAction(index);
    }

    /**
     * Check if a determinate PinBox {@link EditText} is empty.
     *
     * @param i PinBox position to check
     * @return State of PinBox
     */
    private boolean pinBoxIsEmpty(int i) {
        return getPinBox(i).getText().toString().isEmpty();
    }

    /**
     * Check if you have written or have deleted (in the latter case, there would be to do nothing).
     * If you have written, you have to move to the following free PinBox {@link EditText} or to do other
     * action if there are no empty values.
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        if (findFocus() != null) {
            currentFocus = Integer.parseInt(findFocus().getTag().toString());
        }

        if (count == 1 && s.length() == mNumberCharacters) {
            if (currentFocus == (mNumberPinBoxes - 1) || currentFocus == 0) {
                checkPinBoxesAvailableOrder();
            } else {
                checkPinBoxesAvailable();
            }

        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    /**
     * Keyboard back button
     */
    @Override
    public boolean dispatchKeyEventPreIme(KeyEvent event) {

        if (mKeyboardMandatory) {
            if (getContext() != null) {
                InputMethodManager imm = (InputMethodManager) getContext()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);

                if (imm.isActive() && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                    setImeVisibility(true);
                    return true;
                }
            }
        }
        return super.dispatchKeyEventPreIme(event);
    }

    void setImeVisibility(final boolean visible) {
        if (visible) {
            post(mShowImeRunnable);
        } else {
            removeCallbacks(mShowImeRunnable);
            PinViewUtils.hideKeyboard(getContext());
        }
    }

    private final Runnable mShowImeRunnable = new Runnable() {
        public void run() {
            if (findFocus() != null) {
                inputMethodManager.showSoftInput(findFocus(), InputMethodManager.SHOW_FORCED);
            }
        }
    };

    public abstract void setTitles(String[] titles);

    public abstract void setPin(int numberPinBoxes);

    protected abstract void notifyPinViewCompleted();
}
