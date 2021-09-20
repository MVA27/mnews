package com.android.mnews.constants;

public interface Errors {

    public static final String ERROR_KEY = "ERROR_KEY";

    public static final String NO_INTERNET_CONNECTION = "001";
    public static final String ACCESS_DENIED= "002";

    public static final String ERROR_IN_THREAD_PARSER = "100"; //indicates error in :- administrator.ThreadParseHTML
    public static final String ERROR_IN_THREAD_DATA_LOADER = "101"; //indicates error in :- threads.ThreadDataLoader

    public static final String ERROR_IN_ACTIVITY_MAIN = "200"; //indicates error in :- MainActivity
    public static final String ERROR_IN_ACTIVITY_DISPLAY = "201"; //indicates error in :- ActivityDisplay

    public static final String ERROR_IN_ADAPTER_DISPLAY = "300"; //indicates error in :- adapters.AdapterDisplay

}
