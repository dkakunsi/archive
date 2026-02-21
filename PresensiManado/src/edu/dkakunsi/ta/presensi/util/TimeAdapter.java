package edu.dkakunsi.ta.presensi.util;

import java.sql.Time;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class TimeAdapter extends XmlAdapter<Date, Time> {

	@Override
	public Date marshal(Time time) throws Exception {
		return new Date(time.getTime());
	}

	@Override
	public Time unmarshal(Date date) throws Exception {
		return new Time(date.getTime());
	}

}
