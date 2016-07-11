package com.mini_proj.annetao.wego;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.google.gson.Gson;
import com.mini_proj.annetao.wego.util.Utils;
import com.squareup.picasso.Picasso;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import me.gujun.android.taggroup.TagGroup;
import me.iwf.photopicker.PhotoPicker;
import okhttp3.Call;

/**
 * Created by huangweiping on 16/7/10.
 */
public class FragmentSubscribe extends Fragment
        implements
        View.OnClickListener,
        DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener {

    private KenBurnsView kenBurnsView;
    private View imageTip;
    private TextView title;
    private TextView time;
    private TextView place;
    private TextView minPeople;
    private TextView maxPeople;
    private TextView credit;
    private TextView average;
    private TextView detail;
    private TagGroup tagGroup;

    private Calendar startCalendar = Calendar.getInstance();
    private Calendar endCalendar = Calendar.getInstance();
    private int setTimeStep = 0;
    private Tag tag = Tag.NONE;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View messageLayout = inflater.inflate(R.layout.fragment_subscribe, container, false);

        kenBurnsView = (KenBurnsView) messageLayout.findViewById(R.id.image);
        title = (TextView) messageLayout.findViewById(R.id.title);
        time = (TextView) messageLayout.findViewById(R.id.time);
        time.setSelected(true);
        place = (TextView) messageLayout.findViewById(R.id.place);
        minPeople = (TextView) messageLayout.findViewById(R.id.min_people);
        maxPeople = (TextView) messageLayout.findViewById(R.id.max_people);
        credit = (TextView) messageLayout.findViewById(R.id.credit);
        average = (TextView) messageLayout.findViewById(R.id.average);
        detail = (TextView) messageLayout.findViewById(R.id.detail);
        tagGroup = (TagGroup) messageLayout.findViewById(R.id.tag_group);

        imageTip = messageLayout.findViewById(R.id.image_tip);
        imageTip.setOnClickListener(this);
        messageLayout.findViewById(R.id.image).setOnClickListener(this);
        messageLayout.findViewById(R.id.title_layout).setOnClickListener(this);
        messageLayout.findViewById(R.id.time_layout).setOnClickListener(this);
        messageLayout.findViewById(R.id.map_button).setOnClickListener(this);
        messageLayout.findViewById(R.id.place_layout).setOnClickListener(this);
        messageLayout.findViewById(R.id.min_people_layout).setOnClickListener(this);
        messageLayout.findViewById(R.id.max_people_layout).setOnClickListener(this);
        messageLayout.findViewById(R.id.credit_layout).setOnClickListener(this);
        messageLayout.findViewById(R.id.average_layout).setOnClickListener(this);
        messageLayout.findViewById(R.id.detail_layout).setOnClickListener(this);
        messageLayout.findViewById(R.id.tags_layout).setOnClickListener(this);

        return messageLayout;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_tip:
            case R.id.image:
                choosePicture();
                break;
            case R.id.title_layout:
                new MaterialDialog.Builder(getActivity())
                        .title("活动标题")
                        .content("")
                        .inputType(InputType.TYPE_CLASS_TEXT)
                        .positiveText("确认")
                        .negativeText("取消")
                        .inputRange(1, 20, Color.RED)
                        .onAny(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                title.setText(dialog.getInputEditText().getText().toString());
                            }
                        })
                        .input("活动标题", title.getText().toString(), new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {

                            }
                        }).show();
                break;
            case R.id.time_layout:
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                        this,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );
                datePickerDialog.setTitle("开始时间");
                datePickerDialog.show(getActivity().getFragmentManager(), "");
                setTimeStep = 0;
                break;
            case R.id.map_button:

                break;
            case R.id.place_layout:

                break;
            case R.id.min_people_layout:
                new MaterialDialog.Builder(getActivity())
                        .title("最少人数")
                        .content("")
                        .inputType(InputType.TYPE_CLASS_NUMBER)
                        .positiveText("确认")
                        .negativeText("取消")
                        .onAny(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                minPeople.setText(dialog.getInputEditText().getText().toString());
                            }
                        })
                        .input("最少人数", minPeople.getText().toString(), new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {

                            }
                        }).show();
                break;
            case R.id.max_people_layout:
                new MaterialDialog.Builder(getActivity())
                        .title("最多人数")
                        .content("")
                        .inputType(InputType.TYPE_CLASS_NUMBER)
                        .positiveText("确认")
                        .negativeText("取消")
                        .onAny(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                maxPeople.setText(dialog.getInputEditText().getText().toString());
                            }
                        })
                        .input("最多人数", maxPeople.getText().toString(), new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {

                            }
                        }).show();
                break;
            case R.id.credit_layout:
                new MaterialDialog.Builder(getActivity())
                        .title("信用要求")
                        .content("")
                        .inputType(InputType.TYPE_CLASS_NUMBER)
                        .positiveText("确认")
                        .negativeText("取消")
                        .onAny(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                credit.setText(dialog.getInputEditText().getText().toString());
                            }
                        })
                        .input("信用要求", credit.getText().toString(), new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {

                            }
                        }).show();
                break;
            case R.id.average_layout:
                new MaterialDialog.Builder(getActivity())
                        .title("人均消费")
                        .content("")
                        .inputType(InputType.TYPE_CLASS_NUMBER)
                        .positiveText("确认")
                        .negativeText("取消")
                        .onAny(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                average.setText(dialog.getInputEditText().getText().toString());
                            }
                        })
                        .input("人均消费", average.getText().toString(), new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {

                            }
                        }).show();
                break;
            case R.id.detail_layout:
                new MaterialDialog.Builder(getActivity())
                        .title("活动描述")
                        .content("")
                        .positiveText("确认")
                        .negativeText("取消")
                        .onAny(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                detail.setText(dialog.getInputEditText().getText().toString());
                            }
                        })
                        .input("活动描述", detail.getText().toString(), new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {

                            }
                        }).show();
                break;
            case R.id.tags_layout:
                new MaterialDialog.Builder(getActivity())
                        .title("标签")
                        .items(Tag.getAllTagName())
                        .itemsCallbackSingleChoice(0, new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                return true;
                            }
                        })
                        .onAny(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                if (which.equals(DialogAction.POSITIVE)) {
                                    tag = Tag.value(dialog.getSelectedIndex());
                                    tagGroup.setTags(tag.toString());
                                    tagGroup.getChildAt(0).setClickable(false);
                                }
                            }
                        })
                        .positiveText("确定")
                        .show();
                break;
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        if (setTimeStep == 0) {
            startCalendar.set(year, monthOfYear, dayOfMonth, 0, 0, 0);
            startCalendar.add(Calendar.SECOND, 0);
        } else {
            endCalendar.set(year, monthOfYear, dayOfMonth, 0, 0, 0);
            endCalendar.add(Calendar.SECOND, 0);
        }
        Calendar now = Calendar.getInstance();
        TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(
                this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                true
        );
        timePickerDialog.setTitle(setTimeStep == 2 ? "结束时间" : "开始时间");
        timePickerDialog.show(getActivity().getFragmentManager(), "");
        setTimeStep++;
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        if (setTimeStep == 1) {
            startCalendar.set(startCalendar.get(Calendar.YEAR), startCalendar.get(Calendar.MONTH), startCalendar.get(Calendar.DAY_OF_MONTH), hourOfDay, minute, second);
            startCalendar.add(Calendar.SECOND, 0);
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                    this,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );
            datePickerDialog.setTitle("结束时间");
            datePickerDialog.show(getActivity().getFragmentManager(), "");
        } else {
            endCalendar.set(endCalendar.get(Calendar.YEAR), endCalendar.get(Calendar.MONTH), endCalendar.get(Calendar.DAY_OF_MONTH), hourOfDay, minute, second);
            endCalendar.add(Calendar.SECOND, 0);
            setTimeText();
        }
        setTimeStep++;
    }

    private void choosePicture() {
        PhotoPicker.builder()
                .setPhotoCount(1)
                .setShowCamera(true)
                .setShowGif(true)
                .setPreviewEnabled(true)
                .start(getActivity(), PhotoPicker.REQUEST_CODE);
    }

    private void setTimeText() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        time.setText(simpleDateFormat.format(startCalendar.getTime()) + " ~ " + simpleDateFormat.format(endCalendar.getTime()));
    }

    public void setPhoto(ArrayList<String> paths) {
        String imageString = paths.get(0);
        File imgFile = new File(imageString);
        if(imgFile.exists()) {
            Picasso.with(getActivity())
                    .load(imgFile)
                    .resize(Utils.getScreenWidth(), Utils.dp2px(200))
                    .into(kenBurnsView);
            imageTip.setVisibility(View.INVISIBLE);
        } else {
            imageTip.setVisibility(View.VISIBLE);
        }
    }

    public void subscribe() {
        String titleStr=title.getText().toString();
        String[] timeStr=time.getText().toString().split("~");
        String startTime=timeStr[0];
        String endTime=timeStr[1];
        String placeStr=place.getText().toString();
        String minPeopleStr=minPeople.getText().toString();
        String maxPeopleStr=maxPeople.getText().toString();
        String creitStr=credit.getText().toString();
        String averageStr=average.getText().toString();
        String detailStr=detail.getText().toString();
        String latitude="";
        String longitude="";
        Map<String,String> map=new HashMap<>();
        Exercise.upload(Float.valueOf(latitude), User.getInstance().getId(), startTime, endTime, titleStr
                , Float.valueOf(longitude), detailStr, Float.valueOf(averageStr), "", map, new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("WeGo", "新建活动失败");
                        Toast.makeText(getActivity(),"发布活动失败！",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        Exercise exercise = gson.fromJson(response, Exercise.class);
                        UserInf.getUserInf().addExerciseMyList(exercise);
                        Map<String, String> tagList = exercise.getTagList();
                        for (Map.Entry<String, String> entry : tagList.entrySet()) {
                            ExercisePool.getTopicPool().addExerciseToMap(entry.getValue(), exercise);
                        }
                        Toast.makeText(getActivity(),"发布活动成功！",Toast.LENGTH_LONG).show();
                    }
                });
    }
}
