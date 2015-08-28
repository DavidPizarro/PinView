package com.dpizarro.pinview.library;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.EditText;

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
public class PinViewSettings implements Parcelable {

    /**
     * Default number of boxes in a simple {@link PinView}. See {@link PinView#mNumberPinBoxes}
     */
    public static final int DEFAULT_NUMBER_PIN_BOXES = 4;

    /**
     * Default mask password behaviour. See {@link PinView#mMaskPassword}
     */
    public static final boolean DEFAULT_MASK_PASSWORD = true;

    /**
     * Default number of characters for each box in {@link PinView}. See {@link PinView#mNumberCharacters}
     */
    public static final int DEFAULT_NUMBER_CHARACTERS = 1;

    /**
     * Default keyboard behaviour to allow disappear or not. See {@link PinView#mKeyboardMandatory}
     */
    public static final boolean DEFAULT_KEYBOARD_MANDATORY = false;

    /**
     * Default behaviour for each PinBox {@link EditText} in {@link PinView} when is selected. See {@link
     * PinView#mDeleteOnClick}
     */
    public static final boolean DEFAULT_DELETE_ON_CLICK = true;

    /**
     * Default {@link PinView}. See {@link PinView#mSplit}
     */
    public static final String DEFAULT_SPLIT = "";

    /**
     * Default EditText style to Pin boxes. See {@link PinView#mNativePinBox}
     */
    public static final boolean DEFAULT_NATIVE_PIN_BOX = false;

    /**
     * Custom style to Pin boxes. See {@link PinView#mCustomDrawablePinBox}
     */
    public static final int DEFAULT_CUSTOM_PIN_BOX = R.drawable.pin_box;

    /**
     * Default text color to Pin boxes. See {@link PinView#mColorTextPinBoxes}
     */
    public static final int DEFAULT_TEXT_COLOR_PIN_BOX = R.color.textbox_pinview;

    /**
     * Default text color to titles. See {@link PinView#mColorTextTitles}
     */
    public static final int DEFAULT_TEXT_COLOR_TITLES = R.color.textbox_pinview;

    /**
     * Default color to mSplit. See {@link PinView#mColorSplit}
     */
    public static final int DEFAULT_COLOR_SPLIT = R.color.textbox_pinview;

    /**
     * Default text size to Pin boxes. See {@link PinView#mTextSizePinBoxes}
     */
    public static final int DEFAULT_TEXT_SIZE_PIN_BOX = R.dimen.pin_size;

    /**
     * Default text size to titles. See {@link PinView#mTextSizeTitles}
     */
    public static final int DEFAULT_TEXT_SIZE_TITLES = R.dimen.pin_text_size;

    /**
     * Default size to mSplit. See {@link PinView#mSizeSplit}
     */
    public static final int DEFAULT_SIZE_SPLIT = R.dimen.pin_size;

    private final String[] mPinTitles;
    private final boolean mKeyboardMandatory;
    private final int mNumberPinBoxes;
    private final boolean mMaskPassword;
    private final int mNumberCharacters;
    private final String mSplit;
    private final boolean mDeleteOnClick;
    private final boolean mNativePinBox;
    private final int mCustomDrawablePinBox;
    private final int mColorTextPinBox;
    private final int mColorTextTitles;
    private final int mColorSplit;
    private final float mTextSizePinBox;
    private final float mTextSizeTitles;
    private final float mSizeSplit;


    private PinViewSettings(Builder builder) {
        mPinTitles = builder.mPinTitles;
        mKeyboardMandatory = builder.mKeyboardMandatory;
        mNumberPinBoxes = builder.mNumberPinBoxes;
        mMaskPassword = builder.mMaskPassword;
        mNumberCharacters = builder.mNumberCharacters;
        mSplit = builder.mSplit;
        mDeleteOnClick = builder.mDeleteOnClick;
        mNativePinBox = builder.mNativePinBox;
        mCustomDrawablePinBox = builder.mCustomDrawablePinBox;
        mColorTextPinBox = builder.mColorTextPinBox;
        mColorTextTitles = builder.mColorTextTitles;
        mColorSplit = builder.mColorSplit;
        mTextSizePinBox = builder.mTextSizePinBox;
        mTextSizeTitles = builder.mTextSizeTitles;
        mSizeSplit = builder.mSizeSplit;
    }

    public String[] getPinTitles() {
        return mPinTitles;
    }

    public boolean isKeyboardMandatory() {
        return mKeyboardMandatory;
    }

    public int getNumberPinBoxes() {
        return mNumberPinBoxes;
    }

    public boolean isMaskPassword() {
        return mMaskPassword;
    }

    public int getNumberCharacters() {
        return mNumberCharacters;
    }

    public String getSplit() {
        return mSplit;
    }

    public boolean isDeleteOnClick() {
        return mDeleteOnClick;
    }

    public boolean isNativePinBox() {
        return mNativePinBox;
    }

    public int getCustomDrawablePinBox() {
        return mCustomDrawablePinBox;
    }

    public int getColorTextPinBox() {
        return mColorTextPinBox;
    }

    public int getColorTextTitles() {
        return mColorTextTitles;
    }

