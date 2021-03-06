/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.com.cs.common.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.springframework.util.StringUtils;

/**
 * <p>A suite of utilities surrounding the use of the
 * {@link java.util.Calendar} and {@link java.util.Date} object.</p>
 * 
 * <p>DateUtils contains a lot of common methods considering manipulations
 * of Dates or Calendars. Some methods require some extra explanation.
 * The truncate, ceiling and round methods could be considered the Math.floor(),
 * Math.ceil() or Math.round versions for dates
 * This way date-fields will be ignored in bottom-up order.
 * As a complement to these methods we've introduced some fragment-methods.
 * With these methods the Date-fields will be ignored in top-down order.
 * Since a date without a year is not a valid date, you have to decide in what
 * kind of date-field you want your result, for instance milliseconds or days.
 * </p>
 *
 * @since 2.0
 * @version $Id: DateUtils.java 1144992 2011-07-11 00:49:04Z ggregory $
 */
public class DateUtils {

    /**
     * Number of milliseconds in a standard second.
     * @since 2.1
     */
    public static final long MILLIS_PER_SECOND = 1000;
    /**
     * Number of milliseconds in a standard minute.
     * @since 2.1
     */
    public static final long MILLIS_PER_MINUTE = 60 * MILLIS_PER_SECOND;
    /**
     * Number of milliseconds in a standard hour.
     * @since 2.1
     */
    public static final long MILLIS_PER_HOUR = 60 * MILLIS_PER_MINUTE;
    /**
     * Number of milliseconds in a standard day.
     * @since 2.1
     */
    public static final long MILLIS_PER_DAY = 24 * MILLIS_PER_HOUR;

    /**
     * This is half a month, so this represents whether a date is in the top
     * or bottom half of the month.
     */
    public static final int SEMI_MONTH = 1001;

    private static final int[][] fields = {
            {Calendar.MILLISECOND},
            {Calendar.SECOND},
            {Calendar.MINUTE},
            {Calendar.HOUR_OF_DAY, Calendar.HOUR},
            {Calendar.DATE, Calendar.DAY_OF_MONTH, Calendar.AM_PM 
                /* Calendar.DAY_OF_YEAR, Calendar.DAY_OF_WEEK, Calendar.DAY_OF_WEEK_IN_MONTH */
            },
            {Calendar.MONTH, DateUtils.SEMI_MONTH},
            {Calendar.YEAR},
            {Calendar.ERA}};

    /**
     * A week range, starting on Sunday.
     */
    public static final int RANGE_WEEK_SUNDAY = 1;
    /**
     * A week range, starting on Monday.
     */
    public static final int RANGE_WEEK_MONDAY = 2;
    /**
     * A week range, starting on the day focused.
     */
    public static final int RANGE_WEEK_RELATIVE = 3;
    /**
     * A week range, centered around the day focused.
     */
    public static final int RANGE_WEEK_CENTER = 4;
    /**
     * A month range, the week starting on Sunday.
     */
    public static final int RANGE_MONTH_SUNDAY = 5;
    /**
     * A month range, the week starting on Monday.
     */
    public static final int RANGE_MONTH_MONDAY = 6;

    /**
     * Constant marker for truncating.
     * @since 3.0
     */
    private static final int MODIFY_TRUNCATE = 0;
    /**
     * Constant marker for rounding.
     * @since 3.0
     */
    private static final int MODIFY_ROUND = 1;
    /**
     * Constant marker for ceiling.
     * @since 3.0
     */
    private static final int MODIFY_CEILING = 2;

    /**
     * <p>{@code DateUtils} instances should NOT be constructed in
     * standard programming. Instead, the static methods on the class should
     * be used, such as {@code DateUtils.parseDate(str);}.</p>
     *
     * <p>This constructor is public to permit tools that require a JavaBean
     * instance to operate.</p>
     */
    public DateUtils() {
        super();
    }

