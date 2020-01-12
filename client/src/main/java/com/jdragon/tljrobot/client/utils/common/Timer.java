package com.jdragon.tljrobot.client.utils.common;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Create by Jdragon on 2020.01.12
 * 计时器类
 */
public class Timer {
	private Calendar calendar = Calendar.getInstance();
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private long startTime =0, endTime =0;
	private long pauseStartTime =0, pauseEndTime =0;
	public double getMillisecond(){
		BigInteger
			timeOne = new BigInteger(String.valueOf(startTime)),
			timeTwo = new BigInteger(String.valueOf(endTime)),
			time;
		time = timeTwo.subtract(timeOne);
		return time.doubleValue();
	}
	public double getSecond(){
		return getMillisecond()/1000.0;
	}
	public double getMinute() {
		return getSecond()/60.0;
	}
	/**
	 * @Author: Jdragon on 2020.01.12 下午 12:10
	 * @param: []
	 * @return: void
	 * @Description 暂停取消后执行该方法，将暂停时间与startTime相加
	 * 		造成计算成绩时开始时间延后。
	 */
	public void subtractStopTime(){
		BigInteger
			timeStopOne = new BigInteger(String.valueOf(pauseStartTime)),
			timeStopTwo = new BigInteger(String.valueOf(pauseEndTime)),
			timeOne = new BigInteger(String.valueOf(startTime));
		//将暂停时间计算出来后加到开始时间上。
		timeOne = timeOne.add(timeStopTwo.subtract(timeStopOne));
		setStartTime(Long.valueOf(String.valueOf(timeOne)));
		setPauseStartTime(0L);
		setEndTime(0L);
	}
	public void timeStart(){
		calendar.setTime(new Date());
		setStartTime(calendar.getTimeInMillis());
	}
	public void timeEnd(){
		calendar.setTime(new Date());
		setEndTime(calendar.getTimeInMillis());
	}
	public void pauseTimeStart(){
		calendar.setTime(new Date());
		setPauseStartTime(calendar.getTimeInMillis());
	}
	public void pauseTimeEnd(){
		calendar.setTime(new Date());
		setPauseEndTime(calendar.getTimeInMillis());
		subtractStopTime();
	}

	/**
	 * @Author: Jdragon on 2020.01.12 下午 6:15
	 * @Description: GetterAndSetter
	 */
	public void setStartTime(long time){
		startTime = time;
	}
	public void setEndTime(long time){
		endTime = time;
	}
	public long getStartTime(){
		return startTime;
	}
	public long getEndTime() {
		return endTime;
	}
	public String getEndTimeStr(){
		return dateFormat.format(endTime);
	}
	public String getStartTimeStr(){
		return dateFormat.format(startTime);
	}
	public void setPauseStartTime(long time){
		pauseStartTime = time;
	}
	public void setPauseEndTime(long time){
		pauseEndTime = time;
	}
}