    public int getColorSplit() {
        return mColorSplit;
    }

    public float getTextSizePinBox() {
        return mTextSizePinBox;
    }

    public float getTextSizeTitles() {
        return mTextSizeTitles;
    }

    public float getSizeSplit() {
        return mSizeSplit;
    }


    public static final class Builder {

        private String[] mPinTitles;
        private boolean mKeyboardMandatory;
        private int mNumberPinBoxes;
        private boolean mMaskPassword;
        private int mNumberCharacters;
        private String mSplit;
        private boolean mDeleteOnClick;
        private boolean mNativePinBox;
        private int mCustomDrawablePinBox;
        private int mColorTextPinBox;
        private int mColorTextTitles;
        private int mColorSplit;
        private float mTextSizePinBox;
        private float mTextSizeTitles;
        private float mSizeSplit;

        public Builder() {
        }

        public Builder withPinTitles(String[] mPinTitles) {
            this.mPinTitles = mPinTitles;
            return this;
        }

        public Builder withKeyboardMandatory(boolean mKeyboardMandatory) {
            this.mKeyboardMandatory = mKeyboardMandatory;
            return this;
        }

        public Builder withNumberPinBoxes(int mNumberPinBoxes) {
            this.mNumberPinBoxes = mNumberPinBoxes;
            return this;
        }

        public Builder withMaskPassword(boolean mMaskPassword) {
            this.mMaskPassword = mMaskPassword;
            return this;
        }

        public Builder withNumberCharacters(int mNumberCharacters) {
            this.mNumberCharacters = mNumberCharacters;
            return this;
        }

        public Builder withSplit(String mSplit) {
            this.mSplit = mSplit;
            return this;
        }

        public Builder withDeleteOnClick(boolean mDeleteOnClick) {
            this.mDeleteOnClick = mDeleteOnClick;
            return this;
        }

        public Builder withNativePinBox(boolean mNativePinBox) {
            this.mNativePinBox = mNativePinBox;
            return this;
        }

        public Builder withCustomDrawablePinBox(int mCustomDrawablePinBox) {
            this.mCustomDrawablePinBox = mCustomDrawablePinBox;
            return this;
        }

        public Builder withColorTextPinBox(int mColorTextPinBox) {
            this.mColorTextPinBox = mColorTextPinBox;
            return this;
        }

        public Builder withColorTextTitles(int mColorTextTitles) {
            this.mColorTextTitles = mColorTextTitles;
            return this;
        }

        public Builder withColorSplit(int mColorSplit) {
            this.mColorSplit = mColorSplit;
            return this;
        }

        public Builder withTextSizePinBox(float mTextSizePinBox) {
            this.mTextSizePinBox = mTextSizePinBox;
            return this;
        }

        public Builder withTextSizeTitles(float mTextSizeTitles) {
            this.mTextSizeTitles = mTextSizeTitles;
            return this;
        }

        public Builder withSizeSplit(float mSizeSplit) {
            this.mSizeSplit = mSizeSplit;
            return this;
        }

        public PinViewSettings build() {
            return new PinViewSettings(this);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(this.mPinTitles);
        dest.writeByte(mKeyboardMandatory ? (byte) 1 : (byte) 0);
        dest.writeInt(this.mNumberPinBoxes);
        dest.writeByte(mMaskPassword ? (byte) 1 : (byte) 0);
        dest.writeInt(this.mNumberCharacters);
        dest.writeString(this.mSplit);
        dest.writeByte(mDeleteOnClick ? (byte) 1 : (byte) 0);
        dest.writeByte(mNativePinBox ? (byte) 1 : (byte) 0);
        dest.writeInt(this.mCustomDrawablePinBox);
        dest.writeInt(this.mColorTextPinBox);
        dest.writeInt(this.mColorTextTitles);
        dest.writeInt(this.mColorSplit);
        dest.writeFloat(this.mTextSizePinBox);
        dest.writeFloat(this.mTextSizeTitles);
        dest.writeFloat(this.mSizeSplit);
    }

    protected PinViewSettings(Parcel in) {
        this.mPinTitles = in.createStringArray();
        this.mKeyboardMandatory = in.readByte() != 0;
        this.mNumberPinBoxes = in.readInt();
        this.mMaskPassword = in.readByte() != 0;
        this.mNumberCharacters = in.readInt();
        this.mSplit = in.readString();
        this.mDeleteOnClick = in.readByte() != 0;
        this.mNativePinBox = in.readByte() != 0;
        this.mCustomDrawablePinBox = in.readInt();
        this.mColorTextPinBox = in.readInt();
        this.mColorTextTitles = in.readInt();
        this.mColorSplit = in.readInt();
        this.mTextSizePinBox = in.readFloat();
        this.mTextSizeTitles = in.readFloat();
        this.mSizeSplit = in.readFloat();
    }

    public static final Creator<PinViewSettings> CREATOR = new Creator<PinViewSettings>() {
        public PinViewSettings createFromParcel(Parcel source) {
            return new PinViewSettings(source);
        }

        public PinViewSettings[] newArray(int size) {
            return new PinViewSettings[size];
        }
    };
}