    //-----------------------------------------------------------------------
    /**
     * <p>Checks if two date objects are on the same day ignoring time.</p>
     *
     * <p>28 Mar 2002 13:45 and 28 Mar 2002 06:01 would return true.
     * 28 Mar 2002 13:45 and 12 Mar 2002 13:45 would return false.
     * </p>
     * 
     * @param date1  the first date, not altered, not null
     * @param date2  the second date, not altered, not null
     * @return true if they represent the same day
     * @throws IllegalArgumentException if either date is <code>null</code>
     * @since 2.1
     */
    public static boolean isSameDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return isSameDay(cal1, cal2);
    }

    /**
     * <p>Checks if two calendar objects are on the same day ignoring time.</p>
     *
     * <p>28 Mar 2002 13:45 and 28 Mar 2002 06:01 would return true.
     * 28 Mar 2002 13:45 and 12 Mar 2002 13:45 would return false.
     * </p>
     * 
     * @param cal1  the first calendar, not altered, not null
     * @param cal2  the second calendar, not altered, not null
     * @return true if they represent the same day
     * @throws IllegalArgumentException if either calendar is <code>null</code>
     * @since 2.1
     */
    public static boolean isSameDay(Calendar cal1, Calendar cal2) {
        if (cal1 == null || cal2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));
    }

    //-----------------------------------------------------------------------
    /**
     * <p>Checks if two date objects represent the same instant in time.</p>
     *
     * <p>This method compares the long millisecond time of the two objects.</p>
     * 
     * @param date1  the first date, not altered, not null
     * @param date2  the second date, not altered, not null
     * @return true if they represent the same millisecond instant
     * @throws IllegalArgumentException if either date is <code>null</code>
     * @since 2.1
     */
    public static boolean isSameInstant(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        return date1.getTime() == date2.getTime();
    }

    /**
     * <p>Checks if two calendar objects represent the same instant in time.</p>
     *
     * <p>This method compares the long millisecond time of the two objects.</p>
     * 
     * @param cal1  the first calendar, not altered, not null
     * @param cal2  the second calendar, not altered, not null
     * @return true if they represent the same millisecond instant
     * @throws IllegalArgumentException if either date is <code>null</code>
     * @since 2.1
     */
    public static boolean isSameInstant(Calendar cal1, Calendar cal2) {
        if (cal1 == null || cal2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        return cal1.getTime().getTime() == cal2.getTime().getTime();
    }

    //-----------------------------------------------------------------------
    /**
     * <p>Checks if two calendar objects represent the same local time.</p>
     *
     * <p>This method compares the values of the fields of the two objects.
     * In addition, both calendars must be the same of the same type.</p>
     * 
     * @param cal1  the first calendar, not altered, not null
     * @param cal2  the second calendar, not altered, not null
     * @return true if they represent the same millisecond instant
     * @throws IllegalArgumentException if either date is <code>null</code>
     * @since 2.1
     */
    public static boolean isSameLocalTime(Calendar cal1, Calendar cal2) {
        if (cal1 == null || cal2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        return (cal1.get(Calendar.MILLISECOND) == cal2.get(Calendar.MILLISECOND) &&
                cal1.get(Calendar.SECOND) == cal2.get(Calendar.SECOND) &&
                cal1.get(Calendar.MINUTE) == cal2.get(Calendar.MINUTE) &&
                cal1.get(Calendar.HOUR_OF_DAY) == cal2.get(Calendar.HOUR_OF_DAY) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
                cal1.getClass() == cal2.getClass());
    }

    //-----------------------------------------------------------------------
    /**
     * <p>Parses a string representing a date by trying a variety of different parsers.</p>
     * 
     * <p>The parse will try each parse pattern in turn.
     * A parse is only deemed successful if it parses the whole of the input string.
     * If no parse patterns match, a ParseException is thrown.</p>
     * The parser will be lenient toward the parsed date.
     * 
     * @param str  the date to parse, not null
     * @param parsePatterns  the date format patterns to use, see SimpleDateFormat, not null
     * @return the parsed date
     * @throws IllegalArgumentException if the date string or pattern array is null
     * @throws ParseException if none of the date patterns were suitable (or there were none)
     */
    public static Date parseDate(String str, String... parsePatterns) throws ParseException {
        return parseDateWithLeniency(str, parsePatterns, true);
    }
    
  //-----------------------------------------------------------------------
    /**
     * <p>Parses a string representing a date by trying a variety of different parsers.</p>
     * 
     * <p>The parse will try each parse pattern in turn.
     * A parse is only deemed successful if it parses the whole of the input string.
     * If no parse patterns match, a ParseException is thrown.</p>
     * The parser parses strictly - it does not allow for dates such as "February 942, 1996". 
     * 
     * @param str  the date to parse, not null
     * @param parsePatterns  the date format patterns to use, see SimpleDateFormat, not null
     * @return the parsed date
     * @throws IllegalArgumentException if the date string or pattern array is null
     * @throws ParseException if none of the date patterns were suitable
     * @since 2.5
     */
    public static Date parseDateStrictly(String str, String... parsePatterns) throws ParseException {
        return parseDateWithLeniency(str, parsePatterns, false);
    }

    /**
     * <p>Parses a string representing a date by trying a variety of different parsers.</p>
     * 
     * <p>The parse will try each parse pattern in turn.
     * A parse is only deemed successful if it parses the whole of the input string.
     * If no parse patterns match, a ParseException is thrown.</p>
     * 
     * @param str  the date to parse, not null
     * @param parsePatterns  the date format patterns to use, see SimpleDateFormat, not null
     * @param lenient Specify whether or not date/time parsing is to be lenient.
     * @return the parsed date
     * @throws IllegalArgumentException if the date string or pattern array is null
     * @throws ParseException if none of the date patterns were suitable
     * @see java.util.Calender#isLenient()
     */
    private static Date parseDateWithLeniency(
            String str, String[] parsePatterns, boolean lenient) throws ParseException {
        if (str == null || parsePatterns == null) {
            throw new IllegalArgumentException("Date and Patterns must not be null");
        }
        
        SimpleDateFormat parser = new SimpleDateFormat();
        parser.setLenient(lenient);
        ParsePosition pos = new ParsePosition(0);
        for (String parsePattern : parsePatterns) {

            String pattern = parsePattern;

            // LANG-530 - need to make sure 'ZZ' output doesn't get passed to SimpleDateFormat
            if (parsePattern.endsWith("ZZ")) {
                pattern = pattern.substring(0, pattern.length() - 1);
            }
            
            parser.applyPattern(pattern);
            pos.setIndex(0);

            String str2 = str;
            // LANG-530 - need to make sure 'ZZ' output doesn't hit SimpleDateFormat as it will ParseException
            if (parsePattern.endsWith("ZZ")) {
                str2 = str.replaceAll("([-+][0-9][0-9]):([0-9][0-9])$", "$1$2"); 
            }

            Date date = parser.parse(str2, pos);
            if (date != null && pos.getIndex() == str2.length()) {
                return date;
            }
        }
        throw new ParseException("Unable to parse the date: " + str, -1);
    }

    //-----------------------------------------------------------------------
    /**
     * Adds a number of years to a date returning a new object.
     * The original {@code Date} is unchanged.
     *
     * @param date  the date, not null
     * @param amount  the amount to add, may be negative
     * @return the new {@code Date} with the amount added
     * @throws IllegalArgumentException if the date is null
     */
    public static Date addYears(Date date, int amount) {
        return add(date, Calendar.YEAR, amount);
    }

    //-----------------------------------------------------------------------
    /**
     * Adds a number of months to a date returning a new object.
     * The original {@code Date} is unchanged.
     *
     * @param date  the date, not null
     * @param amount  the amount to add, may be negative
     * @return the new {@code Date} with the amount added
     * @throws IllegalArgumentException if the date is null
     */
    public static Date addMonths(Date date, int amount) {
        return add(date, Calendar.MONTH, amount);
    }

    //-----------------------------------------------------------------------
    /**
     * Adds a number of weeks to a date returning a new object.
     * The original {@code Date} is unchanged.
     *
     * @param date  the date, not null
     * @param amount  the amount to add, may be negative
     * @return the new {@code Date} with the amount added
     * @throws IllegalArgumentException if the date is null
     */
    public static Date addWeeks(Date date, int amount) {
        return add(date, Calendar.WEEK_OF_YEAR, amount);
    }

    //-----------------------------------------------------------------------
    /**
     * Adds a number of days to a date returning a new object.
     * The original {@code Date} is unchanged.
     *
     * @param date  the date, not null
     * @param amount  the amount to add, may be negative
     * @return the new {@code Date} with the amount added
     * @throws IllegalArgumentException if the date is null
     */
    public static Date addDays(Date date, int amount) {
        return add(date, Calendar.DAY_OF_MONTH, amount);
    }

    //-----------------------------------------------------------------------
    /**
     * Adds a number of hours to a date returning a new object.
     * The original {@code Date} is unchanged.
     *
     * @param date  the date, not null
     * @param amount  the amount to add, may be negative
     * @return the new {@code Date} with the amount added
     * @throws IllegalArgumentException if the date is null
     */
    public static Date addHours(Date date, int amount) {
        return add(date, Calendar.HOUR_OF_DAY, amount);
    }

    //-----------------------------------------------------------------------
    /**
     * Adds a number of minutes to a date returning a new object.
     * The original {@code Date} is unchanged.
     *
     * @param date  the date, not null
     * @param amount  the amount to add, may be negative
     * @return the new {@code Date} with the amount added
     * @throws IllegalArgumentException if the date is null
     */
    public static Date addMinutes(Date date, int amount) {
        return add(date, Calendar.MINUTE, amount);
    }

    //-----------------------------------------------------------------------
    /**
     * Adds a number of seconds to a date returning a new object.
     * The original {@code Date} is unchanged.
     *
     * @param date  the date, not null
     * @param amount  the amount to add, may be negative
     * @return the new {@code Date} with the amount added
     * @throws IllegalArgumentException if the date is null
     */
    public static Date addSeconds(Date date, int amount) {
        return add(date, Calendar.SECOND, amount);
    }

    //-----------------------------------------------------------------------
    /**
     * Adds a number of milliseconds to a date returning a new object.
     * The original {@code Date} is unchanged.
     *
     * @param date  the date, not null
     * @param amount  the amount to add, may be negative
     * @return the new {@code Date} with the amount added
     * @throws IllegalArgumentException if the date is null
     */
    public static Date addMilliseconds(Date date, int amount) {
        return add(date, Calendar.MILLISECOND, amount);
    }

    //-----------------------------------------------------------------------
    /**
     * Adds to a date returning a new object.
     * The original {@code Date} is unchanged.
     *
     * @param date  the date, not null
     * @param calendarField  the calendar field to add to
     * @param amount  the amount to add, may be negative
     * @return the new {@code Date} with the amount added
     * @throws IllegalArgumentException if the date is null
     */
    private static Date add(Date date, int calendarField, int amount) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(calendarField, amount);
        return c.getTime();
    }
    
    //-----------------------------------------------------------------------
    /**
     * Sets the years field to a date returning a new object.
     * The original {@code Date} is unchanged.
     *
     * @param date  the date, not null
     * @param amount the amount to set
     * @return a new {@code Date} set with the specified value
     * @throws IllegalArgumentException if the date is null
     * @since 2.4
     */
    public static Date setYears(Date date, int amount) {
        return set(date, Calendar.YEAR, amount);
    }

    //-----------------------------------------------------------------------
    /**
     * Sets the months field to a date returning a new object.
     * The original {@code Date} is unchanged.
     *
     * @param date  the date, not null
     * @param amount the amount to set
     * @return a new {@code Date} set with the specified value
     * @throws IllegalArgumentException if the date is null
     * @since 2.4
     */
    public static Date setMonths(Date date, int amount) {
        return set(date, Calendar.MONTH, amount);
    }

    //-----------------------------------------------------------------------
    /**
     * Sets the day of month field to a date returning a new object.
     * The original {@code Date} is unchanged.
     *
     * @param date  the date, not null
     * @param amount the amount to set
     * @return a new {@code Date} set with the specified value
     * @throws IllegalArgumentException if the date is null
     * @since 2.4
     */
    public static Date setDays(Date date, int amount) {
        return set(date, Calendar.DAY_OF_MONTH, amount);
    }

    //-----------------------------------------------------------------------
    /**
     * Sets the hours field to a date returning a new object.  Hours range 
     * from  0-23.
     * The original {@code Date} is unchanged.
     *
     * @param date  the date, not null
     * @param amount the amount to set
     * @return a new {@code Date} set with the specified value
     * @throws IllegalArgumentException if the date is null
     * @since 2.4
     */
    public static Date setHours(Date date, int amount) {
        return set(date, Calendar.HOUR_OF_DAY, amount);
    }

    //-----------------------------------------------------------------------
    /**
     * Sets the minute field to a date returning a new object.
     * The original {@code Date} is unchanged.
     *
     * @param date  the date, not null
     * @param amount the amount to set
     * @return a new {@code Date} set with the specified value
     * @throws IllegalArgumentException if the date is null
     * @since 2.4
     */
    public static Date setMinutes(Date date, int amount) {
        return set(date, Calendar.MINUTE, amount);
    }
    
    //-----------------------------------------------------------------------
    /**
     * Sets the seconds field to a date returning a new object.
     * The original {@code Date} is unchanged.
     *
     * @param date  the date, not null
     * @param amount the amount to set
     * @return a new {@code Date} set with the specified value
     * @throws IllegalArgumentException if the date is null
     * @since 2.4
     */
    public static Date setSeconds(Date date, int amount) {
        return set(date, Calendar.SECOND, amount);
    }

    //-----------------------------------------------------------------------
    /**
     * Sets the miliseconds field to a date returning a new object.
     * The original {@code Date} is unchanged.
     *
     * @param date  the date, not null
     * @param amount the amount to set
     * @return a new {@code Date} set with the specified value
     * @throws IllegalArgumentException if the date is null
     * @since 2.4
     */
    public static Date setMilliseconds(Date date, int amount) {
        return set(date, Calendar.MILLISECOND, amount);
    } 
    
    //-----------------------------------------------------------------------
    /**
     * Sets the specified field to a date returning a new object.  
     * This does not use a lenient calendar.
     * The original {@code Date} is unchanged.
     *
     * @param date  the date, not null
     * @param calendarField  the {@code Calendar} field to set the amount to
     * @param amount the amount to set
     * @return a new {@code Date} set with the specified value
     * @throws IllegalArgumentException if the date is null
     * @since 2.4
     */
    private static Date set(Date date, int calendarField, int amount) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        // getInstance() returns a new object, so this method is thread safe.
        Calendar c = Calendar.getInstance();
        c.setLenient(false);
        c.setTime(date);
        c.set(calendarField, amount);
        return c.getTime();
    }   

    //-----------------------------------------------------------------------
    /**
     * Convert a {@code Date} into a {@code Calendar}. 
     * 
     * @param date the date to convert to a Calendar
     * @return the created Calendar
     * @throws NullPointerException if null is passed in
     * @since 3.0
     */
    public static Calendar toCalendar(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c;
    }
    
    //-----------------------------------------------------------------------
    /**
     * <p>Round this date, leaving the field specified as the most
     * significant field.</p>
     *
     * <p>For example, if you had the datetime of 28 Mar 2002
     * 13:45:01.231, if this was passed with HOUR, it would return
     * 28 Mar 2002 14:00:00.000. If this was passed with MONTH, it
     * would return 1 April 2002 0:00:00.000.</p>
     * 
     * <p>For a date in a timezone that handles the change to daylight
     * saving time, rounding to Calendar.HOUR_OF_DAY will behave as follows.
     * Suppose daylight saving time begins at 02:00 on March 30. Rounding a 
     * date that crosses this time would produce the following values:
     * <ul>
     * <li>March 30, 2003 01:10 rounds to March 30, 2003 01:00</li>
     * <li>March 30, 2003 01:40 rounds to March 30, 2003 03:00</li>
     * <li>March 30, 2003 02:10 rounds to March 30, 2003 03:00</li>
     * <li>March 30, 2003 02:40 rounds to March 30, 2003 04:00</li>
     * </ul>
     * </p>
     * 
     * @param date  the date to work with, not null
     * @param field  the field from {@code Calendar} or {@code SEMI_MONTH}
     * @return the different rounded date, not null
     * @throws ArithmeticException if the year is over 280 million
     */
    public static Date round(Date date, int field) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar gval = Calendar.getInstance();
        gval.setTime(date);
        modify(gval, field, MODIFY_ROUND);
        return gval.getTime();
    }

    /**
     * <p>Round this date, leaving the field specified as the most
     * significant field.</p>
     *
     * <p>For example, if you had the datetime of 28 Mar 2002
     * 13:45:01.231, if this was passed with HOUR, it would return
     * 28 Mar 2002 14:00:00.000. If this was passed with MONTH, it
     * would return 1 April 2002 0:00:00.000.</p>
     * 
     * <p>For a date in a timezone that handles the change to daylight
     * saving time, rounding to Calendar.HOUR_OF_DAY will behave as follows.
     * Suppose daylight saving time begins at 02:00 on March 30. Rounding a 
     * date that crosses this time would produce the following values:
     * <ul>
     * <li>March 30, 2003 01:10 rounds to March 30, 2003 01:00</li>
     * <li>March 30, 2003 01:40 rounds to March 30, 2003 03:00</li>
     * <li>March 30, 2003 02:10 rounds to March 30, 2003 03:00</li>
     * <li>March 30, 2003 02:40 rounds to March 30, 2003 04:00</li>
     * </ul>
     * </p>
     * 
     * @param date  the date to work with, not null
     * @param field  the field from {@code Calendar} or <code>SEMI_MONTH</code>
     * @return the different rounded date, not null
     * @throws IllegalArgumentException if the date is <code>null</code>
     * @throws ArithmeticException if the year is over 280 million
     */
    public static Calendar round(Calendar date, int field) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar rounded = (Calendar) date.clone();
        modify(rounded, field, MODIFY_ROUND);
        return rounded;
    }

    /**
     * <p>Round this date, leaving the field specified as the most
     * significant field.</p>
     *
     * <p>For example, if you had the datetime of 28 Mar 2002
     * 13:45:01.231, if this was passed with HOUR, it would return
     * 28 Mar 2002 14:00:00.000. If this was passed with MONTH, it
     * would return 1 April 2002 0:00:00.000.</p>
     * 
     * <p>For a date in a timezone that handles the change to daylight
     * saving time, rounding to Calendar.HOUR_OF_DAY will behave as follows.
     * Suppose daylight saving time begins at 02:00 on March 30. Rounding a 
     * date that crosses this time would produce the following values:
     * <ul>
     * <li>March 30, 2003 01:10 rounds to March 30, 2003 01:00</li>
     * <li>March 30, 2003 01:40 rounds to March 30, 2003 03:00</li>
     * <li>March 30, 2003 02:10 rounds to March 30, 2003 03:00</li>
     * <li>March 30, 2003 02:40 rounds to March 30, 2003 04:00</li>
     * </ul>
     * </p>
     * 
     * @param date  the date to work with, either {@code Date} or {@code Calendar}, not null
     * @param field  the field from {@code Calendar} or <code>SEMI_MONTH</code>
     * @return the different rounded date, not null
     * @throws IllegalArgumentException if the date is <code>null</code>
     * @throws ClassCastException if the object type is not a {@code Date} or {@code Calendar}
     * @throws ArithmeticException if the year is over 280 million
     */
    public static Date round(Object date, int field) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        if (date instanceof Date) {
            return round((Date) date, field);
        } else if (date instanceof Calendar) {
            return round((Calendar) date, field).getTime();
        } else {
            throw new ClassCastException("Could not round " + date);
        }
    }

    //-----------------------------------------------------------------------
    /**
     * <p>Truncate this date, leaving the field specified as the most
     * significant field.</p>
     *
     * <p>For example, if you had the datetime of 28 Mar 2002
     * 13:45:01.231, if you passed with HOUR, it would return 28 Mar
     * 2002 13:00:00.000.  If this was passed with MONTH, it would
     * return 1 Mar 2002 0:00:00.000.</p>
     * 
     * @param date  the date to work with, not null
     * @param field  the field from {@code Calendar} or <code>SEMI_MONTH</code>
     * @return the different truncated date, not null
     * @throws IllegalArgumentException if the date is <code>null</code>
     * @throws ArithmeticException if the year is over 280 million
     */
    public static Date truncate(Date date, int field) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar gval = Calendar.getInstance();
        gval.setTime(date);
        modify(gval, field, MODIFY_TRUNCATE);
        return gval.getTime();
    }

    /**
     * <p>Truncate this date, leaving the field specified as the most
     * significant field.</p>
     *
     * <p>For example, if you had the datetime of 28 Mar 2002
     * 13:45:01.231, if you passed with HOUR, it would return 28 Mar
     * 2002 13:00:00.000.  If this was passed with MONTH, it would
     * return 1 Mar 2002 0:00:00.000.</p>
     * 
     * @param date  the date to work with, not null
     * @param field  the field from {@code Calendar} or <code>SEMI_MONTH</code>
     * @return the different truncated date, not null
     * @throws IllegalArgumentException if the date is <code>null</code>
     * @throws ArithmeticException if the year is over 280 million
     */
    public static Calendar truncate(Calendar date, int field) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar truncated = (Calendar) date.clone();
        modify(truncated, field, MODIFY_TRUNCATE);
        return truncated;
    }

    /**
     * <p>Truncate this date, leaving the field specified as the most
     * significant field.</p>
     *
     * <p>For example, if you had the datetime of 28 Mar 2002
     * 13:45:01.231, if you passed with HOUR, it would return 28 Mar
     * 2002 13:00:00.000.  If this was passed with MONTH, it would
     * return 1 Mar 2002 0:00:00.000.</p>
     * 
     * @param date  the date to work with, either {@code Date} or {@code Calendar}, not null
     * @param field  the field from {@code Calendar} or <code>SEMI_MONTH</code>
     * @return the different truncated date, not null
     * @throws IllegalArgumentException if the date is <code>null</code>
     * @throws ClassCastException if the object type is not a {@code Date} or {@code Calendar}
     * @throws ArithmeticException if the year is over 280 million
     */
    public static Date truncate(Object date, int field) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        if (date instanceof Date) {
            return truncate((Date) date, field);
        } else if (date instanceof Calendar) {
            return truncate((Calendar) date, field).getTime();
        } else {
            throw new ClassCastException("Could not truncate " + date);
        }
    }
    
  //-----------------------------------------------------------------------
    /**
     * <p>Ceil this date, leaving the field specified as the most
     * significant field.</p>
     *
     * <p>For example, if you had the datetime of 28 Mar 2002
     * 13:45:01.231, if you passed with HOUR, it would return 28 Mar
     * 2002 14:00:00.000.  If this was passed with MONTH, it would
     * return 1 Apr 2002 0:00:00.000.</p>
     * 
     * @param date  the date to work with, not null
     * @param field  the field from {@code Calendar} or <code>SEMI_MONTH</code>
     * @return the different ceil date, not null
     * @throws IllegalArgumentException if the date is <code>null</code>
     * @throws ArithmeticException if the year is over 280 million
     * @since 2.5
     */
    public static Date ceiling(Date date, int field) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar gval = Calendar.getInstance();
        gval.setTime(date);
        modify(gval, field, MODIFY_CEILING);
        return gval.getTime();
    }

    /**
     * <p>Ceil this date, leaving the field specified as the most
     * significant field.</p>
     *
     * <p>For example, if you had the datetime of 28 Mar 2002
     * 13:45:01.231, if you passed with HOUR, it would return 28 Mar
     * 2002 13:00:00.000.  If this was passed with MONTH, it would
     * return 1 Mar 2002 0:00:00.000.</p>
     * 
     * @param date  the date to work with, not null
     * @param field  the field from {@code Calendar} or <code>SEMI_MONTH</code>
     * @return the different ceil date, not null
     * @throws IllegalArgumentException if the date is <code>null</code>
     * @throws ArithmeticException if the year is over 280 million
     * @since 2.5
     */
    public static Calendar ceiling(Calendar date, int field) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar ceiled = (Calendar) date.clone();
        modify(ceiled, field, MODIFY_CEILING);
        return ceiled;
    }

    /**
     * <p>Ceil this date, leaving the field specified as the most
     * significant field.</p>
     *
     * <p>For example, if you had the datetime of 28 Mar 2002
     * 13:45:01.231, if you passed with HOUR, it would return 28 Mar
     * 2002 13:00:00.000.  If this was passed with MONTH, it would
     * return 1 Mar 2002 0:00:00.000.</p>
     * 
     * @param date  the date to work with, either {@code Date} or {@code Calendar}, not null
     * @param field  the field from {@code Calendar} or <code>SEMI_MONTH</code>
     * @return the different ceil date, not null
     * @throws IllegalArgumentException if the date is <code>null</code>
     * @throws ClassCastException if the object type is not a {@code Date} or {@code Calendar}
     * @throws ArithmeticException if the year is over 280 million
     * @since 2.5
     */
    public static Date ceiling(Object date, int field) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        if (date instanceof Date) {
            return ceiling((Date) date, field);
        } else if (date instanceof Calendar) {
            return ceiling((Calendar) date, field).getTime();
        } else {
            throw new ClassCastException("Could not find ceiling of for type: " + date.getClass());
        }
    }

    //-----------------------------------------------------------------------
    /**
     * <p>Internal calculation method.</p>
     * 
     * @param val  the calendar, not null
     * @param field  the field constant
     * @param modType  type to truncate, round or ceiling
     * @throws ArithmeticException if the year is over 280 million
     */
    private static void modify(Calendar val, int field, int modType) {
        if (val.get(Calendar.YEAR) > 280000000) {
            throw new ArithmeticException("Calendar value too large for accurate calculations");
        }
        
        if (field == Calendar.MILLISECOND) {
            return;
        }

        // ----------------- Fix for LANG-59 ---------------------- START ---------------
        // see http://issues.apache.org/jira/browse/LANG-59
        //
        // Manually truncate milliseconds, seconds and minutes, rather than using
        // Calendar methods.

        Date date = val.getTime();
        long time = date.getTime();
        boolean done = false;

        // truncate milliseconds
        int millisecs = val.get(Calendar.MILLISECOND);
        if (MODIFY_TRUNCATE == modType || millisecs < 500) {
            time = time - millisecs;
        }
        if (field == Calendar.SECOND) {
            done = true;
        }

        // truncate seconds
        int seconds = val.get(Calendar.SECOND);
        if (!done && (MODIFY_TRUNCATE == modType || seconds < 30)) {
            time = time - (seconds * 1000L);
        }
        if (field == Calendar.MINUTE) {
            done = true;
        }

        // truncate minutes
        int minutes = val.get(Calendar.MINUTE);
        if (!done && (MODIFY_TRUNCATE == modType || minutes < 30)) {
            time = time - (minutes * 60000L);
        }

        // reset time
        if (date.getTime() != time) {
            date.setTime(time);
            val.setTime(date);
        }
        // ----------------- Fix for LANG-59 ----------------------- END ----------------

        boolean roundUp = false;
        for (int[] aField : fields) {
            for (int element : aField) {
                if (element == field) {
                    //This is our field... we stop looping
                    if (modType == MODIFY_CEILING || (modType == MODIFY_ROUND && roundUp)) {
                        if (field == DateUtils.SEMI_MONTH) {
                            //This is a special case that's hard to generalize
                            //If the date is 1, we round up to 16, otherwise
                            //  we subtract 15 days and add 1 month
                            if (val.get(Calendar.DATE) == 1) {
                                val.add(Calendar.DATE, 15);
                            } else {
                                val.add(Calendar.DATE, -15);
                                val.add(Calendar.MONTH, 1);
                            }
// ----------------- Fix for LANG-440 ---------------------- START ---------------
                        } else if (field == Calendar.AM_PM) {
                            // This is a special case
                            // If the time is 0, we round up to 12, otherwise
                            //  we subtract 12 hours and add 1 day
                            if (val.get(Calendar.HOUR_OF_DAY) == 0) {
                                val.add(Calendar.HOUR_OF_DAY, 12);
                            } else {
                                val.add(Calendar.HOUR_OF_DAY, -12);
                                val.add(Calendar.DATE, 1);
                            }
// ----------------- Fix for LANG-440 ---------------------- END ---------------
                        } else {
                            //We need at add one to this field since the
                            //  last number causes us to round up
                            val.add(aField[0], 1);
                        }
                    }
                    return;
                }
            }
            //We have various fields that are not easy roundings
            int offset = 0;
            boolean offsetSet = false;
            //These are special types of fields that require different rounding rules
            switch (field) {
                case DateUtils.SEMI_MONTH:
                    if (aField[0] == Calendar.DATE) {
                        //If we're going to drop the DATE field's value,
                        //  we want to do this our own way.
                        //We need to subtrace 1 since the date has a minimum of 1
                        offset = val.get(Calendar.DATE) - 1;
                        //If we're above 15 days adjustment, that means we're in the
                        //  bottom half of the month and should stay accordingly.
                        if (offset >= 15) {
                            offset -= 15;
                        }
                        //Record whether we're in the top or bottom half of that range
                        roundUp = offset > 7;
                        offsetSet = true;
                    }
                    break;
                case Calendar.AM_PM:
                    if (aField[0] == Calendar.HOUR_OF_DAY) {
                        //If we're going to drop the HOUR field's value,
                        //  we want to do this our own way.
                        offset = val.get(Calendar.HOUR_OF_DAY);
                        if (offset >= 12) {
                            offset -= 12;
                        }
                        roundUp = offset >= 6;
                        offsetSet = true;
                    }
                    break;
            }
            if (!offsetSet) {
                int min = val.getActualMinimum(aField[0]);
                int max = val.getActualMaximum(aField[0]);
                //Calculate the offset from the minimum allowed value
                offset = val.get(aField[0]) - min;
                //Set roundUp if this is more than half way between the minimum and maximum
                roundUp = offset > ((max - min) / 2);
            }
            //We need to remove this field
            if (offset != 0) {
                val.set(aField[0], val.get(aField[0]) - offset);
            }
        }
        throw new IllegalArgumentException("The field " + field + " is not supported");

    }

    //-----------------------------------------------------------------------
    /**
     * <p>This constructs an <code>Iterator</code> over each day in a date
     * range defined by a focus date and range style.</p>
     *
     * <p>For instance, passing Thursday, July 4, 2002 and a
     * <code>RANGE_MONTH_SUNDAY</code> will return an <code>Iterator</code>
     * that starts with Sunday, June 30, 2002 and ends with Saturday, August 3,
     * 2002, returning a Calendar instance for each intermediate day.</p>
     *
     * <p>This method provides an iterator that returns Calendar objects.
     * The days are progressed using {@link Calendar#add(int, int)}.</p>
     *
     * @param focus  the date to work with, not null
     * @param rangeStyle  the style constant to use. Must be one of
     * {@link DateUtils#RANGE_MONTH_SUNDAY}, 
     * {@link DateUtils#RANGE_MONTH_MONDAY},
     * {@link DateUtils#RANGE_WEEK_SUNDAY},
     * {@link DateUtils#RANGE_WEEK_MONDAY},
     * {@link DateUtils#RANGE_WEEK_RELATIVE},
     * {@link DateUtils#RANGE_WEEK_CENTER}
     * @return the date iterator, not null, not null
     * @throws IllegalArgumentException if the date is <code>null</code>
     * @throws IllegalArgumentException if the rangeStyle is invalid
     */
    public static Iterator<Calendar> iterator(Date focus, int rangeStyle) {
        if (focus == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar gval = Calendar.getInstance();
        gval.setTime(focus);
        return iterator(gval, rangeStyle);
    }

    /**
     * <p>This constructs an <code>Iterator</code> over each day in a date
     * range defined by a focus date and range style.</p>
     *
     * <p>For instance, passing Thursday, July 4, 2002 and a
     * <code>RANGE_MONTH_SUNDAY</code> will return an <code>Iterator</code>
     * that starts with Sunday, June 30, 2002 and ends with Saturday, August 3,
     * 2002, returning a Calendar instance for each intermediate day.</p>
     *
     * <p>This method provides an iterator that returns Calendar objects.
     * The days are progressed using {@link Calendar#add(int, int)}.</p>
     *
     * @param focus  the date to work with, not null
     * @param rangeStyle  the style constant to use. Must be one of
     * {@link DateUtils#RANGE_MONTH_SUNDAY}, 
     * {@link DateUtils#RANGE_MONTH_MONDAY},
     * {@link DateUtils#RANGE_WEEK_SUNDAY},
     * {@link DateUtils#RANGE_WEEK_MONDAY},
     * {@link DateUtils#RANGE_WEEK_RELATIVE},
     * {@link DateUtils#RANGE_WEEK_CENTER}
     * @return the date iterator, not null
     * @throws IllegalArgumentException if the date is <code>null</code>
     * @throws IllegalArgumentException if the rangeStyle is invalid
     */
    public static Iterator<Calendar> iterator(Calendar focus, int rangeStyle) {
        if (focus == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar start = null;
        Calendar end = null;
        int startCutoff = Calendar.SUNDAY;
        int endCutoff = Calendar.SATURDAY;
        switch (rangeStyle) {
            case RANGE_MONTH_SUNDAY:
            case RANGE_MONTH_MONDAY:
                //Set start to the first of the month
                start = truncate(focus, Calendar.MONTH);
                //Set end to the last of the month
                end = (Calendar) start.clone();
                end.add(Calendar.MONTH, 1);
                end.add(Calendar.DATE, -1);
                //Loop start back to the previous sunday or monday
                if (rangeStyle == RANGE_MONTH_MONDAY) {
                    startCutoff = Calendar.MONDAY;
                    endCutoff = Calendar.SUNDAY;
                }
                break;
            case RANGE_WEEK_SUNDAY:
            case RANGE_WEEK_MONDAY:
            case RANGE_WEEK_RELATIVE:
            case RANGE_WEEK_CENTER:
                //Set start and end to the current date
                start = truncate(focus, Calendar.DATE);
                end = truncate(focus, Calendar.DATE);
                switch (rangeStyle) {
                    case RANGE_WEEK_SUNDAY:
                        //already set by default
                        break;
                    case RANGE_WEEK_MONDAY:
                        startCutoff = Calendar.MONDAY;
                        endCutoff = Calendar.SUNDAY;
                        break;
                    case RANGE_WEEK_RELATIVE:
                        startCutoff = focus.get(Calendar.DAY_OF_WEEK);
                        endCutoff = startCutoff - 1;
                        break;
                    case RANGE_WEEK_CENTER:
                        startCutoff = focus.get(Calendar.DAY_OF_WEEK) - 3;
                        endCutoff = focus.get(Calendar.DAY_OF_WEEK) + 3;
                        break;
                }
                break;
            default:
                throw new IllegalArgumentException("The range style " + rangeStyle + " is not valid.");
        }
        if (startCutoff < Calendar.SUNDAY) {
            startCutoff += 7;
        }
        if (startCutoff > Calendar.SATURDAY) {
            startCutoff -= 7;
        }
        if (endCutoff < Calendar.SUNDAY) {
            endCutoff += 7;
        }
        if (endCutoff > Calendar.SATURDAY) {
            endCutoff -= 7;
        }
        while (start.get(Calendar.DAY_OF_WEEK) != startCutoff) {
            start.add(Calendar.DATE, -1);
        }
        while (end.get(Calendar.DAY_OF_WEEK) != endCutoff) {
            end.add(Calendar.DATE, 1);
        }
        return new DateIterator(start, end);
    }

    /**
     * <p>This constructs an <code>Iterator</code> over each day in a date
     * range defined by a focus date and range style.</p>
     *
     * <p>For instance, passing Thursday, July 4, 2002 and a
     * <code>RANGE_MONTH_SUNDAY</code> will return an <code>Iterator</code>
     * that starts with Sunday, June 30, 2002 and ends with Saturday, August 3,
     * 2002, returning a Calendar instance for each intermediate day.</p>
     *
     * @param focus  the date to work with, either {@code Date} or {@code Calendar}, not null
     * @param rangeStyle  the style constant to use. Must be one of the range
     * styles listed for the {@link #iterator(Calendar, int)} method.
     * @return the date iterator, not null
     * @throws IllegalArgumentException if the date is <code>null</code>
     * @throws ClassCastException if the object type is not a {@code Date} or {@code Calendar}
     */
    public static Iterator<?> iterator(Object focus, int rangeStyle) {
        if (focus == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        if (focus instanceof Date) {
            return iterator((Date) focus, rangeStyle);
        } else if (focus instanceof Calendar) {
            return iterator((Calendar) focus, rangeStyle);
        } else {
            throw new ClassCastException("Could not iterate based on " + focus);
        }
    }
    
    /**
     * <p>Returns the number of milliseconds within the 
     * fragment. All datefields greater than the fragment will be ignored.</p>
     * 
     * <p>Asking the milliseconds of any date will only return the number of milliseconds
     * of the current second (resulting in a number between 0 and 999). This 
     * method will retrieve the number of milliseconds for any fragment. 
     * For example, if you want to calculate the number of milliseconds past today, 
     * your fragment is Calendar.DATE or Calendar.DAY_OF_YEAR. The result will
     * be all milliseconds of the past hour(s), minutes(s) and second(s).</p>
     * 
     * <p>Valid fragments are: Calendar.YEAR, Calendar.MONTH, both 
     * Calendar.DAY_OF_YEAR and Calendar.DATE, Calendar.HOUR_OF_DAY, 
     * Calendar.MINUTE, Calendar.SECOND and Calendar.MILLISECOND
     * A fragment less than or equal to a SECOND field will return 0.</p> 
     * 
     * <p>
     * <ul>
     *  <li>January 1, 2008 7:15:10.538 with Calendar.SECOND as fragment will return 538</li>
     *  <li>January 6, 2008 7:15:10.538 with Calendar.SECOND as fragment will return 538</li>
     *  <li>January 6, 2008 7:15:10.538 with Calendar.MINUTE as fragment will return 10538 (10*1000 + 538)</li>
     *  <li>January 16, 2008 7:15:10.538 with Calendar.MILLISECOND as fragment will return 0
     *   (a millisecond cannot be split in milliseconds)</li>
     * </ul>
     * </p>
     * 
     * @param date the date to work with, not null
     * @param fragment the {@code Calendar} field part of date to calculate 
     * @return number of milliseconds within the fragment of date
     * @throws IllegalArgumentException if the date is <code>null</code> or
     * fragment is not supported
     * @since 2.4
     */
    public static long getFragmentInMilliseconds(Date date, int fragment) {
        return getFragment(date, fragment, Calendar.MILLISECOND);    
    }
    
    /**
     * <p>Returns the number of seconds within the 
     * fragment. All datefields greater than the fragment will be ignored.</p> 
     * 
     * <p>Asking the seconds of any date will only return the number of seconds
     * of the current minute (resulting in a number between 0 and 59). This 
     * method will retrieve the number of seconds for any fragment. 
     * For example, if you want to calculate the number of seconds past today, 
     * your fragment is Calendar.DATE or Calendar.DAY_OF_YEAR. The result will
     * be all seconds of the past hour(s) and minutes(s).</p> 
     * 
     * <p>Valid fragments are: Calendar.YEAR, Calendar.MONTH, both 
     * Calendar.DAY_OF_YEAR and Calendar.DATE, Calendar.HOUR_OF_DAY, 
     * Calendar.MINUTE, Calendar.SECOND and Calendar.MILLISECOND
     * A fragment less than or equal to a SECOND field will return 0.</p> 
     * 
     * <p>
     * <ul>
     *  <li>January 1, 2008 7:15:10.538 with Calendar.MINUTE as fragment will return 10
     *   (equivalent to deprecated date.getSeconds())</li>
     *  <li>January 6, 2008 7:15:10.538 with Calendar.MINUTE as fragment will return 10
     *   (equivalent to deprecated date.getSeconds())</li>
     *  <li>January 6, 2008 7:15:10.538 with Calendar.DAY_OF_YEAR as fragment will return 26110
     *   (7*3600 + 15*60 + 10)</li>
     *  <li>January 16, 2008 7:15:10.538 with Calendar.MILLISECOND as fragment will return 0
     *   (a millisecond cannot be split in seconds)</li>
     * </ul>
     * </p>
     * 
     * @param date the date to work with, not null
     * @param fragment the {@code Calendar} field part of date to calculate 
     * @return number of seconds within the fragment of date
     * @throws IllegalArgumentException if the date is <code>null</code> or
     * fragment is not supported
     * @since 2.4
     */
    public static long getFragmentInSeconds(Date date, int fragment) {
        return getFragment(date, fragment, Calendar.SECOND);
    }
    
    /**
     * <p>Returns the number of minutes within the 
     * fragment. All datefields greater than the fragment will be ignored.</p> 
     * 
     * <p>Asking the minutes of any date will only return the number of minutes
     * of the current hour (resulting in a number between 0 and 59). This 
     * method will retrieve the number of minutes for any fragment. 
     * For example, if you want to calculate the number of minutes past this month, 
     * your fragment is Calendar.MONTH. The result will be all minutes of the 
     * past day(s) and hour(s).</p> 
     * 
     * <p>Valid fragments are: Calendar.YEAR, Calendar.MONTH, both 
     * Calendar.DAY_OF_YEAR and Calendar.DATE, Calendar.HOUR_OF_DAY, 
     * Calendar.MINUTE, Calendar.SECOND and Calendar.MILLISECOND
     * A fragment less than or equal to a MINUTE field will return 0.</p> 
     * 
     * <p>
     * <ul>
     *  <li>January 1, 2008 7:15:10.538 with Calendar.HOUR_OF_DAY as fragment will return 15
     *   (equivalent to deprecated date.getMinutes())</li>
     *  <li>January 6, 2008 7:15:10.538 with Calendar.HOUR_OF_DAY as fragment will return 15
     *   (equivalent to deprecated date.getMinutes())</li>
     *  <li>January 1, 2008 7:15:10.538 with Calendar.MONTH as fragment will return 15</li>
     *  <li>January 6, 2008 7:15:10.538 with Calendar.MONTH as fragment will return 435 (7*60 + 15)</li>
     *  <li>January 16, 2008 7:15:10.538 with Calendar.MILLISECOND as fragment will return 0
     *   (a millisecond cannot be split in minutes)</li>
     * </ul>
     * </p>
     * 
     * @param date the date to work with, not null
     * @param fragment the {@code Calendar} field part of date to calculate 
     * @return number of minutes within the fragment of date
     * @throws IllegalArgumentException if the date is <code>null</code> or 
     * fragment is not supported
     * @since 2.4
     */
    public static long getFragmentInMinutes(Date date, int fragment) {
        return getFragment(date, fragment, Calendar.MINUTE);
    }
    
    /**
     * <p>Returns the number of hours within the 
     * fragment. All datefields greater than the fragment will be ignored.</p> 
     * 
     * <p>Asking the hours of any date will only return the number of hours
     * of the current day (resulting in a number between 0 and 23). This 
     * method will retrieve the number of hours for any fragment. 
     * For example, if you want to calculate the number of hours past this month, 
     * your fragment is Calendar.MONTH. The result will be all hours of the 
     * past day(s).</p> 
     * 
     * <p>Valid fragments are: Calendar.YEAR, Calendar.MONTH, both 
     * Calendar.DAY_OF_YEAR and Calendar.DATE, Calendar.HOUR_OF_DAY, 
     * Calendar.MINUTE, Calendar.SECOND and Calendar.MILLISECOND
     * A fragment less than or equal to a HOUR field will return 0.</p> 
     * 
     * <p>
     * <ul>
     *  <li>January 1, 2008 7:15:10.538 with Calendar.DAY_OF_YEAR as fragment will return 7
     *   (equivalent to deprecated date.getHours())</li>
     *  <li>January 6, 2008 7:15:10.538 with Calendar.DAY_OF_YEAR as fragment will return 7
     *   (equivalent to deprecated date.getHours())</li>
     *  <li>January 1, 2008 7:15:10.538 with Calendar.MONTH as fragment will return 7</li>
     *  <li>January 6, 2008 7:15:10.538 with Calendar.MONTH as fragment will return 127 (5*24 + 7)</li>
     *  <li>January 16, 2008 7:15:10.538 with Calendar.MILLISECOND as fragment will return 0
     *   (a millisecond cannot be split in hours)</li>
     * </ul>
     * </p>
     * 
     * @param date the date to work with, not null
     * @param fragment the {@code Calendar} field part of date to calculate 
     * @return number of hours within the fragment of date
     * @throws IllegalArgumentException if the date is <code>null</code> or 
     * fragment is not supported
     * @since 2.4
     */
    public static long getFragmentInHours(Date date, int fragment) {
        return getFragment(date, fragment, Calendar.HOUR_OF_DAY);
    }
    
    /**
     * <p>Returns the number of days within the 
     * fragment. All datefields greater than the fragment will be ignored.</p> 
     * 
     * <p>Asking the days of any date will only return the number of days
     * of the current month (resulting in a number between 1 and 31). This 
     * method will retrieve the number of days for any fragment. 
     * For example, if you want to calculate the number of days past this year, 
     * your fragment is Calendar.YEAR. The result will be all days of the 
     * past month(s).</p> 
     * 
     * <p>Valid fragments are: Calendar.YEAR, Calendar.MONTH, both 
     * Calendar.DAY_OF_YEAR and Calendar.DATE, Calendar.HOUR_OF_DAY, 
     * Calendar.MINUTE, Calendar.SECOND and Calendar.MILLISECOND
     * A fragment less than or equal to a DAY field will return 0.</p> 
     *  
     * <p>
     * <ul>
     *  <li>January 28, 2008 with Calendar.MONTH as fragment will return 28
     *   (equivalent to deprecated date.getDay())</li>
     *  <li>February 28, 2008 with Calendar.MONTH as fragment will return 28
     *   (equivalent to deprecated date.getDay())</li>
     *  <li>January 28, 2008 with Calendar.YEAR as fragment will return 28</li>
     *  <li>February 28, 2008 with Calendar.YEAR as fragment will return 59</li>
     *  <li>January 28, 2008 with Calendar.MILLISECOND as fragment will return 0
     *   (a millisecond cannot be split in days)</li>
     * </ul>
     * </p>
     * 
     * @param date the date to work with, not null
     * @param fragment the {@code Calendar} field part of date to calculate 
     * @return number of days  within the fragment of date
     * @throws IllegalArgumentException if the date is <code>null</code> or 
     * fragment is not supported
     * @since 2.4
     */
    public static long getFragmentInDays(Date date, int fragment) {
        return getFragment(date, fragment, Calendar.DAY_OF_YEAR);
    }

    /**
     * <p>Returns the number of milliseconds within the 
     * fragment. All datefields greater than the fragment will be ignored.</p> 
     * 
     * <p>Asking the milliseconds of any date will only return the number of milliseconds
     * of the current second (resulting in a number between 0 and 999). This 
     * method will retrieve the number of milliseconds for any fragment. 
     * For example, if you want to calculate the number of seconds past today, 
     * your fragment is Calendar.DATE or Calendar.DAY_OF_YEAR. The result will
     * be all seconds of the past hour(s), minutes(s) and second(s).</p> 
     * 
     * <p>Valid fragments are: Calendar.YEAR, Calendar.MONTH, both 
     * Calendar.DAY_OF_YEAR and Calendar.DATE, Calendar.HOUR_OF_DAY, 
     * Calendar.MINUTE, Calendar.SECOND and Calendar.MILLISECOND
     * A fragment less than or equal to a MILLISECOND field will return 0.</p> 
     * 
     * <p>
     * <ul>
     *  <li>January 1, 2008 7:15:10.538 with Calendar.SECOND as fragment will return 538
     *   (equivalent to calendar.get(Calendar.MILLISECOND))</li>
     *  <li>January 6, 2008 7:15:10.538 with Calendar.SECOND as fragment will return 538
     *   (equivalent to calendar.get(Calendar.MILLISECOND))</li>
     *  <li>January 6, 2008 7:15:10.538 with Calendar.MINUTE as fragment will return 10538
     *   (10*1000 + 538)</li>
     *  <li>January 16, 2008 7:15:10.538 with Calendar.MILLISECOND as fragment will return 0
     *   (a millisecond cannot be split in milliseconds)</li>
     * </ul>
     * </p>
     * 
     * @param calendar the calendar to work with, not null
     * @param fragment the {@code Calendar} field part of calendar to calculate 
     * @return number of milliseconds within the fragment of date
     * @throws IllegalArgumentException if the date is <code>null</code> or 
     * fragment is not supported
     * @since 2.4
     */
  public static long getFragmentInMilliseconds(Calendar calendar, int fragment) {
    return getFragment(calendar, fragment, Calendar.MILLISECOND);
  }
    /**
     * <p>Returns the number of seconds within the 
     * fragment. All datefields greater than the fragment will be ignored.</p> 
     * 
     * <p>Asking the seconds of any date will only return the number of seconds
     * of the current minute (resulting in a number between 0 and 59). This 
     * method will retrieve the number of seconds for any fragment. 
     * For example, if you want to calculate the number of seconds past today, 
     * your fragment is Calendar.DATE or Calendar.DAY_OF_YEAR. The result will
     * be all seconds of the past hour(s) and minutes(s).</p> 
     * 
     * <p>Valid fragments are: Calendar.YEAR, Calendar.MONTH, both 
     * Calendar.DAY_OF_YEAR and Calendar.DATE, Calendar.HOUR_OF_DAY, 
     * Calendar.MINUTE, Calendar.SECOND and Calendar.MILLISECOND
     * A fragment less than or equal to a SECOND field will return 0.</p> 
     * 
     * <p>
     * <ul>
     *  <li>January 1, 2008 7:15:10.538 with Calendar.MINUTE as fragment will return 10
     *   (equivalent to calendar.get(Calendar.SECOND))</li>
     *  <li>January 6, 2008 7:15:10.538 with Calendar.MINUTE as fragment will return 10
     *   (equivalent to calendar.get(Calendar.SECOND))</li>
     *  <li>January 6, 2008 7:15:10.538 with Calendar.DAY_OF_YEAR as fragment will return 26110
     *   (7*3600 + 15*60 + 10)</li>
     *  <li>January 16, 2008 7:15:10.538 with Calendar.MILLISECOND as fragment will return 0
     *   (a millisecond cannot be split in seconds)</li>
     * </ul>
     * </p>
     * 
     * @param calendar the calendar to work with, not null
     * @param fragment the {@code Calendar} field part of calendar to calculate 
     * @return number of seconds within the fragment of date
     * @throws IllegalArgumentException if the date is <code>null</code> or 
     * fragment is not supported
     * @since 2.4
     */
    public static long getFragmentInSeconds(Calendar calendar, int fragment) {
        return getFragment(calendar, fragment, Calendar.SECOND);
    }
    
    /**
     * <p>Returns the number of minutes within the 
     * fragment. All datefields greater than the fragment will be ignored.</p> 
     * 
     * <p>Asking the minutes of any date will only return the number of minutes
     * of the current hour (resulting in a number between 0 and 59). This 
     * method will retrieve the number of minutes for any fragment. 
     * For example, if you want to calculate the number of minutes past this month, 
     * your fragment is Calendar.MONTH. The result will be all minutes of the 
     * past day(s) and hour(s).</p> 
     * 
     * <p>Valid fragments are: Calendar.YEAR, Calendar.MONTH, both 
     * Calendar.DAY_OF_YEAR and Calendar.DATE, Calendar.HOUR_OF_DAY, 
     * Calendar.MINUTE, Calendar.SECOND and Calendar.MILLISECOND
     * A fragment less than or equal to a MINUTE field will return 0.</p> 
     * 
     * <p>
     * <ul>
     *  <li>January 1, 2008 7:15:10.538 with Calendar.HOUR_OF_DAY as fragment will return 15
     *   (equivalent to calendar.get(Calendar.MINUTES))</li>
     *  <li>January 6, 2008 7:15:10.538 with Calendar.HOUR_OF_DAY as fragment will return 15
     *   (equivalent to calendar.get(Calendar.MINUTES))</li>
     *  <li>January 1, 2008 7:15:10.538 with Calendar.MONTH as fragment will return 15</li>
     *  <li>January 6, 2008 7:15:10.538 with Calendar.MONTH as fragment will return 435 (7*60 + 15)</li>
     *  <li>January 16, 2008 7:15:10.538 with Calendar.MILLISECOND as fragment will return 0
     *   (a millisecond cannot be split in minutes)</li>
     * </ul>
     * </p>
     * 
     * @param calendar the calendar to work with, not null
     * @param fragment the {@code Calendar} field part of calendar to calculate 
     * @return number of minutes within the fragment of date
     * @throws IllegalArgumentException if the date is <code>null</code> or 
     * fragment is not supported
     * @since 2.4
     */
    public static long getFragmentInMinutes(Calendar calendar, int fragment) {
        return getFragment(calendar, fragment, Calendar.MINUTE);
    }
    
    /**
     * <p>Returns the number of hours within the 
     * fragment. All datefields greater than the fragment will be ignored.</p> 
     * 
     * <p>Asking the hours of any date will only return the number of hours
     * of the current day (resulting in a number between 0 and 23). This 
     * method will retrieve the number of hours for any fragment. 
     * For example, if you want to calculate the number of hours past this month, 
     * your fragment is Calendar.MONTH. The result will be all hours of the 
     * past day(s).</p> 
     * 
     * <p>Valid fragments are: Calendar.YEAR, Calendar.MONTH, both 
     * Calendar.DAY_OF_YEAR and Calendar.DATE, Calendar.HOUR_OF_DAY, 
     * Calendar.MINUTE, Calendar.SECOND and Calendar.MILLISECOND
     * A fragment less than or equal to a HOUR field will return 0.</p> 
     *  
     * <p>
     * <ul>
     *  <li>January 1, 2008 7:15:10.538 with Calendar.DAY_OF_YEAR as fragment will return 7
     *   (equivalent to calendar.get(Calendar.HOUR_OF_DAY))</li>
     *  <li>January 6, 2008 7:15:10.538 with Calendar.DAY_OF_YEAR as fragment will return 7
     *   (equivalent to calendar.get(Calendar.HOUR_OF_DAY))</li>
     *  <li>January 1, 2008 7:15:10.538 with Calendar.MONTH as fragment will return 7</li>
     *  <li>January 6, 2008 7:15:10.538 with Calendar.MONTH as fragment will return 127 (5*24 + 7)</li>
     *  <li>January 16, 2008 7:15:10.538 with Calendar.MILLISECOND as fragment will return 0
     *   (a millisecond cannot be split in hours)</li>
     * </ul>
     * </p>
     *  
     * @param calendar the calendar to work with, not null
     * @param fragment the {@code Calendar} field part of calendar to calculate 
     * @return number of hours within the fragment of date
     * @throws IllegalArgumentException if the date is <code>null</code> or 
     * fragment is not supported
     * @since 2.4
     */
    public static long getFragmentInHours(Calendar calendar, int fragment) {
        return getFragment(calendar, fragment, Calendar.HOUR_OF_DAY);
    }
    
    /**
     * <p>Returns the number of days within the 
     * fragment. All datefields greater than the fragment will be ignored.</p> 
     * 
     * <p>Asking the days of any date will only return the number of days
     * of the current month (resulting in a number between 1 and 31). This 
     * method will retrieve the number of days for any fragment. 
     * For example, if you want to calculate the number of days past this year, 
     * your fragment is Calendar.YEAR. The result will be all days of the 
     * past month(s).</p> 
     * 
     * <p>Valid fragments are: Calendar.YEAR, Calendar.MONTH, both 
     * Calendar.DAY_OF_YEAR and Calendar.DATE, Calendar.HOUR_OF_DAY, 
     * Calendar.MINUTE, Calendar.SECOND and Calendar.MILLISECOND
     * A fragment less than or equal to a DAY field will return 0.</p> 
     * 
     * <p>
     * <ul>
     *  <li>January 28, 2008 with Calendar.MONTH as fragment will return 28
     *   (equivalent to calendar.get(Calendar.DAY_OF_MONTH))</li>
     *  <li>February 28, 2008 with Calendar.MONTH as fragment will return 28
     *   (equivalent to calendar.get(Calendar.DAY_OF_MONTH))</li>
     *  <li>January 28, 2008 with Calendar.YEAR as fragment will return 28
     *   (equivalent to calendar.get(Calendar.DAY_OF_YEAR))</li>
     *  <li>February 28, 2008 with Calendar.YEAR as fragment will return 59
     *   (equivalent to calendar.get(Calendar.DAY_OF_YEAR))</li>
     *  <li>January 28, 2008 with Calendar.MILLISECOND as fragment will return 0
     *   (a millisecond cannot be split in days)</li>
     * </ul>
     * </p>
     * 
     * @param calendar the calendar to work with, not null
     * @param fragment the {@code Calendar} field part of calendar to calculate 
     * @return number of days within the fragment of date
     * @throws IllegalArgumentException if the date is <code>null</code> or 
     * fragment is not supported
     * @since 2.4
     */
    public static long getFragmentInDays(Calendar calendar, int fragment) {
        return getFragment(calendar, fragment, Calendar.DAY_OF_YEAR);
    }
    
    /**
     * Date-version for fragment-calculation in any unit
     * 
     * @param date the date to work with, not null
     * @param fragment the Calendar field part of date to calculate 
     * @param unit the {@code Calendar} field defining the unit
     * @return number of units within the fragment of the date
     * @throws IllegalArgumentException if the date is <code>null</code> or 
     * fragment is not supported
     * @since 2.4
     */
    private static long getFragment(Date date, int fragment, int unit) {
        if(date == null) {
            throw  new IllegalArgumentException("The date must not be null");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return getFragment(calendar, fragment, unit);
    }

    /**
     * Calendar-version for fragment-calculation in any unit
     * 
     * @param calendar the calendar to work with, not null
     * @param fragment the Calendar field part of calendar to calculate 
     * @param unit the {@code Calendar} field defining the unit
     * @return number of units within the fragment of the calendar
     * @throws IllegalArgumentException if the date is <code>null</code> or 
     * fragment is not supported
     * @since 2.4
     */
    private static long getFragment(Calendar calendar, int fragment, int unit) {
        if(calendar == null) {
            throw  new IllegalArgumentException("The date must not be null"); 
        }
        long millisPerUnit = getMillisPerUnit(unit);
        long result = 0;
        
        // Fragments bigger than a day require a breakdown to days
        switch (fragment) {
            case Calendar.YEAR:
                result += (calendar.get(Calendar.DAY_OF_YEAR) * MILLIS_PER_DAY) / millisPerUnit;
                break;
            case Calendar.MONTH:
                result += (calendar.get(Calendar.DAY_OF_MONTH) * MILLIS_PER_DAY) / millisPerUnit;
                break;
        }

        switch (fragment) {
            // Number of days already calculated for these cases
            case Calendar.YEAR:
            case Calendar.MONTH:
            
            // The rest of the valid cases
            case Calendar.DAY_OF_YEAR:
            case Calendar.DATE:
                result += (calendar.get(Calendar.HOUR_OF_DAY) * MILLIS_PER_HOUR) / millisPerUnit;
                //$FALL-THROUGH$
            case Calendar.HOUR_OF_DAY:
                result += (calendar.get(Calendar.MINUTE) * MILLIS_PER_MINUTE) / millisPerUnit;
                //$FALL-THROUGH$
            case Calendar.MINUTE:
                result += (calendar.get(Calendar.SECOND) * MILLIS_PER_SECOND) / millisPerUnit;
                //$FALL-THROUGH$
            case Calendar.SECOND:
                result += (calendar.get(Calendar.MILLISECOND) * 1) / millisPerUnit;
                break;
            case Calendar.MILLISECOND: break;//never useful
                default: throw new IllegalArgumentException("The fragment " + fragment + " is not supported");
        }
        return result;
    }
    
    /**
     * Determines if two calendars are equal up to no more than the specified 
     * most significant field.
     * 
     * @param cal1 the first calendar, not <code>null</code>
     * @param cal2 the second calendar, not <code>null</code>
     * @param field the field from {@code Calendar}
     * @return <code>true</code> if equal; otherwise <code>false</code>
     * @throws IllegalArgumentException if any argument is <code>null</code>
     * @see #truncate(Calendar, int)
     * @see #truncatedEquals(Date, Date, int)
     * @since 3.0
     */
    public static boolean truncatedEquals(Calendar cal1, Calendar cal2, int field) {
        return truncatedCompareTo(cal1, cal2, field) == 0;
    }

    /**
     * Determines if two dates are equal up to no more than the specified 
     * most significant field.
     * 
     * @param date1 the first date, not <code>null</code>
     * @param date2 the second date, not <code>null</code>
     * @param field the field from {@code Calendar}
     * @return <code>true</code> if equal; otherwise <code>false</code>
     * @throws IllegalArgumentException if any argument is <code>null</code>
     * @see #truncate(Date, int)
     * @see #truncatedEquals(Calendar, Calendar, int)
     * @since 3.0
     */
    public static boolean truncatedEquals(Date date1, Date date2, int field) {
        return truncatedCompareTo(date1, date2, field) == 0;
    }

    /**
     * Determines how two calendars compare up to no more than the specified 
     * most significant field.
     * 
     * @param cal1 the first calendar, not <code>null</code>
     * @param cal2 the second calendar, not <code>null</code>
     * @param field the field from {@code Calendar}
     * @return a negative integer, zero, or a positive integer as the first 
     * calendar is less than, equal to, or greater than the second.
     * @throws IllegalArgumentException if any argument is <code>null</code>
     * @see #truncate(Calendar, int)
     * @see #truncatedCompareTo(Date, Date, int)
     * @since 3.0
     */
    public static int truncatedCompareTo(Calendar cal1, Calendar cal2, int field) {
        Calendar truncatedCal1 = truncate(cal1, field);
        Calendar truncatedCal2 = truncate(cal2, field);
        return truncatedCal1.compareTo(truncatedCal2);
    }

    /**
     * Determines how two dates compare up to no more than the specified 
     * most significant field.
     * 
     * @param date1 the first date, not <code>null</code>
     * @param date2 the second date, not <code>null</code>
     * @param field the field from <code>Calendar</code>
     * @return a negative integer, zero, or a positive integer as the first 
     * date is less than, equal to, or greater than the second.
     * @throws IllegalArgumentException if any argument is <code>null</code>
     * @see #truncate(Calendar, int)
     * @see #truncatedCompareTo(Date, Date, int)
     * @since 3.0
     */
    public static int truncatedCompareTo(Date date1, Date date2, int field) {
        Date truncatedDate1 = truncate(date1, field);
        Date truncatedDate2 = truncate(date2, field);
        return truncatedDate1.compareTo(truncatedDate2);
    }

    /**
     * Returns the number of milliseconds of a {@code Calendar} field, if this is a constant value.
     * This handles millisecond, second, minute, hour and day (even though days can very in length).
     * 
     * @param unit  a {@code Calendar} field constant which is a valid unit for a fragment
     * @return the number of milliseconds in the field
     * @throws IllegalArgumentException if date can't be represented in milliseconds
     * @since 2.4 
     */
    private static long getMillisPerUnit(int unit) {
        long result = Long.MAX_VALUE;
        switch (unit) {
            case Calendar.DAY_OF_YEAR:
            case Calendar.DATE:
                result = MILLIS_PER_DAY;
                break;
            case Calendar.HOUR_OF_DAY:
                result = MILLIS_PER_HOUR;
                break;
            case Calendar.MINUTE:
                result = MILLIS_PER_MINUTE;
                break;
            case Calendar.SECOND:
                result = MILLIS_PER_SECOND;
                break;
            case Calendar.MILLISECOND:
                result = 1;
                break;
            default: throw new IllegalArgumentException("The unit " + unit + " cannot be represented is milleseconds");
        }
        return result;
    }

    //-----------------------------------------------------------------------
    /**
     * <p>Date iterator.</p>
     */
    static class DateIterator implements Iterator<Calendar> {
        private final Calendar endFinal;
        private final Calendar spot;
        
        /**
         * Constructs a DateIterator that ranges from one date to another. 
         *
         * @param startFinal start date (inclusive)
         * @param endFinal end date (not inclusive)
         */
        DateIterator(Calendar startFinal, Calendar endFinal) {
            super();
            this.endFinal = endFinal;
            spot = startFinal;
            spot.add(Calendar.DATE, -1);
        }

        /**
         * Has the iterator not reached the end date yet?
         *
         * @return <code>true</code> if the iterator has yet to reach the end date
         */
        public boolean hasNext() {
            return spot.before(endFinal);
        }

        /**
         * Return the next calendar in the iteration
         *
         * @return Object calendar for the next date
         */
        public Calendar next() {
            if (spot.equals(endFinal)) {
                throw new NoSuchElementException();
            }
            spot.add(Calendar.DATE, 1);
            return (Calendar) spot.clone();
        }

        /**
         * Always throws UnsupportedOperationException.
         * 
         * @throws UnsupportedOperationException
         * @see java.util.Iterator#remove()
         */
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

	/** 
	 * 将微信消息中的CreateTime转换成标准格式的时间（yyyy-MM-dd HH:mm:ss） 
	 *  
	 * @param createTime 消息创建时间 
	 * @return 
	 */  
	public static String formatTime(String createTime) {  
	    // 将微信传入的CreateTime转换成long类型，再乘以1000  
	    long msgCreateTime = Long.parseLong(createTime) * 1000L;  
	    DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	    return format.format(new Date(msgCreateTime));  
	}

	// 各种时间格式
	public static final SimpleDateFormat date_sdf = new SimpleDateFormat(
			"yyyy-MM-dd");
	// 各种时间格式
	public static final SimpleDateFormat yyyyMMdd = new SimpleDateFormat(
			"yyyyMMdd");
	// 各种时间格式
	public static final SimpleDateFormat date_sdf_wz = new SimpleDateFormat(
			"yyyy年MM月dd日");
	public static final SimpleDateFormat time_sdf = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm");
	public static final SimpleDateFormat yyyymmddhhmmss = new SimpleDateFormat(
	"yyyyMMddHHmmss");
	public static final SimpleDateFormat short_time_sdf = new SimpleDateFormat(
			"HH:mm");
	public static final  SimpleDateFormat datetimeFormat = new SimpleDateFormat(
	"yyyy-MM-dd HH:mm:ss");
	// 以毫秒表示的时间
	private static final long DAY_IN_MILLIS = 24 * 3600 * 1000;
	private static final long HOUR_IN_MILLIS = 3600 * 1000;
	private static final long MINUTE_IN_MILLIS = 60 * 1000;
	private static final long SECOND_IN_MILLIS = 1000;
	// 指定模式的时间格式
	private static SimpleDateFormat getSDFormat(String pattern) {
		return new SimpleDateFormat(pattern);
	}

	/**
	 * 当前日历，这里用中国时间表示
	 * 
	 * @return 以当地时区表示的系统当前日历
	 */
	public static Calendar getCalendar() {
		return Calendar.getInstance();
	}

	/**
	 * 指定毫秒数表示的日历
	 * 
	 * @param millis
	 *            毫秒数
	 * @return 指定毫秒数表示的日历
	 */
	public static Calendar getCalendar(long millis) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date(millis));
		return cal;
	}
	/**
	 * 时间转字符串
	 * @return
	 */
	public static String date2SStr() 
	{
		Date date=getDate();
		if (null == date) {
			return null;
		}
		return yyyyMMdd.format(date);
	}

	// ////////////////////////////////////////////////////////////////////////////
	// getDate
	// 各种方式获取的Date
	// ////////////////////////////////////////////////////////////////////////////

	/**
	 * 当前日期
	 * 
	 * @return 系统当前时间
	 */
	public static Date getDate() {
		return new Date();
	}

	/**
	 * 指定毫秒数表示的日期
	 * 
	 * @param millis
	 *            毫秒数
	 * @return 指定毫秒数表示的日期
	 */
	public static Date getDate(long millis) {
		return new Date(millis);
	}

	/**
	 * 时间戳转换为字符串
	 * 
	 * @param time
	 * @return
	 */
	public static String timestamptoStr(Timestamp time) {
		Date date = null;
		if (null != time) {
			date = new Date(time.getTime());
		}
		return date2Str(date_sdf);
	}

	/**
	 * 字符串转换时间戳
	 * 
	 * @param str
	 * @return
	 */
	public static Timestamp str2Timestamp(String str) {
		Date date = str2Date(str, date_sdf);
		return new Timestamp(date.getTime());
	}
	/**
	 * 字符串转换成日期
	 * @param str
	 * @param sdf
	 * @return
	 */
	public static Date str2Date(String str, SimpleDateFormat sdf) {
		if (null == str || "".equals(str)) {
			return null;
		}
		Date date = null;
		try {
			date = sdf.parse(str);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 日期转换为字符串
	 * 
	 * @param date
	 *            日期
	 * @param format
	 *            日期格式
	 * @return 字符串
	 */
	public static String date2Str(SimpleDateFormat date_sdf) {
		Date date=getDate();
		if (null == date) {
			return null;
		}
		return date_sdf.format(date);
	}
	/**
	 * 格式化时间
	 * @param data
	 * @param format
	 * @return
	 */
	public static String dataformat(String data,String format)
	{
		SimpleDateFormat sformat = new SimpleDateFormat(format);
		Date date=null;
		try {
			 date=sformat.parse(data);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sformat.format(date);
	}
	/**
	 * 日期转换为字符串
	 * 
	 * @param date
	 *            日期
	 * @param format
	 *            日期格式
	 * @return 字符串
	 */
	public static String date2Str(Date date, SimpleDateFormat date_sdf) {
		if (null == date) {
			return null;
		}
		return date_sdf.format(date);
	}
	/**
	 * 日期转换为字符串
	 * 
	 * @param date
	 *            日期
	 * @param format
	 *            日期格式
	 * @return 字符串
	 */
	public static String getDate(String format) {
		Date date=new Date();
		if (null == date) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	/**
	 * 指定毫秒数的时间戳
	 * 
	 * @param millis
	 *            毫秒数
	 * @return 指定毫秒数的时间戳
	 */
	public static Timestamp getTimestamp(long millis) {
		return new Timestamp(millis);
	}

	/**
	 * 以字符形式表示的时间戳
	 * 
	 * @param time
	 *            毫秒数
	 * @return 以字符形式表示的时间戳
	 */
	public static Timestamp getTimestamp(String time) {
		return new Timestamp(Long.parseLong(time));
	}

	/**
	 * 系统当前的时间戳
	 * 
	 * @return 系统当前的时间戳
	 */
	public static Timestamp getTimestamp() {
		return new Timestamp(new Date().getTime());
	}

	/**
	 * 指定日期的时间戳
	 * 
	 * @param date
	 *            指定日期
	 * @return 指定日期的时间戳
	 */
	public static Timestamp getTimestamp(Date date) {
		return new Timestamp(date.getTime());
	}

	/**
	 * 指定日历的时间戳
	 * 
	 * @param cal
	 *            指定日历
	 * @return 指定日历的时间戳
	 */
	public static Timestamp getCalendarTimestamp(Calendar cal) {
		return new Timestamp(cal.getTime().getTime());
	}

	public static Timestamp gettimestamp() {
		Date dt = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowTime = df.format(dt);
		java.sql.Timestamp buydate = java.sql.Timestamp.valueOf(nowTime);
		return buydate;
	}

	// ////////////////////////////////////////////////////////////////////////////
	// getMillis
	// 各种方式获取的Millis
	// ////////////////////////////////////////////////////////////////////////////

	/**
	 * 系统时间的毫秒数
	 * 
	 * @return 系统时间的毫秒数
	 */
	public static long getMillis() {
		return new Date().getTime();
	}

	/**
	 * 指定日历的毫秒数
	 * 
	 * @param cal
	 *            指定日历
	 * @return 指定日历的毫秒数
	 */
	public static long getMillis(Calendar cal) {
		return cal.getTime().getTime();
	}

	/**
	 * 指定日期的毫秒数
	 * 
	 * @param date
	 *            指定日期
	 * @return 指定日期的毫秒数
	 */
	public static long getMillis(Date date) {
		return date.getTime();
	}

	/**
	 * 指定时间戳的毫秒数
	 * 
	 * @param ts
	 *            指定时间戳
	 * @return 指定时间戳的毫秒数
	 */
	public static long getMillis(Timestamp ts) {
		return ts.getTime();
	}

	// ////////////////////////////////////////////////////////////////////////////
	// formatDate
	// 将日期按照一定的格式转化为字符串
	// ////////////////////////////////////////////////////////////////////////////

	/**
	 * 默认方式表示的系统当前日期，具体格式：年-月-日
	 * 
	 * @return 默认日期按“年-月-日“格式显示
	 */
	public static String formatDate() {
		return date_sdf.format(getCalendar().getTime());
	}
	/**
	 * 获取时间字符串
	 */
	public static String getDataString(SimpleDateFormat formatstr) {
		return formatstr.format(getCalendar().getTime());
	}
	/**
	 * 指定日期的默认显示，具体格式：年-月-日
	 * 
	 * @param cal
	 *            指定的日期
	 * @return 指定日期按“年-月-日“格式显示
	 */
	public static String formatDate(Calendar cal) {
		return date_sdf.format(cal.getTime());
	}

	/**
	 * 指定日期的默认显示，具体格式：年-月-日
	 * 
	 * @param date
	 *            指定的日期
	 * @return 指定日期按“年-月-日“格式显示
	 */
	public static String formatDate(Date date) {
		return date_sdf.format(date);
	}

	/**
	 * 指定毫秒数表示日期的默认显示，具体格式：年-月-日
	 * 
	 * @param millis
	 *            指定的毫秒数
	 * @return 指定毫秒数表示日期按“年-月-日“格式显示
	 */
	public static String formatDate(long millis) {
		return date_sdf.format(new Date(millis));
	}

	/**
	 * 默认日期按指定格式显示
	 * 
	 * @param pattern
	 *            指定的格式
	 * @return 默认日期按指定格式显示
	 */
	public static String formatDate(String pattern) {
		return getSDFormat(pattern).format(getCalendar().getTime());
	}

	/**
	 * 指定日期按指定格式显示
	 * 
	 * @param cal
	 *            指定的日期
	 * @param pattern
	 *            指定的格式
	 * @return 指定日期按指定格式显示
	 */
	public static String formatDate(Calendar cal, String pattern) {
		return getSDFormat(pattern).format(cal.getTime());
	}

	/**
	 * 指定日期按指定格式显示
	 * 
	 * @param date
	 *            指定的日期
	 * @param pattern
	 *            指定的格式
	 * @return 指定日期按指定格式显示
	 */
	public static String formatDate(Date date, String pattern) {
		return getSDFormat(pattern).format(date);
	}

	// ////////////////////////////////////////////////////////////////////////////
	// formatTime
	// 将日期按照一定的格式转化为字符串
	// ////////////////////////////////////////////////////////////////////////////

	/**
	 * 默认方式表示的系统当前日期，具体格式：年-月-日 时：分
	 * 
	 * @return 默认日期按“年-月-日 时：分“格式显示
	 */
	public static String formatTime() {
		return time_sdf.format(getCalendar().getTime());
	}

	/**
	 * 指定毫秒数表示日期的默认显示，具体格式：年-月-日 时：分
	 * 
	 * @param millis
	 *            指定的毫秒数
	 * @return 指定毫秒数表示日期按“年-月-日 时：分“格式显示
	 */
	public static String formatTime(long millis) {
		return time_sdf.format(new Date(millis));
	}

	/**
	 * 指定日期的默认显示，具体格式：年-月-日 时：分
	 * 
	 * @param cal
	 *            指定的日期
	 * @return 指定日期按“年-月-日 时：分“格式显示
	 */
	public static String formatTime(Calendar cal) {
		return time_sdf.format(cal.getTime());
	}

	/**
	 * 指定日期的默认显示，具体格式：年-月-日 时：分
	 * 
	 * @param date
	 *            指定的日期
	 * @return 指定日期按“年-月-日 时：分“格式显示
	 */
	public static String formatTime(Date date) {
		return time_sdf.format(date);
	}

	// ////////////////////////////////////////////////////////////////////////////
	// formatShortTime
	// 将日期按照一定的格式转化为字符串
	// ////////////////////////////////////////////////////////////////////////////

	/**
	 * 默认方式表示的系统当前日期，具体格式：时：分
	 * 
	 * @return 默认日期按“时：分“格式显示
	 */
	public static String formatShortTime() {
		return short_time_sdf.format(getCalendar().getTime());
	}

	/**
	 * 指定毫秒数表示日期的默认显示，具体格式：时：分
	 * 
	 * @param millis
	 *            指定的毫秒数
	 * @return 指定毫秒数表示日期按“时：分“格式显示
	 */
	public static String formatShortTime(long millis) {
		return short_time_sdf.format(new Date(millis));
	}

	/**
	 * 指定日期的默认显示，具体格式：时：分
	 * 
	 * @param cal
	 *            指定的日期
	 * @return 指定日期按“时：分“格式显示
	 */
	public static String formatShortTime(Calendar cal) {
		return short_time_sdf.format(cal.getTime());
	}

	/**
	 * 指定日期的默认显示，具体格式：时：分
	 * 
	 * @param date
	 *            指定的日期
	 * @return 指定日期按“时：分“格式显示
	 */
	public static String formatShortTime(Date date) {
		return short_time_sdf.format(date);
	}

	// ////////////////////////////////////////////////////////////////////////////
	// parseDate
	// parseCalendar
	// parseTimestamp
	// 将字符串按照一定的格式转化为日期或时间
	// ////////////////////////////////////////////////////////////////////////////

	/**
	 * 根据指定的格式将字符串转换成Date 如输入：2003-11-19 11:20:20将按照这个转成时间
	 * 
	 * @param src
	 *            将要转换的原始字符窜
	 * @param pattern
	 *            转换的匹配格式
	 * @return 如果转换成功则返回转换后的日期
	 * @throws ParseException
	 * @throws AIDateFormatException
	 */
	public static Date parseDate(String src, String pattern)
			throws ParseException {
		return getSDFormat(pattern).parse(src);

	}

	/**
	 * 根据指定的格式将字符串转换成Date 如输入：2003-11-19 11:20:20将按照这个转成时间
	 * 
	 * @param src
	 *            将要转换的原始字符窜
	 * @param pattern
	 *            转换的匹配格式
	 * @return 如果转换成功则返回转换后的日期
	 * @throws ParseException
	 * @throws AIDateFormatException
	 */
	public static Calendar parseCalendar(String src, String pattern)
			throws ParseException {

		Date date = parseDate(src, pattern);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}

	public static String formatAddDate(String src, String pattern, int amount)
			throws ParseException {
		Calendar cal;
		cal = parseCalendar(src, pattern);
		cal.add(Calendar.DATE, amount);
		return formatDate(cal);
	}

	/**
	 * 根据指定的格式将字符串转换成Date 如输入：2003-11-19 11:20:20将按照这个转成时间
	 * 
	 * @param src
	 *            将要转换的原始字符窜
	 * @param pattern
	 *            转换的匹配格式
	 * @return 如果转换成功则返回转换后的时间戳
	 * @throws ParseException
	 * @throws AIDateFormatException
	 */
	public static Timestamp parseTimestamp(String src, String pattern)
			throws ParseException {
		Date date = parseDate(src, pattern);
		return new Timestamp(date.getTime());
	}

	// ////////////////////////////////////////////////////////////////////////////
	// dateDiff
	// 计算两个日期之间的差值
	// ////////////////////////////////////////////////////////////////////////////

	/**
	 * 计算两个时间之间的差值，根据标志的不同而不同
	 * 
	 * @param flag
	 *            计算标志，表示按照年/月/日/时/分/秒等计算
	 * @param calSrc
	 *            减数
	 * @param calDes
	 *            被减数
	 * @return 两个日期之间的差值
	 */
	public static int dateDiff(char flag, Calendar calSrc, Calendar calDes) {

		long millisDiff = getMillis(calSrc) - getMillis(calDes);

		if (flag == 'y') {
			return (calSrc.get(calSrc.YEAR) - calDes.get(calDes.YEAR));
		}

		if (flag == 'd') {
			return (int) (millisDiff / DAY_IN_MILLIS);
		}

		if (flag == 'h') {
			return (int) (millisDiff / HOUR_IN_MILLIS);
		}

		if (flag == 'm') {
			return (int) (millisDiff / MINUTE_IN_MILLIS);
		}

		if (flag == 's') {
			return (int) (millisDiff / SECOND_IN_MILLIS);
		}

		return 0;
	}

	public static int getYear(){
	    GregorianCalendar calendar=new GregorianCalendar();
	    calendar.setTime(getDate());
	    return calendar.get(Calendar.YEAR);
	  }

}
