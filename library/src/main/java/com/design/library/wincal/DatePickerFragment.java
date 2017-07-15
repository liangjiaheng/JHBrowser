package com.design.library.wincal;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.design.library.R;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;


public class DatePickerFragment extends DialogFragment {


    private View mFragmentView;
    private RelativeLayout mRootLayout;
    private ListView mMonthListview;
    private ListView mDateListView;
    private ListView mYearListView;
    private MonthYearAdapter mMonthAdapter;
    private MonthYearAdapter mYearAdapter;
    private MonthYearAdapter mDateAdapter;

    private int mMiddlePositionInScreen = 0;
    private int mBottomPositionOfMiddleElement = 0;
    private int mCurrentMonth;
    private int mCurrentYear;
    private int mCurrentDate;
    private int mCurrentDummyDate;
    private int mCurrentYearPosition;
    private int mCurrentMonthPosition;
    private int mCurrentDatePosition;

    private int mNumberOfMonthDays;

    private String[] daysOfTheMonth;


    private AtomicBoolean mMonthListBeingTouched = new AtomicBoolean(false);
    private AtomicBoolean mYearListBeingTouched = new AtomicBoolean(false);
    private AtomicBoolean mDateListBeingTouched = new AtomicBoolean(false);
    private int mRootLayoutHeight;
    private ScrollState mScrollStateOfMonthView;
    private ScrollState mScrollStateOfYearView;
    private ScrollState mScrollStateOfDayView;

    private ListViewVisible mMonthViewVisible;
    private ListViewVisible mYearViewVisible;
    private ListViewVisible mDateViewVisible;

    private View mDummyView;


    //private int mInitialMonth;

    private int mMiddlePositionFromTop;
    private int ACTION_MOVED = 0;
    private String mDialogTitle;

    //private int mFirstVisiblePosition;
    private ColorDrawable mColorDrawable;
    //private boolean mLowerHalf=false;
    private int mDateInDummyView;
    boolean mMonthOrYearTouched;
    int mDelta;


    private StringBuilder mFirstVisibleMonth = new StringBuilder("");
    private StringBuilder mFirstVisibleYear = new StringBuilder("");
    private AtomicInteger mFirstVisiblePositionMonth = new AtomicInteger(0);
    private AtomicInteger mFirstVisiblePositionYear = new AtomicInteger(0);
    private AtomicBoolean mLowerHalfMonth = new AtomicBoolean(false);
    private AtomicBoolean mLowerHalfYear = new AtomicBoolean(false);
    private AtomicInteger mInitialPositionMonth = new AtomicInteger(0);
    private AtomicInteger mInitialPositionYear = new AtomicInteger(0);
    private int mStartingPositionOfScrollMonth;
    private int mStartingPositionOfScrollYear;

    //    private ActionBar mActionBar;
    private DateSelectListener mDateSelectListener;
    private Handler mHandler;
    private Runnable mStatusChecker;

    private AtomicBoolean itemScrolledToMiddleMonth = new AtomicBoolean(false);
    private AtomicBoolean itemScrolledToMiddleDate = new AtomicBoolean(false);
    private AtomicBoolean itemScrolledToMiddleYear = new AtomicBoolean(false);
    private boolean mDummyTouched = false;

    private AtomicBoolean mOneViewScrolledMonth = new AtomicBoolean(false);
    private AtomicBoolean mOneViewScrolledYear = new AtomicBoolean(false);

    private int mBackgroundResourceId = R.color.material_background;
    private int mSelectedRowResourceId = R.color.material_selected_row_color;
    private int mListRowBackgroundResourceId = R.drawable.list_border;
    private int mListRowTextColor = R.color.material_text_color;
    private int mSelectedListRowTextColor = R.color.material_text_color;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        mFragmentView = inflater.inflate(R.layout.date_picker, container, false);

        //initializeColorVariables();


