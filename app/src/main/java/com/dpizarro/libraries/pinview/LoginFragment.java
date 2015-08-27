package com.dpizarro.libraries.pinview;

import com.dpizarro.pinview.library.PinView;
import com.dpizarro.pinview.library.PinViewSettings;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * User: David Pizarro de Jesús
 */
public class LoginFragment extends Fragment {

    private PinView pinView;
    private ProgressDialog dialog;
    private View snackView;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    public LoginFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.login_fragment, container, false);

        pinView = (PinView) view.findViewById(R.id.pinView);
        snackView = view.findViewById(R.id.snackbarPosition);

        String[] titles = getResources().getStringArray(R.array.titles);
        String[] titlesAux = new String[5];
        System.arraycopy(titles, 0, titlesAux, 0, 5);

        /**
         * Option using an easy Builder
         */
        PinViewSettings pinViewSettings = new PinViewSettings.Builder()
                .withPinTitles(titlesAux)
                .withMaskPassword(true)
                .withDeleteOnClick(true)
                .withKeyboardMandatory(false)
                .withSplit(null)
                .withNumberPinBoxes(5)
                .withNativePinBox(false)
                .build();

        pinView.setSettings(pinViewSettings);

        pinView.setOnCompleteListener(new PinView.OnCompleteListener() {
            @Override
            public void onComplete(boolean completed, final String pinResults) {
                if (completed) {
                    /**
                     * Do what you want
                     */
                    showProgressDialogLogin();
                    showSnackbar(pinResults);
                }
            }
        });

        return view;
    }


    private void showProgressDialogLogin() {
        dialog = ProgressDialog.show(getActivity(),
                getResources().getString(R.string.login),
                getResources().getString(R.string.loading), true);
    }

    private void showSnackbar(final String pinResults) {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.cancel();
                Utils.hideKeyboard(getActivity());

                Snackbar.make(snackView, getResources().getString(R.string.login_ok) + pinResults,
                        Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.clear_pin, clickListener)
                        .show();
            }
        }, 2000);
    }

    private final View.OnClickListener clickListener = new View.OnClickListener() {
        public void onClick(View v) {
            pinView.clear();
        }
    };
}
