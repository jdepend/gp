package com.rofine.gp.platform.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.rofine.gp.platform.exception.GpException;

public class DateUtil {

	public static Date getSysDate() {
		return new Date();
	}

	public static int getSysYear() {
		Calendar a = Calendar.getInstance();
		return a.get(Calendar.YEAR);
	}

	public static Date createDate(String dateStr) throws GpException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return simpleDateFormat.parse(dateStr);
		} catch (ParseException e) {
			throw new GpException(e);
		}
	}
}
