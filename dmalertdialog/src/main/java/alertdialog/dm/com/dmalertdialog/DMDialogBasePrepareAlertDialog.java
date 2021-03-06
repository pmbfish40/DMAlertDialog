package alertdialog.dm.com.dmalertdialog;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.List;

abstract class DMDialogBasePrepareAlertDialog implements DMDialogIBaseUseMethods, DMDialogIBasePrepareMethods, DMDialogIConstants {

    final <T extends DMDialogAlertDialogItem> DMDialogIAlertDialog prepareConfigs(final DMDialogBaseConfigs<T> configs) {
        try {
            if (configs == null) {
                throw new Exception("DMBaseDialogConfigs: Config cannot be null");
            } else if (configs.getContext() == null) {
                throw new Exception("DMBaseDialogConfigs: Context cannot be null");
            }
        } catch (final Exception e) {
            e.printStackTrace();
            return null;
        }

        final DMDialogBaseConfigs<T> mainConfigs;

        switch (configs.getDialogType()) {
            case SUCCESSFUL:
                final DMDialogBaseConfigs<T> successConfigs = setSuccessDialog(configs.getContext());
                mainConfigs = successConfigs != null ? successConfigs : new DMDialogBaseConfigs(configs.getContext());
                break;
            case CONFIRM:
                final DMDialogBaseConfigs<T> confirmConfigs = setConfirmDialog(configs.getContext());
                mainConfigs = confirmConfigs != null ? confirmConfigs : new DMDialogBaseConfigs(configs.getContext());
                break;
            case NEUTRAL:
                final DMDialogBaseConfigs<T> neutralConfigs = setNeutralDialog(configs.getContext());
                mainConfigs = neutralConfigs != null ? neutralConfigs : new DMDialogBaseConfigs(configs.getContext());
                break;
            case WARNING:
                final DMDialogBaseConfigs<T> warningConfigs = setWarningDialog(configs.getContext());
                mainConfigs = warningConfigs != null ? warningConfigs : new DMDialogBaseConfigs(configs.getContext());
                break;
            case ERROR:
                final DMDialogBaseConfigs<T> errorConfigs = setErrorDialog(configs.getContext());
                mainConfigs = errorConfigs != null ? errorConfigs : new DMDialogBaseConfigs(configs.getContext());
                break;
            case LIST:
                final DMDialogBaseConfigs<T> listConfigs = setListDialog(configs.getContext());
                mainConfigs = listConfigs != null ? listConfigs : new DMDialogBaseConfigs(configs.getContext());
                break;
            case CUSTOM:
                final DMDialogBaseConfigs<T> customConfigs = setCustomDialog(configs.getContext());
                mainConfigs = customConfigs != null ? customConfigs : new DMDialogBaseConfigs(configs.getContext());
                break;
            default:
                final DMDialogBaseConfigs<T> successConfigsDefault = setSuccessDialog(configs.getContext());
                mainConfigs = successConfigsDefault != null ? successConfigsDefault : new DMDialogBaseConfigs(configs.getContext());
        }


        mainConfigs.setDialogType(configs.getDialogType());
        mainConfigs.setTitle(configs.getTitle() != null ? configs.getTitle() : mainConfigs.getTitle());
        mainConfigs.setContent(configs.getContent() != null ? configs.getContent() : mainConfigs.getContent());
        mainConfigs.setPositive(configs.getPositive() != null ? configs.getPositive() : mainConfigs.getPositive());
        mainConfigs.setNegative(configs.getNegative() != null ? configs.getNegative() : mainConfigs.getNegative());
        mainConfigs.setNeutral(configs.getNeutral() != null ? configs.getNeutral() : mainConfigs.getNeutral());
        mainConfigs.setDrawable(configs.getDrawable() != null ? configs.getDrawable() : mainConfigs.getDrawable());

        mainConfigs.setRegularFont(configs.getRegularFont() != null ? configs.getRegularFont() : mainConfigs.getRegularFont());
        mainConfigs.setMediumFont(configs.getMediumFont() != null ? configs.getMediumFont() : mainConfigs.getMediumFont());

        mainConfigs.setTitleColor(configs.getTitleColor() != 0 ? configs.getTitleColor() : mainConfigs.getTitleColor());
        mainConfigs.setContentColor(configs.getContentColor() != 0 ? configs.getContentColor() : mainConfigs.getContentColor());
        mainConfigs.setPositiveColor(configs.getPositiveColor() != 0 ? configs.getPositiveColor() : mainConfigs.getPositiveColor());
        mainConfigs.setNegativeColor(configs.getNegativeColor() != 0 ? configs.getNegativeColor() : mainConfigs.getNegativeColor());
        mainConfigs.setNeutralColor(configs.getNeutralColor() != 0 ? configs.getNeutralColor() : mainConfigs.getNeutralColor());
        mainConfigs.setBackgroundColor(configs.getBackgroundColor() != 0 ? configs.getBackgroundColor() : mainConfigs.getBackgroundColor());
        mainConfigs.setDividerColor(configs.getDividerColor() != 0 ? configs.getDividerColor() : mainConfigs.getDividerColor());

        mainConfigs.setAutoDismiss(configs.isAutoDismiss() != null ? configs.isAutoDismiss() : mainConfigs.isAutoDismiss());
        mainConfigs.setCancelable(configs.isCancelable() != null ? configs.isCancelable() : mainConfigs.isCancelable());

        mainConfigs.setCustomView(configs.getCustomView() != null ? configs.getCustomView() : mainConfigs.getCustomView());

        mainConfigs.setList(configs.getList() != null ? configs.getList() : mainConfigs.getList());

        mainConfigs.setListener(configs.getListener());


        final MaterialDialog.Builder builder = new MaterialDialog.Builder(mainConfigs.getContext());

        if (mainConfigs.getDialogType() == DialogType.CUSTOM) {
            builder.customView(mainConfigs.getCustomView(), true);
        } else {

            final String title = mainConfigs.getTitle();
            if (!TextUtils.isEmpty(title)) {
                builder.title(title);
            }

            final String content = mainConfigs.getContent();
            if (!TextUtils.isEmpty(content)) {
                builder.content(content);
            }

            final String positiveText = mainConfigs.getPositive();
            if (!TextUtils.isEmpty(positiveText)) {
                builder.positiveText(positiveText);
                builder.onPositive((dialog, which) -> {
                    DMDialogIBaseClickListener listener = mainConfigs.getListener();
                    if (listener != null) {
                        listener.onPositive();
                    }
                });
            }

            if (mainConfigs.getDialogType() == DialogType.CONFIRM
                    || mainConfigs.getDialogType() == DialogType.NEUTRAL
                    || mainConfigs.getDialogType() == DialogType.LIST) {

                final String negativeText = mainConfigs.getNegative();
                if (!TextUtils.isEmpty(negativeText)) {
                    builder.negativeText(negativeText);
                    builder.onNegative((dialog, which) -> {
                        final DMDialogIBaseClickListener listener = mainConfigs.getListener();
                        if (listener != null) {
                            listener.onNegative();
                        }
                    });
                }
            }

            if (mainConfigs.getDialogType() == DialogType.NEUTRAL || mainConfigs.getDialogType() == DialogType.LIST) {
                final String neutralText = mainConfigs.getNeutral();
                if (!TextUtils.isEmpty(neutralText)) {
                    builder.neutralText(neutralText);
                    builder.onNeutral((dialog, which) -> {
                        final DMDialogIBaseClickListener listener = mainConfigs.getListener();
                        if (listener != null) {
                            listener.onNeutral();
                        }
                    });
                }
            }

            if (mainConfigs.getDialogType() == DialogType.LIST) {
                final List<T> list = mainConfigs.getList();
                if (list != null && list.size() > 0) {
                    builder.items(mainConfigs.getList());
                    builder.itemsCallback((dialog, itemView, position, text) -> {
                        final DMDialogIBaseClickListener<T> listener = mainConfigs.getListener();
                        if (listener != null) {
                            listener.onSelect(list.get(position));
                        }
                    });
                }
            }

            if (mainConfigs.getRegularFont() != null && mainConfigs.getMediumFont() != null) {
                builder.typeface(mainConfigs.getMediumFont(), mainConfigs.getRegularFont());
            }

            final Drawable drawable = mainConfigs.getDrawable();
            if (drawable != null) {
                builder.icon(drawable);
            }

            if (mainConfigs.getTitleColor() != 0) {
                builder.titleColor(mainConfigs.getTitleColor());
            }

            if (mainConfigs.getContentColor() != 0) {
                builder.contentColor(mainConfigs.getContentColor());
            }

            if (mainConfigs.getPositiveColor() != 0) {
                builder.positiveColor(mainConfigs.getPositiveColor());
            }

            if (mainConfigs.getNegativeColor() != 0) {
                builder.negativeColor(mainConfigs.getNegativeColor());
            }

            if (mainConfigs.getNeutralColor() != 0) {
                builder.negativeColor(mainConfigs.getNeutralColor());
            }

            if (mainConfigs.getBackgroundColor() != 0) {
                builder.backgroundColor(mainConfigs.getBackgroundColor());
            }

            if (mainConfigs.getDividerColor() != 0) {
                builder.dividerColor(mainConfigs.getDividerColor());
            }
        }

        final DialogActionStatus cancelable = mainConfigs.isCancelable();
        builder.cancelable(cancelable == null || cancelable == DialogActionStatus.ENABLE);

        final DialogActionStatus autoDismiss = mainConfigs.isAutoDismiss();
        builder.autoDismiss(autoDismiss == null || autoDismiss == DialogActionStatus.ENABLE);

        builder.dismissListener(dialog -> {
            final DMDialogIBaseClickListener listener = mainConfigs.getListener();
            if (listener != null) {
                listener.onDismiss();
            }
        });

        final MaterialDialog materialDialog = builder.show();

        return prepareActions(configs.getContext(), materialDialog);
    }

    private DMDialogIAlertDialog prepareActions(final Context context, final MaterialDialog dialog) {
        if (dialog != null) {
            return new DMDialogIAlertDialog() {
                @Override
                public void dismiss() {
                    if (context != null) {
                        final Activity activity = getActivity(context);
                        if (activity != null &&
                                !activity.isFinishing() &&
                                !activity.isDestroyed()) {

                            if (dialog.isShowing()) {
                                dialog.dismiss();
                            }
                        }
                    }
                }

                @Override
                public View getCustomView() {
                    return dialog.getCustomView();
                }
            };
        }

        return null;
    }

    private static Activity getActivity(Context context) {
        if (context == null) return null;
        if (context instanceof Activity) return (Activity) context;
        if (context instanceof ContextWrapper)
            return getActivity(((ContextWrapper) context).getBaseContext());
        return null;
    }
}