        getCurrentDate();
        retrieveInitialArgs();
        mHandler = new Handler();
        if (getDialog() != null) {
            try {
                setRetainInstance(true);
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }


        return mFragmentView;

    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        mMonthListview = (ListView) mFragmentView.findViewById(R.id.month_listview);
        mDateListView = (ListView) mFragmentView.findViewById(R.id.date_listview);
        mYearListView = (ListView) mFragmentView.findViewById(R.id.year_listview);
        mColorDrawable = new ColorDrawable(Color.rgb(255, 255, 255));

        initializeObjects();

        //mMonthListview.setClickable(true);
        String monthNames[] = getResources().getStringArray(R.array.month_names);
        mMonthAdapter = new MonthYearAdapter(getActivity(), monthNames, monthNames.length, Constants.NOT_FOR_DATE_VIEW, mMonthListview);
        mMonthAdapter.setAllItemsVisible(true);

        mYearAdapter = new MonthYearAdapter(getActivity(), null, Constants.NO_OF_YEARS, Constants.NOT_FOR_DATE_VIEW, mYearListView);
        mDateAdapter = new MonthYearAdapter(getActivity(), daysOfTheMonth, daysOfTheMonth.length, Constants.FOR_DATE_VIEW, mDateListView);
        mDateAdapter.setCurrentMonth(mCurrentMonth);
        mDateAdapter.setCurrentYear(mCurrentYear);
        mYearAdapter.setAllItemsVisible(false);
        mDateAdapter.setAllItemsVisible(false);
        setColorValuesForListViews();

        mMonthListview.setAdapter(mMonthAdapter);
        mYearListView.setAdapter(mYearAdapter);
        mDateListView.setAdapter(mDateAdapter);

        setCurrentPositionsInListViews();
        mRootLayout = (RelativeLayout) mFragmentView.findViewById(R.id.root_layout);

        ViewTreeObserver vto = mRootLayout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                mRootLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                mRootLayoutHeight = mRootLayout.getMeasuredHeight();

                mMonthListview.setSelectionFromTop(mCurrentMonthPosition, mRootLayoutHeight / 3);
                mYearListView.setSelectionFromTop(mCurrentYearPosition, mRootLayoutHeight / 3);
                mDateListView.setSelectionFromTop(mCurrentDatePosition, mRootLayoutHeight / 3);

                mMonthListview.post(new Runnable() {
                    @Override
                    public void run() {

                        getMiddlePosition();
                        setAllListeners();


                    }
                });
            }
        });

    }

    private void initializeColorVariables() {

        mBackgroundResourceId = R.color.material_background;
        mSelectedRowResourceId = R.color.material_selected_row_color;
        mListRowBackgroundResourceId = R.drawable.list_border;
        mListRowTextColor = R.color.material_text_color;
        mSelectedListRowTextColor = R.color.material_text_color;
    }

    public void setDateSelectListener(DateSelectListener dateSelectListener) {

        this.mDateSelectListener = dateSelectListener;
    }

    public void setClickListener() {

        View monthView = getMiddleView(mMonthListview, mMiddlePositionFromTop);
        View yearView = getMiddleView(mYearListView, mMiddlePositionFromTop);
        View dateView = getMiddleView(mDateListView, mMiddlePositionFromTop);

        int month = (Integer.parseInt(((TextView) monthView.findViewById(R.id.row_number)).getText().toString()));
        int year = (Integer.parseInt(((TextView) yearView.findViewById(R.id.row_number)).getText().toString()));
        int date = (Integer.parseInt(((TextView) dateView.findViewById(R.id.row_number)).getText().toString()));

        mDateSelectListener.onSelectDate(date, month, year);
    }


    private void setAllListeners() {

        setListenersOnListView(mMonthAdapter, mMonthListview, mMonthListBeingTouched, mScrollStateOfMonthView, mMonthViewVisible, mFirstVisiblePositionMonth, mLowerHalfMonth, mInitialPositionMonth, mStartingPositionOfScrollMonth, mFirstVisibleMonth, itemScrolledToMiddleMonth, mOneViewScrolledMonth);
        setListenersOnListView(mYearAdapter, mYearListView, mYearListBeingTouched, mScrollStateOfYearView, mYearViewVisible, mFirstVisiblePositionYear, mLowerHalfYear, mInitialPositionYear, mStartingPositionOfScrollYear, mFirstVisibleYear, itemScrolledToMiddleYear, mOneViewScrolledYear);
        setListenersOnListView(mDateAdapter, mDateListView, mDateListBeingTouched, mScrollStateOfDayView, mDateViewVisible, new AtomicInteger(0), new AtomicBoolean(), new AtomicInteger(0), 0, null, itemScrolledToMiddleDate, null);


    }

    private void setColorValuesForListViews() {

        mMonthAdapter.setColorValues(mListRowTextColor, mSelectedListRowTextColor, mSelectedRowResourceId, mListRowBackgroundResourceId);
        mDateAdapter.setColorValues(mListRowTextColor, mSelectedListRowTextColor, mSelectedRowResourceId, mListRowBackgroundResourceId);
        mYearAdapter.setColorValues(mListRowTextColor, mSelectedListRowTextColor, mSelectedRowResourceId, mListRowBackgroundResourceId);
    }

    protected void retrieveInitialArgs() {

        Bundle args = getArguments();
        if (args != null) {

            mCurrentMonth = args.getInt(Constants.MONTH, mCurrentMonth);
            mCurrentYear = args.getInt(Constants.YEAR, mCurrentYear);
            mCurrentDate = args.getInt(Constants.DATE, mCurrentDate);
            mCurrentDummyDate = args.getInt(Constants.DUMMY_DATE, mCurrentDummyDate);
            mDialogTitle = args.getString(Constants.DIALOG_TITLE);

        }
        mDateInDummyView = mCurrentDate;
        findCalendarForCurrentMonth(mCurrentYear, mCurrentMonth + 1);
        Dialog dialog = getDialog();
        if (dialog != null) {
            if (mDialogTitle != null) {
                dialog.setTitle(mDialogTitle);
            } else {
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            }
        }
    }

    /**
     * Restore current states from savedInstanceState
     *
     * @param savedInstanceState
     * @param key
     */
    public void restoreStatesFromKey(Bundle savedInstanceState, String key) {
        if (savedInstanceState != null && savedInstanceState.containsKey(key)) {
            Bundle savedState = savedInstanceState.getBundle(key);
            setArguments(savedState);
        }
    }


    /**
     * Restore state for dialog
     *
     * @param savedInstanceState
     * @param key
     * @param dialogTag
     */
    public void restoreDialogStatesFromKey(FragmentManager manager,
                                           Bundle savedInstanceState, String key, String dialogTag) {
        restoreStatesFromKey(savedInstanceState, key);

        DatePickerFragment existingDialog = (DatePickerFragment) manager
                .findFragmentByTag(dialogTag);
        if (existingDialog != null) {
            existingDialog.dismiss();
            show(manager, dialogTag);
        }
    }

    /**
     * Save current state to bundle outState
     *
     * @param outState
     * @param key
     */
    public void saveStatesToKey(Bundle outState, String key) {
        outState.putBundle(key, getSavedStates());
    }

    /**
     * Get current saved sates of the Calendar. Useful for handling rotation.
     */
    public Bundle getSavedStates() {

        Bundle bundle = new Bundle();

        try {
            int middlePosition = mMonthAdapter.getCurrentPos() - mMonthListview.getFirstVisiblePosition();
            TextView monthView = (TextView) mMonthListview.getChildAt(middlePosition).findViewById(R.id.row_number);
            TextView yearView = (TextView) mYearListView.getChildAt(middlePosition).findViewById(R.id.row_number);
            TextView dateView = (TextView) mDateListView.getChildAt(middlePosition).findViewById(R.id.row_number);
            TextView dummyView = (TextView) mDummyView.findViewById(R.id.row_number);


            bundle.putInt(Constants.MONTH, Integer.parseInt(monthView.getText().toString()) - 1);
            bundle.putInt(Constants.YEAR, Integer.parseInt(yearView.getText().toString()));
            bundle.putInt(Constants.DATE, Integer.parseInt(dateView.getText().toString()));
            bundle.putInt(Constants.DUMMY_DATE, Integer.parseInt(dummyView.getText().toString()));
            if (mDialogTitle != null) {
                bundle.putString(Constants.DIALOG_TITLE, mDialogTitle);
            }
        } catch (Exception e) {
            return null;
        }

        return bundle;
    }


    //A good way to initialize
    public static DatePickerFragment newInstance(String dialogTitle, int month,
                                                 int year, int date) {
        DatePickerFragment fragment = new DatePickerFragment();

        Bundle args = new Bundle();
        args.putString(Constants.DIALOG_TITLE, dialogTitle);
        args.putInt(Constants.MONTH, month);
        args.putInt(Constants.YEAR, year);
        args.putInt(Constants.DATE, date);

        fragment.setArguments(args);

        return fragment;
    }


    /**
     * set the background color of the screen. R.color.something or R.drawable.something can be used
     */
    public void setBackgroundDrawable(int resId) {

        mBackgroundResourceId = resId;
    }

    /**
     * set the background resource for the selected row
     */

    public void setSelectedRowBackground(int resId) {

        mSelectedRowResourceId = resId;
    }

    /**
     * set the background of each ListView row
     */
    public void setListRowBackground(int resId) {

        mListRowBackgroundResourceId = resId;
    }

    /**
     * set the textcolor of each listview row
     */

    public void setListRowTextColor(int colorId) {

        mListRowTextColor = colorId;
    }

    /**
     * set the textcolor of selected  listview row
     */
    public void setSelectedListRowTextColor(int colorId) {

        mSelectedListRowTextColor = colorId;
    }


    /**
     * returns the midlle position which has the highlight
     */
    protected void getMiddlePosition() {

        mMiddlePositionFromTop = mMonthAdapter.getCurrentPos() - mMonthListview.getFirstVisiblePosition();
        setAnimationParams();
        putDummyViewInMiddle();
        //getInitialAndFinalMonth(mMiddlePositionFromTop);
        mFirstVisiblePositionMonth.set(mMonthListview.getFirstVisiblePosition());
        mFirstVisiblePositionYear.set(mYearListView.getFirstVisiblePosition());
//
//        mActionBar = ((ActionBarActivity) getActivity()).getActionBar();
//        setClickListenerOnActionBar(mActionBar);

    }

    private void setAnimationParams() {
        mDateAdapter.mMiddlePositionFromTop = mMiddlePositionFromTop;
        mMonthAdapter.setScrollState(mScrollStateOfMonthView);
        mYearAdapter.setScrollState(mScrollStateOfYearView);
        mDateAdapter.setScrollState(mScrollStateOfDayView);
        mYearAdapter.setTouchedParam(mYearListBeingTouched);
        mMonthAdapter.setTouchedParam(mMonthListBeingTouched);
        mDateAdapter.setTouchedParam(mDateListBeingTouched);
    }

    private void putDummyViewInMiddle() {

        View middleView = mDateListView.getChildAt(mMiddlePositionFromTop);
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mDummyView = inflater.inflate(R.layout.calendar_row, null);
        mDummyView.setLayoutParams(new RelativeLayout.LayoutParams(middleView.getWidth(), middleView.getHeight()));

        int location[] = new int[2];
        middleView.getLocationInWindow(location);
        mDummyView.setX(location[0]);
        // mDummyView.setX(middleView.getX());
        mDummyView.setY(middleView.getY());

        setDataInDummyView();

        mRootLayout.addView(mDummyView);
        mInitialPositionMonth.set(middleView.getTop());
        mInitialPositionYear.set(middleView.getTop());

        mStartingPositionOfScrollMonth = mStartingPositionOfScrollYear = mInitialPositionMonth.get();
        // mInitialMonthForDummyView=((TextView)(getMiddleView(mMonthListview,0).findViewById(R.id.row_text))).getText().toString();

        mFirstVisibleMonth.delete(0, mFirstVisibleMonth.length());
        mFirstVisibleYear.delete(0, mFirstVisibleYear.length());

        mFirstVisibleMonth.append(((TextView) (getMiddleView(mMonthListview, 0).findViewById(R.id.row_text))).getText().toString());
        mFirstVisibleYear.append(((TextView) (getMiddleView(mYearListView, 0).findViewById(R.id.row_number))).getText().toString());

        // Toast.makeText(getActivity(),""+mFirstVisibleYear,Toast.LENGTH_SHORT).show();


    }

    private void setDataInDummyView() {

        int temporaryDate;
        if (mCurrentDummyDate != 0) {
            temporaryDate = mCurrentDummyDate;
        } else {
            temporaryDate = mCurrentDate;
        }

        Date date = new GregorianCalendar(mCurrentYear, mCurrentMonth, temporaryDate).getTime();

        String dayOfTheWeek = new SimpleDateFormat("EEEE").format(date);
        ((TextView) mDummyView.findViewById(R.id.row_text)).setText(dayOfTheWeek);
        ((TextView) mDummyView.findViewById(R.id.row_text)).setTextColor(mColorDrawable.getColor());
        ((TextView) mDummyView.findViewById(R.id.row_number)).setText(String.format("%02d", temporaryDate));
        // mDummyView.setBackgroundColor(getResources().getColor(R.color.material_selected_row_color));
        mDummyView.setBackgroundResource(mSelectedRowResourceId);


    }

    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }


    private void initializeObjects() {

        mMonthViewVisible = new ListViewVisible();
        mMonthViewVisible.setCompleteListViewVisible(true);
        mYearViewVisible = new ListViewVisible();
        mDateViewVisible = new ListViewVisible();

        mScrollStateOfMonthView = new ScrollState();
        mScrollStateOfYearView = new ScrollState();
        mScrollStateOfDayView = new ScrollState();


    }

    private void getCurrentDate() {

        Date currentDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);

        mCurrentMonth = cal.get(Calendar.MONTH);
        // mCurrentMonth=2;
        mCurrentYear = cal.get(Calendar.YEAR);
        mCurrentDate = cal.get(Calendar.DAY_OF_MONTH);


    }

    private void findCalendarForCurrentMonth(int currentYear, int currentMonth) {

        Calendar cal = new GregorianCalendar();
        cal.clear();
        cal.set(currentYear, currentMonth - 1, 1);

        // mFirstWeekDayOfMonth=cal.get(Calendar.DAY_OF_WEEK);
        mNumberOfMonthDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        daysOfTheMonth = new String[mNumberOfMonthDays];
        for (int i = 0; i < mNumberOfMonthDays; i++) {

            daysOfTheMonth[i] = String.valueOf(i + 1);
        }

    }


    private void setCurrentPositionsInListViews() {

        mCurrentMonthPosition = mMonthAdapter.getCount() / 2 - Constants.OFFSET_FOR_MONTH + mCurrentMonth;
        mCurrentYearPosition = mYearAdapter.getCount() / 2 - Constants.OFFSET_FOR_YEAR + (mCurrentYear - Constants.STARTING_YEAR);


        // For placing the mCurrentDate in the middle of the screen
        View view = mDateAdapter.getView(mDateAdapter.getCount() / 2, null, mDateListView);
        int textInView = Integer.parseInt(((TextView) view.findViewById(R.id.row_number)).getText().toString());
        mCurrentDatePosition = mDateAdapter.getCount() / 2 + (mCurrentDate - textInView);


        mMonthAdapter.setCurrentPos(mCurrentMonthPosition);
        mYearAdapter.setCurrentPos(mCurrentYearPosition);
        mDateAdapter.setCurrentPos(mCurrentDatePosition);

    }


    private void setListenersOnListView(final MonthYearAdapter adapter, final ListView listView, final AtomicBoolean listBeingTouched, final ScrollState state, final ListViewVisible completeListVisible, final AtomicInteger firstVisiblePosition, final AtomicBoolean lowerHalf, final AtomicInteger initialPosition, final int startingPositionOfScroll, final StringBuilder firstVisibleText, final AtomicBoolean itemScrolledToMiddle, final AtomicBoolean oneViewScrolled) {

        state.setScrollState(OnScrollListener.SCROLL_STATE_IDLE);
        listView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    if (listView.getId() == R.id.month_listview || listView.getId() == R.id.year_listview) {
                        if (!mDummyTouched) {
                            mDummyView.setVisibility(View.VISIBLE);
                        }

                        mMonthOrYearTouched = true;

                    } else {
                        mDummyTouched = false;
                        mDummyView.setVisibility(View.INVISIBLE);
                    }

                    itemScrolledToMiddle.set(false);
                    if (ACTION_MOVED == 1) {
                        if (state.getScrollState() == OnScrollListener.SCROLL_STATE_TOUCH_SCROLL || state.getScrollState() == OnScrollListener.SCROLL_STATE_FLING)
                            ACTION_MOVED = 1;
                        else if (state.getScrollState() == OnScrollListener.SCROLL_STATE_IDLE)
                            ACTION_MOVED = 0;
                    }

                    disableOtherListViews(listView);
                    setOtherListViewsInvisible(listView);
                    stopOtherScrolls(listView);
                    listBeingTouched.set(true);

                    if (!adapter.getAllItemsVisible()) {
                        adapter.setAllItemsVisible(true);
                        adapter.notifyDataSetChanged();

                    }

                    if (state.getScrollState() == OnScrollListener.SCROLL_STATE_IDLE && mMonthOrYearTouched) {

                        if (listView.getId() == R.id.date_listview) {

                            addDatesInDateView();
                        }

                    }

                }

                if (event.getAction() == MotionEvent.ACTION_UP) {

                    enableAllListViews();
                    listBeingTouched.compareAndSet(true, false);

                    if (ACTION_MOVED != 1) {
                        if (completeListVisible.isCompleteListViewVisible()) {
                            putThisViewInMiddle(event.getY(), listView, adapter);
                        }

                    }

                    if (state.getScrollState() == OnScrollListener.SCROLL_STATE_IDLE) {

                        completeListVisible.setCompleteListViewVisible(true);

                    }


                } else if (event.getAction() == MotionEvent.ACTION_MOVE) {

                    ACTION_MOVED = 1;
                    if (listView.getId() == R.id.date_listview) {
                        mDummyTouched = true;
                    }

                }

                return false;
            }
        });


        listView.setOnScrollListener(new OnScrollListener() {
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                if (listView.getId() != R.id.date_listview) {

                    View cc = null;
                    if (!oneViewScrolled.get()) {
                        int position = mMiddlePositionFromTop;
                        cc = listView.getChildAt(position);
                        if (cc != null) {
                            cc.setTag(String.valueOf(listView.getId()));
                            oneViewScrolled.set(true);
                        }
                    }


                    View c = listView.findViewWithTag(String.valueOf(listView.getId()));
                    if (c != null) {

                        int heightOfView = c.getHeight() / 2;
                        if (c.getTop() != 0 && c.getTop() > 0) {

                            fadeView(c, c.getTop(), heightOfView, listView, firstVisiblePosition, lowerHalf, initialPosition, startingPositionOfScroll, firstVisibleText, oneViewScrolled);

                        }
                    }

                } else {
                    // mDummyView.setVisibility(View.INVISIBLE);
                }
            }


            public void onScrollStateChanged(AbsListView view, int scrollState) {
                state.setScrollState(scrollState);
//                mActionBar.getCustomView().findViewById(R.id.done).setVisibility(View.GONE);


                if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {

//                    mActionBar.getCustomView().findViewById(R.id.done).setVisibility(View.VISIBLE);
                    if (listView.getId() == R.id.month_listview || listView.getId() == R.id.year_listview) {
                        // putDayOnDummyView();
                    }

                }

                if (scrollState == OnScrollListener.SCROLL_STATE_IDLE && !listBeingTouched.get()) {

                    putSomeRowInMiddle(listView, adapter);
                    itemScrolledToMiddle.set(true);

                }

            }
        });

    }


    public void fadeView(View c, int top, int heightOfView, ListView listView, AtomicInteger firstVisiblePosition, AtomicBoolean lowerHalf, AtomicInteger initialPosition, int startingPositionForScroll, StringBuilder firstVisibleText, AtomicBoolean oneViewScrolled) {


        int fadeFraction = 0;
        if (!lowerHalf.get()) {
            fadeFraction = findFadeFraction(c, top, heightOfView, listView, firstVisiblePosition, lowerHalf, initialPosition, firstVisibleText);


        } else {

            fadeFraction = findFadeFractionLower(c, top, heightOfView, listView, firstVisiblePosition, lowerHalf, initialPosition, startingPositionForScroll, oneViewScrolled);


        }


        mColorDrawable.setAlpha(fadeFraction);
        TextView rowText = (TextView) mDummyView.findViewById(R.id.row_text);
        if (fadeFraction == 0) {

            //rowText.setTextColor(getResources().getColor(R.color.material_selected_row_color));
            rowText.setTextColor(getResources().getColor(mSelectedRowResourceId));
        } else {
            rowText.setTextColor(mColorDrawable.getColor());
        }

    }


    public int findFadeFraction(View c, int top, int heightOfView, ListView listView, AtomicInteger firstVisiblePosition, AtomicBoolean lowerHalf, AtomicInteger initialPosition, StringBuilder firstVisibleText) {

        int id = 0;
        id = R.id.row_number;

        if (Math.abs(top - initialPosition.get()) >= heightOfView) {

            int currentValue = Integer.parseInt(((TextView) c.findViewById(R.id.row_number)).getText().toString());

            int newValue = 0;
            if (top > initialPosition.get()) {
                if (currentValue == 1)
                    currentValue = 13;
                setNewDayOfWeek(listView, currentValue - 1);
            } else if (top < initialPosition.get()) {
                if (currentValue == 12)
                    currentValue = 0;
                setNewDayOfWeek(listView, currentValue + 1);
            }


            initialPosition.set(top);
            lowerHalf.set(true);
            firstVisibleText.delete(0, firstVisibleText.length());
            firstVisibleText.append(((TextView) (getMiddleView(listView, 0).findViewById(id))).getText().toString());


            return 0;
        } else if (top == initialPosition.get())
            return (255);

        int x = (int) ((255.0 * 1 * initialPosition.get()) / (Math.abs(top - initialPosition.get()) + initialPosition.get())) - Math.abs(top - initialPosition.get());
        return x >= 0 ? x : 0;


    }


    private void setNewDayOfWeek(ListView listView, int value) {

        View monthView = getMiddleView(mMonthListview, mMiddlePositionFromTop);
        View yearView = getMiddleView(mYearListView, mMiddlePositionFromTop);


        int month = Integer.parseInt(((TextView) monthView.findViewById(R.id.row_number)).getText().toString());
        int year = Integer.parseInt(((TextView) yearView.findViewById(R.id.row_number)).getText().toString());
        int dateInDummyView = Integer.parseInt(((TextView) mDummyView.findViewById(R.id.row_number)).getText().toString());

        if (listView.getId() == R.id.month_listview) {
            month = value;
        } else {
            year = value;
        }

        Date date = new GregorianCalendar(year, month - 1, dateInDummyView).getTime();
        ((TextView) mDummyView.findViewById(R.id.row_text)).setText(new SimpleDateFormat("EEEE").format(date));

    }


    public int findFadeFractionLower(View c, int top, int heightOfView, ListView listView, AtomicInteger firstVisiblePosition, AtomicBoolean lowerhalf, AtomicInteger initialPosition, int startingPositionForScroll, AtomicBoolean oneViewScrolled) {

        if (Math.abs(top - initialPosition.get()) >= heightOfView) {

            lowerhalf.set(false);
            // initialPosition.set(startingPositionForScroll);
            initialPosition.set(mRootLayoutHeight / 3);
            firstVisiblePosition.set(listView.getFirstVisiblePosition());

            c.setTag(null);
            oneViewScrolled.set(false);
            return 255;

        }
        // else if(top<initialPosition.get() || c.getBottom()>initialPosition.get()+heightOfView)


        else if (initialPosition.get() > mRootLayoutHeight / 3 && top < initialPosition.get() || ((initialPosition.get() < mRootLayoutHeight / 3 + heightOfView) && (c.getBottom() > mRootLayoutHeight / 3 + heightOfView))) {
            //{

            int currentValue = Integer.parseInt(((TextView) c.findViewById(R.id.row_number)).getText().toString());
            int newValue = 0;
            setNewDayOfWeek(listView, currentValue);


        } else if (top == initialPosition.get())
            return 0;

        int x = (int) ((Math.abs(top - initialPosition.get()) * 1.0 / initialPosition.get()) * 255.0) + Math.abs(top - initialPosition.get());
        return x <= 255 ? x : 255;

    }

    protected void addDatesInDateView() {

        View monthChildView = getMiddleView(mMonthListview, mMiddlePositionFromTop);
        View yearChildView = getMiddleView(mYearListView, mMiddlePositionFromTop);

        TextView month = (TextView) monthChildView.findViewById(R.id.row_number);
        TextView year = (TextView) yearChildView.findViewById(R.id.row_number);

        int yearText = Integer.parseInt(year.getText().toString());
        int monthText = Integer.parseInt(month.getText().toString());

        //mFinalMonth = monthText;
        findCalendarForCurrentMonth(yearText, monthText);
        setNewDatesInDateAdapter(yearText, monthText);
        mMonthOrYearTouched = false;

    }


    private void setDateParams(int year, int month) {

        mDateAdapter.setNewDateParameters(daysOfTheMonth);
        mDateAdapter.setCurrentMonth(month - 1);
        mDateAdapter.setCurrentYear(year);
        mDateAdapter.notifyDataSetChanged();
    }

    protected void setNewDatesInDateAdapter(final int year, final int month) {


        setDateParams(year, month);

        mDateListView.post(new Runnable() {
            @Override
            public void run() {

                View dateView = getMiddleView(mDateListView, mMiddlePositionFromTop);
                int date = Integer.parseInt(((TextView) dateView.findViewById(R.id.row_number)).getText().toString());
                int dateOnDummyView = Integer.parseInt(((TextView) mDummyView.findViewById(R.id.row_number)).getText().toString());

                if (dateOnDummyView > date)
                    mDelta = dateOnDummyView - date;
                else
                    mDelta = -1 * (date - dateOnDummyView);

                mDateListView.post(new Runnable() {
                    @Override
                    public void run() {

                        int position = (mDelta + mMiddlePositionFromTop) + mDateListView.getFirstVisiblePosition();

                        mDateAdapter.setCurrentPos(position);
                        mDateAdapter.notifyDataSetChanged();
                        mDateListView.setSelectionFromTop(position, mRootLayoutHeight / 3);


                    }
                });

            }
        });


    }

    protected int getNumberOfDaysInMonth(int month, int year) {

        Calendar cal = new GregorianCalendar();
        cal.set(year, month - 1, 1);
        int numberOfDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        return numberOfDays;


    }

    protected View getMiddleView(ListView listView, int position) {

        return listView.getChildAt(position);

    }

    private void disableOtherListViews(ListView listView) {

        if (listView.getId() == R.id.month_listview) {
            mDateListView.setEnabled(false);
            mYearListView.setEnabled(false);

        } else if (listView.getId() == R.id.year_listview) {
            mDateListView.setEnabled(false);
            mMonthListview.setEnabled(false);
        } else if (listView.getId() == R.id.date_listview) {
            mMonthListview.setEnabled(false);
            mYearListView.setEnabled(false);
        }

    }

    private void enableAllListViews() {

        mMonthListview.setEnabled(true);
        mDateListView.setEnabled(true);
        mYearListView.setEnabled(true);
    }

    private ListView getScrollingListView(ScrollState state1, ScrollState state2, ListView view1, ListView view2) {

        ListView listView = (state1.getScrollState() == OnScrollListener.SCROLL_STATE_FLING) ? view1 : (state2.getScrollState() == OnScrollListener.SCROLL_STATE_FLING) ? view2 : null;

        return listView;

    }

    private void stopOtherScrolls(ListView listView) {

        if (listView.getId() == R.id.month_listview) {
            listView = getScrollingListView(mScrollStateOfDayView, mScrollStateOfYearView, mDateListView, mYearListView);
        } else if (listView.getId() == R.id.date_listview) {
            listView = getScrollingListView(mScrollStateOfMonthView, mScrollStateOfYearView, mMonthListview, mYearListView);
        } else if (listView.getId() == R.id.year_listview) {
            listView = getScrollingListView(mScrollStateOfMonthView, mScrollStateOfDayView, mMonthListview, mDateListView);
        }

        if (listView != null) {

            try {
                Field field = AbsListView.class.getDeclaredField("mFlingRunnable");
                field.setAccessible(true);
                Object flingRunnable = field.get(listView);
                if (flingRunnable != null) {
                    Method method = Class.forName("android.widget.AbsListView$FlingRunnable").getDeclaredMethod("endFling");
                    method.setAccessible(true);
                    method.invoke(flingRunnable);
                }
            } catch (Exception e) {
            }

        }

    }

    void putThisViewInMiddle(float y, final ListView listView, MonthYearAdapter adapter) {

        double yValue = Math.ceil((double) y);
        for (int i = 0; i <= listView.getLastVisiblePosition() - listView.getFirstVisiblePosition(); i++) {

            View v = listView.getChildAt(i);
            if (v != null) {

                if (yValue >= v.getTop() - listView.getDividerHeight() && yValue <= v.getBottom()) {

                    if (i == mMiddlePositionFromTop)
                        return;
                    scrollToMiddle(listView, i, v, adapter);
                    break;
                }
            }
        }

    }


    void scrollToMiddle(final ListView listView, final int i, final View v, final MonthYearAdapter adapter) {

        listView.smoothScrollBy(v.getTop() - mRootLayoutHeight / 3, 1000);
        adapter.setCurrentPos(listView.getFirstVisiblePosition() + i);
        adapter.notifyDataSetChanged();

    }

    class ScrollState {

        private int mScrollStateOfListView;

        public void setScrollState(int scrollState) {

            this.mScrollStateOfListView = scrollState;
        }

        public int getScrollState() {

            return this.mScrollStateOfListView;
        }


    }

    class ListViewVisible {

        private boolean mCompleteListViewVisible;

        public void setCompleteListViewVisible(boolean value) {

            this.mCompleteListViewVisible = value;
        }

        public boolean isCompleteListViewVisible() {
            return this.mCompleteListViewVisible;
        }
    }


    private void setOtherListViewsInvisible(ListView listView) {

        if (listView.getId() == R.id.month_listview) {

            mYearViewVisible.setCompleteListViewVisible(false);
            mDateViewVisible.setCompleteListViewVisible(false);

            if (mYearAdapter.getAllItemsVisible()) {
                makeAllItemsInvisible(mYearAdapter);
            }
            if (mDateAdapter.getAllItemsVisible()) {
                makeAllItemsInvisible(mDateAdapter);
            }
        } else if (listView.getId() == R.id.year_listview) {

            mDateViewVisible.setCompleteListViewVisible(false);
            mMonthViewVisible.setCompleteListViewVisible(false);

            if (mMonthAdapter.getAllItemsVisible()) {
                makeAllItemsInvisible(mMonthAdapter);
            }
            if (mDateAdapter.getAllItemsVisible()) {
                makeAllItemsInvisible(mDateAdapter);
            }
        } else if (listView.getId() == R.id.date_listview) {

            mMonthViewVisible.setCompleteListViewVisible(false);
            mYearViewVisible.setCompleteListViewVisible(false);

            if (mMonthAdapter.getAllItemsVisible()) {
                makeAllItemsInvisible(mMonthAdapter);
            }
            if (mYearAdapter.getAllItemsVisible()) {
                makeAllItemsInvisible(mYearAdapter);
            }
        }

    }


    private void makeAllItemsInvisible(MonthYearAdapter adapter) {

        adapter.setAllItemsVisible(false);
        adapter.notifyDataSetChanged();
    }


    private synchronized void putSomeRowInMiddle(ListView listView, MonthYearAdapter adapter) {


        for (int i = 0; i <= listView.getLastVisiblePosition() - listView.getFirstVisiblePosition(); i++) {
            final View v = listView.getChildAt(i);
            if (v != null) {

                if (mMiddlePositionInScreen == 0) {
                    mMiddlePositionInScreen = mRootLayoutHeight / 3 + v.getHeight() / 2;
                    mBottomPositionOfMiddleElement = mRootLayoutHeight / 3 + v.getHeight();
                }

                //if(listView.getId()==R.id.year_listview)
                //Log.d("A",""+i+" "+v.getTop()+ " "+mRootLayoutHeight/3+ " "+mMiddlePositionInScreen);
                if ((v.getTop() >= mRootLayoutHeight / 3 - listView.getDividerHeight() / 2) && v.getTop() < mMiddlePositionInScreen) {
                    scrollUp(i, listView, v, v.getTop(), listView, adapter, listView.getFirstVisiblePosition() + i);
                    break;
                }

                //if(listView.getId()==R.id.year_listview)
                //Log.d("B",""+i+" "+v.getBottom()+ " "+mMiddlePositionInScreen+ " "+mBottomPositionOfMiddleElement);
                if ((v.getBottom() >= mMiddlePositionInScreen) && v.getBottom() <= mBottomPositionOfMiddleElement + listView.getDividerHeight() / 2) {

                    //if(listView.getId()==R.id.year_listview)
                    //   Log.d("rahulrajayes",""+i+" "+v.getBottom()+" "+mMiddlePositionInScreen+" "+mBottomPositionOfMiddleElement);
                    scrollDown(i, listView, v, v.getBottom(), v.getHeight(), listView, adapter, listView.getFirstVisiblePosition() + i);
                    break;
                }

                //if(listView.getId()==R.id.year_listview)
                //Log.d("C",""+v.getBottom()+ " "+mMiddlePositionInScreen+ " "+mRootLayoutHeight/3+ " "+(v.getBottom()+listView.getDividerHeight()/2));
                if (v.getBottom() <= mMiddlePositionInScreen && v.getBottom() > mRootLayoutHeight / 3) {

                    if (v.getBottom() + listView.getDividerHeight() / 2 >= mMiddlePositionInScreen) {
                        //  if(listView.getId()==R.id.year_listview)
                        // Log.d("rahulraja",""+i+" "+v.getBottom()+" "+mMiddlePositionInScreen+" "+mRootLayoutHeight/3);
                        scrollDown(i, listView, v, v.getBottom(), v.getHeight(), listView, adapter, listView.getFirstVisiblePosition() + i);
                        break;
                    }

                }

                //if(listView.getId()==R.id.year_listview)
                //Log.d("D",""+i+" "+v.getTop()+ " "+mMiddlePositionInScreen+ " "+mBottomPositionOfMiddleElement+" "+(v.getTop()-listView.getDividerHeight()/2));

                if (v.getTop() >= mMiddlePositionInScreen && v.getTop() < mBottomPositionOfMiddleElement) {

                    if (v.getTop() - listView.getDividerHeight() / 2 <= mMiddlePositionInScreen) {
                        scrollUp(i, listView, v, v.getTop(), listView, adapter, listView.getFirstVisiblePosition() + i);
                        break;
                    }

                }


            }

        }

    }

    private void scrollDown(final int i, final ListView listview, final View view, final int viewBottom, final int viewHeight, final ListView listView, final MonthYearAdapter adapter, final int currentPosInMiddle) {

        listView.post(new Runnable() {
            @Override
            public void run() {

                highLightMiddleRow(listview, view, adapter, currentPosInMiddle);
                listView.smoothScrollBy(viewBottom - (mRootLayoutHeight / 3 + viewHeight), 1000);
                putDayOnDummyView(listview, i);


            }
        });

    }


    private void scrollUp(final int i, final ListView listview, final View view, final int viewTop, final ListView listView, final MonthYearAdapter adapter, final int currentPosInMiddle) {

        listView.post(new Runnable() {
            @Override
            public void run() {

                highLightMiddleRow(listView, view, adapter, currentPosInMiddle);
                listView.smoothScrollBy(viewTop - mRootLayoutHeight / 3, 1000);
                putDayOnDummyView(listview, i);


            }
        });

    }

    private void highLightMiddleRow(ListView listview, View view, MonthYearAdapter adapter, int currentPosInMiddle) {

        adapter.setCurrentPos(currentPosInMiddle);
        adapter.notifyDataSetChanged();
        if (listview.getId() == R.id.date_listview)
            showDummyView(view);


    }

    private void showDummyView(final View view) {

        mStatusChecker = new Runnable() {
            @Override
            public void run() {

                if (view.getTop() != mRootLayoutHeight / 3) {
                    mHandler.postDelayed(mStatusChecker, 200);
                } else {
                    mDummyView.setVisibility(View.VISIBLE);
                    mDummyTouched = true;
                    putDataOnDummyView();
                    mHandler.removeCallbacks(mStatusChecker);
                }
            }
        };
        mStatusChecker.run();

    }

    private void putDataOnDummyView() {

        View dateView = getMiddleView(mDateListView, mMiddlePositionFromTop);
        if (dateView != null) {
            String date = ((TextView) dateView.findViewById(R.id.row_number)).getText().toString();
            String day = ((TextView) dateView.findViewById(R.id.row_text)).getText().toString();

            ((TextView) mDummyView.findViewById(R.id.row_number)).setText(date);
            ((TextView) mDummyView.findViewById(R.id.row_text)).setText(day);
        }


    }

    private void putDayOnDummyView(ListView listView, int position) {

        View monthView = getMiddleView(mMonthListview, mMiddlePositionFromTop);
        View yearView = getMiddleView(mYearListView, mMiddlePositionFromTop);


        if (listView.getId() == R.id.month_listview) {

            monthView = getMiddleView(mMonthListview, position);
            yearView = getMiddleView(mYearListView, mMiddlePositionFromTop);

        } else if (listView.getId() == R.id.year_listview) {

            yearView = getMiddleView(mYearListView, position);
            monthView = getMiddleView(mMonthListview, mMiddlePositionFromTop);

        }


        int month = Integer.parseInt(((TextView) monthView.findViewById(R.id.row_number)).getText().toString());
        int year = Integer.parseInt(((TextView) yearView.findViewById(R.id.row_number)).getText().toString());
        int date = Integer.parseInt(((TextView) mDummyView.findViewById(R.id.row_number)).getText().toString());


        Calendar cal = new GregorianCalendar(year, month - 1, date);
        String dayOfWeek = new SimpleDateFormat("EEEE").format(cal.getTime());
        ((TextView) mDummyView.findViewById(R.id.row_text)).setText(dayOfWeek);

    }


    @Override
    public void onDetach() {

        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class
                    .getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }
}
